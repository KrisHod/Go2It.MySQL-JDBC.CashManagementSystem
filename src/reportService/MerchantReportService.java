package reportService;

import entity.Merchant;
import service.MerchantService;
import service.PaymentService;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MerchantReportService {
    private MerchantService merchantService;
    private PaymentService paymentService;

    public MerchantReportService() {
    }

    public MerchantService getMerchantService() {
        return merchantService;
    }

    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //The report shows a total sum paid for a merchant with a given id (argument), merchant_id, title and lastSent info
    public void showTotalSumPaid(int id) {
        Merchant merchant = merchantService.getById(id, true);
        System.out.println("Total sum paid: " + paymentService.getTotalSumPaid(merchant) + ". Merchant Id: " + id + "," +
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
