package impls;

import objects.Account;
import objects.Income;
import objects.PlanExpense;
import objects.PlanIncome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TypeOfAccount;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PlanBookTest {
    PlanBook incomePlan;
    PlanBook expensePlan;

    @BeforeEach
    void setUp() {
        incomePlan = new PlanBook();
        incomePlan.add(new PlanIncome(1000.,"тест доход 1"));
        incomePlan.add(new PlanIncome(1000.,"тест доход 2"));
        incomePlan.add(new PlanIncome(1000.,"тест доход 3"));
        expensePlan = new PlanBook();
        expensePlan.add(new PlanExpense(10.,"тест расход 1"));
        expensePlan.add(new PlanExpense(10.,"тест расход 2"));
        expensePlan.add(new PlanExpense(10.,"тест расход 3"));
    }

    @Test
    void resultSumPlanExpense() {
        assertEquals(30,expensePlan.resultSumPlanExpense());
    }

    @Test
    void resultSumPlanIncome() {
        assertEquals(3000,incomePlan.resultSumPlanIncome());
    }

}