package cl.ione.globallogic.Holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cl.ione.globallogic.Activities.DetailActivity;
import cl.ione.globallogic.R;

public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Controles
    public ImageView mImage;
    public TextView mTitle, mDescription;

    //Variables
    public Context mContext;
    public String imageUrl;

    public NewsHolder(Context context, @NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mContext = context;
        mImage = itemView.findViewById(R.id.item_image);
        mTitle = itemView.findViewById(R.id.item_title);
        mDescription = itemView.findViewById(R.id.item_description);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra("image", imageUrl);
        intent.putExtra("title", mTitle.getText().toString());
        intent.putExtra("description", mDescription.getText().toString());
        mContext.startActivity(intent);
    }
}
