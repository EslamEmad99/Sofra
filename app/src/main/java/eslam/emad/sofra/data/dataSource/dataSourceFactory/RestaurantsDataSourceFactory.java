package eslam.emad.sofra.data.dataSource.dataSourceFactory;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import eslam.emad.sofra.data.dataSource.RestaurantsDataSource;

public class RestaurantsDataSourceFactory extends DataSource.Factory {

    RestaurantsDataSource dataSource;
    MutableLiveData<RestaurantsDataSource> dataSourceMutableLiveData;

    public RestaurantsDataSourceFactory() {
        dataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {

        dataSource = new RestaurantsDataSource();
        dataSourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<RestaurantsDataSource> getDataSourceMutableLiveData() {
        return dataSourceMutableLiveData;
    }
}
