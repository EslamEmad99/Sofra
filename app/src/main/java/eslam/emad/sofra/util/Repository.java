package eslam.emad.sofra.util;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import eslam.emad.sofra.data.api.ApiClient;
import eslam.emad.sofra.data.models.city.City;
import eslam.emad.sofra.data.models.city.CityModel;
import eslam.emad.sofra.data.models.auth.AuthModel;
import eslam.emad.sofra.data.models.new_password.NewPasswordModel;
import eslam.emad.sofra.data.models.region.Region;
import eslam.emad.sofra.data.models.region.RegionModel;
import eslam.emad.sofra.data.models.reset_password.ResetPasswordModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eslam.emad.sofra.ui.fragments.ClientRegisterFragment.TAG;

public class Repository {

    private static Repository INSTANCE;
    private MutableLiveData<List<City>> allCities;
    private MutableLiveData<List<Region>> allRegions;
    private MutableLiveData<AuthModel> onLogin;
    private MutableLiveData<ResetPasswordModel> resetPassword;
    private MutableLiveData<NewPasswordModel> newPassword;
    private MutableLiveData<AuthModel> clientRegister;
    private MutableLiveData<AuthModel> restaurantRegister;

    private Repository() {
        allCities = new MutableLiveData<>();
        allRegions = new MutableLiveData<>();
        onLogin = new MutableLiveData<>();
        resetPassword = new MutableLiveData<>();
        newPassword = new MutableLiveData<>();
        clientRegister = new MutableLiveData<>();
        restaurantRegister = new MutableLiveData<>();
    }

    public static Repository getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    public MutableLiveData<List<City>> getAllCities() {
        ApiClient.getINSTANCE().getAllCities().enqueue(new Callback<CityModel>() {
            @Override
            public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                if (response.body() != null) {
                    if (response.body().getCityResponseData().getData() != null) {
                        allCities.setValue(response.body().getCityResponseData().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<CityModel> call, Throwable t) {

            }
        });
        return allCities;
    }

    public MutableLiveData<List<Region>> getAllRegions() {
        return allRegions;
    }

    public void setAllRegions(int cityId) {
        ApiClient.getINSTANCE().getRegions(cityId).enqueue(new Callback<RegionModel>() {
            @Override
            public void onResponse(Call<RegionModel> call, Response<RegionModel> response) {
                if (response.body() != null) {
                    if (response.body().getData() != null) {
                        allRegions.setValue(response.body().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<RegionModel> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<AuthModel> getOnLogin() {
        return onLogin;
    }

    public void setOnLogin(String parameter, String email, String password) {
        ApiClient.getINSTANCE().login(parameter, email, password).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                if (response.isSuccessful()) {
                    onLogin.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<ResetPasswordModel> getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(String userType, String email) {
        ApiClient.getINSTANCE().resetPassword(userType, email).enqueue(new Callback<ResetPasswordModel>() {
            @Override
            public void onResponse(Call<ResetPasswordModel> call, Response<ResetPasswordModel> response) {
                if (response.isSuccessful()) {
                    resetPassword.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordModel> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<NewPasswordModel> getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String userType, String code, String password, String passwordConformation) {
        ApiClient.getINSTANCE().newPassword(userType, code, password, passwordConformation).enqueue(new Callback<NewPasswordModel>() {
            @Override
            public void onResponse(Call<NewPasswordModel> call, Response<NewPasswordModel> response) {
                if (response.isSuccessful()) {
                    newPassword.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewPasswordModel> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<AuthModel> getClientRegister() {
        return clientRegister;
    }

    public void setClientRegister(RequestBody name,
                                  RequestBody email,
                                  RequestBody password,
                                  RequestBody passwordConfirmation,
                                  RequestBody phone,
                                  RequestBody regionId,
                                  MultipartBody.Part profileImage) {

        ApiClient.getINSTANCE().clientRegister(name, email, password, passwordConfirmation, phone, regionId, profileImage).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                if (response.isSuccessful()) {
                    clientRegister.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
            }
        });
    }

    public MutableLiveData<AuthModel> getRestaurantRegister() {
        return restaurantRegister;
    }

    public void setRestaurantRegister(RequestBody name,
                                      RequestBody email,
                                      RequestBody password,
                                      RequestBody passwordConfirmation,
                                      RequestBody phone,
                                      RequestBody whatsApp,
                                      RequestBody regionId,
                                      RequestBody deliveryCost,
                                      RequestBody minimumCharge,
                                      MultipartBody.Part photo,
                                      RequestBody deliveryTime) {

        ApiClient.getINSTANCE().restaurantRegister(name,
                email,
                password,
                passwordConfirmation,
                phone,
                whatsApp,
                regionId,
                deliveryCost,
                minimumCharge,
                photo,
                deliveryTime)
                .enqueue(new Callback<AuthModel>() {
                    @Override
                    public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                        if (response.isSuccessful()) {
                            restaurantRegister.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthModel> call, Throwable t) {

                    }
                });
    }
}