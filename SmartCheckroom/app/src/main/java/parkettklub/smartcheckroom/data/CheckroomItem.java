package parkettklub.smartcheckroom.data;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Badbeloved on 2018. 04. 02..
 */

public class CheckroomItem extends SugarRecord<CheckroomItem> {

    private Boolean dueReserved;
    private String dueBarcodeNumber;
    private Integer dueCoatNumber;
    private Date dueDate;

    public CheckroomItem(){
    }

    public CheckroomItem(Boolean dueReserved, String dueBarcodeNumber, Integer dueCoatNumber, Date dueDate) {
        this.dueReserved = dueReserved;
        this.dueBarcodeNumber = dueBarcodeNumber;
        this.dueCoatNumber = dueCoatNumber;
        this.dueDate = dueDate;
    }

    public Boolean getDueReserved() {
        return dueReserved;
    }

    public void setDueReserved(Boolean dueReserved) {
        this.dueReserved = dueReserved;
    }

    public String getDueBarcodeNumber() {
        return dueBarcodeNumber;
    }

    public void setDueBarcodeNumber(String dueBarcodeNumber) {
        this.dueBarcodeNumber = dueBarcodeNumber;
    }

    public Integer getDueCoatNumber() {
        return dueCoatNumber;
    }

    public void setDueCoatNumber(Integer dueCoatNumber) {
        this.dueCoatNumber = dueCoatNumber;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
