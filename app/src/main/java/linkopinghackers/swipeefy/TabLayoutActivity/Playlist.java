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
    String image;

    public Playlist(String uri, String image){
        this.uri = uri;
        this.image = image;
    }

    public Bitmap getImage() throws IOException {
        URL url = new URL(image);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        return bmp;
    }
}
