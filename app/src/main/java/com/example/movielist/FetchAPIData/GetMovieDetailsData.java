package com.example.movielist.FetchAPIData;

import android.os.AsyncTask;
import android.util.Log;

import com.example.movielist.MainActivity;
import com.example.movielist.MovieDetailsActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class GetMovieDetailsData extends AsyncTask<Void,Void,Void> {

    public static String data = "*";
    public static String url="";

    public String  performPostCall(String requestURL,
                                   HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";
            }
        } catch (Exception e) {
            System.out.println("Connection failed at url request");
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }


        return result.toString();
    }


    @Override
    protected Void doInBackground(Void... voids) {


        HashMap<String, String> param = new HashMap<String, String>();
        data = performPostCall(url, param);


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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

/*MovieDetailsActivity.receivedData = data;
        MovieDetailsActivity.waitMovieDetailedResponse.setText(" ");
        if(data.isEmpty()){
            Log.i("Nagato ", " No data lol");
        }else {
            Log.i("Nagato", data );
        }*/
