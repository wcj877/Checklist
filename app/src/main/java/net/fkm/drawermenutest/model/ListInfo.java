package net.fkm.drawermenutest.model;

public class ListInfo {
    private int listId;
    private String userId;
    private String listTitle;
    private String describe;
    private int listStatus;
    private int priority;
    private int isPerfection;
    private String time;

    public ListInfo() {
    }

    public int getIsPerfection() {
        return isPerfection;
    }

    public void setIsPerfection(int isPerfection) {
        this.isPerfection = isPerfection;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getListStatus() {
        return listStatus;
    }

    public void setListStatus(int listStatus) {
        this.listStatus = listStatus;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ListInfo{" +
                "listId=" + listId +
                ", userId='" + userId + '\'' +
                ", listTitle='" + listTitle + '\'' +
                ", describe='" + describe + '\'' +
                ", listStatus=" + listStatus +
                ", priority=" + priority +
                ", time='" + time + '\'' +
                '}';
    }
}
