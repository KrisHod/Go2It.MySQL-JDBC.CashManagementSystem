package repository;

import entity.Customer;
import entity.Merchant;
import entity.Payment;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private CustomerRepository customerRepository;
    private MerchantRepository merchantRepository;

    public PaymentRepository() {
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public MerchantRepository getMerchantRepository() {
        return merchantRepository;
    }

    public void setMerchantRepository(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public PaymentRepository(CustomerRepository customerRepository, MerchantRepository merchantRepository) {
        this.customerRepository = customerRepository;
        this.merchantRepository = merchantRepository;
    }

    public List<Payment> getAll() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payment";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                payments.add(getPayment(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return payments;
    }

    private Payment getPayment(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Timestamp dtStamp = rs.getTimestamp("dt");
        LocalDateTime dt = dtStamp.toLocalDateTime();
        int merchantId = rs.getInt("merchantId");
        int customerId = rs.getInt("customerId");
        String goods = rs.getString("goods");
        double sumPaid = rs.getDouble("sumPaid");
        double chargePaid = rs.getDouble("chargePaid");

        //we need the boolean flag for getById() to indicate whether we've assigned payment list for merchant(or customer) or not
        return new Payment(id, dt, merchantRepository.getById(merchantId, true),
                customerRepository.getById(customerId, true), goods, sumPaid, chargePaid);
    }

    public List<Payment> getByMerchant(Merchant merchant) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payment WHERE merchantId=" + merchant.getId();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                payments.add(getPayment(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return payments;
    }

    public List<Payment> getByCustomer(Customer customer) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payment WHERE customerId=" + customer.getId();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                payments.add(getPayment(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return payments;
    }

    public double getTotalSumPaid(Merchant merchant) {
        double totalSum = 0;
        String sql = "SELECT SUM(sumPaid) FROM payment WHERE merchantId =" + merchant.getId();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                totalSum = rs.getDouble("SUM(sumPaid)");
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return totalSum;
    }

    public void addPayment(Payment payment) {
        String sql = "INSERT INTO payment (dt, merchantId, customerId, goods, sumPaid, chargePaid) values(?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(payment.getDateTime()));
            stmt.setInt(2, payment.getMerchant().getId());
            stmt.setInt(3, payment.getCustomer().getId());
            stmt.setString(4, payment.getGoods());
            stmt.setDouble(5, payment.getSumPaid());
            stmt.setDouble(6, payment.getChargePaid());
            merchantRepository.update(payment);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }
}
