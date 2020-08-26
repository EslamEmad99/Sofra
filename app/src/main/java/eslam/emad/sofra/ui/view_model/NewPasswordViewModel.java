package eslam.emad.sofra.ui.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eslam.emad.sofra.data.models.new_password.NewPasswordModel;
import eslam.emad.sofra.data.models.reset_password.ResetPasswordModel;
import eslam.emad.sofra.util.Repository;

public class NewPasswordViewModel extends ViewModel {

    private MutableLiveData<NewPasswordModel> newPassword;

    public NewPasswordViewModel() {
        newPassword = new MutableLiveData<>();
        newPassword = Repository.getINSTANCE().getNewPassword();
    }

    public MutableLiveData<NewPasswordModel> getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String userType, String code, String password, String passwordConformation) {
        Repository.getINSTANCE().setNewPassword(userType, code, password, passwordConformation);
    }
}
