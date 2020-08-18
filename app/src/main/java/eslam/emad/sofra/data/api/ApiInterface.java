package eslam.emad.sofra.data.api;

import eslam.emad.sofra.data.models.city.CityModel;
import eslam.emad.sofra.data.models.my_categorize.MyCategorizeModel;
import eslam.emad.sofra.data.models.restaurants.RestaurantsModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("restaurants")
    Call<RestaurantsModel> getAllRestaurants(@Query("page") int page,
                                             @Query("keyword") String keyword,
                                             @Query("region_id") int region_id);

    @GET("cities")
    Call<CityModel> getAllCities();

    @GET("restaurant/my-categories")
    Call<MyCategorizeModel> getMyCategorize(@Query("api_token") String api_token,
                                            @Query("page") int page);
}