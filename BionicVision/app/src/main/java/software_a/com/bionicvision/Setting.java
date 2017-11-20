package software_a.com.bionicvision;

import android.os.Parcel;
import android.os.Parcelable;


class Setting implements Parcelable
{
    private String phosAlgorithm;
    private int phosAmount;
    private int phosMaxList;
    private double phosCFoV;
    private double phosSFoV;
    private double phosSpacing;
    private int phosSize;
    private boolean phosLoad;
    private boolean phosRecord;

    Setting(String alg, int amount, int maxlist, double cfov, double sfov, double spacing, int size, boolean load, boolean record)
    {
        update(alg, amount, maxlist, cfov, sfov, spacing, size, load, record);
    }

    void update(String algorithm, int amount, int maxlist, double cfov, double sfov, double spacing, int size, boolean load, boolean record)
    {
        this.phosAlgorithm = algorithm;
        this.phosAmount = amount;
        this.phosMaxList = maxlist;
        this.phosCFoV = cfov;
        this.phosSFoV = sfov;
        this.phosSpacing = spacing;
        this.phosSize = size;
        this.phosLoad = load;
        this.phosRecord = record;
    }

    String getAlgorithm() {return this.phosAlgorithm;}
    int getPhosAmount() {return this.phosAmount;}
    int getPhosMaxList() {return this.phosMaxList;}
    double getPhosCFoV() {return this.phosCFoV;}
    double getPhosSFoV() {return this.phosSFoV;}
    double getPhosSpacing() {return this.phosSpacing;}
    int getPhosSize() {return this.phosSize;}
    boolean getPhosLoad() {return this.phosLoad;}
    boolean getPhosRecord() {return this.phosRecord;}

    @Override
    public int describeContents() {return 0;}

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(phosAlgorithm);
        out.writeInt(phosAmount);
        out.writeInt(phosMaxList);
        out.writeDouble(phosCFoV);
        out.writeDouble(phosSFoV);
        out.writeDouble(phosSpacing);
        out.writeInt(phosSize);
        out.writeByte((byte) (phosLoad ? 1 : 0));
        out.writeByte((byte) (phosRecord ? 1 : 0));
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
        phosAmount = in.readInt();
        phosMaxList = in.readInt();
        phosCFoV = in.readDouble();
        phosSFoV = in.readDouble();
        phosSpacing = in.readDouble();
        phosSize = in.readInt();
        phosLoad = in.readByte() != 0;
        phosRecord = in.readByte() != 0;
    }
}
