package spit.matrix2017.HelperClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import spit.matrix2017.R;

/**
 * Created by akshayy on 11-02-2017.
 */

public class web extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent=getIntent();
        String site = intent.getStringExtra("SITE");
        WebView webView=(WebView) findViewById(R.id.my_webview);
        webView.loadUrl(site);
    }
}
