package com.example.myapplication.ui.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

    public settingPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_page, container, false);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        TextView about = getActivity().findViewById(R.id.textView_about);
//        ImageView setting = getActivity().findViewById(R.id.imageView_setting);
//        about.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavController controller = Navigation.findNavController(v);
//                controller.navigate(R.id.action_settingPageFragment_to_aboutFragment2);
//            }
//        });
//
//        String user_name = getArguments().getString("user_name");
//        TextView tx_user_name = getActivity().findViewById(R.id.textView_user_name);
//        tx_user_name.setText(user_name);
//        setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavController controller = Navigation.findNavController(v);
//                controller.navigate(R.id.action_settingPageFragment_to_editInfoFragment);
//            }
//        });
//
//
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.textView_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_settingPageFragment_to_aboutFragment2);
            }
        });

        getView().findViewById(R.id.imageView_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_settingPageFragment_to_editInfoFragment);
            }
        });

        String string = getArguments().getString("user_name");
        TextView textView = getView().findViewById(R.id.textView_user_name);
        textView.setText(string);

    }
}
