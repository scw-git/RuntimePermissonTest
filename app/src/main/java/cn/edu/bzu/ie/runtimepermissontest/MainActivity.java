package cn.edu.bzu.ie.runtimepermissontest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button make_call;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        make_call = findViewById(R.id.make_call);
        make_call.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.make_call:
                //如果没有申请权限，调用申请权限对话框
                if(ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    //调用申请权限对话框
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},1);
                }else {//申请了权限
                    call();
                }
                break;
        }
    }

    //打电话
    private void call(){
        Intent intent = new Intent();//等价 Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setAction(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
    }

    //弹出申请权限的对话框后，不管是同意还是或拒绝。结果都会存储在 grantResults 中
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                    if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                        call();
                    }else {
                        //Toast.makeText(context,"获取权限失败",Toast.LENGTH_SHORT).show();
                        showDialog();
                    }
                break;
        }
    }

    private void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("警告！")
                .setMessage("请前往设置->应用->PermissionDemo->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                }).show();
    }
}
