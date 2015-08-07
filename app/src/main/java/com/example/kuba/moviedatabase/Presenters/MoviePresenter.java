package com.example.kuba.moviedatabase.presenters;

import android.widget.ImageView;

import com.appunite.rx.ObservableExtensions;
import com.appunite.rx.ResponseOrError;
import com.example.kuba.moviedatabase.daos.MovieDao;
import com.example.kuba.moviedatabase.models.Movies;
import com.example.kuba.moviedatabase.models.Search;
import com.example.kuba.moviedatabase.detector.SimpleDetector;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;


import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.Observers;

public class MoviePresenter {

    @Nonnull
    private MovieDao movieDao;
    @Nonnull
    private final Observable<ImmutableList<AdapterMovies>> titleObservable;

    @Inject
    public MoviePresenter(@Nonnull MovieDao movieDao) {
        this.movieDao = movieDao;

        titleObservable = searchObservable()
                .compose(ResponseOrError.<Search>onlySuccess())
                .flatMap(new Func1<Search, Observable<ImmutableList<AdapterMovies>>>() {
                    @Override
                    public Observable<ImmutableList<AdapterMovies>> call(Search search) {
                        return Observable.just(ImmutableList.copyOf(Lists.transform(search.getMoviesList(),
                                new Function<Movies, AdapterMovies>() {
                                    @Nullable
                                    @Override
                                    public AdapterMovies apply(@Nullable Movies movie) {
                                        return new AdapterMovies(movie.getImdbID(), movie.getTitle());
                                    }
                                })));
                    }
                })
                .compose(ObservableExtensions.<ImmutableList<AdapterMovies>>behaviorRefCount());
    }

    @Nonnull
    private Observable<ResponseOrError<Search>> searchObservable() {
        return this.movieDao.searchObservable();
    }
    @Nonnull
    public Observable<ImmutableList<AdapterMovies>> titleObservable() {
        return titleObservable;
    }


    public class AdapterMovies implements SimpleDetector.Detectable<AdapterMovies> {


        @Nullable
        private final String name;
        @Nonnull
        private final String id;

        public AdapterMovies(String imdbID, @Nullable String name) {
            this.name = name;
            this.id= imdbID;
        }

        @Nonnull
        public String getId() {
            return id;
        }

        @Nullable
        public String getName() {
            return name;
        }

//
//        @Nonnull
//        public Observer<ImageView> clickObserver() {
//            return Observers.create(new Action1<ImageView>() {
//                @Override
//                public void call(ImageView imageViewCdCover) {
//                    //        openDetailsSubject.onNext(new BothParams<>(AdapterItem.this, imageViewCdCover));
//                }
//            });
//        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AdapterMovies that = (AdapterMovies) o;
            return Objects.equal(name, that.name);
        }


        @Override
        public boolean matches(@Nonnull AdapterMovies item) {
            return false;
        }

        @Override
        public boolean same(@Nonnull AdapterMovies item) {
            return false;
        }
    }
}
