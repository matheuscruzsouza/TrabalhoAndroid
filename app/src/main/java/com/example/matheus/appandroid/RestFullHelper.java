<<<<<<< HEAD
package com.example.matheus.appandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


/**
 * Created by matheus on 12/04/16.
 */
public class RestFullHelper {

    public static String POST = "POST";
    public static String PUT = "PUT";
    public static String DELETAR = "DELETE";
    public static String GET = "GET";
    public final int TIMEOUT_MILLIS = 15000;
    private final String TAG = "Http";
    public boolean LOG_ON = false;
    private String contentType;
    private String charsetToEncode;


    public JSONObject doPost(String url, JSONObject params) {
        return doPost(url, params, Charset.defaultCharset());

    }

    public JSONObject doPost(String url, JSONObject params, Charset charset) {


        if (LOG_ON) {
            Log.d(TAG, ">> Http.doPost: " + url);
        }

        return getJSON(url, POST, params, charset);
    }

    public JSONObject doPut(String url, JSONObject params) {
        return doPut(url, params, Charset.defaultCharset());

    }

    public JSONObject doPut(String url, JSONObject params, Charset charset) {


        if (LOG_ON) {
            Log.d(TAG, ">> Http.doPut: " + url);
        }

        return getJSON(url, PUT, params, charset);
    }

    public JSONObject doGet(String url) {
        return doGet(url, Charset.defaultCharset());
    }

    public JSONObject doGet(String url, Charset charset) {


        if (LOG_ON) {
            Log.d(TAG, ">>>>>>>>>>> Http.doGet: " + url);
        }

        return getJSON(url, GET);
    }

    public JSONObject doDelete(String url) {
        return doDelete(url, Charset.defaultCharset());
    }

    public JSONObject doDelete(String url, Charset charset) {


        if (LOG_ON) {
            Log.d(TAG, ">> Http.doDelete: " + url);
        }

        return getJSON(url, DELETAR);
    }

    public JSONObject getJSON(String url, String method) {
        return getJSON(url, method, null, Charset.defaultCharset());
    }

    public JSONObject getJSON(String url, String method, JSONObject params) {
        return getJSON(url, method, params, Charset.defaultCharset());
    }

