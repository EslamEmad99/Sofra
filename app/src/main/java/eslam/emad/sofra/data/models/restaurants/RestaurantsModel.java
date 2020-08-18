
package eslam.emad.sofra.data.models.restaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantsModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RestaurantsResponseData restaurantsResponseData;

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

    public RestaurantsResponseData getRestaurantsResponseData() {
        return restaurantsResponseData;
    }

    public void setRestaurantsResponseData(RestaurantsResponseData restaurantsResponseData) {
        this.restaurantsResponseData = restaurantsResponseData;
    }

}
