package com.example.movielist.FetchAPIData;

import android.os.AsyncTask;
import android.util.Log;

import com.example.movielist.MovieDetailsActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class GetMovieData extends AsyncTask<Void,Void,Void> {


    public static String data = "*";
    public static String url="";

    @Override
    protected Void doInBackground(Void... voids) {

        /*HashMap<String, String> param = new HashMap<String, String>();
        data = performPostCall(url, param);*/

        try {
            URL urls = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection)urls.openConnection();
            httpConn.setRequestMethod("GET");

            InputStream inputStream = httpConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            httpConn.disconnect();

            data = line;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MovieDetailsActivity.receivedData = data;
        MovieDetailsActivity.waitMovieDetailedResponse.setText(" ");
        if(data.isEmpty()){
            Log.i("Nagato ", " No data lol");
        }else {
            Log.i("Nagato", data );
        }
    }
}
