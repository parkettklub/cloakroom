package parkettklub.smartcheckroom.core.driver.dbdriver;

import android.support.annotation.NonNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import parkettklub.smartcheckroom.core.Item;
import parkettklub.smartcheckroom.core.Transaction;

/**
 * Created by Badbeloved on 2018. 04. 02..
 */

public class ManageDB implements DataBaseDriverI {

    private static ManageDB instance = null;

    public CheckroomItem checkroomItem = null;

    private ManageDB() {

    }

    public static ManageDB getInstance() {
        if (instance == null) {
            instance = new ManageDB();
        }
        return instance;
    }

    public void setCheckroomItem(Long checkroomNumber){
        checkroomItem = CheckroomItem.findById(CheckroomItem.class, checkroomNumber);
    }



    @Override
    public void deleteAllCheckRoomItems() {
        CheckroomItem.deleteAll(CheckroomItem.class);
    }

    @Override
    public void listAllDayItems() {

    }


    @Override
    public void createNewItem(String CRnum, Integer coatNum) {
        CheckroomItem item = new CheckroomItem();
        item.save();
    }


    @Override
    public Long findItemByBarcode(String number) {

        Long checkroomNumber = Long.valueOf(0);

        boolean itemFound = false;

        List<CheckroomItem> items = CheckroomItem.listAll(CheckroomItem.class);

        for (CheckroomItem item : items) {

            if (item.getDueBarcodeNumber().equals(number)) {
                itemFound = true;

                checkroomNumber = item.getId();
                /* In the database, so delete it
                item.setDueReserved(false);
                item.setDueBarcodeNumber("0000");

                item.save();
                */
            }
        }

        if (itemFound == false) {
            checkroomNumber = null;
            /* create the db item */
            //createNewDayItem(number);
        }

        return checkroomNumber;
    }

    @Override
    public ArrayList<Long> getFreeIds() {

        boolean itemFound = false;

        //List<CheckroomItem> items = CheckroomItem.listAll(CheckroomItem.class);


        List<CheckroomItem> reservedItems = CheckroomItem.find(CheckroomItem.class,
                "due_reserved = ?", new String[]{"1"}, null, "id desc", null);

        List<CheckroomItem> items;

        if (reservedItems.size() != 0) {
            items = CheckroomItem.find(CheckroomItem.class,
                    "id > ? and due_reserved = ?", reservedItems.get(0).getId().toString(), "0");
        } else {
            items = CheckroomItem.find(CheckroomItem.class,
                    "id > ? and due_reserved = ?", "0", "0");
        }

        //int arrayNum = (int) CheckroomItem.count(CheckroomItem.class, "due_reserved = ?", new String[]{"0"});

        int arrayNum = items.size();

        System.out.println(arrayNum);

        ArrayList<Long> ids = new ArrayList<Long>();

        for (CheckroomItem item : items) {

            if (item.getDueReserved() == false && (item.getId() % 2) == 1) {
                ids.add(item.getId());
            }
        }

        return ids;
    }


    @Override
    public void fillDataBase() {
        List<CheckroomItem> checkroomItemsInDb = CheckroomItem.listAll(CheckroomItem.class);

        for (int i = checkroomItemsInDb.size(); i < 100; i++) {
            CheckroomItem item = new CheckroomItem(false, "", 0, 0, 0, 0,
                    new Date(System.currentTimeMillis()));
            item.save();
        }
    }

    @Override
    public Item findItemByCheckroomNum(Long aCheckroomNum) {

        Item item = new Item();

        CheckroomItem dbItem = CheckroomItem.findById(CheckroomItem.class, aCheckroomNum);

        item.setCheckroomNum(aCheckroomNum);
        item.setBarcode(dbItem.getDueBarcodeNumber());
        item.setCoatNum(dbItem.getDueCoatNumber());
        item.setBagNum(dbItem.getDueBagNumber());
        item.setShoeNum(dbItem.getDueShoeNumber());
        item.setOtherNum(dbItem.getDueOtherNumber());
        //item.setTime(dbItem.getDueDate().getTime()); FIXME

        return item;
    }

