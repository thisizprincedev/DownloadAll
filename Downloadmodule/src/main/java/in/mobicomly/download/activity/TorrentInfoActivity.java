package in.mobicomly.download.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coorchice.library.SuperTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.adapter.TorrentInfoAdapter;
import in.mobicomly.download.common.BaseActivity;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.common.MessageEvent;
import in.mobicomly.download.common.Msg;
import in.mobicomly.download.listener.GetThumbnailsListener;
import in.mobicomly.download.mvp.e.TorrentInfoEntity;
import in.mobicomly.download.mvp.p.TorrentInfoPresenter;
import in.mobicomly.download.mvp.p.TorrentInfoPresenterImp;
import in.mobicomly.download.mvp.v.TorrentInfoView;
import in.mobicomly.download.thread.GetTorrentVideoThumbnailsTask;
import in.mobicomly.download.util.AlertUtil;
import in.mobicomly.download.util.Util;

public class TorrentInfoActivity extends BaseActivity implements TorrentInfoView{
    private RecyclerView recyclerView;
    private SuperTextView rightBtn;
    private LinearLayout downLinearLayout;
    private List<TorrentInfoEntity> list;
    private List<TorrentInfoEntity> checkList=new ArrayList<>();
    private TorrentInfoAdapter torrentInfoAdapter;
    private TorrentInfoPresenter torrentInfoPresenter;
    private String torrentPath;
    private boolean isCheckAll=false;
    private boolean isDown=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torrent_info);
        setTopBarTitle(R.string.bt_file_info);
        recyclerView = findViewById(R.id.recyclerview);
        rightBtn = findViewById(R.id.right_view);
        downLinearLayout = findViewById(R.id.start_download);
        //rightBtn.setText(R.string.check_all);
        Intent getIntent = getIntent();
        torrentPath=getIntent.getStringExtra("torrentPath");
        isDown=getIntent.getBooleanExtra("isDown",false);
        if(isDown){
            downLinearLayout.setVisibility(View.VISIBLE);
        }
        torrentInfoPresenter=new TorrentInfoPresenterImp(this,torrentPath);
        downLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkList();
                torrentInfoPresenter.startTask(checkList);
            }
        });
    }

    @Override
    public void initTaskListView(List<TorrentInfoEntity> list) {
        this.list=list;
        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        torrentInfoAdapter=new TorrentInfoAdapter(this,this,this.list);
        recyclerView.setAdapter(torrentInfoAdapter);
        if(!isDown){
            AlertUtil.showLoading();
            new GetTorrentVideoThumbnailsTask(new GetThumbnailsListener() {
                @Override
                public void success(Bitmap bitmap) {
                    torrentInfoAdapter.notifyDataSetChanged();
                    AlertUtil.hideLoading();
                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,list);
        }
    }

    @Override
    public void itemClick(int index) {
//        TorrentInfoEntity torrent=list.get(index);
//        if(torrent.getCheck()){
//            torrent.setCheck(false);
//            isCheckAll=false;
//            rightBtn.setText(R.string.check_all);
//        }else{
//            torrent.setCheck(true);
//        }
//        torrentInfoAdapter.notifyDataSetChanged();
//        checkList();
//        setTopBarTitle(String.format(getString(R.string.check_count),list.size()+"",checkList.size()+""));

    }

    @Override
    public void startTaskSuccess() {
        EventBus.getDefault().postSticky(new MessageEvent(new Msg(Const.MESSAGE_TYPE_SWITCH_TAB, 0)));
        finish();
    }

    @Override
    public void startTaskFail(String msg) {
        Util.alert(this,msg, Const.ERROR_ALERT);
    }

    @Override
    public boolean getIsDown() {
        return isDown;
    }

    @Override
    public void playerViedo(TorrentInfoEntity te) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("videoPath", te.getPath());
        startActivity(intent);
    }



    private void checkList(){
        checkList.clear();
        for(TorrentInfoEntity torrent:list){
            if(torrent.getCheck()){
                checkList.add(torrent);
            }
        }
    }

}
