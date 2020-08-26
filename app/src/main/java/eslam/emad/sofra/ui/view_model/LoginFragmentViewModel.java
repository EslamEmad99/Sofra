package eslam.emad.sofra.ui.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eslam.emad.sofra.data.models.auth.AuthModel;
import eslam.emad.sofra.util.Repository;

public class LoginFragmentViewModel extends ViewModel {

    private MutableLiveData<AuthModel> loginModel;

    public LoginFragmentViewModel() {
        loginModel = new MutableLiveData<>();
        loginModel = Repository.getINSTANCE().getOnLogin();
    }

    public MutableLiveData<AuthModel> getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(String parameter, String email, String password) {
        Repository.getINSTANCE().setOnLogin(parameter, email, password);
    }
}
