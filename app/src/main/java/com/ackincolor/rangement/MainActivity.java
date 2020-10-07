package com.ackincolor.rangement;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ackincolor.rangement.controllers.SearchableActivity;
import com.ackincolor.rangement.controllers.SearchableFragment;
import com.ackincolor.rangement.ui.home.HomeViewModel;
import com.ackincolor.rangement.ui.objet.DashboardViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchableActivity {

    HomeViewModel homeViewModel;
    DashboardViewModel dashboardViewModel;
    SearchableFragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    public HomeViewModel getHomeViewModel() {
        return homeViewModel;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_reader, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));

        }
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    public void setHomeViewModel(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
    }



    public DashboardViewModel getDashboardViewModel() {
        return dashboardViewModel;
    }

    public void setDashboardViewModel(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(this.currentFragment!= null){
            this.currentFragment.searchText(query);
        }else{
            Log.d("debug search","fragment null");
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(this.currentFragment!= null){
            this.currentFragment.searchText(newText);
        }else{
            Log.d("debug search","fragment null");
        }
        return false;
    }

    @Override
    public void setCurrentFragment(SearchableFragment fragment) {
        this.currentFragment = fragment;
    }
}
