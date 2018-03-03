package verizon.a20180302_mounikvelagapudi_nycschools;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Scores extends AppCompatActivity {

    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main2);

        String detailedList = "https://data.cityofnewyork.us/resource/734v-jeq5.json";

        recycle = (RecyclerView) findViewById(R.id.recycler2);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recycle.setLayoutManager(mLayoutManager);

        new WebService(getApplicationContext(), recycle, "GET", detailedList, "please wait for loading", "").execute();

    }

}
