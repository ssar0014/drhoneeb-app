package com.example.myapplication.ui.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class   editInfoFragment extends Fragment {



    public editInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_info, container, false);
        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.button_edit_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = getView().findViewById(R.id.editText_userName);
                String string = editText.getText().toString();
                EditText editText1 = getView().findViewById(R.id.editText_email);
                String string2 = editText1.getText().toString();
                if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                    Toast.makeText(getActivity(),"Please Input User Name and Email!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validateEmailAddress(string2)) {
                    Toast.makeText(getActivity(),"Email format is wrong",Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("user_name", string);
                bundle.putString("user_email", string2);
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_editInfoFragment_to_settingPageFragment,bundle);
            }
        });

        getView().findViewById(R.id.button_edit_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_editInfoFragment_to_settingPageFragment);
            }
        });
    }

    private boolean validateEmailAddress(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

