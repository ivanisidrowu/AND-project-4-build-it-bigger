package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import tw.invictus.androidlibrary.JokeActivity;


public class MainActivity extends ActionBarActivity implements EndpointAsyncTask.Listener {

    private ProgressBar progressBar;
    private InterstitialAd interstitialAd;
    private String joke;
    private String adUnitId = "ca-app-pub-3940256099942544/1033173712";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(adUnitId);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                loadNewAd();
                startActivity(JokeActivity.createIntent(MainActivity.this, joke));
            }
        });
        loadNewAd();
    }

    private void loadNewAd(){
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(adRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        if (NetworkUtil.isOnline(this)) {
            progressBar.setVisibility(View.VISIBLE);
            EndpointAsyncTask task = new EndpointAsyncTask();
            task.setListener(this);
            task.execute();
            interstitialAd.show();
        }else{
            toastErrorMsg(R.string.network_error_msg);
        }
    }

    @Override
    public void onLoaded(String joke) {
        progressBar.setVisibility(View.GONE);

        if(joke == null || joke.length() == 0){
            toastErrorMsg(R.string.not_a_joke);
        }else{
            this.joke = joke;
        }

    }

    public void toastErrorMsg(int resId){
        String msg = getString(resId);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
