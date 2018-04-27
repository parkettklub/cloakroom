package parkettklub.smartcheckroom.core;

import java.util.Date;
import java.util.List;

import parkettklub.smartcheckroom.data.CheckroomItem;

public class Core {

    void fillDataBase()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<CheckroomItem> checkroomItemsInDb = CheckroomItem.listAll(CheckroomItem.class);

                for (int i = checkroomItemsInDb.size(); i < 300; i++) {
                    CheckroomItem item = new CheckroomItem(false, "", 0, 0, 0, 0,
                            new Date(System.currentTimeMillis()));
                    item.save();
                }

            }
        }).start();
    }
}
