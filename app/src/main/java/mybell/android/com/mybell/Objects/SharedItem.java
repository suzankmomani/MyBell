package mybell.android.com.mybell.Objects;

import java.io.Serializable;

/**
 * Created by suzan on 10/05/17.
 */


public class SharedItem implements Serializable {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
