package genius.baselib.frame.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;

import com.miniram.donpush.cccid.db.DB_Install;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hongsec on 2016-08-30.
 */
public class AppUploadService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        DB_Install db_install = new DB_Install(this);
        db_install.openDB();

        for(String pkg : getInstalledAPP()){
            db_install.InsertOrUpdate(pkg,"");
        }

        db_install.closeDB();



        return super.onStartCommand(intent, flags, startId);
    }


    public List<String> getInstalledAPP(){
        PackageManager packageManager = this.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        List<String> appBeens= new ArrayList<>();

        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {
                appBeens.add(packageInfo.packageName);
            } else {
                // 系统应用　　　　　　　　
            }
        }

        return  appBeens;
    }


}
