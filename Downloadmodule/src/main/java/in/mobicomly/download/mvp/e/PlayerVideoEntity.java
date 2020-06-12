package in.mobicomly.download.mvp.e;

import android.graphics.Bitmap;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "PlayerVideo")
public class PlayerVideoEntity {
    @Column(name = "id", isId = true,autoGen = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "currentPlayTimeMs")
    private int currentPlayTimeMs;
    @Column(name = "durationTimeMs")
    private int durationTimeMs;
    @Column(name = "localPath")
    private String localPath;
    private Bitmap thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentPlayTimeMs() {
        return currentPlayTimeMs;
    }

    public void setCurrentPlayTimeMs(int currentPlayTimeMs) {
        this.currentPlayTimeMs = currentPlayTimeMs;
    }

    public int getDurationTimeMs() {
        return durationTimeMs;
    }

    public void setDurationTimeMs(int durationTimeMs) {
        this.durationTimeMs = durationTimeMs;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
