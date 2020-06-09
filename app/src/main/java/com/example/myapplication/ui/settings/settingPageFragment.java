package com.example.myapplication.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class settingPageFragment extends Fragment {

    private TextView nameTv;
    private TextView emailTv;

    public settingPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nameTv = getView().findViewById(R.id.textView_user_name);
        nameTv.setText(loadName());
        emailTv = getView().findViewById(R.id.textView_user_email);
        emailTv.setText(loadEmail());
        getView().findViewById(R.id.textView_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_settingPageFragment_to_aboutFragment2);
            }
        });
        getView().findViewById(R.id.iamge_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_settingPageFragment_to_aboutFragment2);
            }
        });

        getView().findViewById(R.id.EditProfileTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_settingPageFragment_to_editInfoFragment);
            }
        });

        getView().findViewById(R.id.image_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_settingPageFragment_to_editInfoFragment);
            }
        });

        getView().findViewById(R.id.shareTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_settingPageFragment_to_shareFramgent);
            }
        });

        getView().findViewById(R.id.image_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_settingPageFragment_to_shareFramgent);
            }
        });
        String user_name = getArguments().getString("user_name");
        String user_email = getArguments().getString("user_email");
        if (!(user_name.equals("User_111")) && !(user_email.equals("123@gmail.com"))) {
            save(user_name, user_email);
        }
    }

    private void save(String user_name, String email) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("NAME", user_name);
        editor.putString("EMAIL", email);
        editor.apply();
        nameTv.setText(user_name);
        emailTv.setText(email);
    }

    private String loadName() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_name", Context.MODE_PRIVATE);
        return sharedPreferences.getString("NAME","user_111");
    }

    private String loadEmail() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_name", Context.MODE_PRIVATE);
        return sharedPreferences.getString("EMAIL","123@gmail.com");
    }
}
