package com.ackincolor.rangement.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ackincolor.rangement.MainActivity;
import com.ackincolor.rangement.R;
import com.ackincolor.rangement.controllers.ClickRangementcontroller;
import com.ackincolor.rangement.ui.dialogs.DialogRangement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rangement, container, false);
        if(root!=null){
            FloatingActionButton fab = root.findViewById(R.id.floatingaddrangementactionbtn);
            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rangementrecyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            ClickRangementcontroller clickRangementcontroller = new ClickRangementcontroller(recyclerView,homeViewModel,(MainActivity) getParentFragment().getActivity());

            RangementAdapter rangementAdapter = new RangementAdapter(inflater, homeViewModel.getRangements(),clickRangementcontroller);
            recyclerView.setAdapter(rangementAdapter);

            final DialogRangement dialogRangement = new DialogRangement(homeViewModel,root.getContext());

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    dialogRangement.onClick(view);
                }
            });
        }
        return root;
    }
}