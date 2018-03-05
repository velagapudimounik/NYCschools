package verizon.a20180302_mounikvelagapudi_nycschools;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.util.ArrayList;

import verizon.a20180302_mounikvelagapudi_nycschools.ModelClasses.School;


public class SchoolsList extends AppCompatActivity implements ServiceListener {
    private RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycle = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recycle.setLayoutManager(mLayoutManager);

        String listOfSchools = "https://data.cityofnewyork.us/resource/97mf-9njv.json";

        new WebService(getApplicationContext(), recycle, this, "GET", listOfSchools, "please wait for loading").execute();

    }

    @Override
    public void onServiceComplete(String respon) {
        ArrayList<School> schools;
        JSONTokener jsonToken = new JSONTokener(respon);
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(jsonToken);
            System.out.println(jsonArray.length());

            schools = new ArrayList<School>();
            for (int i = 0; i < jsonArray.length(); i++) {
                schools.add(new School(jsonArray.getJSONObject(i), "school"));
            }
            CustomAdapter adapter = new CustomAdapter(this, schools);
            recycle.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public void onServiceCancel(String posts) {
        Log.d("", "Response null");
    }
}
