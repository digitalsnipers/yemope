package com.yemope.yemope;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;



public class MainActivity  extends Activity {

    private ImageView iv;
    private WebView webView;
    private SwipeRefreshLayout swipe;
    private RelativeLayout splash;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        splash=(RelativeLayout)findViewById(R.id.splash_view);
        iv = (ImageView) findViewById(R.id.iv);
        Animation logoanim = AnimationUtils.loadAnimation(this,R.anim.logotransition);
        iv.startAnimation(logoanim);

        int SPLASH_TIME_OUT = 1500;
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                splash.setVisibility(View.GONE);
            }
        }, SPLASH_TIME_OUT);

        //swipe to refresh
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //LoadWeb();
                webView.reload();
            }
        });
        LoadWeb(); // calling refresh oncreate
    }

    public void LoadWeb(){

        webView= (WebView)findViewById(R.id.webv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl("https://www.yemope.com/");
        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                //hide the swipe refresh
                swipe.setRefreshing(false);
            }
        });

    }

    //goback function
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){

            switch(keyCode){

                case KeyEvent.KEYCODE_BACK :

                    if(webView.canGoBack()){
                        webView.goBack();
                    }
                    else{
                        //finish();

                        new AlertDialog.Builder(this)
                                .setIcon(R.mipmap.ic_launcher)
                                .setTitle("Yemope")
                                .setMessage("\n\t" +
                                        "Sure to exit?")
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                finish();
                                            }

                                        }).setNegativeButton("No", null).show();

                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /*@Override
    public void onBackPressed() {
        //Log.i(TAG, "onBackPressed");

        if (doubleBackToExitPressedOnce) {
            //Log.i(TAG, "double click");
            new AlertDialog.Builder(this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("Yemope")
                    .setMessage("\n\t" +
                            "Sure to exit?")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }

                            }).setNegativeButton("No", null).show();
            return;
        } else {
            //Log.i(TAG, "single click");
            if (webView.canGoBack()) {
                //Log.i(TAG, "canGoBack");
                webView.goBack();
            } else {
                //Log.i(TAG, "nothing to canGoBack");
                Toast.makeText(this, "Please click BACK again to exit",
                        Toast.LENGTH_SHORT).show();
            }
        }

        this.doubleBackToExitPressedOnce = true;
        if (getApplicationContext() == null) {
            return;
        } else {
            //Toast.makeText(this, "Please click BACK again to exit",
                   // Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }*/

}

