package no.hvl.dat109;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ImageView menuBtn = findViewById(R.id.menuButton);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Legger til en knapp nederst til høyre hvor den går tilbake til startsiden, eller menyen.
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, MainActivity.class));
            }
        });

        Button newQstBtn = findViewById(R.id.startButton);
        newQstBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Legger til en knapp nederst til venstre der den går til et spørsmål.
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpActivity.this, QuestionActivity.class));
            }
        });
    }
}
