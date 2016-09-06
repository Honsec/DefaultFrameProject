package genius.baselib.frame.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.miniram.donpush.cccid.R;
import com.miniram.donpush.cccid.api.Call_login;
import com.miniram.donpush.cccid.base.BaseAct;
import com.miniram.donpush.cccid.db.DB_PartIn;
import com.sera.hongsec.volleyhelper.imp.CallBackListener;

import genius.baselib.inter.ClickFilter;
import genius.utils.UtilsActivity;

/**
 * Created by Hongsec on 2016-09-05.
 */
public class LoginAct extends BaseAct implements View.OnClickListener{

    private View id_loginbtn;
    private TextView id_loginid;
    private TextView id_passwd;

    @Override
    protected int setContentLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void viewLoadFinished() {
        UtilsActivity.getInstance().killAllActivities(this.getClass());

    }

    @Override
    protected void initViews() {
        id_loginbtn = findViewBId(R.id.id_loginbtn);
        id_loginid = findViewBId(R.id.id_loginid);
        id_passwd = findViewBId(R.id.id_passwd);

        id_loginbtn.setOnClickListener(this);

        DB_PartIn db_partIn = new DB_PartIn(getApplicationContext());
        db_partIn.openDB();
        db_partIn.closeDB();

    }


    private ClickFilter clickFilter = new ClickFilter();




    @Override
    public void onClick(View v) {
        if(clickFilter.isClicked())return;

        switch (v.getId()){

            case R.id.id_loginbtn:
                //Login or join
                if(TextUtils.isEmpty(id_loginid.getText().toString())){
                    Toast.makeText(this,R.string.loginid_empty,Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(id_passwd.getText().toString())){
                    Toast.makeText(this,R.string.pw_empty,Toast.LENGTH_SHORT).show();    return;
                }

                Call_login login = new Call_login(this,id_loginid.getText().toString().trim(),id_passwd.getText().toString());
                login.request(this, new CallBackListener<Call_login>() {
                    @Override
                    public void showloadingUI() {
                        LoginAct.this.showLoading();
                    }

                    @Override
                    public void cancleloadingUI() {
                        LoginAct.this.cancleLoading();
                    }

                    @Override
                    public void onResponse(Call_login call_login) {
                        Intent intent = new Intent(LoginAct.this,MainAct.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }

                    @Override
                    public void onErrorResponse(Call_login call_login) {

                        Toast.makeText(LoginAct.this,call_login.result.msg+"", Toast.LENGTH_SHORT).show();
                    }
                });


                break;

        }

    }
}
