package linkopinghackers.swipeefy.cardstack;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import linkopinghackers.swipeefy.R;
import linkopinghackers.swipeefy.TabLayoutActivity.Playlist;

/**
 * Created by Alexander on 2016-08-09.
 */
public class CardsDataAdapter extends ArrayAdapter<Playlist> {

    public CardsDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        //supply the layout for your card
        ImageView v = (ImageView) (contentView.findViewById(R.id.content));
        //v.setImageResource(getItem(position));
        return contentView;
    }
}
