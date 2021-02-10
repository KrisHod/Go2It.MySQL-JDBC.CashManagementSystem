package util;

import entity.Payment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    MerchantRepository merchantRepository = new MerchantRepository();
    CustomerRepository customerRepository = new CustomerRepository();

    public List<Payment> getAll() {
        List<Payment> payments = new ArrayList<>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM payment")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp dtStamp = rs.getTimestamp("dt");
                LocalDateTime dt = dtStamp.toLocalDateTime();
                int merchantId = rs.getInt("merchantId");
                int customerId = rs.getInt("customerId");
                String goods = rs.getString("goods");
                double sumPaid = rs.getDouble("sumPaid");
                double chargePaid = rs.getDouble("chargePaid");

                payments.add(new Payment(id, dt, merchantRepository.getById(merchantId), customerRepository.getById(customerId), goods, sumPaid, chargePaid));
            }
            return payments;
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return null;
    }

    public double getTotalSumPaid(int merchantId) {
        double totalSum = 0;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT SUM(sumPaid) FROM payments WHERE merchantId =" + merchantId)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                totalSum = rs.getDouble("SUM(sumPaid)");
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return totalSum;
    }
}
