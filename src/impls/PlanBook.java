package impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.PlanExpense;
import objects.PlanIncome;

public class PlanBook {
    private ObservableList<PlanIncome> planIncomes = FXCollections.observableArrayList();
    private ObservableList<PlanExpense> planExpenses = FXCollections.observableArrayList();

    public ObservableList<PlanIncome> getPlanIncomes() {
        return planIncomes;
    }

    public ObservableList<PlanExpense> getPlanExpenses() {
        return planExpenses;
    }

    public void add(PlanExpense expPlan) {
        planExpenses.add(expPlan);
    }

    public void delete(PlanExpense expPlan) {
        planExpenses.remove(expPlan);
    }

    public void add(PlanIncome planIncome) {
        planIncomes.add(planIncome);
    }

    public void delete(PlanIncome planIncome) {
        planIncomes.remove(planIncome);
    }

    public double resultSumPlanExpense() {
        double res = 0.0;
        for (PlanExpense planExpense : planExpenses) {
            res += planExpense.getSum();
        }
        return res;
    }

    public double resultSumPlanIncome() {
        double res = 0.0;
        for (PlanIncome planIncome : planIncomes) {
            res += planIncome.getSum();
        }
        return res;
    }

}
