package com.example.kuba.moviedatabase.Presenters;

import android.widget.ImageView;

import com.appunite.rx.ObservableExtensions;
import com.appunite.rx.ResponseOrError;
import com.appunite.rx.functions.BothParams;
import com.example.kuba.moviedatabase.Daos.MovieDao;
import com.example.kuba.moviedatabase.Models.Search;
import com.example.kuba.moviedatabase.detector.SimpleDetector;
import com.google.common.base.Objects;


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


    public class AdapterItem implements SimpleDetector.Detectable<AdapterItem> {

        @Nonnull
        private final String id;
        @Nullable
        private final String name;
        @Nonnull
        private final String offset;
        @Nonnull
        private final String previewImageUrl;

        public AdapterItem(@Nonnull String id,
                           @Nullable String name,
                           @Nonnull String offset,
                           @Nonnull String previewImageUrl) {
            this.id = id;
            this.name = name;
            this.offset = offset;
            this.previewImageUrl = previewImageUrl;
        }

        @Nonnull
        public String getId() {
            return id;
        }

        @Nullable
        public String getName() {
            return name;
        }

        @Nonnull
        public String getOffset() {
            return offset;
        }

        @Nonnull
        public String getPreviewImageUrl() {
            return previewImageUrl;
        }

        @Nonnull
        public Observer<ImageView> clickObserver() {
            return Observers.create(new Action1<ImageView>() {
                @Override
                public void call(ImageView imageViewCdCover) {
            //        openDetailsSubject.onNext(new BothParams<>(AdapterItem.this, imageViewCdCover));
                }
            });
        }

        @Override
        public boolean matches(@Nonnull AdapterItem item) {
            return Objects.equal(id, item.id);
        }

        @Override
        public boolean same(@Nonnull AdapterItem item) {
            return equals(item);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AdapterItem that = (AdapterItem) o;
            return Objects.equal(id, that.id) &&
                    Objects.equal(name, that.name) &&
                    Objects.equal(offset, that.offset) &&
                    Objects.equal(previewImageUrl, that.previewImageUrl);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id, name, offset, previewImageUrl);
        }
    }
}
