package linkopinghackers.swipeefy.TabLayoutActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import linkopinghackers.swipeefy.R;
import linkopinghackers.swipeefy.SessionManager;
import linkopinghackers.swipeefy.cardstack.CardStack;
import linkopinghackers.swipeefy.cardstack.CardStackListener;
import linkopinghackers.swipeefy.cardstack.CardsDataAdapter;

/**
 * Created by Alexander on 2016-08-10.
 */
public class SwipeFragment extends android.support.v4.app.Fragment implements PlayerNotificationCallback, ConnectionStateCallback {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String LISTVIEWADAPTER_ACTION = "RECEIVER";
    private List<String> listDataHeader;
    private EditText editTextMessage;
    private FragmentCommunicator fragmentCommunicator;
    private ExpandableListView expandableListView;
    private Player mPlayer;
    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    private SessionManager sessionManager;
    private ImageButton previous, pause, play, next;
    private String playURI;

    // Container Activity must implement this interface
   /* public interface OnEventStartedListener {
        public void onSmsSent(String message, String senderName, HashMap<String, List<Contact>> listDataChild);

    }*/

    // Might need to use Activity instead of context in this method
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sessionManager = fragmentCommunicator.getSessionManager();
        Config playerConfig = new Config(getActivity().getApplicationContext(), sessionManager.getToken(), sessionManager.getClientId());
        mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {

            @Override
            public void onInitialized(Player player) {
                mPlayer.addConnectionStateCallback(SwipeFragment.this);
                mPlayer.addPlayerNotificationCallback(SwipeFragment.this);
                mPlayer.play("spotify:track:2TpxZ7JUBn3uw46aR7qd6V");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_swipe, container, false);
        setRetainInstance(true);

        //   setRetainInstance(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        previous = (ImageButton) view.findViewById(R.id.action_previous);
        pause = (ImageButton) view.findViewById(R.id.action_pause);
        play = (ImageButton) view.findViewById(R.id.action_play);
        next = (ImageButton) view.findViewById(R.id.action_next);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.skipToPrevious();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.pause();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.play(playURI);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.skipToNext();
            }
        });

        mCardStack = (CardStack) view.findViewById(R.id.container);

        mCardStack.setContentResource(R.layout.card_content);
        mCardStack.setStackMargin(20);

        mCardAdapter = new CardsDataAdapter(getActivity().getApplicationContext(),0);
        mCardAdapter.add(R.drawable.swipeefy);
        mCardAdapter.add(R.drawable.swipeefy);
        mCardAdapter.add(R.drawable.swipeefy);
        mCardAdapter.add(R.drawable.swipeefy);
        mCardAdapter.add(R.drawable.swipeefy);

        mCardStack.setListener(new CardStackListener());

        mCardStack.setAdapter(mCardAdapter);


    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("MainActivity", "Playback event received: " + eventType.name());
        switch (eventType) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
        switch (errorType) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void onDestroyView() {
        Spotify.destroyPlayer(this);

        super.onDestroyView();
    }

}
