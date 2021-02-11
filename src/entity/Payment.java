package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private int id;
    private LocalDateTime dt;
    private Merchant merchant;
    private Customer customer;
    private String goods;
    private double sumPaid;
    private double chargePaid;

    public Payment(int id, LocalDateTime dt, Merchant merchant, Customer customer,
                   String goods, double sumPaid, double chargePaid) {
        this.id = id;
        this.dt = dt;
        this.merchant = merchant;
        this.customer = customer;
        this.goods = goods;
        this.sumPaid = sumPaid;
        this.chargePaid = chargePaid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDt() {
        return dt;
    }

    public void setDt(LocalDateTime dt) {
        this.dt = dt;
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
        return getId() == payment.getId() && Double.compare(payment.getSumPaid(), getSumPaid()) == 0 && Double.compare(payment.getChargePaid(), getChargePaid()) == 0 && Objects.equals(getDt(), payment.getDt()) && Objects.equals(getMerchant(), payment.getMerchant()) && Objects.equals(getCustomer(), payment.getCustomer()) && Objects.equals(getGoods(), payment.getGoods());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDt(), getMerchant(), getCustomer(), getGoods(), getSumPaid(), getChargePaid());
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", dt=" + dt +
                ", merchant=" + merchant +
                ", customer=" + customer +
                ", goods='" + goods + '\'' +
                ", sumPaid=" + sumPaid +
                ", chargePaid=" + chargePaid +
                '}';
    }
}
