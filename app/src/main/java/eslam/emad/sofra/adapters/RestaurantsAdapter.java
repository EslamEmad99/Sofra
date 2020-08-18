package eslam.emad.sofra.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import eslam.emad.sofra.R;
import eslam.emad.sofra.data.models.restaurants.Restaurant;
import eslam.emad.sofra.databinding.RestaurantsItemBinding;
import eslam.emad.sofra.interfaces.RestaurantItemOnClick;

public class RestaurantsAdapter extends PagedListAdapter<Restaurant, RestaurantsAdapter.RestaurantsViewHolder> {

    private Context mCtx;
    private RestaurantItemOnClick onClick;

    public RestaurantsAdapter(Context mCtx, RestaurantItemOnClick onClick) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        RestaurantsItemBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.restaurants_item, parent, false);
        return new RestaurantsViewHolder(binding, onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsViewHolder holder, int position) {
        Restaurant restaurant = getItem(position);
        holder.binding.setRestaurant(restaurant);
        if (restaurant.getActivated().equals("1")) {
            holder.binding.setOnOf(mCtx.getString(R.string.open));
        }else {
            holder.binding.setOnOf(mCtx.getString(R.string.close));
        }
    }

    private static DiffUtil.ItemCallback<Restaurant> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Restaurant>() {
                @Override
                public boolean areItemsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
                    return oldItem.equals(newItem);
                }
            };

    static class RestaurantsViewHolder extends RecyclerView.ViewHolder {

        private RestaurantsItemBinding binding;

        RestaurantsViewHolder(RestaurantsItemBinding binding, RestaurantItemOnClick onClick) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setOnClick(onClick);
        }
    }
}
