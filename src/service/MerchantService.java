package service;

import entities.Merchant;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MerchantService {

    public List<String> getAll() {
        List<String> data = new ArrayList<>();

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM merchant");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(rs.getString("id"));
                data.add(rs.getString("name"));
                data.add(rs.getString("bankName"));
                data.add(rs.getString("swift"));
                data.add(rs.getString("account"));
                data.add(rs.getString("charge"));
                data.add(rs.getString("period"));
                data.add(rs.getString("minSum"));
                data.add(rs.getString("needToSend"));
                data.add(rs.getString("sent"));
                data.add(rs.getString("lastSent"));
            }
            return data;
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        return null;
    }

    public List<Merchant> createMerchantList() {
        List<Merchant> merchantList = new ArrayList<>();
        List<String> merchantData = getAll();

        for (int i = 0; i < merchantData.size(); i += 11) {
            int id = Integer.parseInt(merchantData.get(i));
            String name = merchantData.get(i + 1);
            String bankName = merchantData.get(i + 2);
            String swift = merchantData.get(i + 3);
            String account = merchantData.get(i + 4);
            double charge = Double.parseDouble(merchantData.get(i + 5));
            int period = Integer.parseInt(merchantData.get(i + 6));
            double minSum = Double.parseDouble(merchantData.get(i + 7));
            double needToSend = merchantData.get(i + 8) == null ? 0 : Double.parseDouble(merchantData.get(i + 8));
            double sent = merchantData.get(i + 9) == null ? 0 : Double.parseDouble(merchantData.get(i + 9));
            LocalDate lastSent = merchantData.get(i + 10) == null ? null : LocalDate.parse(merchantData.get(i + 10));

            merchantList.add(new Merchant(id, name, bankName, swift, account, charge, period, minSum, needToSend, sent, lastSent));
        }
        return merchantList;
    }

    public Merchant getById(int id) {
        List<Merchant> merchantList = createMerchantList();
        for (Merchant mer : merchantList) {
            if (mer.getId() == id) {
                return mer;
            }
        }
        return null;
    }
}
