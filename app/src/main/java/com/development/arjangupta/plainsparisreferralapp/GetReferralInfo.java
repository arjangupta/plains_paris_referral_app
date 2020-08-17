package com.development.arjangupta.plainsparisreferralapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetReferralInfo extends AppCompatActivity {

    private String _ngrok_url             = "http://fafd18114419.ngrok.io/sms";
    private String _referree_phone_number = "";
    private OkHttpClient _http_client     = new OkHttpClient();

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

                _referree_phone_number = phone;

                try {
                    // Send the POST message
                    sendPOSTRequestToPlainsParis(_ngrok_url, new  Callback(){

                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            Log.d(SUBMIT_BUTTON_TAG, "Response: " + response.message());

                            if (500 == response.code()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "SMS Failure", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                return;
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"SMS Sent to Plains Paris!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            private static final String SUBMIT_BUTTON_TAG = "Submit Button";

            private String getTextFromEditableField(int id) {
                TextInputLayout input_layout = (TextInputLayout) findViewById(id);
                return input_layout.getEditText().getText().toString();
            }
        });
    }

    void sendPOSTRequestToPlainsParis(String url, Callback callback) throws IOException{
        RequestBody formBody = new FormBody.Builder()
                .add("To", "+18166166041")
                .add("Body", "Hello, Plains Paris. Somebody was referred to you.")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call response = _http_client.newCall(request);
        response.enqueue(callback);
    }
}
