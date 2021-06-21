package com.mcsquare.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.mcsquare.Model.ImageModel;
import com.mcsquare.R;
import com.mcsquare.View.Adapter.RecyclerViewAdapter;
import com.mcsquare.ViewModel.ImageViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private int PAGE_NUMBER;

    public void previousPageOnClick(View view)
    {
        findViewById(R.id.nextFloatingButton).setVisibility(View.VISIBLE);

        PAGE_NUMBER--;

        if(PAGE_NUMBER==1)
        {
            findViewById(R.id.previousFloatingButton).setVisibility(View.GONE);
        }

        getImages(PAGE_NUMBER);

    }

    public void nextPageOnClick(View view)
    {
        findViewById(R.id.previousFloatingButton).setVisibility(View.VISIBLE);

        PAGE_NUMBER++;

        getImages(PAGE_NUMBER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PAGE_NUMBER=1;

        getImages(PAGE_NUMBER);

    }

    private void getImages(int page)
    {
        showAlertDialog();

        ImageViewModel imageViewModel=new ImageViewModel(MainActivity.this);

        imageViewModel.getImageURLs("https://api.pexels.com/v1/search?query=wallpapers&per_page=10&size=small&page="+String.valueOf(page)).observe(MainActivity.this, new Observer<List<ImageModel>>() {
            @Override
            public void onChanged(List<ImageModel> imageModels) {

                dismissAlertDialog();

                RecyclerViewAdapter adapter=new RecyclerViewAdapter(MainActivity.this, imageModels);

                ((RecyclerView) findViewById(R.id.recyclerView)).setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));

                ((RecyclerView) findViewById(R.id.recyclerView)).setAdapter(adapter);

            }
        });
    }

    private void showAlertDialog()
    {
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(MainActivity.this);

        ProgressBar progressBar=new ProgressBar(MainActivity.this);

        progressBar.setLayoutParams(new WindowManager.LayoutParams(250, 250));

        progressBar.setPadding(10, 10, 10, 10);

        progressBar.setIndeterminateTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.teal_200)));

        alertDialog=alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setView(progressBar);
        alertDialog.show();

        WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();

        layoutParams.copyFrom(alertDialog.getWindow().getAttributes());

        layoutParams.width=250;
        layoutParams.height=250;

        alertDialog.getWindow().setAttributes(layoutParams);
    }

    private void dismissAlertDialog()
    {
        if(alertDialog!=null && alertDialog.isShowing())
        {
            alertDialog.dismiss();
        }
    }

}