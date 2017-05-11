package na.expenserecorder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wmc.
 */

public class ExpenseEntry implements Parcelable {

    private long entryKey;
    private String time;
    private float amount;
    private String category;

    public ExpenseEntry() {}

    public ExpenseEntry(long key, String time, float amount) {
        this.setEntryKey(key);
        this.setTime(time);
        this.setAmount(amount);
    }

    public long getEntryKey() {
        return entryKey;
    }

    public void setEntryKey(long entryKey) {
        this.entryKey = entryKey;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Parcelable methods
    protected ExpenseEntry(Parcel in) {
        entryKey = in.readLong();
        time = in.readString();
        amount = in.readFloat();
        category = in.readString();
    }

    public static final Creator<ExpenseEntry> CREATOR = new Creator<ExpenseEntry>() {
        @Override
        public ExpenseEntry createFromParcel(Parcel in) {
            return new ExpenseEntry(in);
        }

        @Override
        public ExpenseEntry[] newArray(int size) {
            return new ExpenseEntry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(entryKey);
        dest.writeString(time);
        dest.writeFloat(amount);
        dest.writeString(category);
    }

}
