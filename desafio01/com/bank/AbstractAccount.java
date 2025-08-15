package com.bank;

import java.util.Objects;

public abstract class AbstractAccount implements Account {
    protected final String id;
    protected final String ownerId;
    protected double balance;

    public AbstractAccount(String id, String ownerId, double initialBalance) {
        this.id = Objects.requireNonNull(id);
        this.ownerId = Objects.requireNonNull(ownerId);
        this.balance = initialBalance;
    }

    @Override public String getId() { return id; }
    @Override public String getOwnerId() { return ownerId; }
    @Override public double getBalance() { return balance; }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Depósito deve ser > 0");
        this.balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        // A verificação de saldo suficiente ocorre no aspecto.
        if (amount <= 0) throw new IllegalArgumentException("Saque deve ser > 0");
        this.balance -= amount; // executado apenas se o aspecto permitir
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id='" + id + "', ownerId='" + ownerId + "', balance=" + balance + "}";
    }
}
