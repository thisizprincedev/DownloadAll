package in.mobicomly.download.mvp.e;

import android.graphics.Bitmap;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.Date;

@Table(name = "DownloadTask")
public class DownloadTaskEntity {
    @Column(name = "id", isId = true,autoGen = true)
    private int id;
    @Column(name = "taskId")
    private long taskId;
    @Column(name = "mTaskStatus")
    private int mTaskStatus;
    @Column(name = "mFileSize")
    private long mFileSize;
    @Column(name = "mFileName")
    private String mFileName;
    @Column(name = "taskType")
    private int taskType;
    @Column(name = "url")
    private String url;
    @Column(name = "localPath")
    private String localPath;
    @Column(name = "mDownloadSize")
    private long mDownloadSize;
    @Column(name = "mDownloadSpeed")
    private long mDownloadSpeed;
    @Column(name = "mDCDNSpeed")
    private long mDCDNSpeed;
    @Column(name = "hash")
    private String hash;
    @Column(name = "isFile")
    private Boolean isFile;
    @Column(name = "createDate")
    private Date createDate;
    @Column(name = "thumbnailPath")
    private String thumbnailPath;
    private Bitmap thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public int getmTaskStatus() {
        return mTaskStatus;
    }

    public void setmTaskStatus(int mTaskStatus) {
        this.mTaskStatus = mTaskStatus;
    }

    public long getmFileSize() {
        return mFileSize;
    }

    public void setmFileSize(long mFileSize) {
        this.mFileSize = mFileSize;
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public long getmDownloadSize() {
        return mDownloadSize;
    }

    public void setmDownloadSize(long mDownloadSize) {
        this.mDownloadSize = mDownloadSize;
    }

    public long getmDownloadSpeed() {
        return mDownloadSpeed;
    }

    public void setmDownloadSpeed(long mDownloadSpeed) {
        this.mDownloadSpeed = mDownloadSpeed;
    }

    public long getmDCDNSpeed() {
        return mDCDNSpeed;
    }

    public void setmDCDNSpeed(long mDCDNSpeed) {
        this.mDCDNSpeed = mDCDNSpeed;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Boolean getFile() {
        return isFile;
    }

    public void setFile(Boolean file) {
        isFile = file;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
