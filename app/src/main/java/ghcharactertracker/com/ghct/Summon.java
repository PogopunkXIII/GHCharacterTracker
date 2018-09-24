package ghcharactertracker.com.ghct;

import android.os.Parcel;
import android.os.Parcelable;

public class Summon implements Parcelable {

    private String name;
    private int health;

    public Summon() {

    }

    public Summon(String name, int health) {
        this.setName(name);
        this.setHealth(health);
    }

    protected Summon(Parcel in) {
        name = in.readString();
        health = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(health);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Summon> CREATOR = new Creator<Summon>() {
        @Override
        public Summon createFromParcel(Parcel in) {
            return new Summon(in);
        }

        @Override
        public Summon[] newArray(int size) {
            return new Summon[size];
        }
    };
}
