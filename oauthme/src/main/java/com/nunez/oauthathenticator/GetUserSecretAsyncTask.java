package com.nunez.oauthathenticator;

import android.net.Uri;
import android.os.AsyncTask;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Created by paulnunez on 2/11/17.
 */

public class GetUserSecretAsyncTask extends AsyncTask<Object, Void, Void> {

  private OAuthConsumer         consumer;
  private OAuthProvider         provider;
    private Uri                   uri;
  private GetUserSecretListener userSecretListener;

  public interface GetUserSecretListener {
    void onUserSecretReceived(String userKey, String userSecret);
  }


    public GetUserSecretAsyncTask(OAuthConsumer consumer, OAuthProvider provider, Uri uri,
                                GetUserSecretListener userSecretListener) {
    if (consumer == null) throw new IllegalStateException("consumer is not set");
    if (provider == null) throw new IllegalStateException("provider is not set");
    if (uri == null) throw new IllegalStateException("uri is not set");

    this.consumer = consumer;
    this.provider = provider;
    this.uri = uri;
    this.userSecretListener = userSecretListener;
  }

  @Override
  protected Void doInBackground(Object... params) {

    // Store the obtained token
    String oauthToken = uri.getQueryParameter(OAuth.OAUTH_TOKEN);

    // Forming the request
    consumer.setTokenWithSecret(consumer.getToken(), consumer.getTokenSecret());

    try {
      // Perform the access token request. It is saved in the consumer.
      provider.retrieveAccessToken(consumer, oauthToken);

    } catch (OAuthMessageSignerException e) {
      e.printStackTrace();
    } catch (OAuthNotAuthorizedException e) {
      e.printStackTrace();
    } catch (OAuthExpectationFailedException e) {
      e.printStackTrace();
    } catch (OAuthCommunicationException e) {
      e.printStackTrace();
    }

    userSecretListener.onUserSecretReceived(consumer.getToken(), consumer.getTokenSecret());
    return null;
  }
}

