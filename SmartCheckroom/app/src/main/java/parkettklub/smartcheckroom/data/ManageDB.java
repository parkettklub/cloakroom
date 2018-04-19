package parkettklub.smartcheckroom.data;

import android.widget.Toast;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Badbeloved on 2018. 04. 02..
 */

public class ManageDB {

    private static ManageDB instance = null;

    private ManageDB () {
    }

    public static ManageDB getInstance() {
        if (instance == null) {
            instance = new ManageDB();
        }
        return instance;
    }

    private int checkroomNumber = 0;


    public void deleteAllCheckRoomItems() {
        CheckroomItem.deleteAll(CheckroomItem.class);
    }

    public void listAllDayItems() {

    }

    /*
    public void createNewDayItem(String CRnum, Integer coatNum) {
        checkroomNumber += 2;
        CheckroomItem item = new CheckroomItem(true, CRnum, coatNum,
                new Date(System.currentTimeMillis()));
        item.save();
    }
    */

    public Long findItem(String number) {

        Long checkroomNumber = Long.valueOf(0);

        boolean itemFound = false;

        List<CheckroomItem> items = CheckroomItem.listAll(CheckroomItem.class);

        for (CheckroomItem item : items) {

            if(item.getDueBarcodeNumber().equals(number))
            {
                itemFound = true;

                checkroomNumber = item.getId();
                /* In the database, so delete it
                item.setDueReserved(false);
                item.setDueBarcodeNumber("0000");

                item.save();
                */
            }
        }

        if(itemFound == false)
        {
            checkroomNumber = null;
            /* create the db item */
            //createNewDayItem(number);
        }

        return checkroomNumber;
    }

    public ArrayList<Long> getFreeIds(int lol) {

        boolean itemFound = false;

        //List<CheckroomItem> items = CheckroomItem.listAll(CheckroomItem.class);


        List<CheckroomItem> reservedItems = CheckroomItem.find(CheckroomItem.class,
                "due_reserved = ?", new String[]{"1"}, null, "id desc", null);

        List<CheckroomItem> items;

        if(reservedItems.size() != 0) {
            items = CheckroomItem.find(CheckroomItem.class,
                    "id > ? and due_reserved = ?", reservedItems.get(0).getId().toString(), "0");
        }
        else
        {
            items = CheckroomItem.find(CheckroomItem.class,
                    "id > ? and due_reserved = ?", "0", "0");
        }

        //int arrayNum = (int) CheckroomItem.count(CheckroomItem.class, "due_reserved = ?", new String[]{"0"});

        int arrayNum = items.size();

        System.out.println( arrayNum );

        ArrayList<Long> ids = new ArrayList<Long>();

        for (CheckroomItem item : items) {

            if(item.getDueReserved() == false && (item.getId()%2) == 1)
            {
                 ids.add(item.getId());
            }
        }

        return ids;
    }

    public int getCheckroomNumber() {
        return checkroomNumber;
    }
}
