package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/30/16.
 */

public class Transaction {
    int id;
    boolean isIncome;
    String date;
    String title;
    int amount;
    String category;

    public Transaction() {
    }

    public Transaction(String date, String title, int amount, String category) {
        this.date = date;
        this.title = title;
        this.amount = amount;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public boolean getIsIncome() { return isIncome; }

    public void setIsIncome(boolean income) { isIncome = income; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toString() {
        return "ID\t" + id +
                "\nIsIncome\t" + isIncome +
                "\nDate\t" + date +
                "\nTitle\t" + title +
                "\nAmount\t" + amount +
                "\nCategory\t" + category;
    }

}
