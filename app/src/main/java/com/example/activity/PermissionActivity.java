package com.example.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 创建日期：2018-03-20 on 16:42
 * 作者：ls
 */

@RuntimePermissions
public class PermissionActivity extends Activity{

    private static final String TAG = "PermissionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PermissionActivityPermissionsDispatcher.readFileWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //PermissionActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void readFile(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File f = new File(path, "1.txt");
        BufferedInputStream bis = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            bis = new BufferedInputStream(fis);
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = bis.read(buf)) != -1){
                Log.d(TAG, "len = " + len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnShowRationale(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void OnShowRationaleForStorage(final PermissionRequest request){
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setMessage("这是解释：我们需要该权限，否则app会无法正确运行，是否打开")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }


    @OnPermissionDenied(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void OnPermissionDeniedForStorage(){
        Log.d(TAG, "权限被拒绝了.....");
    }

    @OnNeverAskAgain(value = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void onNerverAskAgainForS(){
        Log.d(TAG, "不再询问被选择了....");
    }

}
