package sg.edu.rp.c346.billplease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    // Variable declaration //
    TextView displayTotalBill;
    TextView displaySplitBill;
    TextView displayDiscount;
    EditText nettBill;
    EditText noOfPax;
    EditText discountField;
    CheckBox checkGst;
    CheckBox checkSvcCharge;
    Button calculation;
    Button clearAllFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        displayTotalBill = findViewById(R.id.displayttl);
        displaySplitBill = findViewById(R.id.displaypax);
        displayDiscount = findViewById(R.id.displaydiscount);
        nettBill = findViewById(R.id.nettBill);
        noOfPax = findViewById(R.id.paxNo);
        discountField = findViewById(R.id.discount);
        checkGst = findViewById(R.id.checkGST);
        checkSvcCharge = findViewById(R.id.checkSvc);
        calculation = findViewById(R.id.calculation);
        clearAllFields = findViewById(R.id.clearFields);

        // Bind Listeners //
        // 1 - Clear Fields //
        clearAllFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emptyString = "";
                displayTotalBill.setText(emptyString);
                displaySplitBill.setText(emptyString);
                displayDiscount.setText(emptyString);
                nettBill.setText(emptyString);
                noOfPax.setText(emptyString);
                discountField.setText(emptyString);
                checkSvcCharge.setChecked(false);
                checkGst.setChecked(false);
            }
        });


        // 2 - Calculation //
        calculation.setOnClickListener(new View.OnClickListener() {
            double bill, totalBill, perPax, discount = 0.0;
            int pax = 0;

            @Override
            public void onClick(View v) {

                displayDiscount.setText("");

                // Check all fields and checkboxes //
                if (nettBill.getText().toString().length() > 0 && noOfPax.getText().toString().length() > 0) {
                    bill = Double.parseDouble(nettBill.getText().toString());
                    pax = Integer.parseInt(noOfPax.getText().toString());


                    totalBill = bill;
                    if (checkSvcCharge.isChecked() && checkGst.isChecked()) {
                        totalBill = totalBill * 1.1;
                        totalBill = totalBill * 1.07;
                    } else {
                        if (checkSvcCharge.isChecked()) {
                            totalBill = totalBill * 1.1;
                        }

                        if (checkGst.isChecked()) {
                            totalBill = totalBill * 1.07;
                        }
                    }


                    perPax = 0.0;
                    if (pax > 0) {
                        if (pax > 1) {
                            //More than 1//
                            perPax = totalBill / pax;
                        } else {
                            //Don't split the bill//
                            perPax = totalBill;
                        }
                    }

                    // Set Text //
                    Log.d("App", "Total Bill : $" + perPax + ", Total Pax: " + pax + ".");


                    double discountedBill = 0.0;
                    if (discountField.getText().toString().length() > 0) {
                        discount = Double.parseDouble(discountField.getText().toString());
                        if(discount > -1 && discount < 101){
                            discountedBill = totalBill / 100 * (100 - discount);
                            perPax = discountedBill / pax;
                            Log.d("App", "A: " + discountedBill);
                            displayDiscount.setText("Discounted Bill: $" + String.format("%.2f", discountedBill));
                        }else{
                            //Discount error//
                            Log.d("App","Error with discount action!");
                        }


                    }

                    // Set Text//
                    displayTotalBill.setText("Total bill: $" + String.format("%.2f", totalBill));
                    displaySplitBill.setText("Per Pax: " + String.format("%.2f", perPax));


                } else {
                    displayTotalBill.setText("Enter all necessary fields!");
                }


            }
        });


    }
}
