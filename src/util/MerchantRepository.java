package util;

import entity.Merchant;
import entity.Payment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MerchantRepository {
    PaymentRepository paymentRepository;

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

    public Merchant getMerchant(ResultSet rs, boolean isPaymentKnown) throws SQLException {
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
        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM merchant WHERE id=" + id)) {
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

        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM merchant")) {
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
        try (Connection con = DBUtil.getConnection()) {
            if (payment.getMerchant().getNeedToSend() > payment.getMerchant().getMinSum()) {
                stmt = con.prepareStatement("UPDATE merchant SET needToSend = ?, sent = ?, lastSent = ? WHERE id =" + payment.getMerchant().getId());
                stmt.setDouble(1, 0);                                           //reset value of needToSend
                payment.getMerchant().setSent(payment.getMerchant().getSent() + payment.getMerchant().getNeedToSend());   //add this sum(needToSend) to existing amount(sent)
                stmt.setDouble(2, payment.getMerchant().getSent());
                stmt.setDate(3, java.sql.Date.valueOf(payment.getDt().toLocalDate())); //update the date of the last transaction
            } else {
                stmt = con.prepareStatement("UPDATE merchant SET needToSend = ? WHERE id = " + payment.getMerchant().getId());
                stmt.setDouble(1, payment.getMerchant().getNeedToSend());
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }
}
