package com.interapp.rainervieira.interapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String language;

    Locale current_locale;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean reload = getIntent().getBooleanExtra("reload", true);

        SharedPreferences languagepref = getSharedPreferences("language_settings", MODE_PRIVATE);
        language = languagepref.getString("language", "pt");

        //if (!reload) {
        updateLocale();
        //}

        Spinner spinner_language = (Spinner) findViewById(R.id.spinner_language);
        spinner_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //String new_language = "pt";
                switch (position) {
                    case 1:
                        language = "pt";
                        break;
                    case 2:
                        language = "en";
                        break;
                    case 3:
                        language = "es";
                        break;
                    case 4:
                        language = "fr";
                        break;

                    default:
                        return;
                }

                //loadLanguage(new_language);
                updateLocale();
                saveLanguage();
                //saveLanguage();
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();



        if(reload){
            refresh();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }

    public void updateLocale() {

           //language = new_language;
           current_locale = new Locale(language);

           Resources res = getResources();
           DisplayMetrics dm = res.getDisplayMetrics();
           Configuration conf = res.getConfiguration();

           conf.locale = current_locale;

           res.updateConfiguration(conf, dm);



           //language = new_language;

           //refresh();
    }

    void saveLanguage(){
        SharedPreferences languagepref = getSharedPreferences("language_settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = languagepref.edit();
        editor.putString("language", language);
        editor.commit();
    }


    void refresh(){

        Intent refresh = new Intent(this, MainActivity.class);
        refresh.putExtra("reload", false);
        startActivity(refresh);

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.interapp.rainervieira.interapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.interapp.rainervieira.interapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
