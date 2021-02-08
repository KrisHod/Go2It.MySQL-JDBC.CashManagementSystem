package ReportService;

import entities.Merchant;
import entities.Payment;
import service.MerchantService;
import service.PaymentService;

import java.util.List;

public class MerchantReportService {
    // Create a specific class and method that will show a total sum paid for a merchant with a given id (argument).
    // The report should also contain merchant_id, title and lastSent info

    PaymentService paymentService = new PaymentService();
    MerchantService merchantService = new MerchantService();

    public double calculateTotalSum(int id) {
        List<Payment> payments = paymentService.createPaymentList();
        double totalSum = 0;

        for (Payment payment : payments) {
            if (payment.getMerchant().getId() == id) {
                totalSum = +payment.getSumPaid();
            }
        }
        return totalSum;
    }

    public void showReport(int id) {
        Merchant merchant = merchantService.getById(id);
        System.out.println("Total sum paid: " + calculateTotalSum(id) + ". Merchant Id: " + id + "," +
                " title: " + merchant.getName() + ", last sent: " + merchant.getLastSent());
    }
}
