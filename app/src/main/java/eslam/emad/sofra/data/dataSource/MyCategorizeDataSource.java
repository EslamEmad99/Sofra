package eslam.emad.sofra.data.dataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import eslam.emad.sofra.data.api.ApiClient;
import eslam.emad.sofra.data.models.my_categorize.MyCategorizeModel;
import eslam.emad.sofra.data.models.my_categorize.MyCategory;
import eslam.emad.sofra.data.models.restaurants.Restaurant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eslam.emad.sofra.util.Constants.API_TOKEN;

public class MyCategorizeDataSource extends PageKeyedDataSource<Integer, MyCategory> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MyCategory> callback) {
        ApiClient.getINSTANCE().getMyCategorize(API_TOKEN, 1).enqueue(new Callback<MyCategorizeModel>() {
            @Override
            public void onResponse(Call<MyCategorizeModel> call, Response<MyCategorizeModel> response) {
                if (response.body() != null) {
                    callback.onResult(response.body().getData().getData(), null, 2);
                }
            }

            @Override
            public void onFailure(Call<MyCategorizeModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MyCategory> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MyCategory> callback) {
        ApiClient.getINSTANCE().getMyCategorize(API_TOKEN, params.key).enqueue(new Callback<MyCategorizeModel>() {
            @Override
            public void onResponse(Call<MyCategorizeModel> call, Response<MyCategorizeModel> response) {
                if (!response.body().getData().getData().isEmpty() && response.body().getData().getData() != null) {
                    callback.onResult(response.body().getData().getData(), params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<MyCategorizeModel> call, Throwable t) {

            }
        });
    }
}
