package verizon.a20180302_mounikvelagapudi_nycschools;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import verizon.a20180302_mounikvelagapudi_nycschools.ModelClasses.School;

/**
 * Created by mounikvelagapudi on 03/03/18.
 */

public class WebService extends AsyncTask<Void, Void, String> {


    // private  Context mContext;
    private static final String VOLLEY_TAG = "VOLLEY_GLOBALS";
    private final Context context;
    private final RecyclerView recycle;
    private final String type;
    int responseCode;
    String method;
    String url;
    String body = null;
    String progressText = null;
    ProgressDialog progress = null;
    HttpURLConnection conn;

    public WebService(Context applicationContext, RecyclerView recycle, String method, String url, String progressText, String type) {
        this.context = applicationContext;
        this.method = method;
        this.url = url;
        this.progressText = progressText;
        this.recycle = recycle;
        this.type = type;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onPreExecute() {
        //  progress = (progressText != null) ? ProgressDialog.show(mContext, progressText, "Please wait...", true) : null;
    }

    @Override
    protected String doInBackground(Void... params) {
        BufferedReader BR;
        String str;
        String respons = "";
        if (!isOnline(context)) {
            respons = "No Internet Connectivity";
            Toast.makeText(context, "Please check connection", Toast.LENGTH_LONG);
        } else {
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setReadTimeout(10000 * 60);
                conn.setConnectTimeout(15000 * 60);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestMethod(method);
                conn.setDoInput(true);

                if (method == "GET")//(method == RequestMethod.DELETE && body == null))
                    conn.setDoOutput(false); // For GET we donot send request body so we can set this to false
                else
                    conn.setDoOutput(true);// Other than GET we send request body so we can set this to true

                // Start the query
                conn.connect();

                int statusCode = conn.getResponseCode();
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    // Log.d(VOLLEY_TAG, "WebService ErrorCode: " + statusCode);
                    conn.disconnect();
                    responseCode = conn.getResponseCode();

                } else {
                    InputStream stream = conn.getInputStream();
                    BR = new BufferedReader(new InputStreamReader(stream));

                    while ((str = BR.readLine()) != null) {
                        respons += str;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();

                if (respons == null) {
                    respons = "Unable to process your request, please try again.";
                }
            }
        }
        return respons;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onPostExecute(String respon) {
        ArrayList<School> schools;
        if (respon != "") {
            try {
                System.out.println("response :  " + respon);
                System.out.println("response.toString() :  " + respon.toString());

                JSONTokener jsonToken = new JSONTokener(respon);
                JSONArray jsonArray = new JSONArray(jsonToken);

                System.out.println(jsonArray.length());

                schools = new ArrayList<School>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    schools.add(new School(jsonArray.getJSONObject(i), type));
                }

                CustomAdapter adapter = new CustomAdapter(context, schools, type);
                recycle.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (progress != null)
                    progress.dismiss();
                if (conn != null)
                    conn.disconnect();
            }
        } else {
            Log.d(VOLLEY_TAG, "Response null");
        }

        try {
            if (progress != null)
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception in code", Toast.LENGTH_SHORT).show();
        }
    }

}
