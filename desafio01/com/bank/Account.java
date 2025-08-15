package com.bank;

public interface Account {
    String getId();
    String getOwnerId();
    double getBalance();
    void deposit(double amount);
    void withdraw(double amount); // validação de saldo é transversal (aspecto)
}
