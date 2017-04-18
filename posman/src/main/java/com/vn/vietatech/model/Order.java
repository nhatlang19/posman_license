package com.vn.vietatech.model;

public class Order {
    private String ordExt;
    private String posPer;

    public Order() {
        setOrdExt("0_0");
        setPosPer("0_0");
    }

    public String getOrd() {
        String[] list = getOrdExt().split("_");
        return list[0];
    }

    public String getExt() {
        String[] list = getOrdExt().split("_");
        if (list.length < 2)
            return "0";
        return list[1];
    }


    public String getPos() {
        String[] list = getPosPer().split("_");
        return list[0];
    }

    public String getPer() {
        String[] list = getPosPer().split("_");
        if (list.length < 2)
            return "0";
        return list[1];
    }

    public String getOrdExt() {
        return ordExt;
    }

    public void setOrdExt(String ordExt) {
        this.ordExt = ordExt;
    }

    public String getPosPer() {
        return posPer;
    }

    public void setPosPer(String posPer) {
        this.posPer = posPer;
    }


}
