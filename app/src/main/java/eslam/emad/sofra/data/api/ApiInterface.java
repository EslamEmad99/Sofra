package eslam.emad.sofra.data.api;

import eslam.emad.sofra.data.models.city.CityModel;
import eslam.emad.sofra.data.models.auth.AuthModel;
import eslam.emad.sofra.data.models.new_password.NewPasswordModel;
import eslam.emad.sofra.data.models.notifications.NotificationModel;
import eslam.emad.sofra.data.models.my_categorize.MyCategorizeModel;
import eslam.emad.sofra.data.models.region.RegionModel;
import eslam.emad.sofra.data.models.reset_password.ResetPasswordModel;
import eslam.emad.sofra.data.models.restaurants.RestaurantsModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("restaurants")
    Call<RestaurantsModel> getAllRestaurants(@Query("page") int page,
                                             @Query("keyword") String keyword,
                                             @Query("region_id") int region_id);

    @GET("cities")
    Call<CityModel> getAllCities();

    @GET("regions-not-paginated")
    Call<RegionModel> getRegions(@Query("city_id") int cityId);

    @GET("restaurant/my-categories")
    Call<MyCategorizeModel> getMyCategorize(@Query("api_token") String api_token,
                                            @Query("page") int page);

    @GET("{parameter}/notifications")
    Call<NotificationModel> getNotifications(@Path("parameter") String parameter,
                                             @Query("api_token") String api_token,
                                             @Query("page") int page);

    @POST("{parameter}/login")
    @FormUrlEncoded
    Call<AuthModel> login(@Path("parameter") String parameter,
                          @Field("email") String email,
                          @Field("password") String password);

    @POST("{parameter}/reset-password")
    @FormUrlEncoded
    Call<ResetPasswordModel> resetPassword(@Path("parameter") String userType,
                                           @Field("email") String email);

    @POST("{parameter}/new-password")
    @FormUrlEncoded
    Call<NewPasswordModel> newPassword(@Path("parameter") String userType,
                                       @Field("code") String code,
                                       @Field("password") String password,
                                       @Field("password_confirmation") String passwordConfirmation);

    @POST("client/sign-up")
    @Multipart
    Call<AuthModel> clientRegister(@Part("name") RequestBody name,
                                   @Part("email") RequestBody email,
                                   @Part("password") RequestBody password,
                                   @Part("password_confirmation") RequestBody passwordConfirmation,
                                   @Part("phone") RequestBody phone,
                                   @Part("region_id") RequestBody regionId,
                                   @Part MultipartBody.Part profileImage);

    @POST("restaurant/sign-up")
    @Multipart
    Call<AuthModel> restaurantRegister(@Part("line") RequestBody line,
                                       @Part("class_name") RequestBody class_name,
                                       @Part("function_name") RequestBody function_name,
                                       @Part("exception_message") RequestBody exception_message,
                                       @Part MultipartBody.Part file,
                                       @Part("type") RequestBody type);
}