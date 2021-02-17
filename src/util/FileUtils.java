package util;

import entity.Merchant;
import service.MerchantService;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class FileUtils {

    public void writeMerchants(MerchantService merchantService) {
//        List<List<String>> rows = Arrays.asList(
//                Arrays.asList("Jean", "author", "Java"),
//                Arrays.asList("David", "editor", "Python"),
//                Arrays.asList("Scott", "editor", "Node.js")
//        );
        List<Merchant> merchants = merchantService.getAll();

        try (FileWriter csvWriter = new FileWriter("Merchants_2020_03_08.csv");) {
            csvWriter.append("id");
            csvWriter.append(",");
            csvWriter.append("name");
            csvWriter.append(",");
            csvWriter.append("bankName");
            csvWriter.append(",");
            csvWriter.append("swift");
            csvWriter.append(",");
            csvWriter.append("account");
            csvWriter.append(",");
            csvWriter.append("charge");
            csvWriter.append(",");
            csvWriter.append("period");
            csvWriter.append(",");
            csvWriter.append("minSum");
            csvWriter.append(",");
            csvWriter.append("needToSend");
            csvWriter.append(",");
            csvWriter.append("sent");
            csvWriter.append(",");
            csvWriter.append("lastSent");
            csvWriter.append("\n");

            for (Merchant row : merchants) {
                csvWriter.append(String.join(",", String.valueOf(row.getId()), row.getName(), row.getBankName(),
                        row.getSwift(), row.getAccount(), String.valueOf(row.getCharge()), String.valueOf(row.getPeriod()),
                        String.valueOf(row.getMinSum()), String.valueOf(row.getNeedToSend()), String.valueOf(row.getSent()),
                        row.getLastSent() == null ? null : row.getLastSent().format(DateTimeFormatter.ofPattern("dd.MMMM yyyy"))));
                csvWriter.append("\n");
            }

            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
