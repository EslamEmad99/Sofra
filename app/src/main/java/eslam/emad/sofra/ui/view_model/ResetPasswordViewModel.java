package eslam.emad.sofra.ui.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import eslam.emad.sofra.data.models.reset_password.ResetPasswordModel;
import eslam.emad.sofra.util.Repository;

public class ResetPasswordViewModel extends ViewModel {

    private MutableLiveData<ResetPasswordModel> resetPassword;

    public ResetPasswordViewModel() {
        resetPassword = new MutableLiveData<>();
        resetPassword = Repository.getINSTANCE().getResetPassword();
    }

    public MutableLiveData<ResetPasswordModel> getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(String userType, String email) {
        Repository.getINSTANCE().setResetPassword(userType, email);
    }
}
