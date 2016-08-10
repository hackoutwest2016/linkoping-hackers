package linkopinghackers.swipeefy.TabLayoutActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Matilda on 2016-08-10.
 */
public class Playlist {
    String uri;
    Bitmap image;

    public Playlist(String uri, Bitmap image){
        this.uri = uri;
        this.image = image;
    }

    public Bitmap getImage() throws IOException {

        return image;
    }
}
