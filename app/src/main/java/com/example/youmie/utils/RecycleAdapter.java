package com.example.youmie.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youmie.R;

import java.util.List;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private final List<String> titles;
    private final List<String> prices;
    private final List<String> details;
    private final List<Integer> images;

    public RecycleAdapter(List<String> titles, List<String> prices, List<String> details, List<Integer> images) {
        this.titles = titles;
        this.prices = prices;
        this.details = details;
        this.images = images;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemTitle;
        private final TextView itemPrice;
        private final TextView itemDetail;
        private final ImageView itemPictures;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.title);
            itemPrice = itemView.findViewById(R.id.price);
            itemDetail = itemView.findViewById(R.id.description);
            itemPictures = itemView.findViewById(R.id.imageIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = ViewHolder.this.getAdapterPosition();
                    Toast.makeText(itemView.getContext(), (CharSequence) ("You clicked on item # " + (position + 1)), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitle.setText(titles.get(position));
        holder.itemPrice.setText(prices.get(position));
        holder.itemDetail.setText(details.get(position));
        holder.itemPictures.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return this.titles.size();
    }
}
