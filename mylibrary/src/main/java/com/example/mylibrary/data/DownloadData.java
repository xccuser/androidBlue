package com.example.mylibrary.data;

import android.os.Parcel;
import android.os.Parcelable;

import static com.example.mylibrary.data.Const.NOME;
import static com.example.mylibrary.data.Const.START;

/**
 * Created by Administrator on 2017/10/23.
 */

public class DownloadData implements Parcelable{
    private String url;
    private String path;
    private String name;
    private int currentLength;
    private int totalLength;
    private float percentage;//已完成量
    private int status=NOME;
    private int childTaskCount;//线程数
    private long date;
    private String last_modify;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setChildTaskCount(int childTaskCount) {
        this.childTaskCount = childTaskCount;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setLast_modify(String last_modify) {
        this.last_modify = last_modify;
    }

    public DownloadData() {

    }

    public String getPath() {
        return path;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getCurrentLength() {
        return currentLength;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public float getPercentage() {
        return percentage;
    }

    public int getStatus() {
        return status;
    }

    public int getChildTaskCount() {
        return childTaskCount;
    }

    public long getDate() {
        return date;
    }

    public String getLast_modify() {
        return last_modify;
    }

    public static Creator<DownloadData> getCREATOR() {
        return CREATOR;
    }


    public DownloadData(String url,String path,String name){
        this.url=url;
        this.path=path;
        this.name=name;
    }
    public DownloadData(String url, String path, String name, int chidTaskCount){
        this.url=url;
        this.path=path;
        this.name=name;
        this.childTaskCount=chidTaskCount;
    }
    public DownloadData(String url, String path, int chidTaskCount,String name,
                        int currentLength,int totalLength,String last_modify,long date){
        this.url=url;
        this.path=path;
        this.name=name;
        this.childTaskCount=chidTaskCount;
        this.currentLength=currentLength;
        this.totalLength=totalLength;
        this.last_modify=last_modify;
        this.date=date;
    }


    protected DownloadData(Parcel in) {
        url = in.readString();
        path = in.readString();
        name = in.readString();
        currentLength = in.readInt();
        totalLength = in.readInt();
        percentage = in.readFloat();
        status = in.readInt();
        childTaskCount = in.readInt();
        date = in.readLong();
        last_modify = in.readString();
    }

    public static final Creator<DownloadData> CREATOR = new Creator<DownloadData>() {
        @Override
        public DownloadData createFromParcel(Parcel in) {
            return new DownloadData(in);
        }

        @Override
        public DownloadData[] newArray(int size) {
            return new DownloadData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(path);
        parcel.writeString(name);
        parcel.writeInt(currentLength);
        parcel.writeInt(totalLength);
        parcel.writeFloat(percentage);
        parcel.writeInt(status);
        parcel.writeInt(childTaskCount);
        parcel.writeLong(date);
        parcel.writeString(last_modify);
    }
}
