package com.zl.hefenweather.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import com.zl.hefenweather.R;

public class AboutActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    private final static String KEY_ABOUT_GITHUB = "about_github";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       addPreferencesFromResource(R.xml.about_preference);

       findPreference(KEY_ABOUT_GITHUB).setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(KEY_ABOUT_GITHUB.equals(preference.getKey())){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse((String) preference.getSummary());
            intent.setData(content_url);
            startActivity(intent);
        }

        return true;
    }

}
