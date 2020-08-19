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

import com.daimajia.swipe.SwipeLayout;

import eslam.emad.sofra.R;
import eslam.emad.sofra.data.models.my_categorize.MyCategory;
import eslam.emad.sofra.databinding.ItemCategorizeBinding;
import eslam.emad.sofra.interfaces.MyCategorizeOnClick;

public class MyCategorizeAdapter extends PagedListAdapter<MyCategory, MyCategorizeAdapter.MyCategorizeViewHolder> {

    private Context mCtx;
    private MyCategorizeOnClick onClick;

    public MyCategorizeAdapter(Context mCtx, MyCategorizeOnClick onClick) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyCategorizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        ItemCategorizeBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_categorize, parent, false);
        return new MyCategorizeAdapter.MyCategorizeViewHolder(binding, onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCategorizeViewHolder holder, int position) {
        MyCategory myCategory = getItem(position);
        holder.binding.setCategory(myCategory);
        holder.binding.swipe.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.binding.swipe.addDrag(SwipeLayout.DragEdge.Left, holder.binding.swipe.findViewById(R.id.linear1));
        holder.binding.swipe.setRightSwipeEnabled(false);
    }

    private static DiffUtil.ItemCallback<MyCategory> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MyCategory>() {
                @Override
                public boolean areItemsTheSame(@NonNull MyCategory oldItem, @NonNull MyCategory newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull MyCategory oldItem, @NonNull MyCategory newItem) {
                    return oldItem.equals(newItem);
                }
            };

    static class MyCategorizeViewHolder extends RecyclerView.ViewHolder {

        private ItemCategorizeBinding binding;

        public MyCategorizeViewHolder(ItemCategorizeBinding binding, MyCategorizeOnClick onClick) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setOnClick(onClick);
        }
    }
}
