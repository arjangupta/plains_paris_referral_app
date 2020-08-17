package com.development.arjangupta.plainsparisreferralapp;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GetReferralInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_referral_info);

        final Button submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Collect the information from the referral info fields
                String name    = getTextFromEditableField(R.id.name_input_layout);
                String phone   = getTextFromEditableField(R.id.phone_number_input_layout);
                String email   = getTextFromEditableField(R.id.email_input_layout);
                String message = getTextFromEditableField(R.id.message_input_layout);

                Log.d(SUBMIT_BUTTON_TAG, "The given name is: " + name);
                Log.d(SUBMIT_BUTTON_TAG, "The given phone number is: " + phone);
                Log.d(SUBMIT_BUTTON_TAG, "The given email is: " + email);
                Log.d(SUBMIT_BUTTON_TAG, "The given message is: " + message);
            }

            private static final String SUBMIT_BUTTON_TAG = "Submit Button";

            private String getTextFromEditableField(int id) {
                TextInputLayout input_layout = (TextInputLayout) findViewById(id);
                return input_layout.getEditText().getText().toString();
            }
        });
    }
}
