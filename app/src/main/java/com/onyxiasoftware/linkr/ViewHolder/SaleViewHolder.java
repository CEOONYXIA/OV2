package com.onyxiasoftware.linkr.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.onyxiasoftware.linkr.Interface.ItemClickListener;
import com.onyxiasoftware.linkr.R;

/**
 * Created by Ramon on 4/18/2018.
 */

public class SaleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView sale_name;
    public ImageView sale_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public SaleViewHolder(View itemView) {
        super(itemView);

        sale_name = (TextView)itemView.findViewById(R.id.menu_name);
        sale_image = (ImageView)itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
