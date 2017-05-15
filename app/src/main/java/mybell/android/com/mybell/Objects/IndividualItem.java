package mybell.android.com.mybell.Objects;

import java.io.Serializable;

/**
 * Created by suzan on 10/05/17.
 */


public class IndividualItem implements Serializable {
    private String value;
    private String name;
    private String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ":   " + value;
    }

    public String getTotalDisplay() {
        return name + ":  " + total;
    }
}
