package in.mobicomly.download.mvp.p;

import java.util.List;

import in.mobicomly.download.listener.MagnetSearchListener;
import in.mobicomly.download.mvp.e.MagnetInfo;
import in.mobicomly.download.mvp.e.MagnetRule;
import in.mobicomly.download.mvp.e.MagnetSearchBean;
import in.mobicomly.download.mvp.m.DownLoadModel;
import in.mobicomly.download.mvp.m.DownLoadModelImp;
import in.mobicomly.download.mvp.m.TaskModel;
import in.mobicomly.download.mvp.m.TaskModelImp;
import in.mobicomly.download.mvp.v.MagnetSearchView;
import in.mobicomly.download.thread.MangetSearchTask;

public class MagnetSearchPresenterImp implements MagnetSearchPresenter {
    private MagnetSearchView magnetSearchView;
    private TaskModel taskModel;
    private DownLoadModel downLoadModel;
    public MagnetSearchPresenterImp(MagnetSearchView magnetSearchView){
        this.magnetSearchView=magnetSearchView;
        taskModel=new TaskModelImp();
        downLoadModel=new DownLoadModelImp();
    }

    @Override
    public void searchMagnet(MagnetRule rule, String keyword,String sort, int page) {
        MangetSearchTask mangetSearchTask=new MangetSearchTask(new MagnetSearchListener() {
            @Override
            public void success(List<MagnetInfo> info) {
                magnetSearchView.refreshData(info);
            }

            @Override
            public void fail(String error) {

            }
        });
        MagnetSearchBean bean=new MagnetSearchBean();
        bean.setKeyword(keyword);
        bean.setPage(page);
        bean.setRule(rule);
        bean.setSort(sort);
        mangetSearchTask.execute(bean);

    }
}
