package interfaces;

import objects.Account;

public interface MoneyMovement {
    void move(Account account) throws Exception;
    void deleteMove(Account account) throws Exception;

}
