package repository;

import entity.Merchant;
import entity.Payment;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MerchantRepository {
    private PaymentRepository paymentRepository;

    public PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }

    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public MerchantRepository() {
    }

    public MerchantRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    private Merchant getMerchant(ResultSet rs, boolean isPaymentKnown) throws SQLException {
        int id = rs.getInt("id");
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
        Merchant merchant = new Merchant(id, name, bankName, swift, account, charge, period, minSum, needToSend, sent, lastSent);

        if (!isPaymentKnown) {
            List<Payment> payments = paymentRepository.getByMerchant(merchant);
            merchant.setPayments(payments);
        }
        return merchant;
    }

    public Merchant getById(int id, boolean isPaymentKnown) {
        Merchant merchant = null;
        String sql = "SELECT * FROM merchant WHERE id=" + id;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                merchant = getMerchant(rs, isPaymentKnown);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return merchant;
    }


    public List<Merchant> getAll() {
        List<Merchant> merchants = new ArrayList<>();
        String sql = "SELECT * FROM merchant";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                merchants.add(getMerchant(rs, false));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return merchants;
    }

    public void update(Payment payment) {
        PreparedStatement stmt = null;
        Merchant merchant = payment.getMerchant();
        try (Connection con = DBUtil.getConnection()) {
            String sql;
            if (merchant.getNeedToSend() > merchant.getMinSum()) {
                sql = "UPDATE merchant SET needToSend = ?, sent = ?, lastSent = ? WHERE id =" + merchant.getId();
                stmt = con.prepareStatement(sql);
                stmt.setDouble(1, 0);                      //reset value of needToSend
                merchant.setSent(merchant.getSent() + merchant.getNeedToSend());   //add this sum(needToSend) to existing amount(sent)
                stmt.setDouble(2, merchant.getSent());
                stmt.setDate(3, java.sql.Date.valueOf(payment.getDateTime().toLocalDate())); //update the date of the last transaction
            } else {
                sql = "UPDATE merchant SET needToSend = ? WHERE id = " + merchant.getId();
                stmt = con.prepareStatement(sql);
                stmt.setDouble(1, merchant.getNeedToSend());
            }
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }
}
