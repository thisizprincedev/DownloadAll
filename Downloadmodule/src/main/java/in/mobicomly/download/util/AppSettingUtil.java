package in.mobicomly.download.util;

import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.e.AppSettingEntity;
import in.mobicomly.download.mvp.m.AppSettingModel;
import in.mobicomly.download.mvp.m.AppSettingModelImp;

public class AppSettingUtil {
    private static AppSettingUtil appSettingUtil;
    private AppSettingModel model;
    public AppSettingUtil(){
        model=new AppSettingModelImp();
    }
    public static synchronized AppSettingUtil getInstance() {
        if (appSettingUtil == null) {
            appSettingUtil = new AppSettingUtil();
        }
        return appSettingUtil;
    }
    public String getFileSavePath(){
        String savePath;
        AppSettingEntity setting=model.getSavePath();
        if(null!=setting){
            savePath=setting.getValue();
        }else{
            savePath=Const.File_SAVE_PATH;
        }
        FileTools.mkdirs(savePath);
        return savePath;
    }
    public int getDownCount(){
        int count=0;
        AppSettingEntity setting=model.getDownCount();
        if(null!=setting){
            count=Integer.valueOf(setting.getValue());
        }else{
            count=Const.MAX_DOWN_COUNT;
        }
        return count;
    }

    public Boolean isMobileNetDown(){
        AppSettingEntity setting=model.getMobileNet();
        if(null==setting){
            return true;
        }
        return setting.getValue().equals(Const.MOBILE_NET_OK+"")?true:false;
    }
    public Boolean isShowDownNotify(){
        AppSettingEntity setting=model.getDownNotify();
        if(null==setting){
            return false;
        }
        return setting.getValue().equals(Const.MOBILE_NET_OK+"")?true:false;
    }
    public Boolean isDown(){
        int netType=SystemConfig.getNetType();
        if(netType==Const.NET_TYPE_UNKNOW){
            return false;
        }else if(netType==Const.NET_TYPE_WIFI){
            return true;
        }else if(netType==Const.NET_TYPE_MOBILE && isMobileNetDown()){
            return true;
        }else if(netType==Const.NET_TYPE_MOBILE && !isMobileNetDown()){
            return false;
        }
        return false;
    }
}
