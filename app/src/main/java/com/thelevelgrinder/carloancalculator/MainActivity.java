package com.thelevelgrinder.carloancalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text
import java.text.NumberFormat; // for currency formatting

// test

public class MainActivity extends AppCompatActivity
{

    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double vehiclePrice = 0.0; // car price
    private double downPayment = 0.0; // down payment

    private double percent = 0.06; // 6 percent apr
    private TextView amountTextView; // shows formatted vehicle amount
    private TextView downPaymentTextView; // show downpayment
    private TextView percentTextView; // shows apr percentage
    private TextView twoTextView; // 2 year loan
    private TextView threeTextView; // 3 " "
    private TextView fourTextView; // 4 " "
    private TextView fiveTextView; // 5 " "

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to programmatically manipulated TextViews
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        downPaymentTextView = (TextView) findViewById(R.id.downPaymentTextView);
        twoTextView = (TextView) findViewById(R.id.twoTextView);
        threeTextView = (TextView) findViewById(R.id.threeTextView);
        fourTextView = (TextView) findViewById(R.id.fourTextView);
        fiveTextView = (TextView) findViewById(R.id.fiveTextView);

        twoTextView.setText(currencyFormat.format(0));
        threeTextView.setText(currencyFormat.format(0));
        fourTextView.setText(currencyFormat.format(0));
        fiveTextView.setText(currencyFormat.format(0));

        // set amountEditText's and downPayment's TextWatcher
        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);
        EditText downPaymentEditText =
                (EditText) findViewById(R.id.downPaymentEditText);
        downPaymentEditText.addTextChangedListener(downPaymentEditTextWatcher);

        // set percentSeekBar's OnSeekBarChangeListener
        SeekBar percentSeekBar =
                (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

    }
    private void calculate() {
        // format percent and display in percentTextView
        percentTextView.setText(percentFormat.format(percent));

        // calculate the final cost from difference of down payment and added APR
        double finalCost = vehiclePrice - downPayment;
        double vehicleCost = (finalCost * percent)+finalCost;

        // display vehicleCost formatted as currency
        twoTextView.setText(currencyFormat.format(vehicleCost/24)); // test before devision
        threeTextView.setText(currencyFormat.format(vehicleCost/36.0));
        fourTextView.setText(currencyFormat.format(vehicleCost/48.0));
        fiveTextView.setText(currencyFormat.format(vehicleCost/60.0));
    }
    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {
                // update percent, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    percent = progress / 100.0; // set percent based on progress
                    calculate(); // calculate and display tip and total
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    // listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
    // called when the user modifies the vehicle cost amount
    @Override
    public void onTextChanged(CharSequence s, int start,
                              int before, int count) {

        try { // get vehicle price amount and display currency formatted value
            vehiclePrice = Double.parseDouble(s.toString()) / 100.0;
            amountTextView.setText(currencyFormat.format(vehiclePrice));
//
        }
        catch (NumberFormatException e) { // if s is empty or non-numeric
            amountTextView.setText("");
            vehiclePrice = 0.0;
//
        }

        calculate(); // update the monthly payment TextViews
    }

    @Override
    public void afterTextChanged(Editable s) { }

    @Override
    public void beforeTextChanged(
            CharSequence s, int start, int count, int after) { }
};
    private final TextWatcher downPaymentEditTextWatcher = new TextWatcher() {
        // called when the user modifies the down payment amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get down payment amount and display currency formatted value

                downPayment = Double.parseDouble(s.toString()) / 100.0;
                downPaymentTextView.setText(currencyFormat.format(downPayment));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric

                downPaymentTextView.setText("");
                downPayment = 0.0;
            }

            calculate(); // update the monthly payment TextViews
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };
}
