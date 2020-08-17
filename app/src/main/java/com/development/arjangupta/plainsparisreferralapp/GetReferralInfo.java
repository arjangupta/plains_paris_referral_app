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

    private static final String TAG          = "GetReferralInfo";
    private final String _ngrok_url          = "http://fafd18114419.ngrok.io/sms";
    private String _all_referral_info        = "";
    private String _referree_phone_number    = "";
    private OkHttpClient _http_client        = new OkHttpClient();

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

                Log.d(TAG, "The given name is: " + name);
                Log.d(TAG, "The given phone number is: " + phone);
                Log.d(TAG, "The given email is: " + email);
                Log.d(TAG, "The given message is: " + message);

                // Build whole referral message
                _all_referral_info = "Hello, Plains Paris, this person was just referred to you:\n"
                        + "Name:" + name + "\n"
                        + "Phone:" + phone + "\n"
                        + "Email:" + email + "\n"
                        + "Message from referrer: " + message;

                _referree_phone_number = phone;

                try {
                    // Send the POST message for the SMS to Plains Paris
                    sendPOSTRequestForPlainsParisText();
                    // Send the POST message for the SMS to the referee
                    sendPOSTRequestToRefree();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            private String getTextFromEditableField(int id) {
                TextInputLayout input_layout = (TextInputLayout) findViewById(id);
                return input_layout.getEditText().getText().toString();
            }
        });
    }

    void sendPOSTRequestForPlainsParisText() throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("To", "+18166166041")
                .add("Body", _all_referral_info)
                .build();
        Request request = new Request.Builder()
                .url(_ngrok_url)
                .post(formBody)
                .build();
        Call response = _http_client.newCall(request);
        response.enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "Response: " + response.message());
                if (500 == response.code()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "SMS Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"SMS Sent to Plains Paris!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    void sendPOSTRequestToRefree() throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("To", _referree_phone_number)
                .add("Body", "You have been referred to Plains Paris! Please download their app here: <insert_link>")
                .build();
        Request request = new Request.Builder()
                .url(_ngrok_url)
                .post(formBody)
                .build();
        Call response = _http_client.newCall(request);
        response.enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "Response: " + response.message());
                if (500 == response.code()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "SMS Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"SMS Sent to Plains Paris!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
