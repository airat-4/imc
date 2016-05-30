package entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by airat on 27.05.16.
 */
public class Bill {
    private String cotegory;
    private String productName;
    private double price;
    private LocalDate date;
    private String place;

    public String getCotegory() {
        return cotegory;
    }

    public void setCotegory(String cotegory) {
        this.cotegory = cotegory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date != null ? date.toString() : "";
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.date = LocalDate.of(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH));
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        if (Double.compare(bill.price, price) != 0) return false;
        if (cotegory != null ? !cotegory.equals(bill.cotegory) : bill.cotegory != null) return false;
        if (productName != null ? !productName.equals(bill.productName) : bill.productName != null) return false;
        if (date != null ? !date.equals(bill.date) : bill.date != null) return false;
        return !(place != null ? !place.equals(bill.place) : bill.place != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = cotegory != null ? cotegory.hashCode() : 0;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        return result;
    }
}
