package service;

import entity.Customer;
import entity.Payment;
import util.CustomerRepository;
import util.PaymentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerService {
    CustomerRepository customerRepository = new CustomerRepository();
    PaymentService paymentService = new PaymentService();
    PaymentRepository paymentRepository = new PaymentRepository();

    public boolean getAll() {
        return customerRepository.getAll().isEmpty();
    }

    //  Find the most active customer based on the number of order within the passed in time period (ie week, month, quarter, year).
    //  The resulting Customer object should contain the list of all Payments made.

    public int findIdMostActiveCustomer(LocalDate startDate, LocalDate endDate) {
        List<Payment> paymentsByPeriod = paymentService.getByPeriod(startDate, endDate);
        List<Integer> idCustomersByPeriod = new ArrayList<>();
        for (Payment p : paymentsByPeriod) {
            idCustomersByPeriod.add(p.getCustomer().getId());
        }
        idCustomersByPeriod.sort(Comparator.naturalOrder());

        int previous = idCustomersByPeriod.get(0);
        int popular = idCustomersByPeriod.get(0);
        int count = 1;
        int maxCount = 1;

        for (int i = 1; i < idCustomersByPeriod.size(); i++) {
            if (idCustomersByPeriod.get(i) == previous) {
                count++;
            } else {
                if (count > maxCount) {
                    popular = idCustomersByPeriod.get(i - 1);
                    maxCount = count;
                }
                previous = idCustomersByPeriod.get(i);
                count = 1;
            }
        }
        return count > maxCount ? idCustomersByPeriod.get(idCustomersByPeriod.size() - 1) : popular;
    }

    public void showCustomerPayments(Customer customer) {
        List<Payment> customerPayments = new ArrayList<>();
        List<Payment> payments = paymentRepository.getAll();
        for (Payment p : payments) {
            if (p.getCustomer().equals(customer)) {
                customerPayments.add(p);
            }
        }
        System.out.println("Customer " + customer.getName() + "have made next purchases " + customerPayments);
    }
}


