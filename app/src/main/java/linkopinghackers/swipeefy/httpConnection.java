package linkopinghackers.swipeefy;

import android.content.Context;
import android.graphics.Point;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Playlists;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Matilda on 2016-08-09.
 */
public class httpConnection {

    RequestQueue queue;

    private httpConnection(RequestQueue queue){
         queue = this.queue;

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

    public void getPlaylist(String clientId, String AccessToken){
        String url = "https://api.spotify.com/v1/users/" + clientId + "/playlists";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
    }

    public void getNewSong(Playlists playlist, String AccessToken){


    }
}
