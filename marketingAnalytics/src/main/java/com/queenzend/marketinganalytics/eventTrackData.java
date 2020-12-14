package com.queenzend.marketinganalytics;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
public class eventTrackData {
        //For save login Event:
        public static void eventData(String user_id, String company_id, String event_name) {


            class eventTrackData extends AsyncTask<String, Void, String> {
                String EventUrl = URLUtils.Url_event_api;
                String server_response;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... strings) {
                    URL url;
                    HttpURLConnection urlConnection;
                    try {
                        url = new URL(EventUrl);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);
                        urlConnection.setDoInput(true);
                        OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                        try {
                            JSONObject object = new JSONObject();
                            object.put("user_id", user_id);
                            object.put("company_id", company_id);
                            object.put("event_name", event_name);
                            wr.write(object.toString());
                            Log.e("JSON INPUT", object.toString());
                            wr.flush();
                            wr.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        urlConnection.connect();
                        int responseCode = urlConnection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            server_response = readStream(urlConnection.getInputStream());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Log.e("Response", "" + server_response);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(server_response.toString());
                        if (jsonObject.has("res_code")) {
                            if ((jsonObject.getString("res_code").contains("2"))) {
                                System.out.println("Failed to save event data");
                            } else if ((jsonObject.getString("res_code").contains("3"))) {
                                System.out.println("Data is null");
                            } else if ((jsonObject.getString("res_code").contains("4"))) {
                                System.out.println("Company not found");
                            } else if ((jsonObject.getString("res_code").contains("1"))) {
                                System.out.println("Event save successfully");

                            } else {
                            System.out.println("Failed to save event data");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                private String readStream(InputStream inputStream) {
                    BufferedReader reader = null;
                    StringBuffer response = new StringBuffer();
                    try {
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return response.toString();
                }
            }
            UserLoginEvent ul = new UserLoginEvent();
            ul.execute();
        }
    }
