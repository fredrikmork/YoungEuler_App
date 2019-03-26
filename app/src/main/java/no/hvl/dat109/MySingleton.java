package no.hvl.dat109;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    /**
     * Create private constructor
     */
    private MySingleton(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }
    /**
     * Create a static method to get instance.
     * @see MySingleton()
     */
    public static synchronized MySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    /**
     *
     * @return RequestQueue
     */
    private RequestQueue getRequestQueue(){
        if(requestQueue ==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     *
     * @param request
     * @param <T>
     */
    public <T> void addToRequestQueue (Request<T> request){
        getRequestQueue().add(request);
    }
}
