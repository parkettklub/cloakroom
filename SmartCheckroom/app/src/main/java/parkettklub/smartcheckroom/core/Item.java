package parkettklub.smartcheckroom.core;

import java.sql.Time;

public class Item {

    private String Barcode;
    private Long CheckroomNum;
    private Integer CoatNum;
    private Integer BagNum;
    private Integer ShoeNum;
    private Integer OtherNum;
    private Time time;

    public Item() {
    }

    public Item(String barcode, Long checkroomNum, Integer coatNum, Integer bagNum, Integer shoeNum, Integer otherNum, Time time) {
        Barcode = barcode;
        CheckroomNum = checkroomNum;
        CoatNum = coatNum;
        BagNum = bagNum;
        ShoeNum = shoeNum;
        OtherNum = otherNum;
        this.time = time;
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

    public Integer getCoatNum() {
        return CoatNum;
    }

    public void setCoatNum(Integer coatNum) {
        CoatNum = coatNum;
    }

    public Integer getBagNum() {
        return BagNum;
    }

    public void setBagNum(Integer bagNum) {
        BagNum = bagNum;
    }

    public Integer getShoeNum() {
        return ShoeNum;
    }

    public void setShoeNum(Integer shoeNum) {
        ShoeNum = shoeNum;
    }

    public Integer getOtherNum() {
        return OtherNum;
    }

    public void setOtherNum(Integer otherNum) {
        OtherNum = otherNum;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
