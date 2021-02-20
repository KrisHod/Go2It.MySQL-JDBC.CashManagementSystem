package service;

import entity.Customer;
import entity.Payment;
import repository.CustomerRepository;

import java.time.LocalDate;
import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository;
    private PaymentService paymentService;

    public CustomerService() {
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public List<Customer> getAll (){
        return customerRepository.getAll();
    }

    public Customer getById(int id, boolean isPaymentKnown) { return customerRepository.getById(id, isPaymentKnown);}

    //  Find the most active customer based on the number of order within the passed in time period (ie week, month, quarter, year).
    //  The resulting Customer object should contain the list of all Payments made.

    public Customer findMostActiveCustomer(LocalDate startDate, LocalDate endDate) {
        Customer theMostActiveCustomer = null;
        List<Payment> paymentsByPeriod = paymentService.getByPeriod(startDate, endDate);
        List<Customer> customers = getAll();
        for (Payment p : paymentsByPeriod) {
            for (Customer c : customers) {
                if (p.getCustomer().equals(c)) {
                    c.getPayments().add(p);
                }
            }
        }
        int max = customers.get(0).getPayments().size();
        for (Customer c : customers) {
            if (c.getPayments().size()>max){
                max = c.getPayments().size();
                theMostActiveCustomer = c;
            }
        }
       return theMostActiveCustomer;
    }
}


