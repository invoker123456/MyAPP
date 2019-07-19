package com.invoker.myapp.bean;

/**
 * Created by invoker on 2019-03-25
 * Description:
 */
public class JcssBean {
    public String ID;
    public String FL;
    public String MC;
    public String FID2;
    public String BZSM;

    public JcssBean(String ID, String FL, String MC, String FID2, String BZSM) {
        this.ID = ID;
        this.FL = FL;
        this.MC = MC;
        this.FID2 = FID2;
        this.BZSM = BZSM;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFL() {
        return FL;
    }

    public void setFL(String FL) {
        this.FL = FL;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public String getFID2() {
        return FID2;
    }

    public void setFID2(String FID2) {
        this.FID2 = FID2;
    }

    public String getBZSM() {
        return BZSM;
    }

    public void setBZSM(String BZSM) {
        this.BZSM = BZSM;
    }
}
