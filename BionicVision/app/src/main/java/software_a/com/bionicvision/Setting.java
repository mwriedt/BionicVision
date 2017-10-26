package software_a.com.bionicvision;

import android.os.Parcel;
import android.os.Parcelable;


class Setting implements Parcelable
{
    private String phosAlgorithm;
    private double phosGamma;
    private double phosSpacing;
    private int phosNumber;
    private int phosSize;
    private boolean phosLoad;

    Setting(String alg, double gamma, double spacing, int number, int size, boolean load)
    {
        update(alg, gamma, spacing, number, size, load);
    }

    void update(String algorithm, double gamma, double spacing, int number, int size, boolean load)
    {
        this.phosAlgorithm = algorithm;
        this.phosGamma = gamma;
        this.phosSpacing = spacing;
        this.phosNumber = number;
        this.phosSize = size;
        this.phosLoad = load;
    }

    String getAlgorithm() {return this.phosAlgorithm;}
    double getPhosGamma() {return this.phosGamma;}
    double getPhosSpacing() {return this.phosSpacing;}
    int getPhosAmount() {return this.phosNumber;}
    int getPhosSize() {return this.phosSize;}
    boolean getPhosLoad() {return this.phosLoad;}

    @Override
    public int describeContents() {return 0;}

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(phosAlgorithm);
        out.writeDouble(phosGamma);
        out.writeDouble(phosSpacing);
        out.writeInt(phosNumber);
        out.writeInt(phosSize);
        out.writeByte((byte) (phosLoad ? 1 : 0));
    }

    public String toString() {return phosAlgorithm;}

    public static final Parcelable.Creator<Setting> CREATOR = new Parcelable.Creator<Setting>()
    {
        public Setting createFromParcel(Parcel in) {return new Setting(in);}
        public Setting[] newArray(int size) {return new Setting[size];}
    };

    private Setting(Parcel in)
    {
        phosAlgorithm = in.readString();
        phosGamma = in.readDouble();
        phosSpacing = in.readDouble();
        phosNumber = in.readInt();
        phosSize = in.readInt();
        phosLoad = in.readByte() != 0;
    }
}
