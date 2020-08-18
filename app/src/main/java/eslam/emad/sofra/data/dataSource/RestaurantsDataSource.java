package eslam.emad.sofra.data.dataSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import eslam.emad.sofra.data.api.ApiClient;
import eslam.emad.sofra.data.models.restaurants.Restaurant;
import eslam.emad.sofra.data.models.restaurants.RestaurantsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eslam.emad.sofra.util.Constants.KEYWORD;
import static eslam.emad.sofra.util.Constants.REGION_ID;

public class RestaurantsDataSource extends PageKeyedDataSource<Integer, Restaurant> {
    private static final String TAG = "RestaurantsDataSource";
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Restaurant> callback) {
        ApiClient.getINSTANCE().getAllRestaurants(KEYWORD , REGION_ID, 1)
                .enqueue(new Callback<RestaurantsModel>() {
                    @Override
                    public void onResponse(Call<RestaurantsModel> call, Response<RestaurantsModel> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().getRestaurantsResponseData().getData(), null, 2);
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantsModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Restaurant> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Restaurant> callback) {
        ApiClient.getINSTANCE().getAllRestaurants(KEYWORD ,REGION_ID, params.key)
                .enqueue(new Callback<RestaurantsModel>() {
                    @Override
                    public void onResponse(Call<RestaurantsModel> call, Response<RestaurantsModel> response) {
                        if (!response.body().getRestaurantsResponseData().getData().isEmpty() && response.body().getRestaurantsResponseData().getData() != null) {
                            callback.onResult(response.body().getRestaurantsResponseData().getData(), params.key + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantsModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}