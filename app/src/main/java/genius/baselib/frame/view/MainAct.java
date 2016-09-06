package genius.baselib.frame.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.miniram.donpush.cccid.R;
import com.miniram.donpush.cccid.api.Call_offer_list;
import com.miniram.donpush.cccid.api.Call_offer_part;
import com.miniram.donpush.cccid.api.Call_profile;
import com.miniram.donpush.cccid.base.BaseAct;
import com.miniram.donpush.cccid.bean.OfferwallItemInfo;
import com.miniram.donpush.cccid.center.CStatic;
import com.miniram.donpush.cccid.util.AutoSwipeListener;
import com.miniram.donpush.cccid.util.CTools;
import com.sera.hongsec.volleyhelper.VolleyHelper;
import com.sera.hongsec.volleyhelper.imp.CallBackListener;

import java.util.ArrayList;
import java.util.List;

import genius.baselib.PreferenceUtil;
import genius.baselib.bus.BusMessage;
import genius.baselib.inter.ClickFilter;
import genius.commonrecyclerviewadpater.CommonAdapter;
import genius.commonrecyclerviewadpater.DividerItemDecoration;
import genius.commonrecyclerviewadpater.base.ViewHolder;
import genius.utils.UtilsActivity;
import genius.utils.Utils_Alert;

public class MainAct extends BaseAct {

    private TextView id_main_email;
    private TextView id_main_logout;
    private AutoSwipeListener id_main_refresh;
    private RecyclerView id_main_recyclerview;
    private MyAdapter listAdapter;
    private ProgressBar id_main_loading;
    private View id_main_waiting;

    @Override
    protected int setContentLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void viewLoadFinished() {
        UtilsActivity.getInstance().killAllActivities(MainAct.class);
//        api_offerlist(true);
//        id_main_refresh.autoRefresh();
    }

