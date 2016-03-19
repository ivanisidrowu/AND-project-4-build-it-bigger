package tw.invictus.androidlibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static String TAG = JokeActivity.class.getSimpleName();

    public static Intent createIntent(Context context, String joke){
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(TAG, joke);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        String joke = getIntent().getStringExtra(TAG);

        TextView jokeView  = (TextView) findViewById(R.id.joke_view);
        jokeView.setText(joke);
    }
}
