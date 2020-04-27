package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

//This class is used to show the health information of the bee.
public class ResultActivity extends AppCompatActivity {

    Button beeHealth;
    ImageView bee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.App);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        //Initializing "Get health for another bee" button.
        beeHealth = (Button) findViewById(R.id.button_anotherBeeHealth);
        bee = (ImageView) findViewById(R.id.image_bee);
        //When clicking on "Get health for another bee" button.
        beeHealth.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                Intent intent_another = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent_another);
            }
        }));
    }
}
