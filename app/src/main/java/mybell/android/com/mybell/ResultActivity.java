package mybell.android.com.mybell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mybell.android.com.mybell.Constants.Constants;
import mybell.android.com.mybell.Objects.IndividualItem;

public class ResultActivity extends AppCompatActivity {

    private List<IndividualItem> individualItems;
    private double sharedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initExtras();
        initializeViews();

    }

    private void initExtras() {

        if (getIntent().getExtras().containsKey(Constants.INDIVIDUAL))
            individualItems = (List<IndividualItem>) getIntent().getSerializableExtra(Constants.INDIVIDUAL);
        else
            sharedValue = getIntent().getDoubleExtra(Constants.SHARED, 0.0);
    }

    private void initializeViews() {
        LinearLayout activity_result = (LinearLayout) findViewById(R.id.activity_result);

        if (individualItems != null)
            for (IndividualItem indItem : individualItems
                    ) {
                activity_result.addView(addListItem(indItem.getTotalDisplay()));
            }
        else {
            activity_result.addView(addListItem(getString(R.string.each_one_should_pay) +" "+ sharedValue));
        }
    }

    private View addListItem(String str) {
        final View listItem = getLayoutInflater().inflate(R.layout.total_list_item, null);

        TextView txt = (TextView) listItem.findViewById(R.id.txt);
        txt.setText(str);

        return listItem;
    }
}
