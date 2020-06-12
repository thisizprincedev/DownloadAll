package com.mobicomly.downloadall;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;


import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.common.RuntimeRationale;
import in.mobicomly.download.mvp.p.AppConfigPresenter;
import in.mobicomly.download.mvp.p.AppConfigPresenterImp;

public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getPermission();
        }else{
            goHome();
        }

    }
    private void goHome(){
        AppConfigPresenter appConfigPresenter=new AppConfigPresenterImp();
        appConfigPresenter.getMagnetWebRule();
        Intent intent =new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getPermission(){
        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE,
                        Permission.CAMERA,
                        Permission.READ_PHONE_STATE)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //Toast.makeText(WelcomeActivity.this, R.string.successfully, Toast.LENGTH_SHORT).show();
                        goHome();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                       // toast(R.string.failure);
                        if (AndPermission.hasAlwaysDeniedPermission(WelcomeActivity.this, permissions)) {
                            showSettingDialog(WelcomeActivity.this, permissions);
                        }
                    }
                })
                .start();
    }

    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }




}
