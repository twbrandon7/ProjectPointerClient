package tw.edu.niu.csie.clx.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Connection {

    private String baseUrl;
    private String method;
    private String token;
    private String postBody;
    private boolean jsonBody;
    private HashMap<String, String> parameters;
    private int responseCode;

    public Connection(String baseUrl, String method){
        this.baseUrl = baseUrl;
        this.method = method;
        this.parameters = new HashMap<>();
        this.token = null;
        this.postBody = null;
        this.jsonBody = false;
        this.responseCode = 0;
    }

    public void setToken(String token){
        this.token = token;
    }

    public void setPostBody(String postBody, boolean isJson){
        this.postBody = postBody;
        this.jsonBody = isJson;
    }

    public void  setParameters(HashMap<String, String> parameters){
        this.parameters = parameters;
    }

    public String execute(){
        String result = "";

        try{
            String urlStr = "";
            if(parameters.size() > 0){
                for(Map.Entry<String, String> entry : parameters.entrySet()){
                    urlStr += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&";
                }
                urlStr = urlStr.substring(0, urlStr.length()-1);
                urlStr = baseUrl + "?" + urlStr;
            }else{
                urlStr = baseUrl;
            }

            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod(method);

            if(token != null){
                urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            }

            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");

            urlConnection.setDoInput(true);

            if(postBody != null){
                if(jsonBody){
                    urlConnection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                }
                urlConnection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes(postBody);
                wr.flush();
                wr.close();
            }

            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(in));
                StringBuilder stb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    stb.append(line).append("\n");
                }

                rd.close();
                result = stb.toString();
            } catch(FileNotFoundException e) {
                //Log.e(TAG, "HTTP Error : "+String.valueOf(responseCode), e);
            } finally {
                this.responseCode = urlConnection.getResponseCode();
                urlConnection.disconnect();
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return result;
    }

    public static JsonObject parseJson(String jsonStr){
        JsonElement jElement = new JsonParser().parse(jsonStr);
        JsonObject jObject = jElement.getAsJsonObject();
        return jObject;
    }

    public int getResponseCode(){
        return responseCode;
    }
}
