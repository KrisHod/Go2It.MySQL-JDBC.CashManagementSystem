package service;

import entity.Merchant;
import entity.Payment;
import repository.MerchantRepository;

import java.util.List;

public class MerchantService {
    PaymentService paymentService;
    MerchantRepository merchantRepository;

    public MerchantService() {
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public MerchantRepository getMerchantRepository() {
        return merchantRepository;
    }

    public void setMerchantRepository(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public List<Merchant> getAll() {
        return merchantRepository.getAll();
    }

    public Merchant getById(int id, boolean isPaymentKnown) {
        return merchantRepository.getById(id, isPaymentKnown);
    }

    public double calculateNeedToSend(Payment payment) {
        return payment.getMerchant().getNeedToSend() + (payment.getSumPaid() - payment.getMerchant().getCharge());
    }

}
