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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eslam.emad.sofra.R;
import eslam.emad.sofra.adapters.MyCategorizeAdapter;
import eslam.emad.sofra.adapters.RestaurantsAdapter;
import eslam.emad.sofra.data.models.city.City;
import eslam.emad.sofra.data.models.my_categorize.MyCategory;
import eslam.emad.sofra.data.models.restaurants.Restaurant;
import eslam.emad.sofra.databinding.FragmentHomeBinding;
import eslam.emad.sofra.interfaces.FragmentHomeOnClick;
import eslam.emad.sofra.interfaces.MyCategorizeOnClick;
import eslam.emad.sofra.interfaces.RestaurantItemOnClick;
import eslam.emad.sofra.ui.view_model.ApplicationViewModel;
import eslam.emad.sofra.ui.view_model.MyCategorizeViewModel;
import eslam.emad.sofra.ui.view_model.RestaurantsFragmentViewModel;
import eslam.emad.sofra.util.UserType;

import static eslam.emad.sofra.util.Constants.KEYWORD;
import static eslam.emad.sofra.util.Constants.REGION_ID;
import static eslam.emad.sofra.util.Constants.USER_TYPE;

public class HomeFragment extends Fragment implements RestaurantItemOnClick, FragmentHomeOnClick, MyCategorizeOnClick {

    private FragmentHomeBinding binding;
    private ApplicationViewModel applicationViewModel;
    private RestaurantsFragmentViewModel restaurantsFragmentViewModel;
    private MyCategorizeViewModel myCategorizeViewModel;
    private ArrayList<City> cityArrayList;
    private RestaurantsAdapter restaurantsAdapter;
    private MyCategorizeAdapter myCategorizeAdapter;
    private ArrayAdapter<City> cityArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();

        if (USER_TYPE == UserType.CLIENT) {
            restaurantsAdapter = new RestaurantsAdapter(getContext(), this);
            restaurantsFragmentViewModel = new ViewModelProvider(this).get(RestaurantsFragmentViewModel.class);
            applicationViewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
            restaurantsFragmentViewModel.getItemPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Restaurant>>() {
                @Override
                public void onChanged(PagedList<Restaurant> restaurants) {
                    restaurantsAdapter.submitList(restaurants);
                }
            });
            binding.setAdapter(restaurantsAdapter);

            cityArrayList = new ArrayList<>();
            cityArrayList.add(new City(0, "المدينة"));

            applicationViewModel.getAllCities().observe(getViewLifecycleOwner(), new Observer<List<City>>() {
                @Override
                public void onChanged(List<City> cities) {
                    if (cities != null) {
                        cityArrayList.clear();
                        cityArrayList.add(new City(0, "المدينة"));
                        cityArrayList.addAll(cities);
                        cityArrayAdapter.notifyDataSetChanged();
                    }
                }
            });
            cityArrayAdapter = new ArrayAdapter<City>(getContext(), android.R.layout.simple_spinner_item, cityArrayList);
            cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.fragmentHomeCitySpinner.setAdapter(cityArrayAdapter);
            binding.fragmentHomeFab.hide();
        } else if (USER_TYPE == UserType.RESTAURANT) {
            binding.constraintLayout1.setVisibility(View.GONE);
            binding.constraintLayout2.setVisibility(View.GONE);
            binding.fragmentHomeFab.show();

            myCategorizeAdapter = new MyCategorizeAdapter(getContext(), this);
            myCategorizeViewModel = new ViewModelProvider(this).get(MyCategorizeViewModel.class);
            myCategorizeViewModel.getItemPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<MyCategory>>() {
                @Override
                public void onChanged(PagedList<MyCategory> categories) {
                    //Toast.makeText(getContext(), "Toast", Toast.LENGTH_SHORT).show();
                    myCategorizeAdapter.submitList(categories);
                }
            });
            binding.setAdapter(myCategorizeAdapter);
        }
        binding.setLayoutManger(new LinearLayoutManager(getContext()));
        binding.setOnClick(this);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return view;
    }

    @Override
    public void onRestaurantClick(Restaurant restaurant) {
        Toast.makeText(getContext(), restaurant.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchButton() {
        KEYWORD = binding.fragmentHomeRestaurantSearchName.getText().toString();
        REGION_ID = 0;
        restaurantsFragmentViewModel.refresh();
    }

    @Override
    public void onFABClicked() {
        Toast.makeText(getContext(), "FAB", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryClick(MyCategory category) {
        Toast.makeText(getContext(), "onCategoryClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryDelete(MyCategory category) {
        Toast.makeText(getContext(), "onCategoryDelete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryEdit(MyCategory category) {
        Toast.makeText(getContext(), "onCategoryEdit", Toast.LENGTH_SHORT).show();
    }
}