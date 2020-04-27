package com.example.myapplication.ui.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MapActivity;
import com.example.myapplication.R;

//This class is for finding bee species and exploring floral species.
//Will be done for iteration 2.
public class exploreFragment extends Fragment {

    private exploreViewModel exploreViewModel;
    private Button search_bee, search_floral;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exploreViewModel =
                ViewModelProviders.of(this).get(exploreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_explore, container, false);
        search_bee = (Button) root.findViewById(R.id.button_explore_search_bee);
        search_floral = (Button) root.findViewById(R.id.button_explore_search_floral);
        search_bee.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_bee;
                intent_bee = new Intent(getContext(), MapActivity.class);
                startActivity(intent_bee);
            }
        }));

        search_floral.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_floral;
                intent_floral = new Intent(getContext(), MapActivity.class);
                startActivity(intent_floral);
            }
        }));
        return root;
    }
}
