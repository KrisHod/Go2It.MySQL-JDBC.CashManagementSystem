package service;

import entity.Payment;
import util.PaymentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    PaymentRepository paymentRepository = new PaymentRepository();

    public boolean getAll() {
        return paymentRepository.getAll().isEmpty();
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
}
