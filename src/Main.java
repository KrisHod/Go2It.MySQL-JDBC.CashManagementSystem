import entity.Customer;
import entity.Payment;
import reportService.MerchantReportService;
import service.CustomerService;
import service.MerchantService;
import service.PaymentService;
import util.CustomerRepository;
import util.FileUtils;
import util.MerchantRepository;
import util.PaymentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        PaymentRepository paymentRepository = new PaymentRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        MerchantRepository merchantRepository = new MerchantRepository();

        paymentRepository.setCustomerRepository(customerRepository);
        paymentRepository.setMerchantRepository(merchantRepository);

        customerRepository.setPaymentRepository(paymentRepository);

        merchantRepository.setPaymentRepository(paymentRepository);

        // service initialization
        PaymentService paymentService = new PaymentService();
        MerchantService merchantService = new MerchantService();
        CustomerService customerService = new CustomerService();

        paymentService.setPaymentRepository(paymentRepository);
        paymentService.setMerchantService(merchantService);

        merchantService.setPaymentService(paymentService);
        merchantService.setMerchantRepository(merchantRepository);

        customerService.setCustomerRepository(customerRepository);
        customerService.setPaymentService(paymentService);

        MerchantReportService merchantReportService = new MerchantReportService();
        merchantReportService.setMerchantService(merchantService);
        merchantReportService.setPaymentService(paymentService);

        // clause 2
        merchantReportService.showReportById(1);

        // clause 3
        merchantReportService.showSortedDescendingOrder();

        //clause 4 & 5
        Payment payment = new Payment(LocalDateTime.of(2012, 07, 01, 6, 23, 00),
                merchantService.getById(3, true), customerService.getById(1, true), "book", 35);
        paymentService.addPayment(payment);

        //clause 7
        Customer theMostActiveCustomer = customerService.findMostActiveCustomer(LocalDate.of(2012, 07, 01), LocalDate.of(2012, 07, 30));
        System.out.println("Customer " + theMostActiveCustomer.getName() + " is the most active customer in July 2012. His payments are "
                + theMostActiveCustomer.getPayments());

        //clause 8
        FileUtils fileUtils = new FileUtils();
        fileUtils.writeMerchants(merchantService);
    }
}
