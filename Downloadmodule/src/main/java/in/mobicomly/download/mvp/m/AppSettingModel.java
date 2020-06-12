package in.mobicomly.download.mvp.m;

import java.util.List;

import in.mobicomly.download.mvp.e.AppSettingEntity;

public interface AppSettingModel {
    List<AppSettingEntity> findAllSetting();
    void saveOrUploadSteeing(AppSettingEntity setting);
    void setSavePath(String path);
    AppSettingEntity getSavePath();
    void setDownCount(String count);
    AppSettingEntity getDownCount();
    AppSettingEntity getMobileNet();
    void setMobileNet(String net);
    AppSettingEntity getDownNotify();
    void setDownNotify(String notify);

}
