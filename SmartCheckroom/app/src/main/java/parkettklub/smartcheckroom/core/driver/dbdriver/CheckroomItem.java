package parkettklub.smartcheckroom.core.driver.dbdriver;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Badbeloved on 2018. 04. 02..
 */

public class CheckroomItem extends SugarRecord<CheckroomItem> {

    private Boolean dueReserved;
    private String dueBarcodeNumber;
    private Integer dueCoatNumber;
    private Integer dueBagNumber;
    private Integer dueShoeNumber;
    private Integer dueOtherNumber;
    private Date dueDate;

    public CheckroomItem(){
    }

    public CheckroomItem(Boolean dueReserved, String dueBarcodeNumber, Integer dueCoatNumber, Integer dueBagNumber, Integer dueShoeNumber, Integer dueOtherNumber, Date dueDate) {
        this.dueReserved = dueReserved;
        this.dueBarcodeNumber = dueBarcodeNumber;
        this.dueCoatNumber = dueCoatNumber;
        this.dueBagNumber = dueBagNumber;
        this.dueShoeNumber = dueShoeNumber;
        this.dueOtherNumber = dueOtherNumber;
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

    public Integer getDueBagNumber() {
        return dueBagNumber;
    }

    public void setDueBagNumber(Integer dueBagNumber) {
        this.dueBagNumber = dueBagNumber;
    }

    public Integer getDueShoeNumber() {
        return dueShoeNumber;
    }

    public void setDueShoeNumber(Integer dueShoeNumber) {
        this.dueShoeNumber = dueShoeNumber;
    }

    public Integer getDueOtherNumber() {
        return dueOtherNumber;
    }

    public void setDueOtherNumber(Integer dueOtherNumber) {
        this.dueOtherNumber = dueOtherNumber;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
