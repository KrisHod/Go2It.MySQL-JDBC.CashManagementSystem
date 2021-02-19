package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private int id;
    private LocalDateTime dateTime;
    private Merchant merchant;
    private Customer customer;
    private String goods;
    private double sumPaid;
    private double chargePaid;

    public Payment(LocalDateTime dateTime, Merchant merchant, Customer customer, String goods, double sumPaid) {
        this.dateTime = dateTime;
        this.goods = goods;
        this.sumPaid = sumPaid;
        this.customer = customer;
        this.merchant = merchant;
    }

    public Payment(int id, LocalDateTime dateTime, Merchant merchant, Customer customer, String goods, double sumPaid, double chargePaid) {
        this(dateTime, merchant, customer, goods, sumPaid);
        this.id = id;
        this.chargePaid = chargePaid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public double getSumPaid() {
        return sumPaid;
    }

    public void setSumPaid(double sumPaid) {
        this.sumPaid = sumPaid;
    }

    public double getChargePaid() {
        return chargePaid;
    }

    public void setChargePaid(double chargePaid) {
        this.chargePaid = chargePaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return getId() == payment.getId() && Double.compare(payment.getSumPaid(), getSumPaid()) == 0 && Double.compare(payment.getChargePaid(), getChargePaid()) == 0 && Objects.equals(getDateTime(), payment.getDateTime()) && Objects.equals(getMerchant(), payment.getMerchant()) && Objects.equals(getCustomer(), payment.getCustomer()) && Objects.equals(getGoods(), payment.getGoods());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateTime(), getMerchant(), getCustomer(), getGoods(), getSumPaid(), getChargePaid());
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", dt=" + dateTime +
                ", merchant=" + merchant +
                ", customer=" + customer +
                ", goods='" + goods + '\'' +
                ", sumPaid=" + sumPaid +
                ", chargePaid=" + chargePaid +
                '}';
    }
}
