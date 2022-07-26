package impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Account;
import objects.Expense;

import java.time.LocalDate;

public class ExpenseBook{

    private ObservableList<Expense> expenseList = FXCollections.observableArrayList();

    public ObservableList<Expense> getExpenseList() {
        return expenseList;
    }

    public void add(Expense expense) {
        expenseList.add(expense);
    }

    public void delete(Expense expense) {
        expenseList.remove(expense);

        // account movement
        if(expense.getAccount() != null)
            expense.deleteMove((expense.getAccount()));

    }

    public double resultSumExpense() {
        double res = 0.0;
        for (Expense ex : expenseList) {
            res += ex.getSum();
        }
        return res;
    }

}
