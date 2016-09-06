package genius.baselib.frame.center;

/**
 * Save static final filed
 * Created by Hongsec on 2016-09-05.
 */
public class CStatic {

    public static final String DEV_URL = "http://dev-cccid.donpush.com";
    public static final String REAL_URL = "http://cccid.donpush.com";
    public static final String KEY = "aresjoyminiramdoaresjoyminiramdo";


    //지우면 안되는 값들
    /**
     * 푸시토큰
     */
    public static final String SP_REGID = "reg_token";
    public static final String SP_GID = "googleid";
    public static final String SP_VERSION_CODE = "versioncode";
    public static final String SP_VERSION_NAME = "versionname";


    //회원 정보들
    /**
     * int
     */
    public static final String SP_MEMNO = "mem_no";
    /**
     * String
     */
    public static final String SP_SES = "SES";
    /**
     * Int
     */
    public static final String SP_POINT = "point";
    /**
     * String
     */
    public static final String SP_EMAIL = "email";
    /**
     * boolean
     */
    public static final String SP_APPROVAL = "approval";
    /**
     * int
     */
    public static final String SP_NEWVERSION = "new_version";


    //bus
    public static final int BUS_UPDATE_PROFILE = 111;
}
