package cl.ione.globallogic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.ione.globallogic.Data.NewsData;
import cl.ione.globallogic.Holder.NewsHolder;
import cl.ione.globallogic.R;
import cl.ione.globallogic.Utilities.Util;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
    //Variables
    private Context mContext;
    private final List<NewsData> mItemList;

    public NewsAdapter(Context context, List<NewsData> mItemList) {
        this.mContext = context;
        this.mItemList = mItemList;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new NewsHolder(mContext, layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Util.downloadImage(mItemList.get(position).getImage(), holder.mImage);
        holder.imageUrl = mItemList.get(position).getImage();
        holder.mTitle.setText(mItemList.get(position).getTitle());
        holder.mDescription.setText(mItemList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}