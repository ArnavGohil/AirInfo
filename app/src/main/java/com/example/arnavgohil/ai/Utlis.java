package com.example.arnavgohil.ai;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


class Utlis extends AsyncTask<Void,Void,Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        URL url = null;
        try
        {
            url = new URL(MainActivity.REQUEST_URL);
        }
        catch (MalformedURLException e) { }
        try
        {

            try
            {
                MainActivity.urlConnection = (HttpURLConnection) url.openConnection();
                MainActivity.urlConnection.setReadTimeout(10000 /* milliseconds */);
                MainActivity.urlConnection.setConnectTimeout(15000 /* milliseconds */);
                MainActivity.urlConnection.setRequestMethod("GET");
                MainActivity.urlConnection.connect();

                // If the request was successful (response code 200),
                // then read the input stream and parse the response.
                if (MainActivity.urlConnection.getResponseCode() == 200)
                {
                    MainActivity.inputStream = MainActivity.urlConnection.getInputStream();
                    StringBuilder output = new StringBuilder();
                    if (MainActivity.inputStream != null)
                    {
                        InputStreamReader inputStreamReader = new InputStreamReader(MainActivity.inputStream, Charset.forName("UTF-8"));
                        BufferedReader reader = new BufferedReader(inputStreamReader);
                        String line = reader.readLine();
                        while (line != null)
                        {
                            output.append(line);
                            line = reader.readLine();
                        }
                    }
                    MainActivity.jsonResponse = output.toString();
                }
            }
            catch (IOException e) { }
            finally
            {
                if (MainActivity.urlConnection != null)
                {
                    MainActivity.urlConnection.disconnect();
                }
                if (MainActivity.inputStream != null)
                {
                    MainActivity.inputStream.close();
                }
            }
        }
        catch (IOException e)
        {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (MainActivity.flag == 1) {
            try {
                JSONArray info = new JSONArray(MainActivity.jsonResponse);
                JSONObject next = info.getJSONObject(0);
                MainActivity.t1.setText(next.getString("productionLine"));
                MainActivity.t2.setText(next.getString("airplaneIataType"));
                MainActivity.t3.setText(next.getString("codeIataAirline"));
            } catch (JSONException e) {
            }
        } else {
            try {
                JSONArray info = new JSONArray(MainActivity.jsonResponse);
                JSONObject next = info.getJSONObject(0);

                JSONObject extra = next.getJSONObject("departure");
                MainActivity.t1.setText(extra.getString("iataCode"));

                JSONObject extra1 = next.getJSONObject("arrival");
                MainActivity.t2.setText(extra1.getString("iataCode"));

                JSONObject extra2 = next.getJSONObject("flight");
                MainActivity.t3.setText(extra2.getString("iataNumber"));

            }
            catch (JSONException e)
            {
            }

        }
    }
}