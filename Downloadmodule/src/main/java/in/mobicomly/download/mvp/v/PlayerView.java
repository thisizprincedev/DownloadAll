package in.mobicomly.download.mvp.v;

public interface PlayerView {
    void initPlayer();
    void openVideo(String path);
    void playPause();
    void setTimeTextView(int currentPlayTimeMs, int durationTimeMs);
    void updateUIPlayStation(int currentPlayTimeMs, int durationTimeMs);
    boolean ismIsNeedUpdateUIProgress();
    boolean ismIsTouchingSeekbar();
    void controlViewToggle();
    void controlViewShow();
    void controlViewHide();
    void setVideoTile(String name);
    void userSeekPlayProgress(int currentPlayTimeMs);
}
