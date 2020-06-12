package in.mobicomly.download.mvp.e;

import android.graphics.Bitmap;

public class TorrentInfoEntity {
    private int mFileIndex;
    private String mFileName;
    private long mFileSize;
    private int mRealIndex;
    private String path;
    private String mSubPath;
    private String playUrl;
    private String hash;
    private Bitmap thumbnail;
    private Boolean isCheck=false;

    public int getmFileIndex() {
        return mFileIndex;
    }

    public void setmFileIndex(int mFileIndex) {
        this.mFileIndex = mFileIndex;
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public long getmFileSize() {
        return mFileSize;
    }

    public void setmFileSize(long mFileSize) {
        this.mFileSize = mFileSize;
    }

    public int getmRealIndex() {
        return mRealIndex;
    }

    public void setmRealIndex(int mRealIndex) {
        this.mRealIndex = mRealIndex;
    }

    public String getmSubPath() {
        return mSubPath;
    }

    public void setmSubPath(String mSubPath) {
        this.mSubPath = mSubPath;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
