package in.mobicomly.download.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.common.BaseActivity;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.p.AppSettingPresenter;
import in.mobicomly.download.mvp.p.AppSettingPresenterImp;
import in.mobicomly.download.mvp.v.AppSettingView;

public class AppSettingActivity extends BaseActivity implements AppSettingView {
    private TextView localPathText;
    private TextView downCountText;
    private Switch mobileNetSwitch;
    private Switch downNotifySwitch;
    private RelativeLayout setlocalpath;
    private ImageView downcountplus;
    private  ImageView downcountcut;

    private AppSettingPresenter appSettingPresenter;
    int REQUESTCODE_FROM_ACTIVITY = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);
        super.setTopBarTitle(getString(R.string.setting));
        localPathText = findViewById(R.id.local_path_text);
        downCountText = findViewById(R.id.down_count_text);
        mobileNetSwitch = findViewById(R.id.mobile_net);
        downNotifySwitch = findViewById(R.id.down_notify);
        setlocalpath = findViewById(R.id.set_local_path);
        downcountplus = findViewById(R.id.down_count_plus);
        downcountcut = findViewById(R.id.down_count_cut);

        appSettingPresenter=new AppSettingPresenterImp(this);
        initView();
    }

    private void initView(){
        mobileNetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int net=b?Const.MOBILE_NET_OK:Const.MOBILE_NET_NOT;
                appSettingPresenter.setMobileNet(net+"");
            }
        });
        downNotifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int net=b?Const.MOBILE_NET_OK:Const.MOBILE_NET_NOT;
                appSettingPresenter.setDownNotify(net+"");
            }
        });
        setlocalpath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                    Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivityForResult(Intent.createChooser(i, "Choose directory"), 9999);
                }
            }
        });
        downcountplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.valueOf(downCountText.getText().toString());
                if(count<Const.MAX_DOWN_COUNT){
                    count++;
                    downCountText.setText(count+"");
                    appSettingPresenter.setDownCount(count+"");
                }
            }
        });
        downcountcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.valueOf(downCountText.getText().toString());
                if(count>Const.MIN_DOWN_COUNT){
                    count--;
                    downCountText.setText(count+"");
                    appSettingPresenter.setDownCount(count+"");
                }
            }
        });

    }
    @Override
    public void initSetting(String key, String value) {
        if(Const.SAVE_PATH_KEY.equals(key)){
            localPathText.setText(value);
        }else if(Const.DOWN_COUNT_KEY.equals(key)){
            downCountText.setText(value);
        }else if(Const.MOBILE_NET_KEY.equals(key)){
            Boolean check=value.equals(Const.MOBILE_NET_OK+"")?true:false;
            mobileNetSwitch.setChecked(check);
        }else if(Const.DOWN_NOTIFY_KEY.equals(key)){
            Boolean check=value.equals(Const.MOBILE_NET_OK+"")?true:false;
            downNotifySwitch.setChecked(check);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 9999:
                localPathText.setText(String.valueOf(data.getData()));
                appSettingPresenter.setSavePath(String.valueOf(data.getData()));
                break;
        }
        if (resultCode == RESULT_OK) {


            if (requestCode == REQUESTCODE_FROM_ACTIVITY) {
                List<String> list = data.getStringArrayListExtra("paths");
                String path = data.getStringExtra("path");
                localPathText.setText(path.toString());
                appSettingPresenter.setSavePath(path.toString());
            }
        }
    }

}
