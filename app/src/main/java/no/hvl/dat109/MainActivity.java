package no.hvl.dat109;

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

        // Oppretter en knapp og lager en OnClickListner
        Button startBtn = findViewById(R.id.startButton);
        startBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Åpner en ny side når knappen trykkes
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuestionActivity.class));
            }
        });

        // Oppretter en knapp og lager en OnClickListner
        Button helpBtn = findViewById(R.id.helpButton);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Åpner en ny side når knappen trykkes
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
            }
        });


    }
}
