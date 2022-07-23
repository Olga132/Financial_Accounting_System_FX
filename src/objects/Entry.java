package objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

// entry income, expense and plans
// SimpleProperty - abstract class for auto-updating data in a table when it changes
public abstract class Entry {
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleDoubleProperty sum = new SimpleDoubleProperty();
    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private ObjectProperty<Account> account = new SimpleObjectProperty<>();

    public Entry(){}

    public Entry(Double sum, String name){
        this.sum = new SimpleDoubleProperty(sum);
        this.name = new SimpleStringProperty(name);
    }

    public Entry(String name, double sum, LocalDate date,Account account) {
        this.name = new SimpleStringProperty(name);
        this.sum = new SimpleDoubleProperty(sum);
        this.date = new SimpleObjectProperty<LocalDate>(date);
        this.account = new SimpleObjectProperty<Account>(account);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getSum() {
        return sum.get();
    }

    public SimpleDoubleProperty sumProperty() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum.set(sum);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public Account getAccount() {
        return account.get();
    }

    public ObjectProperty<Account> accountProperty() {
        return account;
    }

    public void setAccount(Account account) {
        this.account.set(account);
    }


    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", sum=" + sum +
                ", date=" + date;
    }
}
