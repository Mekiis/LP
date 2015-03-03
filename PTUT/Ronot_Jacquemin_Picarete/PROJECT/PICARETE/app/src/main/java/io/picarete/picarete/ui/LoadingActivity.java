package io.picarete.picarete.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import io.picarete.picarete.R;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.container.userdata.User;
import io.picarete.picarete.model.container.userdata.Config;
import io.picarete.picarete.model.container.userdata.UserAccessor;
import io.picarete.picarete.model.data_sets.TitleSet;

public class LoadingActivity extends ActionBarActivity {

    private static final double MIN_TIME_TO_FIND = 3 * 1000; // In seconds;

    TextView UITextViewLoading;

    private class LoadingSign extends Thread{
        private static final double TIME_BEFORE_MAJ_SIGN = 0.5 * 1000;
        TextView textViewToEdit;
        String stringToEdit;

        int nbPointAfterString = 0;
        String stringDisplayed;
        public boolean needToStop = false;

        public LoadingSign(TextView textViewToEdit, String stringToEdit) {
            super();
            this.textViewToEdit = textViewToEdit;
            this.stringToEdit = stringToEdit;
            this.nbPointAfterString = 0;
        }

        @Override
        public void run() {
            while(!needToStop){
                stringDisplayed = stringToEdit;
                nbPointAfterString = (nbPointAfterString + 1) % 4;
                for(int i = 0; i < nbPointAfterString; i++)
                    stringDisplayed += ".";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewToEdit.setText(stringDisplayed);
                    }
                });
                try {
                    Thread.sleep((long) TIME_BEFORE_MAJ_SIGN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        UITextViewLoading = (TextView) findViewById(R.id.loading_text_view_msg);

        new AsyncTask<Void, String, String>(){

            @Override
            protected String doInBackground(Void... params) {
                TitleSet.getTitles(LoadingActivity.this);
                Config.getLevels(LoadingActivity.this);

                LoadingSign loadingSign = new LoadingSign(UITextViewLoading, "Loading");
                loadingSign.start();

                long timeBefore = System.currentTimeMillis();

                UserAccessor.getUser(LoadingActivity.this);
                Gson gson = new Gson();
                String userJson = gson.toJson(UserAccessor.getUser(LoadingActivity.this));

                long timeAfter = System.currentTimeMillis();
                if((timeAfter - timeBefore) <= MIN_TIME_TO_FIND){
                    try {
                        Thread.sleep((long) (MIN_TIME_TO_FIND - (timeAfter - timeBefore)));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                loadingSign.needToStop = true;

                return userJson;
            }

            @Override
            protected void onPostExecute(String userJson) {
                super.onPostExecute(userJson);
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                intent.putExtra(Constants.EXTRA_USER, userJson);
                startActivity(intent);
                finish();
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loading, menu);
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
}
