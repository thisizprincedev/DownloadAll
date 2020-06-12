package in.mobicomly.download.mvp.p;

public interface AppSettingPresenter {
    void initSetting();
    void setSavePath(String path);
    void setDownCount(String count);
    void setMobileNet(String net);
    void setDownNotify(String notify);
}
