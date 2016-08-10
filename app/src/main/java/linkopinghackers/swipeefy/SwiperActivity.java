package linkopinghackers.swipeefy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import linkopinghackers.swipeefy.cardstack.CardStack;
import linkopinghackers.swipeefy.cardstack.CardStackListener;
import linkopinghackers.swipeefy.cardstack.CardsDataAdapter;

public class SwiperActivity extends AppCompatActivity implements PlayerNotificationCallback, ConnectionStateCallback {
        private Player mPlayer;
    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    private SessionManager sessionManager;
    private ImageButton previous, pause, play, next;
    private String playURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiper);
        sessionManager = new SessionManager();

        previous = (ImageButton) findViewById(R.id.action_previous);
        pause = (ImageButton) findViewById(R.id.action_pause);
        play = (ImageButton) findViewById(R.id.action_play);
        next = (ImageButton) findViewById(R.id.action_next);

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

        mCardStack = (CardStack)findViewById(R.id.container);

        mCardStack.setContentResource(R.layout.card_content);
        mCardStack.setStackMargin(20);

        mCardAdapter = new CardsDataAdapter(getApplicationContext(),0);
        mCardAdapter.add(R.drawable.swipeefy);
        mCardAdapter.add(R.drawable.swipeefy);
        mCardAdapter.add(R.drawable.swipeefy);
        mCardAdapter.add(R.drawable.swipeefy);
        mCardAdapter.add(R.drawable.swipeefy);

        mCardStack.setListener(new CardStackListener());

        mCardStack.setAdapter(mCardAdapter);

        Config playerConfig = new Config(this, sessionManager.getToken(), sessionManager.getClientId());
        mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {

            @Override
            public void onInitialized(Player player) {
                mPlayer.addConnectionStateCallback(SwiperActivity.this);
                mPlayer.addPlayerNotificationCallback(SwiperActivity.this);
                mPlayer.play("spotify:track:2TpxZ7JUBn3uw46aR7qd6V");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });



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
}
