package parkettklub.smartcheckroom.core.driver.dbdriver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        for (int i = checkroomItemsInDb.size(); i < 300; i++) {
            CheckroomItem item = new CheckroomItem(false, "", 0, 0, 0, 0,
                    new Date(System.currentTimeMillis()));
            item.save();
        }
    }
}
