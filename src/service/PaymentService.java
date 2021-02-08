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

    public List<Payment> createPaymentList() {
        List<Payment> paymentList = new ArrayList<>();
        List<String> paymentData = getData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < paymentData.size(); i += 7) {
            int id = Integer.parseInt(paymentData.get(i));
            LocalDateTime dt = LocalDateTime.parse(paymentData.get(i + 1), formatter);
            int merchantId = Integer.parseInt(paymentData.get(i + 2));
            int customerId = Integer.parseInt(paymentData.get(i + 3));
            String goods = paymentData.get(i + 4);
            double sumPaid = Double.parseDouble(paymentData.get(i + 5));
            double chargePaid = paymentData.get(i + 6) == null ? 0 : Double.parseDouble(paymentData.get(i + 6));

            paymentList.add(new Payment(id, dt, merchantService.getById(merchantId),
                    customerService.getById(customerId), goods, sumPaid, chargePaid));
        }
        return paymentList;
    }
}
