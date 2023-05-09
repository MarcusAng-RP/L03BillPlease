package sg.edu.rp.c346.id22002222.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText etAmountInput;

    EditText etPaxInput;

    ToggleButton tgSVS;
    ToggleButton tgGST;

    EditText discount;

    RadioGroup rgPayment;
    Button btnSplit;

    Button btnReset;
    TextView tvTotalDisplay;

    TextView tveachPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etAmountInput = findViewById(R.id.etAmountInput);
        etPaxInput = findViewById(R.id.etPax);
        tgSVS = findViewById(R.id.tgSVS);
        tgGST = findViewById(R.id.tgGST);
        discount = findViewById(R.id.etDiscount);
        rgPayment = findViewById(R.id.rgPayment);
        btnSplit = findViewById(R.id.btnSplit);
        btnReset = findViewById(R.id.btnReset);
        tvTotalDisplay = findViewById(R.id.tvTotalDisplay);
        tveachPay = findViewById(R.id.tveachPay);


        btnSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etAmountInput.getText().toString()) || !TextUtils.isEmpty(etPaxInput.getText().toString()) || !TextUtils.isEmpty(discount.getText().toString())) {
                    if (etAmountInput.getText().toString().trim().length() != 0 && etPaxInput.getText().toString().trim().length() != 0) {
                        double originalAmt = Double.parseDouble(etAmountInput.getText().toString());
                        double newAmt = 0.0;
                        if (!tgSVS.isChecked() && !tgGST.isChecked()) {
                            newAmt = originalAmt;
                        } else if (tgSVS.isChecked() && !tgGST.isChecked()) {
                            newAmt = originalAmt * 1.1;
                        } else if (!tgSVS.isChecked() && tgGST.isChecked()) {
                            newAmt = originalAmt * 1.07;
                        } else {
                            newAmt = originalAmt * 1.17;
                        }

                        if (discount.getText().toString().trim().length() != 0) {
                            newAmt *= 1 - Double.parseDouble(discount.getText().toString()) / 100;

                        }

                        String mode = " in cash";
                        if (rgPayment.getCheckedRadioButtonId() == R.id.radioPayNow) {
                            mode = " via PayNow to 91234567";
                        }

                        tvTotalDisplay.setText("Total Bill: $ " + String.format("%.2f", newAmt));
                        int numPerson = Integer.parseInt(etPaxInput.getText().toString());
                        if (numPerson != 1) {
                            tveachPay.setText("Each pays: $" + String.format("%.2f", newAmt / numPerson) + mode);
                        } else {
                            tveachPay.setText("Each pays: $" + newAmt + mode);
                        }

                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Incomplete input",Toast.LENGTH_SHORT).show();

                }
            }
            });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAmountInput.setText("");
                etPaxInput.setText("");
                tgSVS.setChecked(false);
                tgGST.setChecked(false);
                discount.setText("");
                rgPayment.check(R.id.radioCash);
                tvTotalDisplay.setText("");
                tveachPay.setText("");
            }
        });

    }

}