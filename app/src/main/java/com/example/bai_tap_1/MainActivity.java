package com.example.bai_tap_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textView, tvName, tvGroup;
    Button button;

    public static final String FILE_NAME = "com.example.bai_tap_1.lang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        tvName = findViewById(R.id.tvName);
        tvGroup = findViewById(R.id.tvGroup);
        button = findViewById(R.id.button);

        SharedPreferences pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        final String lang = pref.getString("lang", "");

        if(lang.equalsIgnoreCase(""))
            showDialog();

        updateView(lang);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private static Context setLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);

        return context.createConfigurationContext(configuration);
    }

    private void updateView(String lang){
        Context context = setLocale(this, lang);
        Resources resources = context.getResources();

        tvName.setText(resources.getString(R.string.name));
        tvGroup.setText(resources.getString(R.string.group));
        textView.setText(resources.getString(R.string.hello));
        button.setText(resources.getString(R.string.change));
    }

    private void showDialog() {
        SharedPreferences pref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        final String lang = pref.getString("lang", "");

        Context context = setLocale(this, lang);
        Resources resources = context.getResources();


        final SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();

        final String[] language = resources.getStringArray(R.array.language);

        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setTitle(resources.getString(R.string.title))
                .setItems(language, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(language[which].equals("English")||language[which].equals("Tiếng Anh")){
                            editor.putString("lang", "en");
                            editor.commit();
                            updateView("en");

                        }
                        else {
                            editor.putString("lang", "vi");
                            editor.commit();
                            updateView("vi");
                        }
                    }
                })
                .create().show();
    }
}
