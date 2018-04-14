package parkettklub.smartcheckroom.data;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Badbeloved on 2018. 04. 14..
 */

public class CheckroomTransaction extends SugarRecord<CheckroomTransaction> {

    private String dueTransactionType;
    private Long dueCheckroomNumber;
    private Integer dueCoatNumber;
    private Date dueDate;

    public CheckroomTransaction(){
    }

    public CheckroomTransaction(String dueTransactionType, Long dueCheckroomNumber, Integer dueCoatNumber, Date dueDate) {
        this.dueTransactionType = dueTransactionType;
        this.dueCheckroomNumber = dueCheckroomNumber;
        this.dueCoatNumber = dueCoatNumber;
        this.dueDate = dueDate;
    }

    public String getDueTransactionType() {
        return dueTransactionType;
    }

    public void setDueTransactionType(String dueTransactionType) {
        this.dueTransactionType = dueTransactionType;
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
