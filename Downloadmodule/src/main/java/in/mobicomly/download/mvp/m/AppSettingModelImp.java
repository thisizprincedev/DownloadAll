package in.mobicomly.download.mvp.m;

import org.xutils.ex.DbException;

import java.util.List;

import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.e.AppSettingEntity;
import in.mobicomly.download.util.DBTools;

public class AppSettingModelImp implements AppSettingModel {

    @Override
    public List<AppSettingEntity> findAllSetting() {
        try {
            return DBTools.getInstance().db().findAll(AppSettingEntity.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrUploadSteeing(AppSettingEntity setting) {
        try {
            DBTools.getInstance().db().saveOrUpdate(setting);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSavePath(String path) {
        AppSettingEntity setting= getSavePath();
        if(null==setting){
            setting=new AppSettingEntity();
            setting.setKey(Const.SAVE_PATH_KEY);
        }
        setting.setValue(path);
        saveOrUploadSteeing(setting);
    }

    @Override
    public AppSettingEntity getSavePath() {
        try {
           return DBTools.getInstance().db().selector(AppSettingEntity.class).where("key","=", Const.SAVE_PATH_KEY).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setDownCount(String count) {
        AppSettingEntity setting= getDownCount();
        if(null==setting){
            setting=new AppSettingEntity();
            setting.setKey(Const.DOWN_COUNT_KEY);
        }
        setting.setValue(count);
        saveOrUploadSteeing(setting);
    }

    @Override
    public AppSettingEntity getDownCount() {
        try {
            return DBTools.getInstance().db().selector(AppSettingEntity.class).where("key","=", Const.DOWN_COUNT_KEY).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AppSettingEntity getMobileNet() {
        try {
            return DBTools.getInstance().db().selector(AppSettingEntity.class).where("key","=", Const.MOBILE_NET_KEY).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setMobileNet(String net) {
        AppSettingEntity setting= getMobileNet();
        if(null==setting){
            setting=new AppSettingEntity();
            setting.setKey(Const.MOBILE_NET_KEY);
        }
        setting.setValue(net);
        saveOrUploadSteeing(setting);
    }
    @Override
    public AppSettingEntity getDownNotify() {
        try {
            return DBTools.getInstance().db().selector(AppSettingEntity.class).where("key","=", Const.DOWN_NOTIFY_KEY).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setDownNotify(String notify) {
        AppSettingEntity setting= getDownNotify();
        if(null==setting){
            setting=new AppSettingEntity();
            setting.setKey(Const.DOWN_NOTIFY_KEY);
        }
        setting.setValue(notify);
        saveOrUploadSteeing(setting);
    }
}
