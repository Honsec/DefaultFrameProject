package genius.baselib.frame.bean;

/**
 * Created by Hongsec on 2016-09-05.
 */
public class PartInBean {


    private String part_id = "";
    private String campaign_id = "";
    private String udid = "";
    private String ifa = "";
    private String pid = "";
    private String landingurl = "";
    private String pkg = "";

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getPart_id() {
        return part_id;
    }

    public void setPart_id(String part_id) {
        this.part_id = part_id;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getIfa() {
        return ifa;
    }

    public void setIfa(String ifa) {
        this.ifa = ifa;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLandingurl() {
        return landingurl;
    }

    public void setLandingurl(String landingurl) {
        this.landingurl = landingurl;
    }

    @Override
    public String toString() {
        return "PartInBean{" +
                "part_id='" + part_id + '\'' +
                ", campaign_id='" + campaign_id + '\'' +
                ", udid='" + udid + '\'' +
                ", ifa='" + ifa + '\'' +
                ", pid='" + pid + '\'' +
                ", landingurl='" + landingurl + '\'' +
                '}';
    }
}
