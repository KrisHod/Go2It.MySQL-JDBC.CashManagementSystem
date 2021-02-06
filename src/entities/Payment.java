package entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private int id;
    private LocalDateTime dt;
    private int merchantId;
    private int customerId;
    private String goods;
    private double sumPaid;
    private double chargePaid;

    public Payment(int id, LocalDateTime dt, int merchantId, int customerId, String goods, double sumPaid, double chargePaid) {
        this.id = id;
        this.dt = dt;
        this.merchantId = merchantId;
        this.customerId = customerId;
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

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id && merchantId == payment.merchantId && customerId == payment.customerId && Double.compare(payment.sumPaid, sumPaid) == 0 && Double.compare(payment.chargePaid, chargePaid) == 0 && Objects.equals(dt, payment.dt) && Objects.equals(goods, payment.goods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dt, merchantId, customerId, goods, sumPaid, chargePaid);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", dt=" + dt +
                ", merchantId=" + merchantId +
                ", customerId=" + customerId +
                ", goods='" + goods + '\'' +
                ", sumPaid=" + sumPaid +
                ", chargePaid=" + chargePaid +
                '}';
    }
}
