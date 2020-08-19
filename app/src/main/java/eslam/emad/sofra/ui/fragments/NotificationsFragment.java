package eslam.emad.sofra.ui.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import eslam.emad.sofra.R;
import eslam.emad.sofra.adapters.NotificationAdapter;
import eslam.emad.sofra.data.models.notifications.Notification;
import eslam.emad.sofra.databinding.FragmentNotificationsBinding;
import eslam.emad.sofra.interfaces.NotificationOnClick;
import eslam.emad.sofra.ui.view_model.ClientNotificationViewModel;
import eslam.emad.sofra.util.UserType;

import static eslam.emad.sofra.util.Constants.USER_TYPE;

public class NotificationsFragment extends Fragment implements NotificationOnClick {

    FragmentNotificationsBinding binding;
    ClientNotificationViewModel clientNotificationViewModel;
    NotificationAdapter notificationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);
        View view = binding.getRoot();
        notificationAdapter = new NotificationAdapter(getContext(), this);
        clientNotificationViewModel = new ViewModelProvider(this).get(ClientNotificationViewModel.class);
        clientNotificationViewModel.getItemPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Notification>>() {
            @Override
            public void onChanged(PagedList<Notification> notifications) {
                notificationAdapter.submitList(notifications);
            }
        });
        binding.setAdapter(notificationAdapter);
        binding.setLayoutManger(new LinearLayoutManager(getContext()));
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return view;
    }

    @Override
    public void onNotificationClicked(Notification notification) {
        Toast.makeText(getContext(), notification.getContent(), Toast.LENGTH_SHORT).show();
    }
}