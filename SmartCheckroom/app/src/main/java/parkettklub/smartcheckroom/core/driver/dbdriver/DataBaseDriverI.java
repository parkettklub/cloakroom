package parkettklub.smartcheckroom.core.driver.dbdriver;

import java.util.ArrayList;

public interface DataBaseDriverI {
    public void deleteAllCheckRoomItems();

    public void listAllDayItems();

    public void createNewItem(String CRnum, Integer coatNum);

    public Long findItemByBarcode(String number);

    public ArrayList<Long> getFreeIds();

    public void fillDataBase();
}
