package com.example.myapplication.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AdapterRecord;
import com.example.myapplication.Constants;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MyDbHelper;
import com.example.myapplication.R;


public class historyFragment extends Fragment {

    private historyViewModel historyViewModel;
    private RecyclerView recordsRv;
    private MyDbHelper dbHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(historyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        recordsRv = root.findViewById(R.id.recordsRv);
        dbHelper = new MyDbHelper(getContext());
        //loadRecords;

        loadRecords();
        return root;
    }


    private void loadRecords() {
        AdapterRecord adapterRecord = new AdapterRecord(getContext(),
                dbHelper.getAllRecords(Constants.C_ADDED_TIMESTAMP + " DESC"));


        recordsRv.setAdapter(adapterRecord);
    }

    private void searchRecords(String query) {
        AdapterRecord adapterRecord = new AdapterRecord(getActivity(),
                dbHelper.searchRecords(query));

        recordsRv.setAdapter(adapterRecord);
    }

    public void onResume() {
        super.onResume();
        loadRecords(); //refresh records list
    }
}
