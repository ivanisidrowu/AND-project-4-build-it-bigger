package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.ivan.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by ivan on 3/14/16.
 */
public class EndpointAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private Listener listener;

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if(myApiService == null) {

            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setApplicationName("backend")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(listener != null){
            listener.onLoaded(result);
        }
    }

    public interface Listener{
        void onLoaded(String joke);
    }
}