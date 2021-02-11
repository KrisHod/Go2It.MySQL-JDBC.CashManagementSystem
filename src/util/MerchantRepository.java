package util;

import entity.Customer;
import entity.Merchant;
import entity.Payment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MerchantRepository {
    public Merchant getById(int id) {
        Merchant merchant = null;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM merchant WHERE id=" + id)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String bankName = rs.getString("bankName");
                String swift = rs.getString("swift");
                String account = rs.getString("account");
                double charge = rs.getDouble("charge");
                int period = rs.getInt("period");
                double minSum = rs.getDouble("minSum");
                double needToSend = rs.getDouble("needToSend");
                double sent = rs.getDouble("sent");
                LocalDate lastSent = rs.getDate("lastSent") == null ? null : rs.getDate("lastSent").toLocalDate();
                merchant = new Merchant(id, name, bankName, swift, account, charge, period, minSum, needToSend, sent, lastSent);
            }
            return merchant;
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return null;
    }


    public List<Merchant> getAll() {
        List<Merchant> merchants = new ArrayList<>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM merchant")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                merchants.add(getById(id));
            }
            return merchants;
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return null;
    }

    public void update(Payment payment) {
        double needToSend = payment.getMerchant().getNeedToSend() + (payment.getSumPaid() - payment.getMerchant().getCharge()); //pick out to another method???
        PreparedStatement stmt = null;
        try (Connection con = DBUtil.getConnection()) {
            if (needToSend > payment.getMerchant().getMinSum()) {
                stmt = con.prepareStatement("UPDATE merchant SET needToSend = ?, sent = ?, lastSent = ? WHERE id =" + payment.getMerchant().getId());
                stmt.setDouble(1, 0);                                           //reset value of needToSend
                payment.getMerchant().setSent(payment.getMerchant().getSent() + needToSend);   //add this sum(needToSend) to existing amount(sent)
                stmt.setDouble(2, payment.getMerchant().getSent());
                stmt.setDate(3, java.sql.Date.valueOf(payment.getDt().toLocalDate())); //update the date of the last transaction
            } else {
                stmt = con.prepareStatement("UPDATE merchant SET needToSend = ? WHERE id = " + payment.getMerchant().getId());
                stmt.setDouble(1, needToSend);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }
}
