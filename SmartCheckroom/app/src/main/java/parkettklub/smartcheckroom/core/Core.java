package parkettklub.smartcheckroom.core;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
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

    public static final String VERSION = "1.0.0";

    public static String networkState = new String();

    public static String barcodeNumber;

    public static Integer coatNum;
    public static Integer bagNum;
    public static Integer shoeNum;
    public static Integer otherNum;

    public static Boolean newItem;
    public static ArrayList<Long> numbers;

    private static Item coreItem = new Item();

    public static String[] values;
    public static long itemOffset;

    //public static CheckroomItem item;

    public static void refreshData(Long checkroomNumber) {
        //item = CheckroomItem.findById(CheckroomItem.class, checkroomNumber);
    }

    public static void fillDataBase() {
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

        if (checkroomNumber == null) {
            newItem = true;
            numbers = DATA_BASE_DRIVER.getFreeIds();

            //Initializing a new string array with elements
            values = new String[Core.numbers.size()];

            for (int i = 0; i < Core.numbers.size(); i++) {
                values[i] = Core.numbers.get(i).toString();
            }

            coatNum = 0;
            bagNum = 0;
            shoeNum = 0;
            otherNum = 0;
        } else {
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

    public static boolean isReserved(Long aCheckroomNum) {
        Long checkroomId = Long.valueOf(values[aCheckroomNum.intValue()]);

        return DATA_BASE_DRIVER.isReserved(checkroomId);
    }

    public static void reserveItem(Long aCheckroomNum, boolean aIsReserved) {

        final Long checkroomId = Long.valueOf(values[aCheckroomNum.intValue()]);

        final boolean isReserved = aIsReserved;

        //item.setDueReserved(newItem);

        coreItem.setCheckroomNum(checkroomId);

        if (isReserved) {
            coreItem.setBarcode(barcodeNumber);
        } else {
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

    public static Long handleItem(Long aCheckroomNum, final String aTransactionType) {

        final Long checkroomId = Long.valueOf(values[aCheckroomNum.intValue()]);
        final String transactionType = aTransactionType;

        //item.setDueReserved(newItem);

        coreItem.setCheckroomNum(checkroomId);

        if (newItem) {
            coreItem.setBarcode(barcodeNumber);
        } else {
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

                Date newDate = new Date(System.currentTimeMillis());

                DATA_BASE_DRIVER.addNewTransaction(true, transactionType, barcodeNumber, checkroomId,
                        allThings, newDate);

                Request request = new Request();
                request.setItem(coreItem);

                Transaction newTransaction = new Transaction(transactionType, barcodeNumber, checkroomId, allThings, newDate);
                request.setTransaction(newTransaction);
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

    public static List<Transaction> listMyTransactions() {

        return DATA_BASE_DRIVER.listMyTransactions();

    }

    public static List<Integer> listNoneUpdatedTransactions() {

        int guestCntr = 0;

        List<Transaction> transactions = DATA_BASE_DRIVER.listNoneUpdatedTransactions();

        List<Integer> guestNum = new ArrayList<Integer>();

        for (Transaction transaction : transactions) {
            if (transaction.getTransactionType().equals("ADDED")) {
                guestCntr++;
            } else {
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

        for (Transaction transaction : transactions) {
            times.add(transaction.getTransactionTime());
        }

        return times;
    }

    public static boolean freshItem(Item item) {
        DATA_BASE_DRIVER.addItem(item, !item.getBarcode().equals(""));
        return true;
    }

    public static void findServer(final Context context, final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String networkState;
                    if(NETWORK_DRIVER.findServer(context))
                    {
                        networkState = "Client";
                    }
                    else
                    {
                        networkState = "Server";
                    }

                    Message message = Message.obtain(); //get null message
                    Bundle bundle = new Bundle();
                    bundle.putString("NetworkState", networkState);
                    message.setData(bundle);
                    //use the handler to send message
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static List<Request> syncItemDataBase() {

        List<Request> requests = new ArrayList<Request>();

        List<Item> items = DATA_BASE_DRIVER.listAllItems();

        for (Item item : items) {
            Request request = new Request();
            request.setItem(item);

            requests.add(request);
            //NETWORK_DRIVER.sendData(request);
        }
        return requests;
    }

    public static String whoAmI() {
        return NETWORK_DRIVER.whoAmI();
    }

    public static void addTransaction(Transaction transaction) {
        DATA_BASE_DRIVER.addNewTransaction(false, transaction.getTransactionType(),
                transaction.getBarcode(), transaction.getCheckroomNum(), transaction.getStuff(),
                transaction.getTransactionTime());
    }

    public static List<Request> syncTransactionDataBase() {

        List<Request> requests = new ArrayList<Request>();

        List<Transaction> transactions = DATA_BASE_DRIVER.listAllTransactions();

        for (Transaction transaction : transactions) {
            Request request = new Request();
            request.setTransaction(transaction);

            requests.add(request);
            //NETWORK_DRIVER.sendData(request);
        }
        return requests;

    }

    public static void closeNetworkConnections() {
        NETWORK_DRIVER.close();
    }

    public static void showAlertMessage(final Context context , final String aMessage) {


        final Handler handler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                Bundle bb = msg.getData();
                String str = bb.getString("NetworkState");
                Toast.makeText(context, str, Toast.LENGTH_LONG).show();
                //Core.showAlertMessage(context, str);
            }
        };


        AlertDialog.Builder alertbox =
                new AlertDialog.Builder(context);
        alertbox.setMessage(aMessage);
        alertbox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0,
                                        int arg1) {
// Ok kiválasztva
                    }
                });
        if (aMessage.contains("Server")) {
            alertbox.setNegativeButton("Find Server",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0,
                                            int arg1) {

                            Core.closeNetworkConnections();
                            Core.findServer(context, handler);

                            //Toast.makeText(getApplicationContext(), Core.whoAmI(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
        alertbox.show();
    }
}
