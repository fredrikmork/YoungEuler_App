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

public class Question extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 9999;
    ImageView cameraBtn;
    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        cameraBtn = findViewById(R.id.cameraButton);
        mTextViewResult = findViewById(R.id.questionTxt);
        Button menuBtn = findViewById(R.id.menuButton);
        Button newQstBtn = findViewById(R.id.newQst);
        jsonParse();
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Question.this, MainActivity.class));
            }
        });

        newQstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                jsonParse();
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });


    }
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
                            JSONArray jsonArray = response.getJSONArray("employee");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject question = jsonArray.getJSONObject(i);

                                int id = question.getInt("id");
                                int niva = question.getInt("niva");
                                String spm = question.getString("spm");
                                String svar = question.getString("svar");

                                mTextViewResult.append(spm);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);
        if(requestCode == CAMERA_REQUEST){
            Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
            cameraBtn.setImageBitmap(bitmap);
        }

    }
}
