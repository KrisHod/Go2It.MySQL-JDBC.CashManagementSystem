package util;

import entity.Customer;
import entity.Merchant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
