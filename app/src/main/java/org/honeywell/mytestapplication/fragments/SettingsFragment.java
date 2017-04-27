package org.honeywell.mytestapplication.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.honeywell.mytestapplication.R;
import org.honeywell.mytestapplication.activity.MainActivity;

/**
 * Created by yash on 14/04/17.
 */

public class SettingsFragment extends Fragment {

    TextView tv;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btn = (Button) view.findViewById(R.id.logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("LOGIN", false);
                editor.apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
