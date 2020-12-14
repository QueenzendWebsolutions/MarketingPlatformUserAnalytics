package com.queenzend.marketinganalytics;
import android.content.Context;
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

public class Userdata {
    public static void SaveUserLoginData(final Context c, final String email, final String password, final String token,
                                         final String source, final String company_token) {
        class UserLogin extends AsyncTask<String, Void, String> {
            String loginUrl = URLUtils.Url_Login;
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
                    url = new URL(loginUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());

                    try {
                        JSONObject object = new JSONObject();
                        object.put("email", email);
                        object.put("password", password);
                        object.put("device_id", token );
                        object.put("source", source);
                        object.put("company_token", company_token);

                        wr.write(object.toString());
                        Log.d("JSON INPUT", object.toString());
                        wr.flush();
                        wr.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(c, "Registration Failed", Toast.LENGTH_LONG).show();
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

                            Toast.makeText(c, "Invalid Login", Toast.LENGTH_LONG).show();
                            //System.out.println("Invalid Login");

                        }
                        else if ((jsonObject.getString("res_code").contains("3"))) {

                            Toast.makeText(c, "Data is null", Toast.LENGTH_LONG).show();
                           // System.out.println("Invalid Login");

                        }
                        else if ((jsonObject.getString("res_code").contains("4"))) {

                            Toast.makeText(c, "Company not found", Toast.LENGTH_LONG).show();
                            //System.out.println("Invalid Login");

                        }

                        else if ((jsonObject.getString("res_code").contains("1"))) {

                            //System.out.println("Inside onSuccess response     " + jsonObject.getString("res_data"));

                            Toast.makeText(c, "Login successfully", Toast.LENGTH_LONG).show();



                            //System.out.println("successfully Login......");
                        } else {
                            Toast.makeText(c, "Invalid Login", Toast.LENGTH_LONG).show();
                           // System.out.println("Invalid Login");
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
        UserLogin ul = new UserLogin();
        ul.execute();
    }
    //For Save Registration data:
    public static void SaveUserRegisterData(final Context c,final String token, final String name,
                                            final String email,final String phone,
                                            final String jobtype,final String gender,final String city,
                                            final String country,final String dob,final String address,
                                            final String LATITUDE_VALUE,final String LONGITUDE_VALUE ,final String password,
                                            final String source,final String company_token) {
        class UserLogin extends AsyncTask<String, Void, String> {
            String loginUrl = URLUtils.Url_appUserRegistration;
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
                    url = new URL(loginUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());

                    try {
                        JSONObject object = new JSONObject();
                        object.put("device_token", token);
                        object.put("user_name", name);
                        object.put("email", email);
                        object.put("phone_number", phone);
                        object.put("jobType", jobtype);
                        object.put("city", city);
                        object.put("country", country);
                        object.put("latitude", LATITUDE_VALUE);
                        object.put("longitude", LONGITUDE_VALUE);
                        object.put("source", source);
                        object.put("dob", dob);
                        object.put("address", address);
                        object.put("gender", gender);
                        object.put("password", password);
                        object.put("company_token", company_token);
                        wr.write(object.toString());
                        Log.d("JSON INPUT", object.toString());
                        wr.flush();
                        wr.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(c, "Registration Failed", Toast.LENGTH_LONG).show();
                    }
                    urlConnection.connect();

                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        server_response = readStream(urlConnection.getInputStream());
//                         Toast.makeText(c, "server_response"+server_response, Toast.LENGTH_LONG).show();
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
                            Toast.makeText(c, "Registration failed please try again", Toast.LENGTH_LONG).show();
                        } else  if ((jsonObject.getString("res_code").contains("1"))) {
                            Toast.makeText(c, "Register successfully", Toast.LENGTH_LONG).show();
                        }
                        else if ((jsonObject.getString("res_code").contains("3"))) {
                            Toast.makeText(c, "Data is null", Toast.LENGTH_LONG).show();
                            System.out.println("Invalid Login");
                        }
                        else if ((jsonObject.getString("res_code").contains("4"))) {
                            Toast.makeText(c, "Company not found", Toast.LENGTH_LONG).show();
                            //System.out.println("Company not found");
                        }
                        else {
                            Toast.makeText(c, "Registration failed please try again", Toast.LENGTH_LONG).show();            }
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
        UserLogin ul = new UserLogin();
        ul.execute();
    }
}
