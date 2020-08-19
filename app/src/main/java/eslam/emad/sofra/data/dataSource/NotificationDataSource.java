package eslam.emad.sofra.data.dataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import eslam.emad.sofra.data.api.ApiClient;
import eslam.emad.sofra.data.models.notifications.Notification;
import eslam.emad.sofra.data.models.notifications.NotificationModel;
import eslam.emad.sofra.util.Constants;
import eslam.emad.sofra.util.UserType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eslam.emad.sofra.util.Constants.CLIENT_API_TOKEN;
import static eslam.emad.sofra.util.Constants.RESTAURANT_API_TOKEN;
import static eslam.emad.sofra.util.Constants.USER_TYPE;

public class NotificationDataSource extends PageKeyedDataSource<Integer, Notification> {
    String apiToken;
    String parameter;
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Notification> callback) {
        if (USER_TYPE == UserType.CLIENT){
            apiToken = CLIENT_API_TOKEN;
            parameter = "client";
        } else if (USER_TYPE == UserType.RESTAURANT){
            apiToken = RESTAURANT_API_TOKEN;
            parameter = "restaurant";
        }
        ApiClient.getINSTANCE().getNotifications(parameter, apiToken, 1).enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if (response.body().getData() != null) {
                    callback.onResult(response.body().getData().getData(), null, 2);
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Notification> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Notification> callback) {
        ApiClient.getINSTANCE().getNotifications(parameter, apiToken, params.key).enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if (!response.body().getData().getData().isEmpty() && response.body().getData().getData() != null) {
                    callback.onResult(response.body().getData().getData(), params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {

            }
        });
    }
}
