package objects;

import interfaces.MoneyMovement;
import java.time.LocalDate;


public class Income extends Entry implements MoneyMovement{

    public Income() {
    }

    public Income(String name, double sum, LocalDate date, Account account) {
        super(name, sum, date, account);
    }

    // reflection on accounts
    @Override
    public void move(Account account){
        double balanceAcc = account.getBalance();
        account.setBalance(balanceAcc + getSum());
    }

    @Override
    public void deleteMove(Account account){
        double balanceAcc = account.getBalance();
        account.setBalance(balanceAcc - getSum());
    }

}
