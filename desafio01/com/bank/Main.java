package com.bank.*;
package com.bank.exception.*;

public class Main {
    public static void main(String[] args) {
        Customer c = new Customer("C001", "Ana");

        Account cc   = new CheckingAccount("CC-1", c.getId(), 300.00);
        Account sal  = new SalaryAccount("SL-1", c.getId(), 200.00);
        Account poup = new SavingsAccount("PP-1", c.getId(), 500.00);
        Account inv  = new InvestmentAccount("IV-1", c.getId(), 1000.00);

        c.addAccount(cc);
        c.addAccount(sal);
        c.addAccount(poup);
        c.addAccount(inv);

        BankService bank = new BankService();

        // Exemplo 1: saque válido
        bank.withdraw(cc, 100.0);
        System.out.println("Após saque de 100 na CC -> " + cc);

        // Exemplo 2: tentativa acima do saldo da conta escolhida
        try {
            bank.withdraw(sal, 500.0);
        } catch (InsufficientFundsException ex) {
            System.out.println("Falhou como esperado: " + ex.getMessage());
        }

        // Exemplo 3: saque preferido por ID (também protegido pelo aspecto)
        try {
            bank.withdrawFromPreferred(c, "PP-1", 600.0); // maior que 500 -> falha
        } catch (InsufficientFundsException ex) {
            System.out.println("Falhou como esperado: " + ex.getMessage());
        }

        // Exemplo 4: saque válido na poupança
        bank.withdrawFromPreferred(c, "PP-1", 200.0);
        System.out.println("Após saque de 200 na Poupança -> " + poup);

        // Total disponível (demonstra o agregado do cliente)
        System.out.println("Saldo total do cliente: " + c.getTotalBalance());
    }
}
