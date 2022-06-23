package cl.ione.globallogic.Utilities;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cl.ione.globallogic.Data.NewsData;

public class CloudConnect {

    //Variables
    private static HttpURLConnection conn;
    private static StringBuilder result;
    private static JSONArray jObj = null;

    private static JSONArray makeHttpRequest(String url, HashMap<String, Object> params) {

        //Variables
        URL urlObj;
        String charset = "UTF-8";
        StringBuilder sbParams = new StringBuilder();
        int i = 0;

        //Armado de la URL + parametros
        if (params != null) {
            for (String key : params.keySet()) {
                try {
                    //Formato URL
                    if (i != 0) {
                        sbParams.append("&");
                    }
                    sbParams.append(key)
                            .append("=")
                            .append(URLEncoder.encode(Objects
                                    .requireNonNull(params.get(key)).toString(), charset));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
        if (sbParams.length() != 0) {
            url += "?" + sbParams;
        }

        //Conexión a internet
        try {
            urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setDoOutput(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setConnectTimeout(15000);
            conn.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            result = new StringBuilder();
            int responseCode = conn.getResponseCode();
            //String responseMessage = conn.getResponseMessage();
            if (responseCode == 200) {
                //Receive the response from the server
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {
                result.append("{\"error\":\"nil\"}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONArray(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e);
        }

        // return JSON Object
        return jObj;
    }

    //Interface para devolver resultados
    public interface JsonParserInterface {
        void result(JSONArray json);
    }

    public static class JsonParser extends AsyncTask<String, String, JSONArray> {

        //Variables
        private final String mUrl;
        private final HashMap<String, Object> mParams;
        private final JsonParserInterface mListener;

        public JsonParser(String url
                , HashMap<String, Object> params
                , JsonParserInterface listener) {
            this.mUrl = url;
            this.mParams = params;
            this.mListener = listener;
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            try {
                JSONArray json = makeHttpRequest(mUrl, mParams);
                if (json != null) {
//                    Log.d("JSON result", json.toString());
                    return json;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONArray json) {
            if (mListener != null) {
                mListener.result(json);
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //Extraer datos del GPS
    public static List<NewsData> extractData(String JSON) {
        if (TextUtils.isEmpty(JSON)) {
            return null;
        }
        List<NewsData> datas = new ArrayList<>();
        try {
            JSONArray baseJsonResponse = new JSONArray(JSON);
            NewsData data;
            if (baseJsonResponse.length() > 0) {
                for (int i = 0; i < baseJsonResponse.length(); i++) {
                    JSONObject featureItem = baseJsonResponse.getJSONObject(i);

                    String image = featureItem.getString("image");
                    String title = featureItem.getString("title");
                    String description = featureItem.getString("description");

                    data = new NewsData(image
                            , title
                            , description);
                    datas.add(data);
                }
            }
        } catch (JSONException e) {
            Log.e("LOG", "Problem parsing JSON results", e);
        }
        return datas;
    }
}
