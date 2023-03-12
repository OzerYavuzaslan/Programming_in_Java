package com.emlakcepte.model;

public class Banner {
    private int number;
    private String announcementNo;
    private String cellPhone;
    private String phone;

    public Banner(){

    }

    public Banner(int number, String announcementNo, String cellPhone, String phone) {
        setNumber(number);
        setAnnouncementNo(announcementNo);
        setCellPhone(cellPhone);
        setPhone(phone);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAnnouncementNo() {
        return announcementNo;
    }

    public void setAnnouncementNo(String announcementNo) {
        this.announcementNo = announcementNo;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "number=" + number +
                ", announcementNo='" + announcementNo + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
