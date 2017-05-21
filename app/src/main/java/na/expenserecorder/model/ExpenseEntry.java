package na.expenserecorder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wmc.
 */

public class ExpenseEntry implements Parcelable {

    private long entryKey;
    private String date;
    private float amount;
    private ExpenseCategory category;

    public ExpenseEntry() {}

    public ExpenseEntry(String date, float amount, ExpenseCategory category) {
        this.setDate(date);
        this.setAmount(amount);
        this.setCategory(category);
    }

    public long getEntryKey() {
        return entryKey;
    }

    public void setEntryKey(long entryKey) {
        this.entryKey = entryKey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    // Parcelable methods
    protected ExpenseEntry(Parcel in) {
        entryKey = in.readLong();
        date = in.readString();
        amount = in.readFloat();
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
        dest.writeString(date);
        dest.writeFloat(amount);
    }
}
