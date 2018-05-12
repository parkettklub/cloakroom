package parkettklub.smartcheckroom.core;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import parkettklub.smartcheckroom.ItemHandlingActivity;
import parkettklub.smartcheckroom.core.driver.dbdriver.DataBaseDriverI;
import parkettklub.smartcheckroom.core.driver.dbdriver.ManageDB;
import parkettklub.smartcheckroom.core.driver.networkdriver.NetworkDriver;
import parkettklub.smartcheckroom.core.driver.networkdriver.Request;

public class Core {

    private static final DataBaseDriverI DATA_BASE_DRIVER = ManageDB.getInstance();
    private static final NetworkDriver NETWORK_DRIVER = NetworkDriver.getInstance();

    public static String barcodeNumber;

    public static Integer coatNum;
    public static Integer bagNum;
    public static Integer shoeNum;
    public static Integer otherNum;

    public static Boolean newItem;
    public static ArrayList<Long> numbers;

    private static Item coreItem = new Item();
    public static String[] values;

    //public static CheckroomItem item;

    public static void refreshData(Long checkroomNumber) {
        //item = CheckroomItem.findById(CheckroomItem.class, checkroomNumber);
    }

    public static void fillDataBase()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DATA_BASE_DRIVER.fillDataBase();
            }
        }).start();
    }

    public static Long findItemByBarcode(String aBarcode) {
        return DATA_BASE_DRIVER.findItemByBarcode(aBarcode);
    }

    public static ArrayList<Long> getFreeIds() {
        return DATA_BASE_DRIVER.getFreeIds();
    }

    public static void init() {

        final Long checkroomNumber = DATA_BASE_DRIVER.findItemByBarcode(barcodeNumber);

        if(checkroomNumber == null) {
            newItem = true;
            numbers = DATA_BASE_DRIVER.getFreeIds();

            //Initializing a new string array with elements
            values = new String[Core.numbers.size()];

            for(int i = 0; i < Core.numbers.size(); i++)
            {
                values[i] = Core.numbers.get(i).toString();
            }

            coatNum = 0;
            bagNum = 0;
            shoeNum = 0;
            otherNum = 0;
        }
        else
        {
            newItem = false;

            numbers = new ArrayList<Long>();
            numbers.add(checkroomNumber);

            //Initializing a new string array with elements
            values = new String[]{checkroomNumber.toString()};

            coreItem = DATA_BASE_DRIVER.findItemByCheckroomNum(checkroomNumber);

            coatNum = coreItem.getCoatNum();
            bagNum = coreItem.getBagNum();
            shoeNum = coreItem.getShoeNum();
            otherNum = coreItem.getOtherNum();
        }
    }

    public static boolean isReserved(Long aCheckroomNum)
    {
        Long checkroomId = Long.valueOf(values[aCheckroomNum.intValue()]);

        return DATA_BASE_DRIVER.isReserved(checkroomId);
    }

    public static void reserveItem(Long aCheckroomNum, boolean aIsReserved) {

        final Long checkroomId = Long.valueOf(values[aCheckroomNum.intValue()]);

        final boolean isReserved = aIsReserved;

        //item.setDueReserved(newItem);

        coreItem.setCheckroomNum(checkroomId);

        if(isReserved)
        {
            coreItem.setBarcode(barcodeNumber);
        }
        else
        {
            coreItem.setBarcode("");
        }

        coreItem.setCoatNum(coatNum);
        coreItem.setBagNum(bagNum);
        coreItem.setShoeNum(shoeNum);
        coreItem.setOtherNum(otherNum);
        coreItem.setTime(new Time(System.currentTimeMillis()));

        new Thread(new Runnable() {
            @Override
            public void run() {

                DATA_BASE_DRIVER.addItem(coreItem, isReserved);

                Request request = new Request();
                request.setItem(coreItem);
                NETWORK_DRIVER.sendData(request);

            }
        }).start();

    }

    public static Long handleItem(Long aCheckroomNum, String aTransactionType) {

        final Long checkroomId = Long.valueOf(values[aCheckroomNum.intValue()]);
        final String transactionType = aTransactionType;

        //item.setDueReserved(newItem);

        coreItem.setCheckroomNum(checkroomId);

        if(newItem)
        {
            coreItem.setBarcode(barcodeNumber);
        }
        else
        {
            coreItem.setBarcode("");
        }

        coreItem.setCoatNum(coatNum);
        coreItem.setBagNum(bagNum);
        coreItem.setShoeNum(shoeNum);
        coreItem.setOtherNum(otherNum);
        coreItem.setTime(new Time(System.currentTimeMillis()));

        final Integer allThings = coreItem.getCoatNum() + coreItem.getBagNum() + coreItem.getShoeNum() + coreItem.getOtherNum();

        new Thread(new Runnable() {
            @Override
            public void run() {

                DATA_BASE_DRIVER.addItem(coreItem, newItem);

                DATA_BASE_DRIVER.addNewTransaction(transactionType, barcodeNumber, checkroomId,
                        allThings, new Date(System.currentTimeMillis()));

                Request request = new Request();
                request.setItem(coreItem);
                NETWORK_DRIVER.sendData(request);

            }
        }).start();

        return checkroomId;

    }

    public static List<Item> listAllItems() {

        return DATA_BASE_DRIVER.listAllItems();

    }

    public static List<Transaction> listAllTransactions() {

        return DATA_BASE_DRIVER.listAllTransactions();

    }

    public static List<Integer> listNoneUpdatedTransactions() {

        int guestCntr = 0;

        List<Transaction> transactions = DATA_BASE_DRIVER.listNoneUpdatedTransactions();

        List<Integer> guestNum = new ArrayList<Integer>();

        for(Transaction transaction : transactions)
        {
            if(transaction.getTransactionType().equals("ADDED"))
            {
                guestCntr++;
            }
            else
            {
                guestCntr--;
            }

            guestNum.add(guestCntr);
        }

        return guestNum;
    }

    public static List<Date> listNoneUpdatedTransactionTimes() {

        int guestCntr = 0;

        List<Transaction> transactions = DATA_BASE_DRIVER.listNoneUpdatedTransactions();

        List<Date> times = new ArrayList<Date>();

        for(Transaction transaction : transactions)
        {
            times.add(transaction.getTransactionTime());
        }

        return times;
    }

    public static boolean freshItem(Item item) {
        DATA_BASE_DRIVER.addItem(item,  !item.getBarcode().equals(""));
        return true;
    }

    public static String findServer(Context applicationContext){

        String sNetwork = "Error";

        try {
            if(NETWORK_DRIVER.findServer(applicationContext))
            {
                sNetwork = "Client";
            }
            else
            {
                sNetwork = "Server";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sNetwork;
    }

}
