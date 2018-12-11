package com.nunez.oauthathenticator;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;


/**
 * Created by paulnunez on 2/14/17.
 */

public class AuthDialog extends DialogFragment implements DialogWebClient.onAuthenticadedListener{
  private static final String TAG = "AuthDialog";

  public interface RequestListener {
    /**
     * After this you can request your user key & secret
     */
    void onAuthorizationTokenReceived(Uri authToken);

    void onDialogCloseByUser();
  }

  private RequestListener listener;

  /**
   * Create a new instance of MyDialogFragment, providing "num"
   * as an argument.
   */
  public static AuthDialog newInstance(String requestToken, String callbackUrl, RequestListener listener) {
    AuthDialog f = new AuthDialog();

    // Supply inputs as an argument.
    Bundle args = new Bundle();
    args.putString("requestToken", requestToken);
    args.putString("callbackUrl", callbackUrl);
    f.listener = listener;
    f.setArguments(args);

    return f;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_webview, container, false);

    WebView webView = (WebView) view.findViewById(R.id.webView);
    ImageButton closeBtn = (ImageButton) view.findViewById(R.id.imageButton);

    setupDialog();
    setupWebView(webView);

    closeBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onDialogCloseByUser();
        getDialog().dismiss();
      }
    });

    return view;
  }

  private void setupDialog() {
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
  }

  private void setupWebView(WebView webView) {
    webView.loadUrl(getArguments().getString("requestToken"));
    webView.getSettings().setAllowContentAccess(true);

    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);

    // Force links and redirects to open in the WebView instead of in a browser
    webView.setWebViewClient(new DialogWebClient(this, getArguments().getString("callbackUrl")));

  }

  @Override
  public void onSuccess(Uri authToken) {
    // Go back to the previous activity to be able to save tokens.
    listener.onAuthorizationTokenReceived(authToken);
    getDialog().dismiss();
  }

  @Override
  public void onFailure() {
    // Notifiy de previous activity that an error has happened.
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    listener.onDialogCloseByUser();
    super.onDismiss(dialog);
  }
}


/**
 * This class checks, when a page is going to start with the app callbackUrl
 */
class DialogWebClient extends WebViewClient {
  private onAuthenticadedListener listener;
  private String                  callbackUrl;

  public interface onAuthenticadedListener {
    void onSuccess(Uri authToken);
    void onFailure();
  }

  DialogWebClient(@NonNull DialogWebClient.onAuthenticadedListener listener, String callbackUrl) {
    this.listener = listener;
    this.callbackUrl = callbackUrl;
  }

  @Override
  public void onPageStarted(WebView view, String url, Bitmap favicon) {
    if (url.startsWith(callbackUrl)) {
      Uri urlUri = Uri.parse(url);
      listener.onSuccess(urlUri);
    }
  }

  @Override
  public void onPageFinished(WebView view, String url) {
    super.onPageFinished(view, url);
      view.scrollTo(0, view.getContentHeight());
  }
}