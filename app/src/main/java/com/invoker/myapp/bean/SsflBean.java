package com.invoker.myapp.bean;

/**
 * Created by invoker on 2019-03-25
 * Description:
 */
public class SsflBean extends JcssBean {
    public String ID;
    public String FL;
    public String MC;
    public String FID2;
    public String BZSM;

    public SsflBean(String ID, String FL, String MC, String FID2, String BZSM) {
        super(ID, FL, MC, FID2, BZSM);
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String getFL() {
        return FL;
    }

    @Override
    public void setFL(String FL) {
        this.FL = FL;
    }

    @Override
    public String getMC() {
        return MC;
    }

    @Override
    public void setMC(String MC) {
        this.MC = MC;
    }

    @Override
    public String getFID2() {
        return FID2;
    }

    @Override
    public void setFID2(String FID2) {
        this.FID2 = FID2;
    }

    @Override
    public String getBZSM() {
        return BZSM;
    }

    @Override
    public void setBZSM(String BZSM) {
        this.BZSM = BZSM;
    }
}
