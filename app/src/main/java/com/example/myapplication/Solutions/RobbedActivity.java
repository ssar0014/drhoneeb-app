package com.example.myapplication.Solutions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

public class RobbedActivity extends AppCompatActivity {
    TextView web1, web2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robbed);

        web1 = (TextView) findViewById(R.id.website1Tv);
        web2 = (TextView) findViewById(R.id.website2Tv);

        web1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webUri1 = Uri.parse("https://www.keepingbackyardbees.com/is-it-hive-robbing/");
                Intent web1Intent = new Intent(Intent.ACTION_VIEW, webUri1);
                startActivity(web1Intent);
            }
        });

        web2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webUri2 = Uri.parse("https://simplyhoney.com.au/bee-robbing/");
                Intent web2Intent = new Intent(Intent.ACTION_VIEW, webUri2);
                startActivity(web2Intent);
            }
        });
    }
}
