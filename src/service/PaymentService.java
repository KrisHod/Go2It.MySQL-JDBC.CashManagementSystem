package service;

import entity.Merchant;
import entity.Payment;
import util.PaymentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private PaymentRepository paymentRepository;
    private MerchantService merchantService;

    public PaymentService() {
    }

    public List<Payment> getByMerchant (Merchant merchant){
        return paymentRepository.getByMerchant(merchant);
    }

    public PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }

    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public MerchantService getMerchantService() {
        return merchantService;
    }

    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    public double getTotalSumPaid(Merchant merchant) { return paymentRepository.getTotalSumPaid(merchant);}

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
