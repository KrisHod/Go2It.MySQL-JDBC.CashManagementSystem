package service;

import entities.Payment;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    MerchantService merchantService = new MerchantService();
    CustomerService customerService = new CustomerService();

    public List<String> getData() {
        Connection con = null;
        PreparedStatement stmt = null;
        List<String> data = new ArrayList<>();

        try {
            con = DBUtil.getConnection();
            stmt = con.prepareStatement("SELECT * FROM payment");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(rs.getString("id"));
                data.add(rs.getString("dt"));
                data.add(rs.getString("merchantId"));
                data.add(rs.getString("customerId"));
                data.add(rs.getString("goods"));
                data.add(rs.getString("sumPaid"));
                data.add(rs.getString("chargePaid"));
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
