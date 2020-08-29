package eslam.emad.sofra.ui.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eslam.emad.sofra.data.models.auth.AuthModel;
import eslam.emad.sofra.util.Repository;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ClientRegisterViewModel extends ViewModel {

    private MutableLiveData<AuthModel> clientRegisterModel;

    public ClientRegisterViewModel() {
        clientRegisterModel = new MutableLiveData<>();
        clientRegisterModel = Repository.getINSTANCE().getClientRegister();
    }

    public MutableLiveData<AuthModel> getClientRegister() {
        return clientRegisterModel;
    }

    public void setClientRegister(RequestBody name,
                              RequestBody email,
                              RequestBody password,
                              RequestBody passwordConfirmation,
                              RequestBody phone,
                              RequestBody regionId,
                              MultipartBody.Part profileImage) {
        Repository.getINSTANCE().setClientRegister(name, email, password, passwordConfirmation, phone, regionId, profileImage);
    }
}