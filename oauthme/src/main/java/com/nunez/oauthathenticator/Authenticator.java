package com.nunez.oauthathenticator;

import android.net.Uri;
import android.util.Log;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

/**
 * Created by paulnunez on 2/10/17.
 */

public class Authenticator implements GetRequestTokenAsyncTask.onRequestTokenListener,
    GetUserSecretAsyncTask.GetUserSecretListener {

  private static final String TAG = "Authenticator";

  private String                consumerKey; //api_key
  private String                consumerSecret; // api_secret
  private String                requestTokenUrl; ///oauth/request_token"
  private String                accessTokenUrl; // /oauth/access_token"
  private String                authorizeUrl; //  /oauth/authorize?mobile=1";
  private String                callbackUrl;
  private String                requestToken; // the URL to which the user must be sent in order to authorize the consumer.
  private AuthenticatorListener listener;

  private OAuthConsumer consumer;
  private OAuthProvider provider;

  private static Authenticator instance;

  public synchronized static Authenticator getInstance() {
    if (instance == null)
      throw new NullPointerException("An instance of this class hasn't been build");
    return instance;
  }

  private Authenticator(Builder builder) {
    consumerKey = builder.consumerKey;
    consumerSecret = builder.consumerSecret;
    requestTokenUrl = builder.requestTokenUrl;
    accessTokenUrl = builder.accessTokenUrl;
    authorizeUrl = builder.authorizeUrl;
    callbackUrl = builder.callbackUrl;
    listener = builder.listener;

    consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
    provider = new DefaultOAuthProvider(requestTokenUrl, accessTokenUrl, authorizeUrl);
    provider.setOAuth10a(true);
  }

  /**
   * Gets the requestToken(authorization url) to be used to let the user
   * grant permissions to the app. This authorization url must be opened on a browser
   * via an intent or webView.
   *
   * And the requestToken, requestTokenSecret to perform call signing.
   */
  public void getRequestToken() {

    new GetRequestTokenAsyncTask(consumer, provider, callbackUrl, this).execute();
  }

  /**
   * Retrieves the userKey and userSecret that is used to sign the requests.
   * <p>
   * Call this after you received the authorization token. This happens after the user
   * log in and grants authorization to your app.
   *
   * @param uri received in the onNewIntent method after user granted the authorization.
   */
  public void getUserSecretKeys(Uri uri) {
    new GetUserSecretAsyncTask(consumer, provider, uri, this).execute();
  }


  // Asynck task callbacks

  @Override
  public void onRequestTokenReceived(String authorizationUrl) {
    this.requestToken = authorizationUrl;

    Log.i(TAG, "onRequestTokenReceived: "+requestToken);
    Log.i(TAG, "onRequestTokenReceived2: "+consumer.getToken()+"\n"+consumer.getTokenSecret());

    // sendiing it back so it can be saved it as preferred.
    listener.onRequestTokenReceived(authorizationUrl, consumer.getToken(), consumer.getTokenSecret());
  }
  @Override
  public void onUserSecretReceived(String userKey, String userSecret) {
    // sending it back so it can be saved it as preferred.
    listener.onUserSecretRecieved(userKey, userSecret);
  }


  // Authenticator interface for callbacks
  public interface AuthenticatorListener {

    /**
     * Called when Authenticator receives the AuthorizationUrl, requestToken and requestTokenSecret,
     * now you can store it and open it in a browser to request the userKey and userSecret.
     *
     * @param requestToken The request token received.
     */
    void onRequestTokenReceived(String authorizationUrl, String requestToken, String requestTokenSecret);

    /**
     * Called when the Authenticator receives the user key & secret.
     * With this values you can star to sign your request to the api.
     *
     * @param userKey The received user key.
     * @param userSecret The received user secret.
     */
    void onUserSecretRecieved(String userKey, String userSecret);
  }


  public static final class Builder {
    private String                consumerKey;
    private String                consumerSecret;
    private String                requestTokenUrl;
    private String                accessTokenUrl;
    private String                authorizeUrl;
    private String                callbackUrl;
    private AuthenticatorListener listener;


    public Builder consumerKey(String consumerKey) {
      if (consumerKey == null) throw new NullPointerException("consumerKey is null");
      this.consumerKey = consumerKey;
      return this;
    }

    public Builder consumerSecret(String consumerSecret) {
      if (consumerSecret == null) throw new NullPointerException("consumerSecret is null");
      this.consumerSecret = consumerSecret;
      return this;
    }

    public Builder requestTokenUrl(String requestTokenUrl) {
      if (requestTokenUrl == null) throw new NullPointerException("requestTokenUrl is null");
      this.requestTokenUrl = requestTokenUrl;
      return this;
    }

    public Builder accessTokenUrl(String accessTokenUrl) {
      if (accessTokenUrl == null) throw new NullPointerException("accessTokenUrl is null");
      this.accessTokenUrl = accessTokenUrl;
      return this;
    }

    public Builder authorizeUrl(String authorizeUrl) {
      if (authorizeUrl == null) throw new NullPointerException("authorizeUrl is null");
      this.authorizeUrl = authorizeUrl;
      return this;
    }

    public Builder callbackUrl(String callbackUrl) {
      if (callbackUrl == null) throw new NullPointerException("callbackUrl is null");
      this.callbackUrl = callbackUrl;
      return this;
    }

    public Builder listener(AuthenticatorListener listener) {
      if (callbackUrl == null) throw new NullPointerException("listener is null");
      this.listener = listener;
      return this;
    }

    public Authenticator build() {
      if (consumerKey == null) throw new IllegalStateException("consumerKey is not set");
      if (consumerSecret == null) throw new IllegalStateException("consumerSecret is not set");
      if (requestTokenUrl == null) throw new IllegalStateException("requestTokenUrl is not set");
      if (accessTokenUrl == null) throw new IllegalStateException("accessTokenUrl is not set");
      if (authorizeUrl == null) throw new IllegalStateException("authorizeUrl is not set");
      if (callbackUrl == null) throw new IllegalStateException("callbackUrl is not set");
      if (listener == null) throw new IllegalStateException("listener is not set");

      instance = new Authenticator(this);
      return instance;
    }

  }


}
