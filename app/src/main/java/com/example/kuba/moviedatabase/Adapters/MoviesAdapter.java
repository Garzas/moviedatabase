package com.example.kuba.moviedatabase.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuba.moviedatabase.R;
import com.example.kuba.moviedatabase.detector.ChangesDetector;
import com.example.kuba.moviedatabase.detector.SimpleDetector;
import com.example.kuba.moviedatabase.presenters.MoviePresenter;
import com.google.common.collect.ImmutableList;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(@Nonnull MoviePresenter.AdapterMovies item);

    public abstract void recycle();
}


public class MoviesAdapter extends RecyclerView.Adapter<BaseViewHolder> implements
        Action1<ImmutableList< MoviePresenter.AdapterMovies>>, ChangesDetector.ChangesAdapter {

    @Nonnull
    private final ChangesDetector<MoviePresenter.AdapterMovies, MoviePresenter.AdapterMovies> changesDetector;
    @Nonnull
    private final Picasso picasso;
    @Nonnull
    private List<MoviePresenter.AdapterMovies> mItems = ImmutableList.of();


    @Inject
    public MoviesAdapter(@Nonnull final Picasso picasso) {
        this.picasso = picasso;
        this.changesDetector = new ChangesDetector<>(new SimpleDetector<MoviePresenter.AdapterMovies>());
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_main_items_cell, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        super.onViewRecycled(holder);
        holder.recycle();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void call(ImmutableList<MoviePresenter.AdapterMovies> adapterItems) {
        mItems = ImmutableList.copyOf(adapterItems);
        changesDetector.newData(this, adapterItems, false);
    }



    public class MyViewHolder extends BaseViewHolder {

        @InjectView(R.id.movieTitle)
        TextView title;


        private CompositeSubscription subscription;

        public MyViewHolder(@Nonnull View itemView) {
            super(itemView);
           ButterKnife.inject(this, itemView);
        }

        public void bind(@Nonnull MoviePresenter.AdapterMovies item) {


            title.setText(item.getName());

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

