package com.tool.timesten.data.initiator.framework.adapter.in;

import com.tool.timesten.data.initiator.application.usecase.RunScriptUseCase;
import com.tool.timesten.data.initiator.domain.entity.SqlScriptSupplier;
import com.tool.timesten.data.initiator.domain.entity.upsert.*;
import com.tool.timesten.data.initiator.framework.adapter.config.holder.AccountsUpsertHolder;
import com.tool.timesten.data.initiator.framework.adapter.config.holder.MrxAccountsUpsertHolder;
import com.tool.timesten.data.initiator.framework.adapter.config.holder.PortfoliosUpsertHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class ApplicationRunnerInputAdapter implements ApplicationRunner {
    private final RunScriptUseCase runScriptUseCase;
    private final AccountsUpsertHolder accountsUpsertHolder;
    private final MrxAccountsUpsertHolder mrxAccountsUpsertHolder;
    private final PortfoliosUpsertHolder portfoliosUpsertHolder;

    private final MrxBasketUpsert defaultBasketUpsert;
    private final MrxSlpManagementUpsert defaultSlpManagementUpsert;
    private final MrxSlpPriorityUpsert defaultMrxSlpPriorityUpsert;
    private final OrdersUpsert defaultOrdersUpsert;
    private final MrxSlpInfoUpsert defaultMrxSlpInfoUpsert;
    private final InstrumentsUpsert defaultInstrumentsUpsert;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Application Startup!");

        Set<String> bankBasket = Set.of("TCB", "MBB", "SHB", "HDB", "ACB", "VPB", "STB");
        Set<String> prioritySymbols = Set.of("FLC", "TCB", "SSI");

        String acctno = "0001ductm4";

        List<SqlScriptSupplier> scriptSupplierList = new ArrayList<>();

        //Create account
        final AccountsUpsert accountsUpsert = accountsUpsertHolder.get(acctno);
        scriptSupplierList.add(accountsUpsert);

        //Create mrx_account
        final MrxAccountsUpsert mrxAccountsUpsert = mrxAccountsUpsertHolder.get(acctno);
        scriptSupplierList.add(mrxAccountsUpsert);

        //Create portfolios
        final List<PortfoliosUpsert> portfoliosUpserts = portfoliosUpsertHolder.get(acctno);
        portfoliosUpserts.forEach(scriptSupplierList::add);

        //Create instruments
        final Map<String, InstrumentsUpsert> instrumentsUpsertMap = portfoliosUpserts.stream()
            .map(p -> defaultInstrumentsUpsert
                .withSymbol(p.getSymbol())
                .withCficode("ES")
                .withBoard("HSX")
                .withExchange("HSX")
                .withFullname("duc sieu dep zai")
                .withHalt("N")
                .withPrice_rf(randomPriceRf()))
            .map(instrument ->
                instrument
                    .withPrice_ce(ceFromRf(instrument.getPrice_rf()))
                    .withPrice_fl(flFromRf(instrument.getPrice_rf())))
            .collect(Collectors.toMap(InstrumentsUpsert::getSymbol, i -> i));
        instrumentsUpsertMap
            .values()
            .forEach(scriptSupplierList::add);

        //Create mrx_basket
        //Need to update price manually
        final Map<String, MrxBasketUpsert> mrxBasketUpsertMap = portfoliosUpserts.stream().map(
            portfoliosUpsert -> defaultBasketUpsert
                .withBasketid(mrxAccountsUpsert.getBasketid())
                .withSymbol(portfoliosUpsert.getSymbol())
                .withPrice_margin(instrumentsUpsertMap.get(portfoliosUpsert.getSymbol()).getPrice_rf())
                .withPrice_asset(instrumentsUpsertMap.get(portfoliosUpsert.getSymbol()).getPrice_rf())
        ).collect(Collectors.toMap(MrxBasketUpsert::getSymbol, $ -> $));
        mrxBasketUpsertMap.values().forEach(scriptSupplierList::add);

        //Create mrx_slp system
        final MrxSlpManagementUpsert systemMrxSlpManagement = defaultSlpManagementUpsert
            .withSlpid("DUCTM_SYSTEM")
            .withGranted("9999999999")
            .withInused("0")
            .withParentid("")
            .withType("DUCTM_SYS")
            .withLast_allocation_id("0")
            .withLevel("0")
            .withRemain("0")
            .withIs_slpmass("")
            .withUsed_inday("0");
        //run
        portfoliosUpserts.stream().map(
            portfoliosUpsert -> systemMrxSlpManagement
                .withSymbol(portfoliosUpsert.getSymbol())
        ).forEach(scriptSupplierList::add);

        //Create mrx_slp product
        final MrxSlpManagementUpsert prodcutMrxSlpManagement = defaultSlpManagementUpsert
            .withSlpid("DUCTM_SLP_PRO")
            .withGranted("3000000000")
            .withInused("0")
            .withParentid(systemMrxSlpManagement.getSlpid())
            .withType("DUCTM_MR")
            .withLast_allocation_id("0")
            .withLevel("1")
            .withRemain("0")
            .withIs_slpmass("")
            .withUsed_inday("0");
        //run
        final Map<String, MrxSlpManagementUpsert> productMrxSlpManagementUpsertMap = portfoliosUpserts.stream().map(
            portfoliosUpsert -> prodcutMrxSlpManagement
                .withSymbol(portfoliosUpsert.getSymbol())
        ).collect(Collectors.toMap(MrxSlpManagementUpsert::getSymbol, $ -> $));
        productMrxSlpManagementUpsertMap.values().forEach(scriptSupplierList::add);

        //Create mrx_slp group map
        final Map<String, MrxSlpManagementUpsert> mrxSlpManagementUpsertMap = new HashMap<>();

        //Create mrx_slp group vip1
        final MrxSlpManagementUpsert vipProductSlpManagementUpsert = defaultSlpManagementUpsert
            .withSlpid(mrxAccountsUpsert.getSlpid())
            .withInused("0")
            .withParentid(prodcutMrxSlpManagement.getSlpid())
            .withType(prodcutMrxSlpManagement.getType())
            .withLast_allocation_id("0")
            .withLevel("2")
            .withRemain("0")
            .withIs_slpmass("N")
            .withUsed_inday("0");
        //run
        final Map<String, MrxSlpManagementUpsert> vipMrxSlpManagementUpsertMap = portfoliosUpserts.stream()
            .map(PortfoliosUpsert::getSymbol)
            .filter(bankBasket::contains)
            .map(vipProductSlpManagementUpsert::withSymbol)
            .map(mrxSlpManagementUpsert -> mrxSlpManagementUpsert.withGranted(
                calculateVipSlpGranted(productMrxSlpManagementUpsertMap.get(mrxSlpManagementUpsert.getSymbol()), 0.2)))
            .peek(e -> mrxSlpManagementUpsertMap.put(e.getSymbol(), e)).collect(Collectors.toMap(MrxSlpManagementUpsert::getSymbol, $ -> $));
        vipMrxSlpManagementUpsertMap.values()
            .forEach(scriptSupplierList::add);

        //Create mrx_slp group mass
        final MrxSlpManagementUpsert massProdcutMrxSlpManagement = defaultSlpManagementUpsert
            .withSlpid("DUCTM_SLP_MASS")
            .withInused("0")
            .withParentid(prodcutMrxSlpManagement.getSlpid())
            .withType(prodcutMrxSlpManagement.getType())
            .withLast_allocation_id("0")
            .withLevel("2")
            .withRemain("0")
            .withIs_slpmass("Y")
            .withUsed_inday("0");
        //run
        final Map<String, MrxSlpManagementUpsert> massMrxSlpManagementUpsertMap = portfoliosUpserts.stream().map(
                portfoliosUpsert -> massProdcutMrxSlpManagement
                    .withSymbol(portfoliosUpsert.getSymbol())
                    .withGranted(calculateMassSlpGranted(productMrxSlpManagementUpsertMap.get(portfoliosUpsert.getSymbol()),
                        vipMrxSlpManagementUpsertMap.get(portfoliosUpsert.getSymbol())))
            )
            .peek(e -> mrxSlpManagementUpsertMap.put(e.getSymbol(), e))
            .collect(Collectors.toMap(MrxSlpManagementUpsert::getSymbol, $ -> $));
        massMrxSlpManagementUpsertMap
            .values()
            .forEach(scriptSupplierList::add);

        //Create mrx_slp_priority
        AtomicInteger i = new AtomicInteger(1);
        portfoliosUpserts.stream()
            .map(PortfoliosUpsert::getSymbol)
            .filter(prioritySymbols::contains)
            .map(defaultMrxSlpPriorityUpsert::withSymbol)
            .map(s -> s.withSide("MARK").withPriority(String.valueOf(i.getAndIncrement())))
            .forEach(scriptSupplierList::add);

        portfoliosUpserts.stream()
            .map(PortfoliosUpsert::getSymbol)
            .filter(prioritySymbols::contains)
            .map(defaultMrxSlpPriorityUpsert::withSymbol)
            .map(s -> s.withSide("UNMARK").withPriority(String.valueOf(i.getAndIncrement())))
            .forEach(scriptSupplierList::add);

        //Create orders for mrx_accounts
        AtomicInteger orderId = new AtomicInteger(1);
        AtomicInteger quoteId = new AtomicInteger(1);
        String symbol = "ACB";

        BigDecimal markable = portfoliosUpserts.stream()
            .map(portfoliosUpsert -> calculateMarkable(portfoliosUpsert,
                mrxBasketUpsertMap.get(portfoliosUpsert.getSymbol()),
                vipMrxSlpManagementUpsertMap.getOrDefault(portfoliosUpsert.getSymbol(),
                    massMrxSlpManagementUpsertMap.get(portfoliosUpsert.getSymbol()))))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        log.info("markablemarkable: {}", markable);

        BigDecimal quotePrice = new BigDecimal(instrumentsUpsertMap.get(symbol).getPrice_ce());
        BigDecimal percentage = BigDecimal.valueOf(0.95);
        BigDecimal quoteQty = calculateQuoteQtty(quotePrice, markable, percentage);

        final OrdersUpsert simpleCustomOrder = defaultOrdersUpsert
            .withAcctno(mrxAccountsUpsert.getAcctno())
            .withOrderid(mrxAccountsUpsert.getAcctno() + "order" + StringUtils.leftPad(String.valueOf(orderId.getAndIncrement()), 3, "0"))
            .withQuoteid(mrxAccountsUpsert.getAcctno() + "quote" + StringUtils.leftPad(String.valueOf(quoteId.getAndIncrement()), 3, "0"))
            .withIsdisposal("N")
            .withCustodycd(accountsUpsert.getCustodycd())
            .withSymbol(symbol)
            .withSide("B")
            .withSubside("NB")
            .withStatus("S")
            .withSubstatus("SS")
            .withQuote_price(quotePrice.toString())
            .withQuote_qtty(quoteQty.toString())
            .withRemain_qtty(quoteQty.toString())
            .withSessionex("CNT");
        scriptSupplierList.add(simpleCustomOrder);

        //Update buyingQtty
        final Map<String, OrdersUpsert> orderMap = Map.of(simpleCustomOrder.getSymbol(), simpleCustomOrder);
        portfoliosUpserts.stream()
            .filter(p -> orderMap.containsKey(p.getSymbol()))
            .map(p -> p.withBuyingqtty(orderMap.get(p.getSymbol()).getQuote_qtty()))
            .forEach(scriptSupplierList::add);

        //Create mrx_slp_info
        final MrxSlpInfoUpsert systemSlpInfo = defaultMrxSlpInfoUpsert
            .withSlpid(systemMrxSlpManagement.getSlpid())
            .withType(systemMrxSlpManagement.getType())
            .withLevel(systemMrxSlpManagement.getLevel())
            .withSlpid_mass("");
        scriptSupplierList.add(systemSlpInfo);

        final MrxSlpInfoUpsert productSlpInfo = defaultMrxSlpInfoUpsert
            .withSlpid(prodcutMrxSlpManagement.getSlpid())
            .withType(prodcutMrxSlpManagement.getType())
            .withLevel(prodcutMrxSlpManagement.getLevel())
            .withSlpid_mass("");
        scriptSupplierList.add(productSlpInfo);

        final MrxSlpInfoUpsert massSlpInfo = defaultMrxSlpInfoUpsert
            .withSlpid(massProdcutMrxSlpManagement.getSlpid())
            .withType(massProdcutMrxSlpManagement.getType())
            .withLevel(massProdcutMrxSlpManagement.getLevel())
            .withSlpid_mass(massProdcutMrxSlpManagement.getSlpid());
        scriptSupplierList.add(massSlpInfo);

        final MrxSlpInfoUpsert vipSlpInfo = defaultMrxSlpInfoUpsert
            .withSlpid(vipProductSlpManagementUpsert.getSlpid())
            .withType(vipProductSlpManagementUpsert.getType())
            .withLevel(vipProductSlpManagementUpsert.getLevel())
            .withSlpid_mass(massSlpInfo.getSlpid());
        scriptSupplierList.add(vipSlpInfo);

        scriptSupplierList.forEach(runScriptUseCase::run);
        log.info("DONE !!!!!!!!!!!!");
    }

    private String calculateMassSlpGranted(MrxSlpManagementUpsert productMrxSlpManagementUpsert,
        MrxSlpManagementUpsert vipMrxSlpManagementUpsert) {
        final BigDecimal productGranted = new BigDecimal(productMrxSlpManagementUpsert.getGranted());
        final BigDecimal vipGranted = new BigDecimal(vipMrxSlpManagementUpsert == null ? "0" : vipMrxSlpManagementUpsert.getGranted());
        return productGranted.subtract(vipGranted).toString();
    }

    private String calculateVipSlpGranted(MrxSlpManagementUpsert mrxSlpManagementUpsert, double percentage) {
        return BigDecimal.valueOf(new BigDecimal(mrxSlpManagementUpsert.getGranted()).multiply(BigDecimal.valueOf(percentage)).longValue() / 1000000 * 1000000).toString();
    }

    private BigDecimal calculateQuoteQtty(BigDecimal quotePrice, BigDecimal markable, BigDecimal percentage) {
        return BigDecimal.valueOf(markable.multiply(percentage).divide(quotePrice, MathContext.DECIMAL32).longValue() / 100 * 100);
    }

    private BigDecimal calculateMarkable(PortfoliosUpsert portfoliosUpsert, MrxBasketUpsert mrxBasketUpsert, MrxSlpManagementUpsert mrxSlpManagementUpsert) {
        final BigDecimal trade = new BigDecimal(portfoliosUpsert.getTrade());
        final BigDecimal rtn = new BigDecimal(portfoliosUpsert.getBod_rtn());
        final BigDecimal buying = new BigDecimal(portfoliosUpsert.getBuyingqtty());
        final BigDecimal price = new BigDecimal(mrxBasketUpsert.getPrice_margin());
        final BigDecimal rate = new BigDecimal(mrxBasketUpsert.getRate_margin());
        final BigDecimal granted = new BigDecimal(mrxSlpManagementUpsert.getGranted());
        final BigDecimal inused = new BigDecimal(mrxSlpManagementUpsert.getInused());
        final BigDecimal remain = granted.subtract(inused);

        return BigDecimal.valueOf((trade.add(rtn).add(buying)).multiply(price).multiply(rate).longValue() /100).min(remain);
    }

    private String flFromRf(String price_rf) {
        return String.valueOf(new BigDecimal(price_rf).multiply(BigDecimal.valueOf(0.93)).longValue() / 100 * 100);
    }

    private String ceFromRf(String price_rf) {
        return String.valueOf(new BigDecimal(price_rf).multiply(BigDecimal.valueOf(1.07)).longValue() / 100 * 100);
    }

    private String randomPriceRf() {
        return String.valueOf
            (BigDecimal.valueOf(10000 + Math.random() * 100000)
                .longValue() / 100 * 100);
    }
}
