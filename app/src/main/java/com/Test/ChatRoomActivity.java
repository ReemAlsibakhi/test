package com.Test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Test.adapter.MessageAdapter;
import com.Test.model.Message;

import java.util.ArrayList;
import java.util.Locale;

public class ChatRoomActivity extends AppCompatActivity {
    private static final String TAG = "ChatRoomActivity";
    SharedPreferences prefs;
    private Locale locale;
    EditText etText;
    RecyclerView recyclerView;
    String msgContent;
    ArrayList<Message> msgList = new ArrayList<Message>();
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        initData();


    }

    public void initData() {
        etText = findViewById(R.id.etMsg);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        prefs = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        Configuration config = getResources().getConfiguration();
        String lang = prefs.getString("LANGUAGE_ID", "en");
        String systemLocale = getSystemLocale(config).getLanguage();
        if (!TextUtils.isEmpty(lang) && systemLocale.equalsIgnoreCase(lang)) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            setSystemLocale(config, locale);
            updateConfiguration(config);
        }

    }

    private void buildRecy() {
        messageAdapter = new MessageAdapter(this, msgList);
        recyclerView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
    }

    public void send(View view) {
        msgContent = etText.getText().toString().trim();
        Log.e(TAG, "send:" + msgContent);
        if (!etText.getText().toString().isEmpty()) {
            msgList.add(new Message(msgContent, true));
            buildRecy();
            etText.setText("");
        } else {
            Log.e(TAG, "content empty:" + msgContent);

        }

    }


    public void recieve(View view) {
        msgContent = etText.getText().toString().trim();
        Log.e(TAG, "receive:" + msgContent);
        if (!etText.getText().toString().isEmpty()) {
            msgList.add(new Message(msgContent, false));
            buildRecy();
            etText.setText("");
        } else {
            Log.e(TAG, "content empty:" + msgContent);

        }
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            setSystemLocale(newConfig, locale);
            Locale.setDefault(locale);
            updateConfiguration(newConfig);
        }
    }

    private void updateConfiguration(Configuration config) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            createConfigurationContext(config);
        } else {
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }
    }

    private Locale getSystemLocale(Configuration config) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return config.getLocales().get(0);
        } else {
            return config.locale;
        }
    }

    private void setSystemLocale(Configuration config, Locale locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
    }


}