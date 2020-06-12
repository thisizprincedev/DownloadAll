package in.mobicomly.download.mvp.p;

import java.util.List;

import in.mobicomly.download.mvp.e.AppSettingEntity;
import in.mobicomly.download.mvp.m.AppSettingModel;
import in.mobicomly.download.mvp.m.AppSettingModelImp;
import in.mobicomly.download.mvp.v.AppSettingView;

public class AppSettingPresenterImp implements AppSettingPresenter {
    private AppSettingView appSettingView;
    private AppSettingModel appSettingModel;
    public AppSettingPresenterImp(AppSettingView appSettingView){
        this.appSettingView=appSettingView;
        appSettingModel= new AppSettingModelImp();
        initSetting();
    }

    @Override
    public void initSetting() {
        List<AppSettingEntity> list=appSettingModel.findAllSetting();
        if(null!=list && list.size()>0) {
            for (AppSettingEntity setting : list) {
                appSettingView.initSetting(setting.getKey(), setting.getValue());
            }
        }
    }

    @Override
    public void setSavePath(String path) {
        appSettingModel.setSavePath(path);
    }

    @Override
    public void setDownCount(String count) {
        appSettingModel.setDownCount(count);
    }

    @Override
    public void setMobileNet(String net) {
        appSettingModel.setMobileNet(net);
    }

    @Override
    public void setDownNotify(String notify) {
        appSettingModel.setDownNotify(notify);
    }
}
