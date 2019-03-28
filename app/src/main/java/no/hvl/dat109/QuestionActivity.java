package no.hvl.dat109;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 9999;
    private ImageView cameraBtn, imageView, menuBtn, newQstBtn, uploadBtn;
    private TextView mTextViewResult, uploadTxt;
    private RequestQueue mQueue;
    private ArrayList<Question> spmSamling = new ArrayList<>();
    private Bitmap bitmap;
    private String uploadUrl = "http://10.0.0.2:5000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //gir variablene verdien til id'ene og setter på en clicklistener på de
        initControl();

        //Henter spørsmålet
        mQueue = Volley.newRequestQueue(this);
        jsonParse();

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

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }

    /**
     * Definerer variablene til knappeID'ene og setter på en clickListener på de
     */
    private void initControl() {
        mTextViewResult = findViewById(R.id.questionTxt);
        uploadTxt = findViewById(R.id.uploadTxt);
        menuBtn = findViewById(R.id.menuButton);
        newQstBtn = findViewById(R.id.newQst);
        cameraBtn = findViewById(R.id.cameraButton);
        uploadBtn = findViewById(R.id.uploadBtn);
//        menuBtn.setOnClickListener((View.OnClickListener)this);
//        cameraBtn.setOnClickListener((View.OnClickListener)this);
//        newQstBtn.setOnClickListener((View.OnClickListener)this);
//        uploadBtn.setOnClickListener((View.OnClickListener)this);
    }

    /**
     * switch på hvilke knapper som blir trykket på.
     * @param v
     */
    //@Override
//    public void onClick (View v){
//        switch (v.getId()) {
//            case R.id.menuButton:
//                startActivity(new Intent(QuestionActivity.this, MainActivity.class));
//                break;
//            case R.id.cameraButton:
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAMERA_REQUEST);
//                break;
//            case R.id.newQst:
//                Intent intent1 = getIntent();
//                finish();
//                startActivity(intent1);
//                jsonParse();
//                break;
//            case R.id.uploadBtn:
//                uploadImage();
//                break;
//        }
//    }

    /**
     * Bruker url'en til serveren for å laste ned alle objektene og tar vare på det ved hjelp av klassen: "Question".
     * Vi bruker dette til å laste ned spørsmål til appen og oppdatere textviewet for hver oppdatering.
     */
    public void jsonParse() {
        String url = "http://data1.hib.no:9090/YE/spm";
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest response", response.toString());
                        try {

                            //JSONArray jsonArray = response.getJSONArray("");


                            for (int i = 0; i < response.length(); i++) {
                                JSONObject question = response.getJSONObject(i);

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
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST) {
            try {
                bitmap = (Bitmap) (data.getExtras().get("data"));
                //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                uploadBtn.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Laster opp bildet til serveren
     *
     * @see String imageToString()
     */
    public void uploadImage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String response1 = jsonObject.getString("response");
                            Toast.makeText(QuestionActivity.this, response1, Toast.LENGTH_LONG).show();
                            imageView.setImageResource(0);
                            imageView.setVisibility(View.GONE);
                        } catch (JSONException je) {
                            je.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("image", imageToString(bitmap));
                return params;
            }
        };
        MySingleton.getInstance(QuestionActivity.this).addToRequestQueue(stringRequest);
    }

    /**
     * Gjør om bildet til en streng som skal brukes når det lastes opp til nettet.
     *
     * @param bitmap
     * @return
     */
    public String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
