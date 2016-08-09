package linkopinghackers.swipeefy;

import android.graphics.Point;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Matilda on 2016-08-09.
 */
public class User {
    String clientId;
    Point latlong;

    private User(String clientId, Point latlong){
        clientId = this.clientId;
        latlong = this.latlong;
    }

    public JSONObject getJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ClientId", clientId);
        jsonObject.put("Coordinates", latlong);
        return jsonObject;
    }

}
