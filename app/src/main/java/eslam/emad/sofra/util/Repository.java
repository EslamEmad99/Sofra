package eslam.emad.sofra.util;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import eslam.emad.sofra.data.api.ApiClient;
import eslam.emad.sofra.data.models.city.City;
import eslam.emad.sofra.data.models.city.CityModel;
import eslam.emad.sofra.data.models.restaurants.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {

    private static Repository INSTANCE;
    private MutableLiveData<List<City>> allCities;

    private Repository() {

    }

    public static Repository getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    public MutableLiveData<List<City>> getAllCities() {
        allCities = new MutableLiveData<>();
        ApiClient.getINSTANCE().getAllCities().enqueue(new Callback<CityModel>() {
            @Override
            public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                if (response.body() != null) {
                    allCities.setValue(response.body().getCityResponseData().getData());
                }
            }

            @Override
            public void onFailure(Call<CityModel> call, Throwable t) {

            }
        });
        return allCities;
    }
}
