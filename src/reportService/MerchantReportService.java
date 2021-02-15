package reportService;

import entity.Merchant;
import entity.Payment;
import service.MerchantService;
import service.PaymentService;
import util.CustomerRepository;
import util.MerchantRepository;
import util.PaymentRepository;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ReportService {
    MerchantService merchantService = new MerchantService();
    PaymentRepository paymentRepository = new PaymentRepository();


    //The report shows a total sum paid for a merchant with a given id (argument), merchant_id, title and lastSent info
    public void showReportById(int id) {
        Merchant merchant = merchantService.getById(id);
        System.out.println("Total sum paid: " + paymentRepository.getTotalSumPaid(merchant) + ". Merchant Id: " + id + "," +
                " title: " + merchant.getName() + ", last sent: " + merchant.getLastSent());
    }

    //Create an application to display a list of all merchants sorted alphabetically in descending order
    public void showSortedDescendingOrder() {
        Set<String> names = new TreeSet<>();
        List<Merchant> merchants = merchantService.getAll();
        for (Merchant mer : merchants) {
            names.add(mer.getName());
        }
        System.out.println(((TreeSet<String>) names).descendingSet());
    }
}
