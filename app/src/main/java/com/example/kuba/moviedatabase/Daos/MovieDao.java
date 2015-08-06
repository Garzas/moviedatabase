package com.example.kuba.moviedatabase.Daos;

import com.appunite.rx.ResponseOrError;
import com.appunite.rx.operators.MoreOperators;
import com.appunite.rx.operators.OperatorMergeNextToken;
import com.appunite.rx.subjects.CacheSubject;
import com.example.kuba.moviedatabase.Models.Movies;
import com.example.kuba.moviedatabase.Models.Search;
import com.example.kuba.moviedatabase.Retrofit;
import com.example.kuba.moviedatabase.schedulers.ObserveOnScheduler;
import com.example.kuba.moviedatabase.schedulers.SubscribeOnScheduler;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class MovieDao {
    @Nonnull
    private final PublishSubject<Object> loadMoreSubject = PublishSubject.create();
    @Nonnull
    private final Observable<ResponseOrError<Search>> search;
    @Nonnull
    private final Scheduler observeOnScheduler;
    @Nonnull
    private final Scheduler subscribeOnScheduler;
    @Nonnull
    private final Retrofit retrofit;


    @Inject
    public MovieDao(@ObserveOnScheduler final Scheduler observeOnScheduler,
                    @SubscribeOnScheduler final Scheduler subscribeOnSchedule,
                    @Nonnull final Retrofit retrofit){
        this.retrofit= retrofit;
        this.observeOnScheduler = observeOnScheduler;
        this.subscribeOnScheduler = subscribeOnSchedule;


        final OperatorMergeNextToken<Search, Object> mergePostsNextToken =
                OperatorMergeNextToken
                        .create(new Func1<Search, Observable<Search>>() {
                            @Override
                            public Observable<Search> call(Search search) {
                                return retrofit.movieList("gangster");
                            }
                        });



        search = loadMoreSubject.startWith((Object) null)
                .lift(mergePostsNextToken)
                .compose(ResponseOrError.<Search>toResponseOrErrorObservable())
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler);




    }
    @Nonnull
    public Observable<ResponseOrError<Search>> searchObservable() {
        return search;
    }

//    private static class MergeTwoResponses implements rx.functions.Func2<Search, Search, Search> {
//        @Override
//        public Search call(Search previous, Search moreData) {
//            final ImmutableList<Movies> posts = ImmutableList.<Movies>builder()
//                    .addAll(previous.items())
//                    .addAll(moreData.items())
//                    .build();
//            return new PostsResponse(moreData.title(), posts, moreData.nextToken());
//        }
//    }



}
