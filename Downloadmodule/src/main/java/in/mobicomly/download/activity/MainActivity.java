package in.mobicomly.download.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cocosw.bottomsheet.BottomSheet;
import com.coorchice.library.SuperTextView;
import com.leon.lfilepickerlibrary.LFilePicker;

import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.common.BaseActivity;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.util.Util;


public class MainActivity extends BaseActivity {
    private BottomSheet.Builder bottomSheet=null;
    private SuperTextView adddownload;
    private SuperTextView downmanage;
    int REQUESTCODE_FROM_ACTIVITY = 1000;
    private static final int REQUEST_CODE_CHOOSE = 10086;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adddownload = findViewById(R.id.add_download);
        downmanage = findViewById(R.id.down_manage);
        initBottomMenu();
        adddownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.show();
            }
        });
        downmanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,DownloadManagementActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initBottomMenu(){
        bottomSheet=new BottomSheet.Builder(this)
                .title(R.string.new_download)
                .sheet(R.menu.down_source)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == R.id.qr) {
                        } else if (which == R.id.url) {
                            Intent intent = new Intent(MainActivity.this, UrlDownLoadActivity.class);
                            startActivity(intent);
                        } else if (which == R.id.bt) {
                            new LFilePicker()
                                    .withActivity(MainActivity.this)
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
                Util.alert(MainActivity.this,"选择的文件不是种子文件", Const.ERROR_ALERT);
            }
        }
    }



}
