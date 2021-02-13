package service;

import entity.Merchant;
import entity.Payment;
import util.PaymentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private PaymentRepository paymentRepository = new PaymentRepository();
    private MerchantService merchantService = new MerchantService();

    public List<Payment> getByMerchant (Merchant merchant){
        return paymentRepository.getByMerchant(merchant);
    }

    public List<Payment> getByPeriod(LocalDate startDate, LocalDate endDate) {
        List<Payment> payments = new ArrayList<>();
        for (Payment p : paymentRepository.getAll()) {
            if (p.getDt().toLocalDate().isAfter(startDate) && p.getDt().toLocalDate().isBefore(endDate)) {
                payments.add(p);
            }
        }
        return payments;
    }

    public boolean addPayment(Payment payment) {
        payment.getMerchant().setNeedToSend(merchantService.calculateNeedToSend(payment));
        paymentRepository.addPayment(payment);
        return true;
    }
}
