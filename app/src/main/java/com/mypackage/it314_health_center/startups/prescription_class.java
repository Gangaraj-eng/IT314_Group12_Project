package com.mypackage.it314_health_center.startups;

public class prescription_class {
    private int pres_id1;
    private String pres_text_id1;
    private String pres_text_id2;
    private String divider;


    public prescription_class(int prescription1, String s, String s1, String s2) {
        this.pres_id1 = prescription1;
        this.pres_text_id1 = s;
        this.pres_text_id2 = s1;
        this.divider = s2;
    }


    public int getPres_id1() {
        return pres_id1;
    }

    public String getPres_text_id1() {
        return pres_text_id1;
    }

    public String getPres_text_id2() {
        return pres_text_id2;
    }

    public String getDivider() {
        return divider;
    }
}