    public JSONObject getJSON(String url, String method, JSONObject params, Charset charset) {

        HttpURLConnection conn = null;

        String json = null;
        JSONObject jObj = null;

        try {
            if (method != "DELETE") {
                conn = (HttpURLConnection) getConnection(url, method, "application/json");
                conn.connect();
            }
            else{
                conn = getConnection(url, method);
                Log.e("Deletar", String.valueOf(conn));
                conn.setDoOutput(false);
                conn.connect();
            }

            if (params != null) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(params.toString());
                out.flush();
                out.close();

            }

            InputStream in = null;

            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d(TAG, "Error code HTTP_BAD_REQUEST : " + status + " - URL: " + url + " - " + method);
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
            json = buffering(reader);

            if (LOG_ON) {
                Log.d(TAG, "JSON << Http.do" + method + ": " + json);
            } else {
                System.out.println("<< Http.do" + method + ": " + json);
            }


            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        if (method != "DELETE") {
            try {
                if (json != null) {
                    jObj = new JSONObject(json);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            jObj = null;
        }


        if (LOG_ON) {
            Log.d(TAG, "<< Http.doGet: " + jObj);
        }

        return jObj;
    }

    public JSONArray getJSONList(String url, String method, JSONObject params) throws JSONException {
        return getJSONList(url, method, params, Charset.defaultCharset());
    }

    public JSONArray getJSONList(String url, String method, JSONObject params, Charset charset) throws JSONException {

        HttpURLConnection conn = null;

        String json = null;
        JSONArray jObj = null;

        try {
            conn = (HttpURLConnection) getConnection(url, method, "application/json");
            conn.connect();

            if (params != null) {

                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(params.toString());
                out.flush();
                out.close();

            }

            InputStream in = null;

            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d(TAG, "Error code HTTP_BAD_REQUEST : " + status + " - URL: " + url + " - " + method);
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
            json = buffering(reader);

            if (LOG_ON) {
                Log.d(TAG, "JSON << Http.do" + method + ": " + json);
            } else {
                System.out.println("<< Http.do" + method + ": " + json);
            }


            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        jObj = new JSONArray(json);

        if (LOG_ON) {
            Log.d(TAG, "<< Http.doGet: " + jObj);
        }

        return jObj;
    }

    public String buffering(BufferedReader reader) {
        StringBuilder sb = new StringBuilder();
        try {

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }


        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return sb.toString();
    }

    public HttpURLConnection getConnection(String endPoint, String method) {
        return getConnection(endPoint, method, null);
    }

    public HttpURLConnection getConnection(String endPoint, String method, String contentType) {

        HttpURLConnection conn = null;

        try {
            URL url = new URL(endPoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            if (!method.equalsIgnoreCase(RestFullHelper.GET))
                conn.setDoOutput(true);


            if (contentType != null)
                conn.setRequestProperty("Content-Type", contentType);
            else
                conn.setRequestProperty("Content-Type", "text/plain");

            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return conn;
    }

}
=======
package com.example.matheus.appandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


/**
 * Created by matheus on 12/04/16.
 */
public class RestFullHelper {

    public static String POST = "POST";
    public static String PUT = "PUT";
    public static String DELETAR = "DELETE";
    public static String GET = "GET";
    public final int TIMEOUT_MILLIS = 15000;
    private final String TAG = "Http";
    public boolean LOG_ON = false;
    private String contentType;
    private String charsetToEncode;


    public JSONObject doPost(String url, JSONObject params) {
        return doPost(url, params, Charset.defaultCharset());

    }

    public JSONObject doPost(String url, JSONObject params, Charset charset) {


        if (LOG_ON) {
            Log.d(TAG, ">> Http.doPost: " + url);
        }

        return getJSON(url, POST, params, charset);
    }

    public JSONObject doPut(String url, JSONObject params) {
        return doPut(url, params, Charset.defaultCharset());

    }

    public JSONObject doPut(String url, JSONObject params, Charset charset) {


        if (LOG_ON) {
            Log.d(TAG, ">> Http.doPut: " + url);
        }

        return getJSON(url, PUT, params, charset);
    }

    public JSONObject doGet(String url) {
        return doGet(url, Charset.defaultCharset());
    }

    public JSONObject doGet(String url, Charset charset) {


        if (LOG_ON) {
            Log.d(TAG, ">>>>>>>>>>> Http.doGet: " + url);
        }

        return getJSON(url, GET);
    }

    public JSONObject doDelete(String url) {
        return doDelete(url, Charset.defaultCharset());
    }

    public JSONObject doDelete(String url, Charset charset) {


        if (LOG_ON) {
            Log.d(TAG, ">> Http.doDelete: " + url);
        }

        return getJSON(url, DELETAR);
    }

    public JSONObject getJSON(String url, String method) {
        return getJSON(url, method, null, Charset.defaultCharset());
    }

    public JSONObject getJSON(String url, String method, JSONObject params) {
        return getJSON(url, method, params, Charset.defaultCharset());
    }

    public JSONObject getJSON(String url, String method, JSONObject params, Charset charset) {

        HttpURLConnection conn = null;

        String json = null;
        JSONObject jObj = null;

        try {
            if (method != "DELETE") {
                conn = (HttpURLConnection) getConnection(url, method, "application/json");
                conn.connect();
            }
            else{
                conn = getConnection(url, method);
                Log.e("Deletar", String.valueOf(conn));
                conn.setDoOutput(false);
                conn.connect();
            }

            if (params != null) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(params.toString());
                out.flush();
                out.close();

            }

            InputStream in = null;

            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d(TAG, "Error code HTTP_BAD_REQUEST : " + status + " - URL: " + url + " - " + method);
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
            json = buffering(reader);

            if (LOG_ON) {
                Log.d(TAG, "JSON << Http.do" + method + ": " + json);
            } else {
                System.out.println("<< Http.do" + method + ": " + json);
            }


            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        if (method != "DELETE") {
            try {
                if (json != null) {
                    jObj = new JSONObject(json);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            jObj = null;
        }


        if (LOG_ON) {
            Log.d(TAG, "<< Http.doGet: " + jObj);
        }

        return jObj;
    }

    public JSONArray getJSONList(String url, String method, JSONObject params) throws JSONException {
        return getJSONList(url, method, params, Charset.defaultCharset());
    }

    public JSONArray getJSONList(String url, String method, JSONObject params, Charset charset) throws JSONException {

        HttpURLConnection conn = null;

        String json = null;
        JSONArray jObj = null;

        try {
            conn = (HttpURLConnection) getConnection(url, method, "application/json");
            conn.connect();

            if (params != null) {

                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(params.toString());
                out.flush();
                out.close();

            }

            InputStream in = null;

            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d(TAG, "Error code HTTP_BAD_REQUEST : " + status + " - URL: " + url + " - " + method);
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
            json = buffering(reader);

            if (LOG_ON) {
                Log.d(TAG, "JSON << Http.do" + method + ": " + json);
            } else {
                System.out.println("<< Http.do" + method + ": " + json);
            }


            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        jObj = new JSONArray(json);

        if (LOG_ON) {
            Log.d(TAG, "<< Http.doGet: " + jObj);
        }

        return jObj;
    }

    public String buffering(BufferedReader reader) {
        StringBuilder sb = new StringBuilder();
        try {

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }


        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return sb.toString();
    }

    public HttpURLConnection getConnection(String endPoint, String method) {
        return getConnection(endPoint, method, null);
    }

    public HttpURLConnection getConnection(String endPoint, String method, String contentType) {

        HttpURLConnection conn = null;

        try {
            URL url = new URL(endPoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            if (!method.equalsIgnoreCase(RestFullHelper.GET))
                conn.setDoOutput(true);


            if (contentType != null)
                conn.setRequestProperty("Content-Type", contentType);
            else
                conn.setRequestProperty("Content-Type", "text/plain");

            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return conn;
    }

}
>>>>>>> ecb04366956e10b66bc580cbe78faf9fc8f585ac
