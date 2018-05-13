package parkettklub.smartcheckroom.core.driver.dbdriver;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Badbeloved on 2018. 04. 14..
 */

public class CheckroomTransaction extends SugarRecord<CheckroomTransaction> {

    private boolean myTransaction;
    private String dueTransactionType;
    private String dueBarcodeNumber;
    private Long dueCheckroomNumber;
    private Integer dueCoatNumber;
    private Date dueDate;

    public CheckroomTransaction(){
    }

    public CheckroomTransaction(boolean myTransaction, String dueTransactionType, String dueBarcodeNumber, Long dueCheckroomNumber, Integer dueCoatNumber, Date dueDate) {
        this.myTransaction = myTransaction;
        this.dueTransactionType = dueTransactionType;
        this.dueBarcodeNumber = dueBarcodeNumber;
        this.dueCheckroomNumber = dueCheckroomNumber;
        this.dueCoatNumber = dueCoatNumber;
        this.dueDate = dueDate;
    }

    public boolean isMyTransaction() {
        return myTransaction;
    }

    public void setMyTransaction(boolean myTransaction) {
        this.myTransaction = myTransaction;
    }

    public String getDueTransactionType() {
        return dueTransactionType;
    }

    public void setDueTransactionType(String dueTransactionType) {
        this.dueTransactionType = dueTransactionType;
    }

    public String getDueBarcodeNumber() {
        return dueBarcodeNumber;
    }

    public void setDueBarcodeNumber(String dueBarcodeNumber) {
        this.dueBarcodeNumber = dueBarcodeNumber;
    }

    public Long getDueCheckroomNumber() {
        return dueCheckroomNumber;
    }

    public void setDueCheckroomNumber(Long dueCheckroomNumber) {
        this.dueCheckroomNumber = dueCheckroomNumber;
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
