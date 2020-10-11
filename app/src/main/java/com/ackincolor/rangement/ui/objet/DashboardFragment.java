package com.ackincolor.rangement.ui.objet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.ackincolor.rangement.controllers.SearchableActivity;
import com.ackincolor.rangement.controllers.SearchableFragment;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.ui.dialogs.DialogObjet;
import com.ackincolor.rangement.ui.dialogs.SwipeHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements SearchableFragment {

    private DashboardViewModel dashboardViewModel;
    private Boolean isMainView = null;
    private ObjetAdapter objetAdapter = null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                    dialogObjet.onClick(view);
                }
            });
            final Activity mainActivity2 = mainActivity;
            //swipe helper
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
                                    Objet objet = dashboardViewModel.getObjets().get(pos);
                                    if(dashboardViewModel.deleteObjet(objet))
                                        objetAdapter.notifyDataSetChanged();

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

    @Override
    public void searchText(String query) {
        Log.d("debug search","text to search in object : "+query);
        this.objetAdapter.setObjets(dashboardViewModel.search(query));
        this.objetAdapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("debug search", "on Resume fragment");
        this.dashboardViewModel.loadFromDatabase();
        this.objetAdapter.setObjets(this.dashboardViewModel.getObjets());
        this.objetAdapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            ((SearchableActivity) context).setCurrentFragment(this);
            Log.d("debug search", "fragment atttached objet");
        }catch(ClassCastException exp){
            Log.d("debug search","exception de cast : "+exp.getMessage());
        }
    }
}