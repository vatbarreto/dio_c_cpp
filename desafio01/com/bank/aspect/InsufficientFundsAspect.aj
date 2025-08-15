package com.bank.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bank.*;

/**
 * Verifica saldo suficiente antes de qualquer saque em qualquer tipo de conta
 * e registra log de erro se insuficiente.
 */
public aspect InsufficientFundsAspect {
    private static final Logger log = LoggerFactory.getLogger(InsufficientFundsAspect.class);

    // Intercepta qualquer execução de withdraw(double) em classes do pacote com.bank..
    pointcut anyWithdraw(AbstractAccount account, double amount):
        execution(void com.bank..*.withdraw(double)) &&
        this(account) && args(amount);

    // around: checa saldo antes de prosseguir
    void around(AbstractAccount account, double amount): anyWithdraw(account, amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Saque deve ser > 0");
        }
        double saldo = account.getBalance();
        if (saldo < amount) {
            String msg = String.format(
                "[ERRO] Saldo insuficiente: conta=%s, owner=%s, saldo=%.2f, requisitado=%.2f",
                account.getId(), account.getOwnerId(), saldo, amount
            );
            log.error(msg);
            throw new InsufficientFundsException("Saldo insuficiente na conta " + account.getId());
        }
        // OK -> prossegue para o método original (que apenas debita)
        proceed(account, amount);
        log.info("[OK] Saque realizado: conta={}, novoSaldo={}", account.getId(), account.getBalance());
    }
}
