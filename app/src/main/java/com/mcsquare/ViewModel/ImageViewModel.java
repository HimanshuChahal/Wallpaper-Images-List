package com.mcsquare.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mcsquare.Model.ImageModel;

import java.util.List;

public class ImageViewModel extends ViewModel {

    private VolleyResponse volleyResponse;
    private MutableLiveData<List<ImageModel>> mutableLiveData;

    public ImageViewModel(Context context)
    {
        volleyResponse=new VolleyResponse(context);
    }

    public MutableLiveData<List<ImageModel>> getImageURLs(String url)
    {
        if(mutableLiveData==null)
        {
            mutableLiveData=volleyResponse.doInBackground(url);
        }

        return mutableLiveData;
    }

}
