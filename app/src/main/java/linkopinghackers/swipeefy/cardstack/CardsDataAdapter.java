package linkopinghackers.swipeefy.cardstack;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//import linkopinghackers.swipeefy.R;

/**
 * Created by Matilda on 2016-08-09.
 */

public class CardsDataAdapter extends ArrayAdapter<String> {

    public CardsDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        //supply the layout for your card

        TextView v = (TextView)(contentView.findViewById(R.id.content));
        v.setText(getItem(position));
        return contentView;
    }

}
