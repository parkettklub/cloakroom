package parkettklub.smartcheckroom.data;

import android.widget.Toast;

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

    public void createNewDayItem(String CRnum, Integer coatNum) {
        checkroomNumber += 2;
        CheckroomItem item = new CheckroomItem(true, CRnum, coatNum,
                new Date(System.currentTimeMillis()));
        item.save();
    }

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

    public Long[] getFreeIds(int lol) {

        boolean itemFound = false;

        List<CheckroomItem> items = CheckroomItem.listAll(CheckroomItem.class);

        int arrayNum = (int) CheckroomItem.count(CheckroomItem.class, "due_reserved = ?", new String[]{"0"});

        Long[] ids = new Long[arrayNum];

        int i = 0;

        for (CheckroomItem item : items) {

            if(item.getDueReserved() == false)
            {
                 ids[i] =  item.getId();
                 i++;
            }
        }

        return ids;
    }

    public int getCheckroomNumber() {
        return checkroomNumber;
    }
}
