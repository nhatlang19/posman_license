package com.vn.vietatech.model;

public class SubMenu {
    private String seqNum;
    private String description;
    private String defaultValue;
    private PosMenu posMenu;
    private Item item;

    public SubMenu() {
        posMenu = new PosMenu();
        this.setItem(null);
        this.setSeqNum("");
        this.setDescription("");
        this.setDefaultValue("");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public PosMenu getPosMenu() {
        return posMenu;
    }

    public void setPosMenu(PosMenu posMenu) {
        this.posMenu = posMenu;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
