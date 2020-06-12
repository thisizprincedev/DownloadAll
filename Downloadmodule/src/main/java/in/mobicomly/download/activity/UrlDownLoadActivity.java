package in.mobicomly.download.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.coorchice.library.SuperTextView;
import com.xunlei.downloadlib.XLTaskHelper;

import in.mobicomly.download.R;
import in.mobicomly.download.common.BaseActivity;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.p.UrlDownLoadPresenter;
import in.mobicomly.download.mvp.p.UrlDownLoadPresenterImp;
import in.mobicomly.download.mvp.v.UrlDownLoadView;
import in.mobicomly.download.util.Util;

public class UrlDownLoadActivity extends BaseActivity implements UrlDownLoadView{
    private EditText urlInput;
    private SuperTextView start;
    private UrlDownLoadPresenter urlDownLoadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_download);
        super.setTopBarTitle(R.string.new_download);
        urlInput = findViewById(R.id.url_input);
        start = findViewById(R.id.start_download);
        XLTaskHelper.init(getApplicationContext());
        urlDownLoadPresenter=new UrlDownLoadPresenterImp(this);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlDownLoadPresenter.startTask(urlInput.getText().toString().trim());
            }
        });
    }

    @Override
    public void addTaskSuccess() {
       // Intent intent =new Intent(UrlDownLoadActivity.this,DownloadManagementActivity.class);
       // startActivity(intent);
        finish();
    }

    @Override
    public void addTaskFail(String msg) {
        Util.alert(this,msg, Const.ERROR_ALERT);
    }
}
