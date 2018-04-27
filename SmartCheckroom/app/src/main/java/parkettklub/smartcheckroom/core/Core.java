package parkettklub.smartcheckroom.core;

import java.util.Date;
import java.util.List;

import parkettklub.smartcheckroom.core.driver.dbdriver.CheckroomItem;
import parkettklub.smartcheckroom.core.driver.dbdriver.DataBaseDriverI;
import parkettklub.smartcheckroom.core.driver.dbdriver.ManageDB;

public class Core {

    private static DataBaseDriverI DATA_BASE_DRIVER = ManageDB.getInstance();

    public static void fillDataBase()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DATA_BASE_DRIVER.fillDataBase();
            }
        }).start();
    }
}
