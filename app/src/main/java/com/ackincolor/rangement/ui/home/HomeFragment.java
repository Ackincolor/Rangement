package com.ackincolor.rangement.ui.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import com.ackincolor.rangement.Database.RangementManager;
import com.ackincolor.rangement.MainActivity;
import com.ackincolor.rangement.R;
import com.ackincolor.rangement.controllers.ClickRangementcontroller;
import com.ackincolor.rangement.controllers.SearchableActivity;
import com.ackincolor.rangement.controllers.SearchableFragment;
import com.ackincolor.rangement.ui.dialogs.DialogRangement;
import com.ackincolor.rangement.ui.dialogs.SwipeHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeFragment extends Fragment implements SearchableFragment {

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
            homeViewModel.setRangementManager(new RangementManager(getContext()));
            homeViewModel.loadFromDatabase();
            ClickRangementcontroller clickRangementcontroller = new ClickRangementcontroller(recyclerView,homeViewModel,(MainActivity) getParentFragment().getActivity());
            ((MainActivity) getParentFragment().getActivity()).setHomeViewModel(homeViewModel);
            RangementAdapter rangementAdapter = new RangementAdapter(inflater, homeViewModel.getRangements(),clickRangementcontroller);
            recyclerView.setAdapter(rangementAdapter);
            Activity mainActivity = null;
            try {
                mainActivity = getParentFragment().getActivity();

            }catch(NullPointerException e){
                mainActivity = getActivity();

            }

            final Activity mainActivity2 = mainActivity;
            SwipeHelper swipeHelper = new SwipeHelper(mainActivity, recyclerView) {
                @Override
                public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                    underlayButtons.add(new SwipeHelper.UnderlayButton(
                            "Supprimer",
                            0,
                            Color.parseColor("#FF3C30"),
                            new SwipeHelper.UnderlayButtonClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    // TODO: onDelete


                                }
                            },
                            mainActivity2
                    ));
                    underlayButtons.add(new SwipeHelper.UnderlayButton(
                            "Modifier",
                            0,
                            Color.parseColor("#C7C7CB"),
                            new SwipeHelper.UnderlayButtonClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    // TODO: OnUnshare
                                }
                            },
                            mainActivity2
                    ));
                }
            };
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
    @Override
    public void searchText(String query) {
        Log.d("debug search","text to search : "+query);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((SearchableActivity)context).setCurrentFragment(this);
        Log.d("debug search","fragment atttached rangement");
    }
}