    @Override
    public void addItem(Item aItem, Boolean aNewItem) {
        CheckroomItem dbItem = CheckroomItem.findById(CheckroomItem.class, aItem.getCheckroomNum());

        dbItem.setDueBarcodeNumber(aItem.getBarcode());
        dbItem.setDueReserved(aNewItem);
        dbItem.setDueCoatNumber(aItem.getCoatNum());
        dbItem.setDueBagNumber(aItem.getBagNum());
        dbItem.setDueShoeNumber(aItem.getShoeNum());
        dbItem.setDueOtherNumber(aItem.getOtherNum());
        dbItem.setDueDate(new Date(System.currentTimeMillis()));

        dbItem.save();
    }

    @Override
    public boolean isReserved(Long aCheckroomNum)
    {
        CheckroomItem item = CheckroomItem.findById(CheckroomItem.class, aCheckroomNum);
        return item.getDueReserved();
    }

    @Override
    public List<Item> listAllItems() {
        List<Item> items = new ArrayList<Item>();

        List<CheckroomItem> checkroomItems = CheckroomItem.listAll(CheckroomItem.class);

        //CheckroomTransaction.listAll(CheckroomTransaction.class);

        for(CheckroomItem item : checkroomItems)
        {
            Item nextItem = new Item();

            nextItem.setCheckroomNum(item.getId());
            nextItem.setBarcode(item.getDueBarcodeNumber());
            nextItem.setCoatNum(item.getDueCoatNumber());
            nextItem.setBagNum(item.getDueBagNumber());
            nextItem.setShoeNum(item.getDueShoeNumber());
            nextItem.setOtherNum(item.getDueOtherNumber());

            items.add(nextItem);
        }

        return items;
    }


    @Override
    public void addNewTransaction(String aTransactionType, String aBarcode, Long aCheckroomNum, Integer aAllThings, Date aDate) {

        CheckroomTransaction newTransaction = new CheckroomTransaction(aTransactionType, aBarcode,
                aCheckroomNum, aAllThings, new Date(System.currentTimeMillis()));

        newTransaction.save();

    }

    @Override
    public List<Transaction> listAllTransactions() {

        List<Transaction> transactions = new ArrayList<Transaction>();

        List<CheckroomTransaction> checkroomTransactions = CheckroomTransaction.find(
                CheckroomTransaction.class, null, null, null,
                "id desc", null);

        //CheckroomTransaction.listAll(CheckroomTransaction.class);

        for(int i =0; i < checkroomTransactions.size(); i++)
        {
            Transaction nextTransaction = new Transaction();

            nextTransaction.setTransactionType(checkroomTransactions.get(i).getDueTransactionType());
            nextTransaction.setBarcode(checkroomTransactions.get(i).getDueBarcodeNumber());
            nextTransaction.setCheckroomNum(checkroomTransactions.get(i).getDueCheckroomNumber());
            nextTransaction.setStuff(checkroomTransactions.get(i).getDueCoatNumber());
            nextTransaction.setTransactionTime(checkroomTransactions.get(i).getDueDate());

            transactions.add(nextTransaction);
        }

        return transactions;
    }

    @Override
    public List<Transaction> listNoneUpdatedTransactions() {
        List<Transaction> transactions = new ArrayList<Transaction>();

        List<CheckroomTransaction> checkroomTransactions = CheckroomTransaction.find(
                CheckroomTransaction.class, "due_transaction_type != ?", "UPDATED");

        //CheckroomTransaction.listAll(CheckroomTransaction.class);

        for(int i =0; i < checkroomTransactions.size(); i++)
        {
            Transaction nextTransaction = new Transaction();

            nextTransaction.setTransactionType(checkroomTransactions.get(i).getDueTransactionType());
            nextTransaction.setBarcode(checkroomTransactions.get(i).getDueBarcodeNumber());
            nextTransaction.setCheckroomNum(checkroomTransactions.get(i).getDueCheckroomNumber());
            nextTransaction.setStuff(checkroomTransactions.get(i).getDueCoatNumber());
            nextTransaction.setTransactionTime(checkroomTransactions.get(i).getDueDate());

            transactions.add(nextTransaction);
        }

        return transactions;
    }

}
