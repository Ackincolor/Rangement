package com.ackincolor.rangement.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.ackincolor.rangement.R;
import com.ackincolor.rangement.controllers.SearchableFragment;

public class NotificationsFragment extends Fragment implements SearchableFragment {

    private NotificationsViewModel notificationsViewModel;
    private WebView myWebView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        this.myWebView = (WebView) root.findViewById(R.id.webviewcontact);
        this.myWebView.loadUrl("http://www.example.com");


        return root;
    }

    @Override
    public void searchText(String query) {
        Log.d("DEBUG Search","recherche dans la doc :"+query);
    }
}