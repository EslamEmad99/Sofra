package eslam.emad.sofra.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import eslam.emad.sofra.R;

public class BindingAdapter {

    @androidx.databinding.BindingAdapter("imageResource")
    public static void setImageResource(ImageView view, String imageUrl) {
        Glide
                .with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.place_holder)
                .fitCenter()
                .into(view);
    }

    @androidx.databinding.BindingAdapter("set_adapter")
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
    }

    @androidx.databinding.BindingAdapter("set_layout_manager")
    public static void setLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
    }
}
