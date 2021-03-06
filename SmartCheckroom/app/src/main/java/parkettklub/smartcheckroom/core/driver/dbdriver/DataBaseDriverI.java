package parkettklub.smartcheckroom.core.driver.dbdriver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import parkettklub.smartcheckroom.core.Item;
import parkettklub.smartcheckroom.core.Transaction;

public interface DataBaseDriverI {
    public void deleteAllCheckRoomItems();

    public void listAllDayItems();

    public void createNewItem(String CRnum, Integer coatNum);

    public Long findItemByBarcode(String number);

    public ArrayList<Long> getFreeIds();

    public void fillDataBase();

    public Item findItemByCheckroomNum(Long aCheckroomNum);

    public void addItem(Item aItem, Boolean aNewItem);

    public void addNewTransaction(boolean aMyTransaction, String aTransactionType, String aBarcode, Long aCheckroomNum, Integer aAllThings, Date aDate);

    public List<Transaction> listAllTransactions();

    public List<Transaction> listNoneUpdatedTransactions();

    public boolean isReserved(Long aCheckroomNum);

    List<Item> listAllItems();

    List<Transaction> listMyTransactions();
}
