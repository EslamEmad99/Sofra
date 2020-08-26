package eslam.emad.sofra.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eslam.emad.sofra.R;
import eslam.emad.sofra.databinding.FragmentMoreBinding;
import eslam.emad.sofra.ui.activities.AuthActivity;
import eslam.emad.sofra.util.SharedPreferencesManger;
import eslam.emad.sofra.util.UserType;

import static eslam.emad.sofra.util.Constants.CLIENT_LOGGED_IN;
import static eslam.emad.sofra.util.Constants.RESTAURANT_LOGGED_IN;
import static eslam.emad.sofra.util.Constants.USER_TYPE;

public class MoreFragment extends Fragment {

    FragmentMoreBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        View view = binding.getRoot();

        //FOR ME
        //if USER_TYPE == UserType.CLIENT && client logged in
        //set Visibility to GONE
        if (USER_TYPE == UserType.CLIENT) {
            if (SharedPreferencesManger.getINSTANCE(getContext()).getBooleanValue(CLIENT_LOGGED_IN)) {
                binding.button.setVisibility(View.GONE);
            } else {
                binding.button.setVisibility(View.VISIBLE);
                binding.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), AuthActivity.class));
                    }
                });
            }
        } else if (USER_TYPE == UserType.RESTAURANT) {
            binding.button.setVisibility(View.GONE);
        }
        return view;
    }
}