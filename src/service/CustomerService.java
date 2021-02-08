package service;

import entities.Customer;
import entities.Merchant;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    public static List<String> getData() {
        Connection con = null;
        PreparedStatement stmt = null;
        List<String> data = new ArrayList<>();

        try {
            con = DBUtil.getConnection();
            stmt = con.prepareStatement("SELECT * FROM customer");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(rs.getString("id"));
                data.add(rs.getString("name"));
                data.add(rs.getString("address"));
                data.add(rs.getString("email"));
                data.add(rs.getString("ccNo"));
                data.add(rs.getString("ccType"));
                data.add(rs.getString("maturity"));
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

    public List<Customer> createCustomerList() {
        List<Customer> customerList = new ArrayList<>();
        List<String> customerData = getData();

        for (int i = 0; i < customerData.size(); i += 7) {
            int id = Integer.parseInt(customerData.get(i));
            String name = customerData.get(i + 1);
            String address = customerData.get(i + 2);
            String email = customerData.get(i + 3);
            String ccNo = customerData.get(i + 4);
            String ccType = customerData.get(i + 5);
            LocalDate maturity = customerData.get(i + 6) == null ? null : LocalDate.parse(customerData.get(i + 6));

            customerList.add(new Customer(id, name, address, email, ccNo, ccType, maturity));
        }
        return customerList;
    }
}
