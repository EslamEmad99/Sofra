package eslam.emad.sofra.ui.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eslam.emad.sofra.data.models.auth.AuthModel;
import eslam.emad.sofra.util.Repository;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RestaurantRegisterViewModel extends ViewModel {
    private MutableLiveData<AuthModel> restaurantRegisterModel;

    public RestaurantRegisterViewModel() {
        restaurantRegisterModel = new MutableLiveData<>();
        restaurantRegisterModel = Repository.getINSTANCE().getRestaurantRegister();
    }

    public MutableLiveData<AuthModel> getRestaurantRegisterModel() {
        return restaurantRegisterModel;
    }

    public void setRestaurantRegisterModel(RequestBody name,
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
        Repository.getINSTANCE().setRestaurantRegister(
                name,
                email,
                password,
                passwordConfirmation,
                phone,
                whatsApp,
                regionId,
                deliveryCost,
                minimumCharge,
                photo,
                deliveryTime);
    }
}
