package com.example.myapplication.ui.explore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.Map.AllocasuarinaMap;
import com.example.myapplication.Map.CarniolanMap;
import com.example.myapplication.Map.CaucasianMap;
import com.example.myapplication.Map.MapItalianActiviy;
import com.example.myapplication.R;

//This class is for finding bee species and exploring floral species.
//Will be done for iteration 2.
public class exploreFragment extends Fragment {

    private exploreViewModel exploreViewModel;
    private Button search_bee, search_floral;
    private Spinner kindOfBee, kindOfFloral;
    private Activity map;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exploreViewModel =
                ViewModelProviders.of(this).get(exploreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_explore, container, false);
        search_bee = (Button) root.findViewById(R.id.button_explore_search_bee);
        search_floral = (Button) root.findViewById(R.id.button_explore_search_floral);
        kindOfBee = (Spinner) root.findViewById(R.id.spinner_kindsOfBees);
        kindOfFloral = (Spinner) root.findViewById(R.id.spinner_kindsOfFloral);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(), R.array.kindsOfBess, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        kindOfBee.setAdapter(adapter);


//        search_bee.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_bee;
//                intent_bee = new Intent(getContext(), ExploreMapActivity.class);
//                startActivity(intent_bee);
//            }
//        }));

        search_floral.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_floral;
                intent_floral = new Intent(getContext(), AllocasuarinaMap.class);
                startActivity(intent_floral);
            }
        }));

        kindOfBee.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: search_bee.setOnClickListener((new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_bee;
                            intent_bee = new Intent(getContext(), MapItalianActiviy.class);
                            startActivity(intent_bee);
                        }
                    }));
                    break;

                    case 1: search_bee.setOnClickListener((new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_bee;
                            intent_bee = new Intent(getContext(), CarniolanMap.class);
                            startActivity(intent_bee);
                        }
                    }));
                    break;

                    case 2: search_bee.setOnClickListener((new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_bee;
                            intent_bee = new Intent(getContext(), CaucasianMap.class);
                            startActivity(intent_bee);
                        }
                    }));
                    break;
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        kindOfFloral.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: search_floral.setOnClickListener((new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent_bee;
                            intent_bee = new Intent(getContext(), AllocasuarinaMap.class);
                            startActivity(intent_bee);
                        }
                    }));
                    break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }
}
