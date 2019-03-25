package no.hvl.dat109;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 9999;
    ImageView cameraBtn;
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private ArrayList<Question> spmSamling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        spmSamling = new ArrayList<>();

        mTextViewResult = findViewById(R.id.questionTxt);

        mQueue = Volley.newRequestQueue(this);

        jsonParse();

        // Oppretter en knapp og lager OnClickListner
        ImageView menuBtn = findViewById(R.id.menuButton);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Åpner en ny side når knappen trykkes
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuestionActivity.this, MainActivity.class));
            }
        });

        // Oppretter en knapp og lager en OnClickListner
        ImageView newQstBtn = findViewById(R.id.newQst);
        newQstBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Åpner en ny side når knappen trykkes
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                jsonParse();
            }
        });

        // Oppretter en knapp og lager en OnClickListner
        cameraBtn = findViewById(R.id.cameraButton);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Åpner telefonens innebygde kamera-app når kanppen trykkes
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });


    }

    /**
     * Bruker url'en til serveren for å laste ned alle objektene og tar vare på det ved hjelp av klassen: "Question".
     * Vi bruker dette til å laste ned spørsmål til appen og oppdatere textviewet for hver oppdatering.
     */
    public void jsonParse(){
        String url = "";
        mQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest response", response.toString());
                        try {

                            JSONArray jsonArray = response.getJSONArray("Sporsmaal");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject question = jsonArray.getJSONObject(i);

                                spmSamling.add(new Question(question.getString("svar"), question.getString("spm"), question.getInt("niva"), question.getInt("id")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest response", error.toString());
                    }
                }
        );

        mQueue.add(objectRequest);
    }

    /**
     * Mottar bildet fra kameraet
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);
        if(requestCode == CAMERA_REQUEST){
            Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
            cameraBtn.setImageBitmap(bitmap);
        }

    }
}
