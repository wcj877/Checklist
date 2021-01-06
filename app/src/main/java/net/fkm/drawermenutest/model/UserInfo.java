package net.fkm.drawermenutest.model;

import java.util.Date;

public class UserInfo {
    private String userId;
    private String password;
    private int userStutas;
    private int userCon;
    private int userTotal;
    private String userDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserStutas() {
        return userStutas;
    }

    public void setUserStutas(int userStutas) {
        this.userStutas = userStutas;
    }


    public int getUserCon() {
        return userCon;
    }

    public void setUserCon(int userCon) {
        this.userCon = userCon;
    }

    public int getUserTotal() {
        return userTotal;
    }

    public void setUserTotal(int userTotal) {
        this.userTotal = userTotal;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }
}
