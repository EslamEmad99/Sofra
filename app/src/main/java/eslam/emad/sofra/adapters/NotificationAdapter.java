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
import eslam.emad.sofra.data.models.notifications.Notification;
import eslam.emad.sofra.databinding.ItemClientNotificationBinding;
import eslam.emad.sofra.interfaces.NotificationOnClick;

public class NotificationAdapter extends PagedListAdapter<Notification, NotificationAdapter.ClientNotifactionViewHolder> {

    private Context mCtx;
    private NotificationOnClick onClick;

    public NotificationAdapter(Context mCtx, NotificationOnClick onClick) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ClientNotifactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        ItemClientNotificationBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_client_notification, parent, false);
        return new NotificationAdapter.ClientNotifactionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientNotifactionViewHolder holder, int position) {
        Notification notification = getItem(position);
        holder.binding.setOnclick(onClick);
        holder.binding.setClientNotification(notification);
        if (notification.getOrder().getState().equals("delivered")){
            holder.binding.itemNotificationAlarmImg.setImageResource(R.drawable.ic_notification);
        }else {
            holder.binding.itemNotificationAlarmImg.setImageResource(R.drawable.ic_notification_none);
        }
    }

    private static DiffUtil.ItemCallback<Notification> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Notification>() {
                @Override
                public boolean areItemsTheSame(@NonNull Notification oldItem, @NonNull Notification newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Notification oldItem, @NonNull Notification newItem) {
                    return oldItem.equals(newItem);
                }
            };

    static class ClientNotifactionViewHolder extends RecyclerView.ViewHolder {

        private ItemClientNotificationBinding binding;

        public ClientNotifactionViewHolder(ItemClientNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}