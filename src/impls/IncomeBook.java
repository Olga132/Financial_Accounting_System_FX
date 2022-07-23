package impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Income;

public class IncomeBook{

    private ObservableList<Income> incomeList = FXCollections.observableArrayList();

    public ObservableList<Income> getIncomeList() {
        return incomeList;
    }

    public void add(Income income) {
        incomeList.add(income);
    }

    public void delete(Income income) {
        incomeList.remove(income);

        // account movement
        income.deleteMove((income.getAccount()));
    }

    public double resultSumIncome() {
        double res = 0.0;
        for (Income inc : incomeList) {
            res += inc.getSum();
        }
        return res;
    }

}
