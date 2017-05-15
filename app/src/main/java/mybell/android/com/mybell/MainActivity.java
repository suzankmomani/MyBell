package mybell.android.com.mybell;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mybell.android.com.mybell.Constants.Constants;
import mybell.android.com.mybell.Objects.IndividualItem;
import mybell.android.com.mybell.Objects.SharedItem;

import static mybell.android.com.mybell.Utils.UiUtils.hideVirtualKeyboard;

public class MainActivity extends AppCompatActivity {

    private List<SharedItem> sharedValues = new ArrayList<>();
    private List<IndividualItem> individualItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            sharedValues = (List<SharedItem>) savedInstanceState.get(Constants.SHARED);
            individualItems = (List<IndividualItem>) savedInstanceState.get(Constants.INDIVIDUAL);
            fillData();
            return;
        }
        initViews();
    }

    private void fillData() {
        if (individualItems != null)
            for (IndividualItem indItem :
                    individualItems) {
                addItemToIndividualList(indItem);
            }
        if (sharedValues != null)
            for (SharedItem sharedItem :
                    sharedValues) {
                addItemToSharedList(sharedItem);
            }

    }

    private void initViews() {
        initIndividualsViews();
        initSharedItemsViews();

        Button totalBtn = (Button) findViewById(R.id.totalBtn);
        totalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedValues.size() == 0 && individualItems.size() == 0) {
                    Toast.makeText(MainActivity.this, getString(R.string.no_values_selected), Toast.LENGTH_LONG).show();
                    return;
                }

                Double sharedValueForEach = 0.0;
                if (sharedValues.size() > 0) {

                    double total = 0;
                    int i = 1;
                    for (SharedItem sItem : sharedValues) {
                        total += Double.valueOf(sItem.getValue());
                        i++;
                    }

                    if (individualItems.size() != 0)
                        sharedValueForEach = total / individualItems.size();
                    else
                        sharedValueForEach = total;
                }

                for (IndividualItem indItem : individualItems
                        ) {
                    indItem.setTotal((Double.valueOf(indItem.getValue()) + sharedValueForEach) + "");
                }

                launchResultActivity(sharedValueForEach);
            }
        });
    }

    private void launchResultActivity(double sharedValue) {
        Intent intent = new Intent(this, ResultActivity.class);
        if (individualItems.size() == 0) {

            intent.putExtra(Constants.SHARED, sharedValue);
        } else {
            intent.putExtra(Constants.INDIVIDUAL, (Serializable) individualItems);
        }
        startActivity(intent);
    }

    private void initIndividualsViews() {
        ImageView addIndividualImg = (ImageView) findViewById(R.id.addIndividualImg);
        final EditText nameEditTxt = (EditText) findViewById(R.id.nameEditTxt);
        final EditText orderPriceEditTxt = (EditText) findViewById(R.id.orderPriceEditTxt);

        nameEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameEditTxt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        orderPriceEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                orderPriceEditTxt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addIndividualImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameEditTxt.getText().length() == 0 || nameEditTxt.getText().equals(" ")) {
                    nameEditTxt.setError(getString(R.string.add_a_name));
                    return;
                }

                if (orderPriceEditTxt.getText().length() == 0 || orderPriceEditTxt.getText().equals(" ")) {
                    orderPriceEditTxt.setError(getString(R.string.add_a_price));
                    return;
                }

//                double d = 0;
//                try {
//                    d = Double.valueOf(orderPriceEditTxt.getText().toString());
//                } catch (NumberFormatException ex) {
//                    orderPriceEditTxt.setError(getString(R.string.enter_valid_price));
//                    return;
//                }

                IndividualItem individualItem = new IndividualItem();
                individualItem.setName(nameEditTxt.getText().toString());
                individualItem.setValue((orderPriceEditTxt.getText().toString()));
                individualItems.add(individualItem);
                addItemToIndividualList(individualItem);

                nameEditTxt.setText("");
                orderPriceEditTxt.setText("");
                findViewById(R.id.focusLayout).requestFocus();
                hideVirtualKeyboard(MainActivity.this);

            }
        });
    }

    private void addItemToIndividualList(IndividualItem individualItem) {
        LinearLayout individualItemsList = (LinearLayout) findViewById(R.id.individualItemsList);


        individualItemsList.addView(addTextToIndividualList(individualItem));
    }

    private void addItemToSharedList(SharedItem sharedItem) {
        LinearLayout sharedItemsList = (LinearLayout) findViewById(R.id.sharedItemsList);


        sharedItemsList.addView(addTextToSharedList(sharedItem));
    }

    private View addTextToIndividualList(IndividualItem individualItem) {

        final View listItem = getLayoutInflater().inflate(R.layout.list_item, null);

        TextView txt = (TextView) listItem.findViewById(R.id.txt);
        txt.setText(individualItem.toString());

        final ImageView removeImg = (ImageView) listItem.findViewById(R.id.removeImg);
        removeImg.setTag(individualItem);
        removeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IndividualItem individualItem = (IndividualItem) removeImg.getTag();
                individualItems.remove(individualItem);
                listItem.setVisibility(View.GONE);
            }
        });
        return listItem;
    }

    private View addTextToSharedList(SharedItem sharedItem) {

        final View listItem = getLayoutInflater().inflate(R.layout.list_item, null);

        TextView txt = (TextView) listItem.findViewById(R.id.txt);
        txt.setText(sharedItem.toString());

        final ImageView removeImg = (ImageView) listItem.findViewById(R.id.removeImg);
        removeImg.setTag(sharedItem);
        removeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedItem sharedItem = (SharedItem) removeImg.getTag();
                sharedValues.remove(sharedItem);
                listItem.setVisibility(View.GONE);
            }
        });
        return listItem;
    }

    private void initSharedItemsViews() {
        ImageView addSharedImg = (ImageView) findViewById(R.id.addSharedImg);
        final EditText sharedEditTxt = (EditText) findViewById(R.id.sharedEditTxt);

        sharedEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sharedEditTxt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        addSharedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedEditTxt.getText().length() == 0 || sharedEditTxt.getText().equals(" ")) {
                    sharedEditTxt.setError(getString(R.string.add_a_price));
                    return;
                }

                SharedItem sharedItem = new SharedItem();
                sharedItem.setValue(sharedEditTxt.getText().toString());

                sharedValues.add(sharedItem);

                sharedEditTxt.setText("");
                findViewById(R.id.focusLayout).requestFocus();
                hideVirtualKeyboard(MainActivity.this);


                addItemToSharedList(sharedItem);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putSerializable(Constants.SHARED, (Serializable) sharedValues);
        outState.putSerializable(Constants.INDIVIDUAL, (Serializable) individualItems);
    }

}
