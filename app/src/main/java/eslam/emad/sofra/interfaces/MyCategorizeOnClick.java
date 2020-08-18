package eslam.emad.sofra.interfaces;

import eslam.emad.sofra.data.models.my_categorize.MyCategory;

public interface MyCategorizeOnClick {

    void onCategoryClick(MyCategory category);

    void onCategoryDelete(MyCategory category);

    void onCategoryEdit(MyCategory category);
}
