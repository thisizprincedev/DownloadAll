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

import com.yanzhenjie.permission.AndPermission;
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
import in.mobicomly.download.adapter.DownloadSuccessListAdapter;
import in.mobicomly.download.common.MessageEvent;
import in.mobicomly.download.common.Msg;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.e.DownloadTaskEntity;
import in.mobicomly.download.mvp.p.DownloadSuccessPresenter;
import in.mobicomly.download.mvp.p.DownloadSuccessPresenterImp;
import in.mobicomly.download.mvp.v.DownLoadSuccessView;
import in.mobicomly.download.util.FileTools;
import in.mobicomly.download.util.Util;

public class DownLoadSuccessFrm extends Fragment implements DownLoadSuccessView {
    private RecyclerView recyclerView;
    private DownloadSuccessListAdapter downloadSuccessListAdapter;
    private DownloadSuccessPresenter downloadSuccessPresenter;
    private List<DownloadTaskEntity> list=null;
    private LovelyChoiceDialog lovelyChoiceDialog=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frm_download_success, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        downloadSuccessPresenter=new DownloadSuccessPresenterImp(this);

    }
    private void initView(){
        recyclerView=getView().findViewById(R.id.recyclerview);

    }
    @Override
    public void initTaskListView(List<DownloadTaskEntity> list) {
        if(list!=null && list.size()>0)
            this.list=list;
        else
            this.list=new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        downloadSuccessListAdapter=new DownloadSuccessListAdapter(getContext(),this,this.list);
        recyclerView.setAdapter(downloadSuccessListAdapter);
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
                        downloadSuccessPresenter.deleTask(task,deleFile);
                    }
                }).show();
    }

    @Override
    public void openFile(DownloadTaskEntity task) {
        String suffix = Util.getFileSuffix(task.getmFileName());
        String filePath=task.getLocalPath()+ File.separator+task.getmFileName();
        if(task.getFile() && !FileTools.exists(filePath)) {
            task.setThumbnailPath(null);
            EventBus.getDefault().postSticky(new MessageEvent(new Msg(Const.MESSAGE_TYPE_RES_TASK, task)));
            refreshData();
        }else if("TORRENT".equals(suffix)) {
            Intent intent = new Intent(getActivity(), TorrentInfoActivity.class);
            intent.putExtra("torrentPath", filePath);
            intent.putExtra("isDown", true);
            startActivity(intent);
        }else if("APK".equals(suffix)){
            File file=new File(filePath);
            AndPermission.with(this)
                    .install()
                    .file(file)
                    .start();
        }else if(FileTools.isVideoFile(task.getmFileName())) {
            Intent intent = new Intent(getActivity(), PlayerActivity.class);
            intent.putExtra("videoPath", filePath);
            startActivity(intent);
        }else if(!task.getFile() && task.getTaskType()==Const.BT_DOWNLOAD){
            Intent intent = new Intent(getActivity(), TorrentInfoActivity.class);
            intent.putExtra("torrentPath", task.getUrl());
            intent.putExtra("isDown", false);
            startActivity(intent);
        }
    }

    @Override
    public void alert(String msg, int msgType) {
        Util.alert(this.getActivity(),msg,msgType);
        refreshData();
    }

    @Override
    public void refreshData(){
        list.clear();
        list.addAll(downloadSuccessPresenter.getDownSuccessTaskList());
        downloadSuccessListAdapter.notifyDataSetChanged();
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky=true)
    public void onMessageEvent(MessageEvent event) {
        Msg msg=event.getMessage();
        if(msg.getType()== Const.MESSAGE_TYPE_REFRESH_DATA){
            refreshData();
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
        super.onDestroy();
    }

}
