package com.example.youmie.utils;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youmie.R;
import com.example.youmie.activities.DetailedActivity;

import java.util.List;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private final List<String> userNames;
    private final List<String> nameOfPlace;
    private final List<String> typesOfFood;
    private final List<String> prices;
    private final List<String> description;
    private final List<Integer> images;

    public RecycleAdapter(List<String> userNames, List<String> nameOfPlace, List<String> typesOfFood, List<String> prices, List<String> description, List<Integer> images) {
        this.userNames = userNames;
        this.nameOfPlace = nameOfPlace;
        this.typesOfFood = typesOfFood;
        this.prices = prices;
        this.description = description;
        this.images = images;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView itemUserName;
        private final TextView itemNameOfPlace;
        private final TextView itemPrice;
        private final TextView itemTypeOfFood;
        private final TextView itemDescription;
        private final ImageView itemPictures;

        public ViewHolder(View itemView) {
            super(itemView);
            itemUserName = itemView.findViewById(R.id.userNamePanel);
            itemNameOfPlace = itemView.findViewById(R.id.nameOfPlace);
            itemTypeOfFood = itemView.findViewById(R.id.typeOfFood);
            itemPrice = itemView.findViewById(R.id.price);
            itemDescription = itemView.findViewById(R.id.description);
            itemPictures = itemView.findViewById(R.id.imageIcon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(itemView.getContext(), DetailedActivity.class);
            intent.putExtra("username_key", ViewHolder.this.itemUserName.getText());
            intent.putExtra("placename_key", ViewHolder.this.itemNameOfPlace.getText());
            intent.putExtra("typeOfFood_key", ViewHolder.this.itemTypeOfFood.getText());
            intent.putExtra("price_key", ViewHolder.this.itemPrice.getText());
            intent.putExtra("description_key", ViewHolder.this.itemDescription.getText());

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
        holder.itemNameOfPlace.setText(nameOfPlace.get(position));
        holder.itemTypeOfFood.setText(typesOfFood.get(position));
        holder.itemPrice.setText(prices.get(position));
        holder.itemDescription.setText(description.get(position));
        holder.itemPictures.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return this.userNames.size();
    }
}
