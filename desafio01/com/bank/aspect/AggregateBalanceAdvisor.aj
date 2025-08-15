package com.example.bank.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.bank.*;

/**
 * Antes de um saque preferido por cliente, valida se o saldo total do cliente
 * cobre o valor. Se não cobrir, bloqueia imediatamente.
 */
public aspect AggregateBalanceAdvisor {
    private static final Logger log = LoggerFactory.getLogger(AggregateBalanceAdvisor.class);

    pointcut withdrawPreferred(Customer c, String accId, double amount):
        execution(void com.example.bank.BankService.withdrawFromPreferred(..)) &&
        args(c, accId, amount);

    before(Customer c, String accId, double amount): withdrawPreferred(c, accId, amount) {
        double total = c.getTotalBalance();
        if (total < amount) {
            String msg = String.format(
                "[ERRO-AGREGADO] Cliente=%s (id=%s) saldoTotal=%.2f < requisitado=%.2f",
                c.getName(), c.getId(), total, amount
            );
            log.error(msg);
            throw new InsufficientFundsException("Saldo total do cliente é insuficiente");
        } else {
            log.info("[CHECK-AGREGADO] Cliente={} saldoTotal={} cobre {} -> prosseguindo",
                     c.getId(), total, amount);
        }
    }
}
