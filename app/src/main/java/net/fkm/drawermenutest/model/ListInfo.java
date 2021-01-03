package net.fkm.drawermenutest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ListInfo implements Parcelable {
    private int listId;
    private String userId;
    private String listTitle;
    private String describe;
    private int listStatus;
    private int priority;
    private int isPerfection;
    private String time;//闹钟响应时间
    private String date;
    private int isClocked;//闹钟表示字段：该清单是否已经响过铃，默认没有

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(listId);
        dest.writeString(userId);
        dest.writeString(listTitle);
        dest.writeString(describe);
        dest.writeInt(listStatus);
        dest.writeInt(priority);
        dest.writeInt(isPerfection);
        dest.writeString(time);
        dest.writeString(date);
        dest.writeInt(isClocked);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIsClocked() {
        return isClocked;
    }

    public void setIsClocked(int isClocked) {
        this.isClocked = isClocked;
    }

    public static final Parcelable.Creator<ListInfo> CREATOR = new Creator<ListInfo>() {
        @Override
        public ListInfo createFromParcel(Parcel source) {
            ListInfo listInfo = new ListInfo();

            listInfo.listId = source.readInt();
            listInfo.userId = source.readString();
            listInfo.listTitle = source.readString();
            listInfo.describe = source.readString();
            listInfo.listStatus = source.readInt();
            listInfo.priority = source.readInt();
            listInfo.isPerfection = source.readInt();
            listInfo.time = source.readString();
            listInfo.date = source.readString();
            listInfo.isClocked = source.readInt();
            return listInfo;
        }

        @Override
        public ListInfo[] newArray(int size) {
            return new ListInfo[0];
        }
    };
}
