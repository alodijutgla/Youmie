package com.example.youmie.utils;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youmie.R;
import com.example.youmie.activities.DetailedActivity;

import java.util.List;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private final List<String> userNames;
    private final List<String> prices;
    private final List<String> typesOfFood;
    private final List<String> details;
    private final List<Integer> images;

    public RecycleAdapter(List<String> userNames, List<String> prices, List<String> typesOfFood, List<String> details, List<Integer> images) {
        this.userNames = userNames;
        this.prices = prices;
        this.typesOfFood = typesOfFood;
        this.details = details;
        this.images = images;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView itemUserName;
        private final TextView itemPrice;
        private final TextView itemTypeOfFood;
        private final TextView itemDetail;
        private final ImageView itemPictures;

        public ViewHolder(View itemView) {
            super(itemView);
            itemUserName = itemView.findViewById(R.id.userNamePanel);
            itemPrice = itemView.findViewById(R.id.price);
            itemTypeOfFood = itemView.findViewById(R.id.typeOfFood);
            itemDetail = itemView.findViewById(R.id.description);
            itemPictures = itemView.findViewById(R.id.imageIcon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = ViewHolder.this.getAdapterPosition();

            Toast.makeText(itemView.getContext(), (CharSequence) ("You clicked on item # " + (position + 1)), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(itemView.getContext(), DetailedActivity.class);
            intent.putExtra("username_key", ViewHolder.this.itemUserName.getText());
            intent.putExtra("price_key", ViewHolder.this.itemPrice.getText());
            intent.putExtra("typeOfFood_key", ViewHolder.this.itemTypeOfFood.getText());
            intent.putExtra("detail_key", ViewHolder.this.itemDetail.getText());

            Bitmap bitmap = ((BitmapDrawable) ViewHolder.this.itemPictures.getDrawable()).getBitmap();
            intent.putExtra("image_key", bitmap);
            itemView.getContext().startActivity(intent);
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
        holder.itemUserName.setText(userNames.get(position));
        holder.itemPrice.setText(prices.get(position));
        holder.itemTypeOfFood.setText(typesOfFood.get(position));
        holder.itemDetail.setText(details.get(position));
        holder.itemPictures.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return this.userNames.size();
    }
}
