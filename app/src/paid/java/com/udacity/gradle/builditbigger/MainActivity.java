package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import tw.invictus.androidlibrary.JokeActivity;


public class MainActivity extends ActionBarActivity implements EndpointAsyncTask.Listener {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
        }else{
            toastErrorMsg(R.string.network_error_msg);
        }
    }

    @Override
    public void onLoaded(String joke) {
        if(joke == null || joke.length() == 0){
            toastErrorMsg(R.string.not_a_joke);
        }else{
            progressBar.setVisibility(View.GONE);
            startActivity(JokeActivity.createIntent(this, joke));
        }
    }

    public void toastErrorMsg(int resId){
        String msg = getString(resId);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}