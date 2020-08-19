package eslam.emad.sofra.data.dataSource.dataSourceFactory;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import eslam.emad.sofra.data.dataSource.NotificationDataSource;

public class NotificationsDataSourceFactory extends DataSource.Factory {
    NotificationDataSource dataSource;
    MutableLiveData<NotificationDataSource> dataSourceMutableLiveData;

    public NotificationsDataSourceFactory() {
        dataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {

        dataSource = new NotificationDataSource();
        dataSourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<NotificationDataSource> getDataSourceMutableLiveData() {
        return dataSourceMutableLiveData;
    }
}