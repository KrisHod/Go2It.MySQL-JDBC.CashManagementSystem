import entity.Payment;
import reportService.MerchantReportService;
import service.CustomerService;
import service.MerchantService;
import service.PaymentService;
import util.MerchantRepository;
import util.PaymentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        MerchantService merchantService = new MerchantService();
        CustomerService customerService = new CustomerService();
        PaymentService paymentService = new PaymentService();

        MerchantReportService merchantReportService = new MerchantReportService();

        // clause 2
        merchantReportService.showReportById(1);

        // clause 3
        merchantReportService.showSortedDescendingOrder();

        //clause 4 & 5
        Payment payment = new Payment(LocalDateTime.of(2012, 07, 01, 6, 23, 00),
                merchantService.getById(3), customerService.getById(1), "book", 35);
        paymentService.addPayment(payment);

        //clause 7
        System.out.println(customerService.findMostActiveCustomer(LocalDate.of(2012, 07, 01), LocalDate.of(2012, 07, 30)));

    }
}
