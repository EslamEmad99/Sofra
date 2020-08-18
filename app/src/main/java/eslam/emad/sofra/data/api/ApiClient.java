package eslam.emad.sofra.data.api;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import eslam.emad.sofra.data.models.city.CityModel;
import eslam.emad.sofra.data.models.my_categorize.MyCategorizeModel;
import eslam.emad.sofra.data.models.restaurants.RestaurantsModel;
import eslam.emad.sofra.util.MyApplication;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static eslam.emad.sofra.util.Constants.BASE_URL;

public class ApiClient {

    private ApiInterface apiInterface;
    private static ApiClient INSTANCE;
    private static final long cacheSize = 5 * 1024 * 1024; // 5 MB
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String HEADER_PRAGMA = "Pragma";

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiClient getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new ApiClient();
        }
        return INSTANCE;
    }

    private static OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
                .build();
    }

    private static Cache cache() {
        return new Cache(new File(MyApplication.getInstance().getCacheDir(), "someIdentifier"), cacheSize);
    }

    private static Interceptor offlineInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // prevent caching when network is on. For that we use the "networkInterceptor"
                if (!MyApplication.hasNetwork()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    private static Interceptor networkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(5, TimeUnit.SECONDS)
                        .build();

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    private static HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                    }
                });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    public Call<RestaurantsModel> getAllRestaurants(String keyword, int region_id, int page) {
        return apiInterface.getAllRestaurants(page, keyword, region_id);
    }

    public Call<CityModel> getAllCities(){
        return apiInterface.getAllCities();
    }

    public Call<MyCategorizeModel> getMyCategorize(String api_token, int page) {
        return apiInterface.getMyCategorize(api_token, page);
    }
}