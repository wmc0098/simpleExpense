package na.expenserecorder.model;

/**
 * Created by wmc.
 * TODO Add header comment
 */

public class Category {

    private long categoryKey;
    private String categoryName;


    public long getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(long categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
