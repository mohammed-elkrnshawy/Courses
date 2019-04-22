package s.panorama.graduationproject.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;

import s.panorama.graduationproject.Classes.LanguageType;
import s.panorama.graduationproject.R;
import s.panorama.graduationproject.Remote.AuthClass;

public class MainActivity extends AppCompatActivity {

    private String type,language;
    private AuthClass authClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ReadSharedPreference();
        setLanguages();

        //readData();

    }

    private void setLanguages() {
        LanguageType languageType=new LanguageType();
        languageType.languageType = type;
        Configuration config = new Configuration();
        config.locale = new Locale(language);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void ReadSharedPreference() {
        SharedPreferences prefs = getSharedPreferences(getApplication().getPackageName(), MODE_PRIVATE);
        type=prefs.getString("type","arabic");
        language=prefs.getString("language","ar");
        boolean isLogin = prefs.getBoolean("isLogin",false);

        if (isLogin)
        {
            getUser(prefs.getString("Token",""));
        }
        else {
            readData();
        }
    }

    private void getUser(String token) {
        authClass=new AuthClass(this);
        authClass.Search(token);
    }

    private void readData() {
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finishAffinity();
            }
        }.start();
    }
}
