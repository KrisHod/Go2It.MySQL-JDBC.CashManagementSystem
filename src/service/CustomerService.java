package service;

import entity.Customer;
import entity.Payment;
import util.CustomerRepository;

import java.time.LocalDate;
import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository = new CustomerRepository();
    private PaymentService paymentService = new PaymentService();

    public List<Customer> getAll (){
        return customerRepository.getAll();
    }

    //  Find the most active customer based on the number of order within the passed in time period (ie week, month, quarter, year).
    //  The resulting Customer object should contain the list of all Payments made.

    public Customer findMostActiveCustomer(LocalDate startDate, LocalDate endDate) {
        Customer theMostActiveCustomer = null;
        List<Payment> paymentsByPeriod = paymentService.getByPeriod(startDate, endDate);
        List<Customer> customers = customerRepository.getAll();
        System.out.println(customers);
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


