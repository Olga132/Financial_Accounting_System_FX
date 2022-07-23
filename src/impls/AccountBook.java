package impls;

import objects.Account;
import utils.TypeOfAccount;

import java.util.List;
import java.util.stream.Collectors;


public class AccountBook {

    public double getSumAllAccount(){
        if (Account.getAccounts().size() > 0) {
            return Account.getAccounts().stream().map(Account::getBalance)
                    .reduce(Double::sum).get();
        }
        return 0.0;
    }

    public double getSumNonCashAllAccount(){
        if(Account.getAccounts().size() > 0) {
            return Account.getAccounts().stream().filter(acc -> acc.getType()
                    .equals(TypeOfAccount.NON_CASH)).map(Account::getBalance)
                    .reduce(Double::sum).get();
        }
        return 0.0;
    }

    public double getSumCashAllAccount(){
        return Account.getAccounts().stream().filter(acc -> acc.getType()
                .equals(TypeOfAccount.CASH)).map(Account::getBalance)
                .reduce(Double::sum).get();
    }

    // print balance allAccounts
    public String showBalanceStartup(){
        StringBuilder res = new StringBuilder();
        String output;
        for ( Account account: Account.getAccounts()) {
            output = String.format("%-19s %-10.2f",account.getName(),account.getBalance());
            res.append(output).append("\n");
        }

        res.append(String.format("%-19s %-10.2f","Итого : ",getSumAllAccount()));

        return res.toString();

    }

    // print balance NON_CASH account
    public String showBalanceNonCashAccount(){
        List<Account> nonCashAcc = Account.getAccounts().stream().filter(acc -> acc.getType()
                .equals(TypeOfAccount.NON_CASH)).collect(Collectors.toList());

        StringBuilder res = new StringBuilder();
        for ( Account account: nonCashAcc) {
            String output = String.format("%-19s %-10.2f",account.getName(),account.getBalance());
            res.append(output).append("\n");
        }

        res.append(String.format("%-19s %-10.2f","Итого : ",getSumNonCashAllAccount()));

        return res.toString();
    }

    // print balance CASH account
    public String showBalanceCashAccount(){
        List<Account> cashAcc = Account.getAccounts().stream().filter(acc -> acc.getType()
                .equals(TypeOfAccount.CASH)).collect(Collectors.toList());

        StringBuilder res = new StringBuilder();
        for ( Account account: cashAcc) {
            String output = String.format("%-19s %-10.2f",account.getName(),account.getBalance());
            res.append(output).append("\n");
        }

        res.append(String.format("%-19s %-10.2f","Итого : ",getSumCashAllAccount()));

        return res.toString();
    }
}
