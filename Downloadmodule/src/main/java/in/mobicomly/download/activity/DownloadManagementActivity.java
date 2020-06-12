package in.mobicomly.download.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cocosw.bottomsheet.BottomSheet;
import com.coorchice.library.SuperTextView;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.common.BaseActivity;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.common.CusAdapter;
import in.mobicomly.download.mvp.p.AppConfigPresenter;
import in.mobicomly.download.mvp.p.AppConfigPresenterImp;
import in.mobicomly.download.service.DownService;
import in.mobicomly.download.util.AppConfigUtil;
import in.mobicomly.download.view.DownLoadIngFrm;
import in.mobicomly.download.view.DownLoadSuccessFrm;
import in.mobicomly.download.mvp.p.DownloadManagementPresenter;
import in.mobicomly.download.mvp.p.DownloadManagementPresenterImp;
import in.mobicomly.download.mvp.v.DownloadManagementView;
import in.mobicomly.download.util.Util;

public class DownloadManagementActivity extends BaseActivity implements DownloadManagementView{
    private ViewPager viewPager;
    private TextView downloading;
    private TextView downloadfinish;
    private SuperTextView openAddTaskPopBtn;
    private List<Fragment> mFragments = new ArrayList<>();
    private  Intent intent=null;
    private BottomSheet.Builder bottomSheet=null;
    private static final int REQUEST_CODE_CHOOSE = 10086;
    private static final int REQUEST_CODE_SCAN = 10010;
    int REQUESTCODE_FROM_ACTIVITY = 1000;

    private DownloadManagementPresenter downloadManagementPresenter;
    private AppConfigPresenter appConfigPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_management);

        viewPager = findViewById(R.id.viewPager);
        downloading = findViewById(R.id.downloading);
        downloadfinish = findViewById(R.id.downloadfinish);
        openAddTaskPopBtn = findViewById(R.id.open_add_task_pop);

        Intent intent = new Intent(this, DownService.class);
        startService(intent);
        downloadManagementPresenter=new DownloadManagementPresenterImp(this);
        appConfigPresenter=new AppConfigPresenterImp();
        initViewPage();
        initBottomMenu();
        downloading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        downloadfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        openAddTaskPopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.show();
            }
        });
    }

    private  void initViewPage(){
        DownLoadSuccessFrm downLoadSuccessFrm=new DownLoadSuccessFrm();
        DownLoadIngFrm downLoadIngFrm=new DownLoadIngFrm();
        mFragments.add(downLoadIngFrm);
        mFragments.add(downLoadSuccessFrm);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new CusAdapter(getSupportFragmentManager(),mFragments));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }
            @Override
            public void onPageSelected(int i) {
                changeTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });

    }

    private void changeTab(int index){
        if(index==0){
            downloading.setTextColor(getResources().getColor(R.color.black_ff));
            downloadfinish.setTextColor(getResources().getColor(R.color.gray_8f));
        }else{
            downloading.setTextColor(getResources().getColor(R.color.black_ff));
            downloadfinish.setTextColor(getResources().getColor(R.color.gray_8f));
        }
    }
   /* @Event(value = R.id.open_setting)
    private void appSettingClick(View view) {
        intent =new Intent(DownloadManagementActivity.this,AppSettingActivity.class);
        startActivity(intent);
    }
    @Event(value = R.id.open_magnet_search)
    private void magnetSearchClick(View view) {
        intent =new Intent(DownloadManagementActivity.this,MagnetSearchActivity.class);
        startActivity(intent);
    }*/
    private void initBottomMenu(){
        bottomSheet=new BottomSheet.Builder(this)
                .title(R.string.new_download)
                .sheet(R.menu.down_source)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == R.id.qr) {
                            intent = new Intent(DownloadManagementActivity.this, CaptureActivity.class);
                            startActivityForResult(intent, REQUEST_CODE_SCAN);
                        } else if (which == R.id.url) {
                            intent = new Intent(DownloadManagementActivity.this, UrlDownLoadActivity.class);
                            startActivity(intent);
                        } else if (which == R.id.bt) {
                            new LFilePicker()
                                    .withActivity(DownloadManagementActivity.this)
                                    .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                                    .withStartPath("/storage/emulated/0")
                                    .withIsGreater(false)
                                    .withFileSize(500 * 1024)
                                    .start();
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUESTCODE_FROM_ACTIVITY) {
            //If it is a file selection mode, you need to get the path collection of all the files selected
            //List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);//Constant.RESULT_INFO == "paths"
            List<String> list = data.getStringArrayListExtra("paths");
            String suffix = list.get(0).substring(list.get(0).lastIndexOf(".") + 1).toUpperCase();
            //If it is a folder selection mode, you need to get the folder path of your choice
            String path = data.getStringExtra("path");
            if("TORRENT".equals(suffix)) {
                Intent intent = new Intent(this, TorrentInfoActivity.class);
                intent.putExtra("torrentPath", path);
                intent.putExtra("isDown", true);
                startActivity(intent);
            }else{
                Util.alert(DownloadManagementActivity.this,"选择的文件不是种子文件", Const.ERROR_ALERT);
            }
        }else  if (requestCode == REQUEST_CODE_SCAN) {
            final String content = data.getStringExtra(Constant.CODED_CONTENT);
            new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorMain)
                    .setIcon(R.drawable.ic_success)
                    .setButtonsColorRes(R.color.colorMain)
                    .setTitle("创建任务")
                    .setMessage(content)
                    .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            downloadManagementPresenter.startTask(content);
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void addTaskSuccess() {
        // Intent intent =new Intent(UrlDownLoadActivity.this,DownloadManagementActivity.class);
        // startActivity(intent);
       // finish();
    }

    @Override
    public void addTaskFail(String msg) {
        Util.alert(this,msg, Const.ERROR_ALERT);
    }

    @Override
    public void updataApp(String version, final String url,String content) {
        new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(R.color.colorMain)
                .setIcon(R.drawable.ic_success)
                .setButtonsColorRes(R.color.colorMain)
                .setTitle("App有更新")
                .setMessage("当前版本："+ AppConfigUtil.getLocalVersionName()+"，最新版本："+version+
                "\n"+content)
                .setPositiveButton("确定下载更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        downloadManagementPresenter.startTask(url);
                    }
                })
                .show();
    }
}
