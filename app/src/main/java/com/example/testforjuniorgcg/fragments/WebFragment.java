package com.example.testforjuniorgcg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testforjuniorgcg.R;

public class WebFragment extends Fragment {

    public static WebFragment newInstance() {
        WebFragment fragment = new WebFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_layout, container, false);

        //add stuff to webview
        String url = "http://www.icndb.com/api/";
        WebView webView = view.findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.findViewById(R.id.web_view);

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        //add zoom option
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(url);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public interface WebFragmentChangeTitle {
        void webFragmentChangeTitle();
    }

}