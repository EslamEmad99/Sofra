package eslam.emad.sofra.ui.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import eslam.emad.sofra.data.dataSource.NotificationDataSource;
import eslam.emad.sofra.data.dataSource.dataSourceFactory.NotificationsDataSourceFactory;
import eslam.emad.sofra.data.models.notifications.Notification;

public class ClientNotificationViewModel extends ViewModel {

    private LiveData<NotificationDataSource> dataSourceLiveData;
    private Executor executor;
    private LiveData<PagedList<Notification>> pagedList;
    private NotificationsDataSourceFactory dataSourceFactory;

    public ClientNotificationViewModel() {

        dataSourceFactory = new NotificationsDataSourceFactory();
        dataSourceLiveData = dataSourceFactory.getDataSourceMutableLiveData();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(/*ItemDataSource.PAGE_SIZE*/ 10)
                        .setPrefetchDistance(10)
                        .build();

        executor = Executors.newFixedThreadPool(5);
        pagedList = (new LivePagedListBuilder<Integer, Notification>(dataSourceFactory, config))
                .setFetchExecutor(executor)
                .build();

    }

    public LiveData<PagedList<Notification>> getItemPagedList() {
        return pagedList;
    }
}
