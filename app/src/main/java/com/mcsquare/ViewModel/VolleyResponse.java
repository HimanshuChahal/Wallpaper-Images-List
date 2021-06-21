package com.mcsquare.ViewModel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mcsquare.Model.ImageModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolleyResponse extends AsyncTask<String, Void, MutableLiveData<List<ImageModel>>> {

    Context context;
    public VolleyResponse(Context context)
    {
        this.context=context;
    }

    @Override
    protected MutableLiveData<List<ImageModel>> doInBackground(String... strings) {

        MutableLiveData<List<ImageModel>> mutableLiveData=new MutableLiveData<>();

        StringRequest request=new StringRequest(Request.Method.GET, strings[0], new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<ImageModel> imageModels=new ArrayList<>();

                try
                {
                    JSONObject object=new JSONObject(response);

                    JSONArray photosJSONArray=object.getJSONArray("photos");

                    for(int i=0;i<photosJSONArray.length();i++)
                    {
                        ImageModel imageModel=new ImageModel();

                        imageModel.setImageURL(photosJSONArray.getJSONObject(i).getJSONObject("src").getString("small"));

                        imageModels.add(imageModel);

                    }

                } catch(Exception e)
                {
                    e.printStackTrace();
                }

                mutableLiveData.setValue(imageModels);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> hashMap=new HashMap<>();

                hashMap.put("Authorization", "API_KEY");

                return hashMap;
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);

        return mutableLiveData;
    }

}
