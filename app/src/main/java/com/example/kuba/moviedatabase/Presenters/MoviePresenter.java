package com.example.kuba.moviedatabase.Presenters;

import com.appunite.rx.ObservableExtensions;
import com.appunite.rx.ResponseOrError;
import com.example.kuba.moviedatabase.Daos.MovieDao;
import com.example.kuba.moviedatabase.Models.Search;
import com.google.common.base.Strings;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class MoviePresenter {

    @Nonnull
    private MovieDao movieDao;
    @Nonnull
    private final Observable<ResponseOrError<String>> titleObservable;

    @Inject
    public MoviePresenter(@Nonnull MovieDao movieDao) {
        this.movieDao = movieDao;

        titleObservable = searchObservable()
                .compose(ResponseOrError.map(new Func1<Search, String>() {
                    @Override
                    public String call(Search search) {
                        return search.getMoviesList().get(0).getTitle();
                    }
                }))
                .compose(ObservableExtensions.<ResponseOrError<String>>behaviorRefCount());

    }

    @Nonnull
    private Observable<ResponseOrError<Search>> searchObservable() {
        return this.movieDao.searchObservable();
    }
    @Nonnull
    public Observable<String> titleObservable() {
        return titleObservable.compose(ResponseOrError.<String>onlySuccess());
    }
}