    @Override
    protected void initViews() {
        id_main_email = findViewBId(R.id.id_main_email);
        id_main_loading = findViewBId(R.id.id_main_loading);
        id_main_logout = findViewBId(R.id.id_main_logout);
        id_main_waiting = findViewBId(R.id.id_main_waiting);
        id_main_refresh = findViewBId(R.id.id_main_refresh);
        id_main_refresh.setOnRefreshListener(new OnSwipeRefresh());
        id_main_recyclerview = findViewBId(R.id.id_main_recyclerview);
        id_main_recyclerview.setHasFixedSize(true);
        id_main_recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        id_main_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        id_main_recyclerview.setAdapter(listAdapter = new MyAdapter(this, R.layout.item_offer, new ArrayList<OfferwallItemInfo>()));
        id_main_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(false);
            }
        });
    }

    @Override
    protected void Bus_onEvent(BusMessage myBus) {
        super.Bus_onEvent(myBus);
        if (id_main_loading == null) return;
        updateProfile(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProfile(true);
    }

    Handler handler = new Handler();

    private class OnSwipeRefresh implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            api_offerlist(false);
        }
    }


    private class MyAdapter extends CommonAdapter<OfferwallItemInfo> {


        public MyAdapter(Context context, int layoutId, List<OfferwallItemInfo> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, final OfferwallItemInfo offerwallItemInfo, int position) {

            holder.setText(R.id.id_item_offertitle, offerwallItemInfo.title);
            holder.setText(R.id.id_item_offerdetail, offerwallItemInfo.sub_title);
            ((NetworkImageView) holder.getView(R.id.id_item_offerifon)).setImageUrl(offerwallItemInfo.icon, VolleyHelper.getImageLoader(MainAct.this));
            holder.getView(R.id.id_item_offer_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    part_mission(offerwallItemInfo);

                }


            });
        }


        public void part_mission(final OfferwallItemInfo offerwallItemInfo) {
            Utils_Alert.showAlertDialog(MainAct.this, offerwallItemInfo.title, R.string.ask_sure_parin, false, android.R.string.ok, new DialogInterface.OnClickListener() {
                ClickFilter clickFilter = new ClickFilter();

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (clickFilter.isClicked()) return;

                    Call_offer_part call_offer_part = new Call_offer_part(MainAct.this, offerwallItemInfo.id + "", offerwallItemInfo.pkg);
                    call_offer_part.request(MainAct.this, new CallBackListener<Call_offer_part>() {
                        @Override
                        public void showloadingUI() {
                            MainAct.this.showLoading();
                        }

                        @Override
                        public void cancleloadingUI() {
                            MainAct.this.cancleLoading();
                        }

                        @Override
                        public void onResponse(Call_offer_part call_offer_part) {
                            //goto install
                            Uri uri = Uri.parse("market://details?id=" + offerwallItemInfo.pkg);
                            Intent it = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(it);
                        }

                        @Override
                        public void onErrorResponse(Call_offer_part call_offer_part) {
                            if (TextUtils.isEmpty(call_offer_part.result.msg)) {
                                Toast.makeText(MainAct.this, R.string.parin_failed, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainAct.this, call_offer_part.result.msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }, android.R.string.cancel, null, null);
        }


    }

    ClickFilter back_app = new ClickFilter(2000L);

    @Override
    public void onBackPressed() {
        if (back_app.isClicked()) {
            super.onBackPressed();
        } else {
            Toast.makeText(MainAct.this, R.string.backtoclose, Toast.LENGTH_SHORT).show();
        }
    }

    public void updateProfile(final boolean first) {

        Call_profile call_profile = new Call_profile(this);
        call_profile.request(this, new CallBackListener<Call_profile>() {
            @Override
            public void showloadingUI() {
//                MainAct.this.showLoading();
                id_main_loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void cancleloadingUI() {
//                MainAct.this.cancleLoading();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        id_main_loading.setVisibility(View.GONE);
                    }
                }, 500);

            }

            @Override
            public void onResponse(Call_profile call_profile) {
                //upDate Email
                id_main_email.setText(PreferenceUtil.getInstance(getApplicationContext()).getValue(CStatic.SP_EMAIL, "") + "\n" + "Point: " +
                        PreferenceUtil.getInstance(getApplicationContext()).getValue(CStatic.SP_POINT, 0));
                if (PreferenceUtil.getInstance(getApplicationContext()).getValue(CStatic.SP_APPROVAL, false)) {
                    id_main_waiting.setVisibility(View.GONE);
                    id_main_refresh.setVisibility(View.VISIBLE);
                    if(first){
                        id_main_refresh.autoRefresh();
                    }
                } else {
                    id_main_waiting.setVisibility(View.VISIBLE);
                    id_main_refresh.setVisibility(View.GONE);
//                    id_main_waiting.setVisibility(View.GONE);
//                    id_main_refresh.setVisibility(View.VISIBLE);
//                    if(first){
//                        id_main_refresh.autoRefresh();
//                    }
                }
            }


            @Override
            public void onErrorResponse(Call_profile call_profile) {
                id_main_email.setText("");
                Toast.makeText(MainAct.this, R.string.profileload_failed, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void logout(View v) {
        CTools.logout(this);
    }


    public void api_offerlist(final boolean showdialog) {

        Call_offer_list offerList = new Call_offer_list(this);
        offerList.request(this, new CallBackListener<Call_offer_list>() {
            @Override
            public void showloadingUI() {
                if (showdialog) {
                    MainAct.this.showLoading();
                }
            }

            @Override
            public void cancleloadingUI() {
                id_main_refresh.setRefreshing(false);
                if (showdialog) {
                    MainAct.this.cancleLoading();
                }
            }

            @Override
            public void onResponse(Call_offer_list call_offer_list) {

                listAdapter.setmDatas(call_offer_list.offerwallItemInfos);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(Call_offer_list call_offer_list) {

            }
        });

    }


}
