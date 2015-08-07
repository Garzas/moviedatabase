package com.example.kuba.moviedatabase;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.appunite.rx.android.MoreViewObservables;
import com.example.kuba.moviedatabase.adapters.MoviesAdapter;
import com.example.kuba.moviedatabase.helpers.LoadMoreHelper;
import com.example.kuba.moviedatabase.presenters.MoviePresenter;
import com.google.common.collect.ImmutableList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity {

    @Inject
    MoviesAdapter myListViewAdapter;
    @InjectView(R.id.listView)
    RecyclerView recyclerView;
    private MoviePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        presenter = MainApplication
                .fromApplication(getApplication())
                .objectGraph()
                .plus(new Module())
                .get(MoviePresenter.class);

        MainApplication
                .fromApplication(getApplication())
                .objectGraph()
                .plus(new Module())
                .inject(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myListViewAdapter);

        presenter.titleObservable()
                .compose(lifecycleMainObservable.<ImmutableList<MoviePresenter.AdapterMovies>>bindLifecycle())
                        .subscribe(myListViewAdapter);

//        MoreViewObservables.scroll(recyclerView)
//                .filter(LoadMoreHelper.mapToNeedLoadMore(layoutManager, myListViewAdapter))
//                .compose(lifecycleMainObservable.bindLifecycle())
//                .subscribe(presenter.loadMoreObserver());
    }




    @dagger.Module(
            injects = {
                    MoviePresenter.class,
                    MainActivity.class

            },
            addsTo = MainApplication.Module.class
    )
    class Module {
    }

}
