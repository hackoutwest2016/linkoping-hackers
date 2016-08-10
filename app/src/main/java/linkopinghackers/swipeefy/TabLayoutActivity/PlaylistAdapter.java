package linkopinghackers.swipeefy.TabLayoutActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import linkopinghackers.swipeefy.R;

/**
 * Created by Matilda on 2016-08-10.
 */
public class PlaylistAdapter {
    Context context;
    int layoutResourceId;
    ArrayList<Playlist> playlists = null;

    public PlaylistAdapter(Context context, int layoutResourceId, ArrayList<Playlist> playlists) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.playlists = playlists;
    }

    //@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PlaylistHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PlaylistHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (PlaylistHolder)row.getTag();
        }

        Playlist playlist = playlists.get(position);
        holder.txtTitle.setText(playlist.name);
        holder.imgIcon.setImageResource(playlist);

        return row;
    }

    static class PlaylistHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}