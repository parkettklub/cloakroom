package parkettklub.smartcheckroom.core.driver.networkdriver;

import parkettklub.smartcheckroom.core.Item;
import parkettklub.smartcheckroom.core.Transaction;

public class Request {
    private Item item;
    private Transaction transaction;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
