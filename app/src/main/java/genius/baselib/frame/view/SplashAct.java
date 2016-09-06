package genius.baselib.frame.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.miniram.donpush.cccid.R;
import com.miniram.donpush.cccid.base.BaseAct;
import com.miniram.donpush.cccid.center.CStatic;
import com.miniram.donpush.cccid.service.AppUploadService;

import genius.baselib.PreferenceUtil;

/**
 * Created by Hongsec on 2016-09-06.
 */
public class SplashAct extends BaseAct {
    @Override
    protected int setContentLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void viewLoadFinished() {
        if (CheckPermission_request(permissions, 1)) return;
        doActions();
    }

    private void doActions() {

        //앱리스트를 올림
        Intent service = new Intent(this, AppUploadService.class);
        service.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(service);


        String ses = PreferenceUtil.getInstance(getApplicationContext()).getValue(CStatic.SP_SES, "");
        int em = PreferenceUtil.getInstance(getApplicationContext()).getValue(CStatic.SP_MEMNO, -1);


        if (TextUtils.isEmpty(ses) || em==-1) {
            // not logined
            Intent intent = new Intent(this,LoginAct.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intent);
        }else{
            //logined
            Intent intent = new Intent(this,MainAct.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    String[] permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.GET_ACCOUNTS
    };

    @Override
    protected void initViews() {

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 1:
                boolean flag = false;
                //권한 신청이 거부되였을 경우 앱을 끕니다.
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        flag = true;
                    }
                }

                if(!flag){
                    doActions();
                }

                break;
        }

    }

}






