package eslam.emad.sofra.ui.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import eslam.emad.sofra.data.api.ApiClient;
import eslam.emad.sofra.data.models.city.City;
import eslam.emad.sofra.data.models.region.Region;
import eslam.emad.sofra.util.Repository;

public class ApplicationViewModel extends ViewModel {
    private MutableLiveData<List<Region>> allRegions;
    private MutableLiveData<List<City>> allCities;

    public ApplicationViewModel() {
        allRegions = new MutableLiveData<>();
        allRegions = Repository.getINSTANCE().getAllRegions();

        allCities = new MutableLiveData<>();
        allCities = Repository.getINSTANCE().getAllCities();
    }

    public MutableLiveData<List<City>> getAllCities() {
        return allCities;
    }

    public MutableLiveData<List<Region>> getAllRegions() {
        return allRegions;
    }

    public void setAllRegions(int cityId) {
        Repository.getINSTANCE().setAllRegions(cityId);
    }
}