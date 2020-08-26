package eslam.emad.sofra.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eslam.emad.sofra.R;

public class RestaurantRegisterFragment extends Fragment {

    //FOR ME
    //when getting data successful navigateUp
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant_register, container, false);
    }
}