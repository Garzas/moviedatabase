package com.example.kuba.moviedatabase;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.appunite.rx.android.MoreViewActions;
import com.example.kuba.moviedatabase.Presenters.MoviePresenter;

import rx.android.view.ViewActions;


public class MainActivity extends BaseActivity {

    private MoviePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text= (TextView) findViewById(R.id.someText);


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

        presenter.titleObservable()
                .compose(lifecycleMainObservable.<String>bindLifecycle())
                .subscribe(ViewActions.setText(text));

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
