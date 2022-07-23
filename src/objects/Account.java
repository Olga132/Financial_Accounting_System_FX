package objects;

import utils.TypeOfAccount;

import java.util.ArrayList;

// debit or credit accounts, wallets
public class Account {
    private String name;
    private double balance;
    TypeOfAccount type;
    private static ArrayList<Account> accounts = new ArrayList<>();


    public Account(String name, double balance, TypeOfAccount type) {
        this.name = name;
        this.balance = balance;
        this.type = type;
        accounts.add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public TypeOfAccount getType() {
        return type;
    }

    public void setType(TypeOfAccount type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }
}
