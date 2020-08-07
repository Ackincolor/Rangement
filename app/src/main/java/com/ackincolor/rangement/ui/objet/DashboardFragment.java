package com.ackincolor.rangement.ui.objet;

import android.app.Activity;
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

import com.ackincolor.rangement.Database.ObjetManager;
import com.ackincolor.rangement.MainActivity;
import com.ackincolor.rangement.R;
import com.ackincolor.rangement.controllers.ClickObjetcontroller;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.ui.dialogs.DialogObjet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Boolean isMainView = null;
    private ObjetAdapter objetAdapter = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_objet, container, false);
        if(root != null){
            FloatingActionButton fab = root.findViewById(R.id.floatingaddobjetactionbtn);
            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.objetrecyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            Activity mainActivity = null;
            try {
                mainActivity = getParentFragment().getActivity();
                ((MainActivity)mainActivity).setDashboardViewModel(dashboardViewModel);
                isMainView = true;

            }catch(NullPointerException e){
                mainActivity = getActivity();
                fab.hide();
                isMainView = false;
            }
            dashboardViewModel.setObjetManager(new ObjetManager(mainActivity));
            dashboardViewModel.loadFromDatabase();
            ClickObjetcontroller clickObjetcontroller = new ClickObjetcontroller(recyclerView,dashboardViewModel,mainActivity);

            this.objetAdapter = new ObjetAdapter(inflater, dashboardViewModel.getObjets(),clickObjetcontroller);
            recyclerView.setAdapter(objetAdapter);

            final DialogObjet dialogObjet = new DialogObjet(dashboardViewModel,root.getContext(),null);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    dialogObjet.onClick(view);
                }
            });
        }
        return root;
    }

    public DashboardViewModel getDashboardViewModel() {
        return dashboardViewModel;
    }

    public void setObjetListe(ArrayList<Objet> liste){
        this.dashboardViewModel.setObjets(liste);
        this.objetAdapter.setObjets(liste);
        this.objetAdapter.notifyDataSetChanged();
    }
}