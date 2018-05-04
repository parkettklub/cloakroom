package parkettklub.smartcheckroom.core;


import java.util.Date;

public class Transaction {

    private String TransactionType;
    private String Barcode;
    private Long CheckroomNum;
    private Integer Stuff;
    private Date TransactionTime;

    public Transaction() {
    }

    public Transaction(String transactionType, String barcode, Long checkroomNum, Integer stuff, Date transactionTime) {
        TransactionType = transactionType;
        Barcode = barcode;
        CheckroomNum = checkroomNum;
        Stuff = stuff;
        TransactionTime = transactionTime;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public Long getCheckroomNum() {
        return CheckroomNum;
    }

    public void setCheckroomNum(Long checkroomNum) {
        CheckroomNum = checkroomNum;
    }

    public Integer getStuff() {
        return Stuff;
    }

    public void setStuff(Integer stuff) {
        Stuff = stuff;
    }

    public Date getTransactionTime() {
        return TransactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        TransactionTime = transactionTime;
    }
}
