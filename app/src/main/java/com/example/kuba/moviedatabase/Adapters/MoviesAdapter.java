package com.example.kuba.moviedatabase.Adapters;


import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuba.moviedatabase.Presenters.MoviePresenter;
import com.example.kuba.moviedatabase.detector.ChangesDetector;
import com.example.kuba.moviedatabase.detector.SimpleDetector;
import com.google.common.collect.ImmutableList;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class MoviesAdapter extends RecyclerView.Adapter<BaseViewHolder> implements
        Action1<ImmutableList< MoviePresenter.AdapterItem>>, ChangesDetector.ChangesAdapter {

    @Nonnull
    private final ChangesDetector<MoviePresenter.AdapterItem, MoviePresenter.AdapterItem> changesDetector;
    @Nonnull
    private final Picasso picasso;
    @Nonnull
    private List<MoviePresenter.AdapterItem> mItems = ImmutableList.of();


    @Inject
    public MoviesAdapter(@Nonnull final Picasso picasso) {
        this.picasso = picasso;
        this.changesDetector = new ChangesDetector<>(new SimpleDetector<MoviePresenter.AdapterItem>());
    }




    @Override
    public void call(ImmutableList<MoviePresenter.AdapterItem> adapterItems) {

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }





    public class MyViewHolder extends BaseViewHolder {



        private CompositeSubscription subscription;

        public MyViewHolder(@Nonnull View itemView) {
            super(itemView);
         //   ButterKnife.inject(this, itemView);
        }

        public void bind(@Nonnull MoviePresenter.AdapterItem item) {

//            picasso.load(item.getPreviewImageUrl())
//                    .into(mImageView);
//            String b = item.getName() + ", Offset: " + item.getOffset();
//            mTextView.setText(b);
//
//            subscription = new CompositeSubscription(ViewObservable.clicks(layoutItemCell)
//                    .map(new Func1<OnClickEvent, ImageView>() {
//                        @Override
//                        public ImageView call(OnClickEvent onClickEvent) {
//                            return mImageView;
//                        }
//                    })
//                    .subscribe(item.clickObserver())
//            );
        }

        @Override
        public void recycle() {
            subscription.unsubscribe();
        }


    }
}




















abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(@Nonnull MoviePresenter.AdapterItem item);

    public abstract void recycle();
}