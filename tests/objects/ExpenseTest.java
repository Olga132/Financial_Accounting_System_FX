package objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TypeOfAccount;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    Expense expense;
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account("Кошель", 100, TypeOfAccount.CASH);
        expense = new Expense("Молоко", 10, LocalDate.of(2022, 6, 12), account);
    }

    @Test
    void move() {
        expense.move(account);
        assertEquals(90,account.getBalance());

    }

    @Test
    void deleteMove() {
        expense.deleteMove(account);
        assertEquals(110,account.getBalance());
    }
}