package in.mobicomly.download.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.activity.PlayerActivity;
import in.mobicomly.download.activity.TorrentInfoActivity;
import in.mobicomly.download.adapter.DownloadingListAdapter;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.common.MessageEvent;
import in.mobicomly.download.common.Msg;
import in.mobicomly.download.mvp.e.DownloadTaskEntity;
import in.mobicomly.download.mvp.p.DownloadIngPresenter;
import in.mobicomly.download.mvp.p.DownloadIngPresenterImp;
import in.mobicomly.download.mvp.v.DownLoadIngView;
import in.mobicomly.download.util.FileTools;
import in.mobicomly.download.util.Util;

public class DownLoadIngFrm extends Fragment implements DownLoadIngView{
    private RecyclerView recyclerView;
    private DownloadIngPresenter downloadIngPresenter;
    private DownloadingListAdapter downloadingListAdapter;
    private List<DownloadTaskEntity> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frm_download_ing, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        downloadIngPresenter=new DownloadIngPresenterImp(this);

    }
    private void initView(){
        recyclerView=getView().findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        downloadingListAdapter=new DownloadingListAdapter(getContext(),this,this.list);
        recyclerView.setAdapter(downloadingListAdapter);
    }

    @Override
    public void startTask(DownloadTaskEntity task) {
        downloadIngPresenter.startTask(task);
    }

    @Override
    public void sopTask(DownloadTaskEntity task) {
        downloadIngPresenter.stopTask(task);
    }

    @Override
    public void openFile(DownloadTaskEntity task) {
        String suffix = task.getmFileName().substring(task.getmFileName().lastIndexOf(".") + 1).toUpperCase();
        if("TORRENT".equals(suffix)) {
            Intent intent = new Intent(getActivity(), TorrentInfoActivity.class);
            intent.putExtra("torrentPath", task.getLocalPath()+ File.separator+task.getmFileName());
            intent.putExtra("isDown", true);
            startActivity(intent);
        }else if(FileTools.isVideoFile(task.getmFileName())){
            String videoPath=task.getLocalPath()+File.separator+task.getmFileName();
            Intent intent = new Intent(getActivity(), PlayerActivity.class);
            intent.putExtra("videoPath", videoPath);
            startActivity(intent);
        }else if(!task.getFile() && task.getTaskType()==Const.BT_DOWNLOAD){
            Intent intent = new Intent(getActivity(), TorrentInfoActivity.class);
            intent.putExtra("torrentPath", task.getUrl());
            intent.putExtra("isDown", false);
            startActivity(intent);
        }
    }

    @Override
    public void deleTask(final DownloadTaskEntity task) {
        String[] items = new String[]{getContext().getString(R.string.dele_data_and_file)};
        new LovelyChoiceDialog(getContext())
                .setTopColorRes(R.color.colorAccent)
                .setTitle(R.string.determine_dele)
                .setIcon(R.drawable.ic_error)
                .setItemsMultiChoice(items, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
                    @Override
                    public void onItemsSelected(List<Integer> positions, List<String> items) {
                        Boolean deleFile=items.size()>0?true:false;
                        downloadIngPresenter.deleTask(task,deleFile);
                    }
                }).show();
    }

    @Override
    public void refreshData(List<DownloadTaskEntity> tasks) {
        list.clear();
        list.addAll(tasks);
        downloadingListAdapter.notifyDataSetChanged();
    }

    @Override
    public void alert(String msg, int msgType) {
        Util.alert(this.getActivity(),msg,msgType);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky=true)
    public void onMessageEvent(MessageEvent event) {
        Msg msg=event.getMessage();
        if(msg.getType()== Const.MESSAGE_TYPE_RES_TASK){
            DownloadTaskEntity task=(DownloadTaskEntity)msg.getObj();
            downloadIngPresenter.startTask(task);
            EventBus.getDefault().postSticky(new MessageEvent(new Msg(Const.MESSAGE_TYPE_SWITCH_TAB, 0)));
        }else if(msg.getType()== Const.MESSAGE_TYPE_APP_UPDATA_PRESS){
            List<DownloadTaskEntity> tasks=(List<DownloadTaskEntity>)msg.getObj();
            refreshData(tasks);
        }
    }
    @Override
    public void onStart() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        downloadIngPresenter.stopLoop();
        downloadIngPresenter.clearHandler();
        super.onDestroy();
    }
}
