package repository;

import entity.Customer;
import entity.Payment;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private PaymentRepository paymentRepository;

    public PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }

    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public CustomerRepository() {
    }

    public CustomerRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    private Customer getCustomer(ResultSet rs, boolean isPaymentKnown) throws SQLException {
        int id = rs.getInt("id");
        String customerName = rs.getString("name");
        String address = rs.getString("address");
        String email = rs.getString("email");
        String ccNo = rs.getString("ccNo");
        String ccType = rs.getString("ccType");
        LocalDate maturity = rs.getDate("maturity") == null ? null : rs.getDate("maturity").toLocalDate();
        Customer customer = new Customer(id, customerName, address, email, ccNo, ccType, maturity);
        if (!isPaymentKnown) {
            List<Payment> payments = paymentRepository.getByCustomer(customer);
            customer.setPayments(payments);
        }
        return customer;
    }

    public Customer getById(int id, boolean isPaymentKnown) {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE id=" + id;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customer = getCustomer(rs, isPaymentKnown);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return customer;
    }

    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                customers.add(getCustomer(rs, false));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return customers;
    }
}
