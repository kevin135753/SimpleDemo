package com.kevin.simpledemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final long THREE_DAYS = 3 * 24 * 60 * 60 * 1000L;
    public static final long FIVE_SECONDS = 5 * 1000L;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        tv = findViewById(R.id.tv);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn:
                clickBtn();
                break;
            default:
                break;
        }
    }

    private void clickBtn(){
        SharedPreferences sp = getSharedPreferences("launchTime", Context.MODE_PRIVATE);
        long saveTime = sp.getLong("saveTime", 0);
        if (saveTime == 0) {
            tv.setText(R.string.first_login);
        }else if(isLaterThreeDays(saveTime)){
            tv.setText(R.string.login_3_days_later);
        }else{
            tv.setText(R.string.login_within_3_days);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("saveTime", System.currentTimeMillis());
        editor.apply();
    }

    private boolean isLaterThreeDays(long target){
        long current = System.currentTimeMillis();
        return current - target >= FIVE_SECONDS;
    }
}
