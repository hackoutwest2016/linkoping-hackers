package linkopinghackers.swipeefy;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements PlayerNotificationCallback, ConnectionStateCallback {


    private static final String CLIENT_ID = "8740928683fe4ab6be03091a875ac618";
    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "swipeefy://callback";
    private Context context;
    private LoginActivity loginActivity = this;
    private static final int REQUEST_CODE = 0;


    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
   // private SessionManager sessionManager;
    private TextView errorMessage;
    private String errorInvalid, errorFailed, error;

    private Player mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        /*errorMessage = (TextView) findViewById(R.id.text_error_message);
        errorInvalid = getResources().getString(R.string.error_invalid_user_credentials);
        errorFailed = getResources().getString(R.string.error_connection_failed);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.prompt_email);
        mEmailView.setText("alexander@46elks.com");

        mPasswordView = (EditText) findViewById(R.id.prompt_password);
        mPasswordView.setText("ufcadell9218");

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });



        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        */Button loginButton;
        Button signupButton;

        loginButton =  (Button) (findViewById(R.id.action_login));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                        AuthenticationResponse.Type.TOKEN,
                        REDIRECT_URI);
                builder.setScopes(new String[]{});
                AuthenticationRequest request = builder.build();

                AuthenticationClient.openLoginActivity(loginActivity, REQUEST_CODE, request);

                //attemptLogin();
                //checkFirstTimeUser();
                //Intent intent = new Intent(context, TabLayoutActivity.class);
                //startActivity(intent);

            }
        });

        /*signupButton =  (Button) (findViewById(R.id.action_sign_up));
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AccountCreationActivity.class);
                startActivity(intent);

            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                SessionManager sessionManager = new SessionManager();
                sessionManager.createLoginSession(context, response.getAccessToken());

                Intent intentLogin = new Intent(this, SwiperActivity.class);
                startActivity(intentLogin);
            }
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
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    /*private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        if (errorMessage.getVisibility()==View.VISIBLE){
            errorMessage.setVisibility(View.INVISIBLE);
        }
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    @Override
    public void onBackPressed() {
        mAuthTask = null;
        //showProgress(false);
        this.moveTaskToBack(true);
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
        /*
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private JsonParser jsonParser;
        private JsonObject serverResponse;
        private InputStream inputStream;
        private HttpURLConnection conn;


        UserLoginTask(String email, String password) {
            mEmail = email.toLowerCase();
            mPassword = password;
            jsonParser = new JsonParser();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: PUT ALL ASYNC TASKS INTO ONE CLASS

            String line = null;

            try {
                // Construct POST data
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(mEmail, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(mPassword, "UTF-8");

                // Make HTTP POST request
                URL url = new URL("https://dashboard.46elks.com/api/login.php");
                conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();

                //kolla om error 200 (dvs att allt gick bra) och ingen ingen timeout, annars försök igen lr säg till användare natt det inte gick
                if (!(conn.getResponseCode() >= 400)) {

                    inputStream = conn.getInputStream();
                    BufferedReader sd = new BufferedReader(new InputStreamReader(inputStream));

                    String receivedJson = null;
                    while ((line = sd.readLine()) != null) {
                        System.out.println(line);
                        receivedJson = line;
                    }

                    if (TextUtils.equals(receivedJson, "username or password incorrect")){
                        System.out.println("Incorrect user details");
                        error = errorInvalid;
                        sd.close();
                        return false;

                    } else{
                        serverResponse = (JsonObject) jsonParser.parse(receivedJson);
                        sd.close();
                        return true;
                    }

                } else {
                    // inputStream = conn.getErrorStream();

                    // If no connection was made

                    error = errorFailed;
                    return false;
                }


            } catch (Exception e) {
                error = errorFailed;
                e.printStackTrace();
                System.out.println("Connection to server failed");
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                sessionManager = new SessionManager();
                sessionManager.createLoginSession(getApplicationContext(),
                        serverResponse.get("secret").getAsString(), serverResponse.get("id").getAsString());
                Intent intent = new Intent(context, TabLayoutActivity.class);
                startActivity(intent);
                //finish();
            } else {
                errorMessage.setText(error);
                errorMessage.setVisibility(View.VISIBLE);
                mLoginFormView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }*/
}
