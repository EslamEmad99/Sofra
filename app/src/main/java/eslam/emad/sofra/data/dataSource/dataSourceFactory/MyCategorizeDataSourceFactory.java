package eslam.emad.sofra.data.dataSource.dataSourceFactory;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import eslam.emad.sofra.data.dataSource.MyCategorizeDataSource;


public class MyCategorizeDataSourceFactory extends DataSource.Factory {
    MyCategorizeDataSource dataSource;
    MutableLiveData<MyCategorizeDataSource> dataSourceMutableLiveData;

    public MyCategorizeDataSourceFactory() {
        dataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {

        dataSource = new MyCategorizeDataSource();
        dataSourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<MyCategorizeDataSource> getDataSourceMutableLiveData() {
        return dataSourceMutableLiveData;
    }
}
