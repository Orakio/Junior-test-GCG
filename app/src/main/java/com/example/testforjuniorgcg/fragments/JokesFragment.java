package com.example.testforjuniorgcg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testforjuniorgcg.JokesResponse;
import com.example.testforjuniorgcg.R;
import com.example.testforjuniorgcg.adapters.JokesAdapter;
import com.example.testforjuniorgcg.network.NetworkService;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class JokesFragment extends Fragment {

    @BindView(R.id.number_of_jokes)
    EditText textEditJokes;
    @BindView(R.id.reload_button)
    Button reloadButton;

    @BindView(R.id.recyclerView)
    RecyclerView myRecyclerView;
    private Unbinder unbinder;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static String jokesCount = "0";

    public static JokesFragment newInstance() {
        JokesFragment fragment = new JokesFragment();
        jokesCount = "0";
        return fragment;
    }

    public static JokesFragment newInstance(String number) {
        JokesFragment fragment = new JokesFragment();
        jokesCount = number;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jokes_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
        reloadButton.setOnClickListener(v -> JokesReloadButton());

        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        //Create subscriber, that returns a sevres response.
        Observable<JokesResponse> jokesResponseObservable = loadData();

        //Create a subscriber
        Disposable disposable = jokesResponseObservable
                .subscribe(this::updateViews);

        compositeDisposable.add(disposable);
    }

    //When there is response from server - using this method.
    //Jokes - server response.
    public void updateViews(JokesResponse jokesResponse) {
        if (jokesResponse != null && jokesResponse.getValue() != null) {
            //Adaptor creation
            JokesAdapter jokesAdapter = new JokesAdapter(getContext(), jokesResponse.getValue());
            myRecyclerView.setAdapter(jokesAdapter);

        }
    }

    public Observable<JokesResponse> loadData() {

        String extraFields = "id, joke, categories";
        String textFormat = "plain";

        return NetworkService.getInstance()//creating of HTTP client and method call from server
                .getJSONApi()
                .getJokes(jokesCount, extraFields, textFormat)
                .subscribeOn(Schedulers.io())//where executes
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void JokesReloadButton() {

        //change count of jokes
        String mJokesCount;
        if (textEditJokes.getText().toString().equals("")) {
            mJokesCount = "0";
        } else {
            mJokesCount = textEditJokes.getText().toString();
        }

        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, JokesFragment.newInstance(mJokesCount))
                .commitNow();
    }

    //extra path
    public interface Api {
        @GET("{count}")
        Observable<JokesResponse> getJokes(@Path("count") String count, @Query("fields") String field, @Query("text_format") String textFormat);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        compositeDisposable.dispose();
        super.onDestroyView();
    }

    public interface JokesChangeTitle {
        void jokesChangeTitle();
    }

}
