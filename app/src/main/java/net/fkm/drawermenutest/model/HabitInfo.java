package net.fkm.drawermenutest.model;

public class HabitInfo {
    private String habitId;
    private String listTitle;
    private int habitStatus;
    private int habitCon;
    private int habitTotal;
    private String userId;
    private String habitDate;
    private String describe;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getHabitId() {
        return habitId;
    }

    public void setHabitId(String habitId) {
        this.habitId = habitId;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public int getHabitStatus() {
        return habitStatus;
    }

    public void setHabitStatus(int habitStatus) {
        this.habitStatus = habitStatus;
    }

    public int getHabitCon() {
        return habitCon;
    }

    public void setHabitCon(int habitCon) {
        this.habitCon = habitCon;
    }

    public int getHabitTotal() {
        return habitTotal;
    }

    public void setHabitTotal(int habitTotal) {
        this.habitTotal = habitTotal;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHabitDate() {
        return habitDate;
    }

    public void setHabitDate(String habitDate) {
        this.habitDate = habitDate;
    }
}
