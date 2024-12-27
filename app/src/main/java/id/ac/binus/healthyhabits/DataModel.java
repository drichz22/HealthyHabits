package id.ac.binus.healthyhabits;

public class DataModel {
    private int plan_id;
    private String plan_desc;
    private String plan_date;

    public DataModel(int plan_id, String plan_desc, String plan_date) {
        this.plan_id = plan_id;
        this.plan_desc = plan_desc;
        this.plan_date = plan_date;
    }

    public String getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(String plan_date) {
        this.plan_date = plan_date;
    }

    public String getPlan_desc() {
        return plan_desc;
    }

    public void setPlan_desc(String plan_desc) {
        this.plan_desc = plan_desc;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }
}
