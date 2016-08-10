package linkopinghackers.swipeefy.TabLayoutActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import linkopinghackers.swipeefy.R;
import linkopinghackers.swipeefy.SessionManager;

/**
 * Created by Alexander on 2016-08-10.
 */
public class FavouritesFragment extends Fragment{

    private SessionManager sessionManager;
    private FragmentCommunicator fragmentCommunicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sessionManager = fragmentCommunicator.getSessionManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        setRetainInstance(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            fragmentCommunicator = (FragmentCommunicator) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnEventStartedListener");
        }
    }
}
