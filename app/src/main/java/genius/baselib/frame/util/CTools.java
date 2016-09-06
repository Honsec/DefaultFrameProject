package genius.baselib.frame.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.miniram.donpush.cccid.center.CStatic;
import com.miniram.donpush.cccid.view.SplashAct;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import genius.baselib.PreferenceUtil;
import genius.utils.encode.AES256Cipher;

/**
 * Created by Hongsec on 2016-09-05.
 */
public class CTools {

    /**
     * Get Imei
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        String deviceId = "";
        try {
            deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deviceId;
    }


    public static void updateVersion(Context context) {
        try {
            PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            PreferenceUtil.getInstance(context).setValue(CStatic.SP_VERSION_NAME,i.versionName);
            PreferenceUtil.getInstance(context).setValue(CStatic.SP_VERSION_CODE,i.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void logout(Context mContext){
        PreferenceUtil.getInstance(mContext.getApplicationContext()).remove(CStatic.SP_SES);
        PreferenceUtil.getInstance(mContext.getApplicationContext()).remove(CStatic.SP_MEMNO);
        Intent intent = new Intent(mContext, SplashAct.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    public static String Encode(String value){

        String result = "";
        try {
            if(!TextUtils.isEmpty(value))
            result = AES256Cipher.AES_Encode(value,CStatic.KEY);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return result.trim();
    }

}
