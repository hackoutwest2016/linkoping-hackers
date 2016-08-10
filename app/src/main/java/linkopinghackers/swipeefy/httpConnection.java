package linkopinghackers.swipeefy;

import android.content.Context;
import android.graphics.Point;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Playlists;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matilda on 2016-08-09.
 */
public class httpConnection {
    private String  playlistUri,
            imageUri;
    private JSONObject playlist;
    static RequestQueue queue;

    public httpConnection(){


    }


   // public void postLocation(String userId){
       /* RequestQueue queue = Volley.newRequestQueue(LoginActivity. );
        // Instantiate the RequestQueue.

        String url = "10.47.12.22:8000";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                       System.out.print("Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }*/

    public void getFriend(Point point, String AccessToken){

    }

    public void instatiateQueue(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public JSONObject getPlaylist(String clientId, String accessToken){
        final String acc = "Bearer " + accessToken;
        String url = "https://api.spotify.com/v1/users/" + "tlds" + "/playlists";
        //Unclear if param needed when getHeaders is overridden below, commented away
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Accept", "application/json");
        params.put("Authorization", "Bearer " + accessToken);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                       // Log.d("Response", response.toString());

                        try {
                            playlist = response.getJSONArray("items").getJSONObject(0);
                            imageUri = playlist.getJSONArray("images").getJSONObject(0).getString("url");
                            System.out.println(imageUri);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error.Response", response);

                    }
                }
        )
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", acc);
                return headers;
            }
        };
        queue.add(request);
        return playlist;
    }

    public String getPlaylistUri(String clientId, String accessToken) throws JSONException {

        JSONObject playlist = getPlaylist(clientId, accessToken);
        return playlist.getString("uri");
    }

    public String getImageUrl(String clientId, String accessToken) throws JSONException {

        JSONObject playlist = getPlaylist(clientId, accessToken);
        if (playlist.getJSONArray("images").length()!=0)
        return playlist.getJSONArray("images").getJSONObject(0).getString("url");
        else return "";
    }
}
