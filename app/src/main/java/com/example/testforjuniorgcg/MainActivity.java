package com.example.testforjuniorgcg;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.testforjuniorgcg.fragments.JokesFragment;
import com.example.testforjuniorgcg.fragments.WebFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements JokesFragment.JokesChangeTitle, WebFragment.WebFragmentChangeTitle {

    @BindView(R.id.web_icon)
    ImageView webIcon;
    @BindView(R.id.jokes_icon)
    ImageView jokesIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        jokesIcon.setImageResource(R.drawable.hat_pressed);
        setTitle("Jokes");
        launchFragment(JokesFragment.newInstance());
    }

    private void launchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void jokesChangeTitle() {
        setTitle("Jokes");
    }

    @Override
    public void webFragmentChangeTitle() {
        setTitle("Api info");
    }

    @OnClick({R.id.web_icon, R.id.jokes_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.web_icon:
                launchFragment(WebFragment.newInstance());
                webFragmentChangeTitle();
                webIcon.setImageResource(R.drawable.web_pressed);
                jokesIcon.setImageResource(R.drawable.hat);
                break;
            case R.id.jokes_icon:
                launchFragment(JokesFragment.newInstance());
                jokesChangeTitle();
                jokesIcon.setImageResource(R.drawable.hat_pressed);
                webIcon.setImageResource(R.drawable.web);
                break;
        }
    }

    @Override
    public void onBackPressed(){
        WebView wv = (WebView)findViewById(R.id.web_view);
        if(wv.canGoBack()){
            wv.goBack();
        } else {
            super.onBackPressed();
            jokesIcon.setImageResource(R.drawable.hat_pressed);
            webIcon.setImageResource(R.drawable.web);
        }
    }

}