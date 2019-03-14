package no.hvl.dat109;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Question extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 9999;
    ImageView cameraBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        Button menuBtn = findViewById(R.id.menuButton);
        menuBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Question.this, MainActivity.class));
            }
        });

        Button newQstBtn = findViewById(R.id.newQst);
        newQstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        cameraBtn = (ImageView) findViewById(R.id.cameraButton);
        /*cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Question.this, Camera.class));
            }
        });*/
    }
    public void OpenCamera(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, CAMERA_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);
        if(requestCode == CAMERA_REQUEST){
            Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
            cameraBtn.setImageBitmap(bitmap);
        }

    }
}
