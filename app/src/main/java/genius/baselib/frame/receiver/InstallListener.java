package genius.baselib.frame.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.miniram.donpush.cccid.api.Call_offer_comp;
import com.miniram.donpush.cccid.bean.PartInBean;
import com.miniram.donpush.cccid.center.CStatic;
import com.miniram.donpush.cccid.db.DB_Install;
import com.miniram.donpush.cccid.db.DB_PartIn;
import com.sera.hongsec.volleyhelper.imp.CallBackListener;

import de.greenrobot.event.EventBus;
import genius.baselib.bus.BusMessage;

/**
 * Created by Hongsec on 2016-09-06.
 */
public class InstallListener extends BroadcastReceiver {
    private String substring;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {
            Log.v("demo", "pkg_received");

            if ("android.intent.action.PACKAGE_ADDED".equalsIgnoreCase(intent.getAction())) {
                substring = intent.getData().getSchemeSpecificPart();
                Log.v("demo", "pkg_added:" + substring);


                //참여 확인 및 기록 삭제
                DB_PartIn db_partIn = new DB_PartIn(context);
                db_partIn.openDB();
                PartInBean partInItem = db_partIn.getPartInItem_pkg(substring);
                if (!TextUtils.isEmpty(partInItem.getCampaign_id())) {
                    db_partIn.deletePartInItem(partInItem.getCampaign_id());
                    complete(context,partInItem);
                }
                db_partIn.closeDB();

                //업데이트
                DB_Install db_install = new DB_Install(context);
                db_install.openDB();
                db_install.InsertOrUpdate(substring,"");
                db_install.closeDB();


            }
        }

    }


    public void complete(Context context, PartInBean partInBean) {


        Call_offer_comp offer_comp = new Call_offer_comp(context, partInBean);
        offer_comp.request(context, new CallBackListener<Call_offer_comp>() {
            @Override
            public void showloadingUI() {

            }

            @Override
            public void cancleloadingUI() {

            }

            @Override
            public void onResponse(Call_offer_comp call_offer_comp) {
                    //프로필 업데이트
                EventBus.getDefault().post(new BusMessage().setAction_code(CStatic.BUS_UPDATE_PROFILE).setBustype(BusMessage.BUSTYPE.onEvent).setTarget_all(true));
            }

            @Override
            public void onErrorResponse(Call_offer_comp call_offer_comp) {

            }
        });
    }
}
