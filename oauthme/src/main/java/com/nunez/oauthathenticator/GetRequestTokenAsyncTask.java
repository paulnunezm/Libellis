package com.nunez.oauthathenticator;

import android.os.AsyncTask;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Created by paulnunez on 2/11/17.
 */

public class GetRequestTokenAsyncTask extends AsyncTask<Object, Void, Void> {

  private OAuthConsumer          consumer;
  private OAuthProvider          provider;
  private String                 callbackUrl;
  private String                 requestToken;
  private onRequestTokenListener onRequestTokenReceivedListener;

  public interface onRequestTokenListener {
    void onRequestTokenReceived(String authorizationUrl);
  }


  public GetRequestTokenAsyncTask(OAuthConsumer consumer, OAuthProvider provider, String callbackUrl,
                                  onRequestTokenListener onRequestTokenReceivedListener) {
    if (consumer == null) throw new IllegalStateException("consumer is not set");
    if (provider == null) throw new IllegalStateException("provider is not set");
    if (callbackUrl == null) throw new IllegalStateException("callbackUrl is not set");

    this.consumer = consumer;
    this.provider = provider;
    this.callbackUrl = callbackUrl;
    this.onRequestTokenReceivedListener = onRequestTokenReceivedListener;
  }

  @Override
  protected Void doInBackground(Object... params) {

    try {
      // We get the URL to which the user must be sent in order to authorize the
      // consumer. This is the URL needed to be opened in a browser.
      requestToken = provider.retrieveRequestToken(consumer, callbackUrl);


    } catch (OAuthMessageSignerException e) {
      e.printStackTrace();
    } catch (OAuthNotAuthorizedException e) {
      e.printStackTrace();
    } catch (OAuthExpectationFailedException e) {
      e.printStackTrace();
    } catch (OAuthCommunicationException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);
    onRequestTokenReceivedListener.onRequestTokenReceived(requestToken);
  }
}
