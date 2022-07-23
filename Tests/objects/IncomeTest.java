package objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TypeOfAccount;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class IncomeTest {
    Income income;
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account("Кошель", 100, TypeOfAccount.CASH);
        income = new Income("Зарплата", 100, LocalDate.of(2022, 6, 12), account);
    }

    @Test
    void move() {
        income.move(account);
        assertEquals(200, account.getBalance());
    }

    @Test
    void deleteMove() {
        income.deleteMove(account);
        assertEquals(0, account.getBalance());
    }
}