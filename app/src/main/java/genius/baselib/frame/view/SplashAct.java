package genius.baselib.frame.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import genius.baselib.frame.base.BaseAct;

/**
 * Created by Hongsec on 2016-09-06.
 */
public class SplashAct extends BaseAct {
    @Override
    protected int setContentLayoutResID() {
        return 0;
    }

    @Override
    protected void viewLoadFinished() {
        if (CheckPermission_request(permissions, 1)) return;
        doActions();
    }

    private void doActions() {


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






