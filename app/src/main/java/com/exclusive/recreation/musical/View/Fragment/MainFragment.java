package com.exclusive.recreation.musical.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exclusive.recreation.musical.R;
import com.exclusive.recreation.musical.View.Adapter.MainAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    RecyclerView recyclerMainView;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        recyclerMainView = view.findViewById(R.id.recyclerMainView);
        recyclerMainView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMainView.setAdapter(new MainAdapter(getContext()));
        return view;
    }

}
