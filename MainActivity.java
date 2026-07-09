package com.harpa.offline;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Não precisamos de bridge de TTS aqui: o áudio cantado toca
        // direto pelo elemento <audio> do HTML, que o WebView já suporta
        // nativamente (inclusive tocando do cache offline).
        webView = getBridge().getWebView();
        webView.addJavascriptInterface(new AppBridge(), "Android");
    }

    public class AppBridge {

        @JavascriptInterface
        public void openUrl(String url) {
            runOnUiThread(() -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
