package parkettklub.smartcheckroom.core;

import android.widget.Toast;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;

import parkettklub.smartcheckroom.ItemHandlingActivity;
import parkettklub.smartcheckroom.core.driver.dbdriver.CheckroomItem;
import parkettklub.smartcheckroom.core.driver.dbdriver.CheckroomTransaction;
import parkettklub.smartcheckroom.core.driver.dbdriver.DataBaseDriverI;
import parkettklub.smartcheckroom.core.driver.dbdriver.ManageDB;

public class Core {

    private static DataBaseDriverI DATA_BASE_DRIVER = ManageDB.getInstance();

    public static String barcodeNumber;

    public static Integer coatNum;
    public static Integer bagNum;
    public static Integer shoeNum;
    public static Integer otherNum;

    public static Boolean newItem;
    public static ArrayList<Long> numbers;

    public static CheckroomItem item;

    public static void refreshData(Long checkroomNumber) {
        item = CheckroomItem.findById(CheckroomItem.class, checkroomNumber);
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

            item = CheckroomItem.findById(CheckroomItem.class, checkroomNumber);

            coatNum = item.getDueCoatNumber();
            bagNum = item.getDueBagNumber();
            shoeNum = item.getDueShoeNumber();
            otherNum = item.getDueOtherNumber();
        }
    }

    public static void handleItem(Long aCheckroomNum, String aTransactionType) {

        final Long checkroomId = aCheckroomNum;
        final String transactionType = aTransactionType;

        new Thread(new Runnable() {
            @Override
            public void run() {
                item = CheckroomItem.findById(CheckroomItem.class, checkroomId);

                item.setDueReserved(newItem);

                if(newItem)
                {
                    item.setDueBarcodeNumber(barcodeNumber);
                }
                else
                {
                    item.setDueBarcodeNumber("");
                }

                item.setDueCoatNumber(coatNum);
                item.setDueBagNumber(bagNum);
                item.setDueShoeNumber(shoeNum);
                item.setDueOtherNumber(otherNum);
                item.setDueDate(new Date(System.currentTimeMillis()));

                item.save();


                Integer allThings = item.getDueCoatNumber() + item.getDueBagNumber() + item.getDueShoeNumber() + item.getDueOtherNumber();

                CheckroomTransaction newTransaction = new CheckroomTransaction(
                        transactionType, item.getDueBarcodeNumber(), item.getId(), allThings,
                        new Date(System.currentTimeMillis()));

                newTransaction.save();

            }
        }).start();

    }
}
