package service;

import entities.Merchant;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MerchantService {

    public static List<String> getData() {
        Connection con = null;
        PreparedStatement stmt = null;
        List<String> data = new ArrayList<>();

        try {
            con = DBUtil.getConnection();
            stmt = con.prepareStatement("SELECT * FROM merchant");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(rs.getString("id"));
                data.add(rs.getString("name"));
                data.add(rs.getString("bankName"));
                data.add(rs.getString("swift"));
                data.add(rs.getString("account"));
                data.add(rs.getString("charge"));
                data.add(rs.getString("period"));
                data.add(rs.getString("minSum"));
                data.add(rs.getString("needToSend"));
                data.add(rs.getString("sent"));
                data.add(rs.getString("lastSent"));
            }
            return data;
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        } finally {
            if (con != null) {
                try {
                    stmt.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return null;
    }
}
