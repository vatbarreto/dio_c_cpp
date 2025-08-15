package com.bank;

import java.util.Optional;

public class BankService {

    /** Saque direto em uma conta específica (aspecto protege). */
    public void withdraw(Account account, double amount) {
        account.withdraw(amount);
    }

    /** Saque por cliente, escolhendo conta preferida (aspecto opcional pode validar total). */
    public void withdrawFromPreferred(Customer customer, String preferredAccountId, double amount) {
        Optional<Account> acc = customer.getAccounts().stream()
                .filter(a -> a.getId().equals(preferredAccountId))
                .findFirst();
        if (acc.isEmpty()) throw new IllegalArgumentException("Conta preferida não encontrada: " + preferredAccountId);
        acc.get().withdraw(amount);
    }
}
