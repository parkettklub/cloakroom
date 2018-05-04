package parkettklub.smartcheckroom.core;

import android.widget.Toast;

import com.orm.SugarRecord;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private static Item coreItem = new Item();

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

            coreItem = DATA_BASE_DRIVER.findItemByCheckroomNum(checkroomNumber);

            coatNum = coreItem.getCoatNum();
            bagNum = coreItem.getBagNum();
            shoeNum = coreItem.getShoeNum();
            otherNum = coreItem.getOtherNum();
        }
    }

    public static void handleItem(Long aCheckroomNum, String aTransactionType) {

        final Long checkroomId = aCheckroomNum;
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

            }
        }).start();

    }

    public static List<Transaction> listAllTransactions() {

        return DATA_BASE_DRIVER.listAllTransactions();

    }
}
