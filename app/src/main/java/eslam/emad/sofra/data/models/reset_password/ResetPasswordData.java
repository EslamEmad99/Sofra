
package eslam.emad.sofra.data.models.reset_password;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordData {

    @SerializedName("code")
    @Expose
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
