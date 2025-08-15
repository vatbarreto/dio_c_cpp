package com.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {
    private final String id;
    private final String name;
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    public void addAccount(Account account) { accounts.add(account); }
    public List<Account> getAccounts() { return Collections.unmodifiableList(accounts); }

    public double getTotalBalance() {
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }
}
