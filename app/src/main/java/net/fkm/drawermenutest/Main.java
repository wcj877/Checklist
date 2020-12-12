package net.fkm.drawermenutest;

import net.fkm.drawermenutest.utils.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBUtils.getConn();
        String sql = "select * from user";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = (Statement) conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
