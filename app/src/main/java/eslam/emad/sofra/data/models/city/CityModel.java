
package eslam.emad.sofra.data.models.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private CityResponseData cityResponseData;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CityResponseData getCityResponseData() {
        return cityResponseData;
    }

    public void setCityResponseData(CityResponseData cityResponseData) {
        this.cityResponseData = cityResponseData;
    }

}
