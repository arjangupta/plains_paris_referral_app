package com.development.arjangupta.plainsparisreferralapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button refer_us_button = (Button) findViewById(R.id.refer_us_button);
        refer_us_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this, GetReferralInfo.class));
            }
        });
    }
}