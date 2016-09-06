package genius.baselib.frame;

import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.miniram.donpush.cccid.center.CConfig;
import com.miniram.donpush.cccid.center.CStatic;
import com.miniram.donpush.cccid.util.AdvertisingIdClient;
import com.miniram.donpush.cccid.util.CTools;

import java.io.IOException;

import genius.baselib.PreferenceUtil;
import genius.baselib.base.BaseApplication;

/**
 * Created by Hongsec on 2016-09-05.
 */
public class BaseApp extends BaseApplication {

    @Override
    public void onCreate() {


        CConfig.init(getApplicationContext());

        super.onCreate();

//        UpdateDB();


        getGoogleAdId();

        CTools.updateVersion(getApplicationContext());

        setToken();
    }

    private void setToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        if(!TextUtils.isEmpty(token)){
            PreferenceUtil.getInstance(getApplicationContext()).setValue(CStatic.SP_REGID,token);
        }
    }


    private void getGoogleAdId() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    AdvertisingIdClient.AdInfo advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                    if (advertisingIdInfo != null) {
                        PreferenceUtil.getInstance(getApplicationContext()).setValue(CStatic.SP_GID, advertisingIdInfo.getId() + "");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }

            }
        }).start();
    }



}
