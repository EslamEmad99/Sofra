package eslam.emad.sofra.ui.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import eslam.emad.sofra.data.dataSource.MyCategorizeDataSource;
import eslam.emad.sofra.data.dataSource.RestaurantsDataSource;
import eslam.emad.sofra.data.dataSource.dataSourceFactory.MyCategorizeDataSourceFactory;
import eslam.emad.sofra.data.dataSource.dataSourceFactory.RestaurantsDataSourceFactory;
import eslam.emad.sofra.data.models.categorize.Category;
import eslam.emad.sofra.data.models.city.City;
import eslam.emad.sofra.data.models.my_categorize.MyCategory;
import eslam.emad.sofra.data.models.restaurants.Restaurant;
import eslam.emad.sofra.util.Repository;

public class MyCategorizeViewModel extends ViewModel {

    private LiveData<MyCategorizeDataSource> dataSourceLiveData;
    private Executor executor;
    private LiveData<PagedList<MyCategory>> pagedList;
    private MyCategorizeDataSourceFactory dataSourceFactory;

    public MyCategorizeViewModel() {

        dataSourceFactory = new MyCategorizeDataSourceFactory();
        dataSourceLiveData = dataSourceFactory.getDataSourceMutableLiveData();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(/*ItemDataSource.PAGE_SIZE*/ 10)
                        .setPrefetchDistance(10)
                        .build();

        executor = Executors.newFixedThreadPool(5);
        pagedList = (new LivePagedListBuilder<Integer, MyCategory>(dataSourceFactory, config))
                .setFetchExecutor(executor)
                .build();

    }

    public LiveData<PagedList<MyCategory>> getItemPagedList() {
        return pagedList;
    }
}
