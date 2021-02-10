package util;

import entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    public Customer getById(int id) {
        Customer customer = null;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM customer WHERE id=" + id)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String customerName = rs.getString("name");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String ccNo = rs.getString("ccNo");
                String ccType = rs.getString("ccType");
                LocalDate maturity = rs.getDate("maturity") == null ? null : rs.getDate("maturity").toLocalDate();
                customer = new Customer(id, customerName, address, email, ccNo, ccType, maturity);
            }
            return customer;
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return null;
    }

    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM customer");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                customers.add(getById(id));
            }
            return customers;
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return null;
    }
}
