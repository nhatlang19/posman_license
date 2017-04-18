package com.vn.vietatech.model;

import java.util.ArrayList;

public class Item {
    public static final String STATUS_OLD = "#";
    public static final String STATUS_CANCEL = "C";
    private static final String SEPARATE = "|";
    private String id;
    private String splited;
    private String qty;
    private String printStatus;
    private String itemName;
    private String price;
    private String total;
    private String itemType;
    private String itemCode;
    private String modifierInt;
    private String masterCode;
    private String comboClass;
    private String hidden;
    private String instruction;
    private ArrayList<Remark> remarks;

    public Item() {
        id = " ";
        qty = "1";
        splited = "0";
        printStatus = " ";
        itemName = " ";
        price = "0";
        total = "0";
        itemType = " ";
        itemCode = " ";
        modifierInt = "0";
        masterCode = " ";
        comboClass = " ";
        hidden = " ";
        instruction = " ";
        setRemarks(new ArrayList<Remark>());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        if (instruction.contains("anyType{}")) {
            instruction = " ";
        }
        this.instruction = instruction;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getComboClass() {
        return comboClass;
    }

    public void setComboClass(String comboClass) {
        this.comboClass = comboClass;
    }

    public String getMasterCode() {
        return masterCode;
    }

    public void setMasterCode(String masterCode) {
        this.masterCode = masterCode;
    }

    public String getModifier() {
        return modifierInt;
    }

    public void setModifier(String modifierInt) {
        this.modifierInt = modifierInt;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        float iPrice = Float.parseFloat(price);
        this.price = String.valueOf((int) iPrice);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrintStatus() {
        if (printStatus.trim().isEmpty()) {
            return printStatus;
        } else if (printStatus.equals("9")) {
            printStatus = STATUS_CANCEL;
        } else {
            printStatus = STATUS_OLD;
        }
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public ArrayList<Remark> getRemarks() {
        return remarks;
    }

    public void setRemarks(ArrayList<Remark> remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        String result = "";
        result += qty + SEPARATE;
        result += printStatus + SEPARATE;
        result += itemName + SEPARATE;
        result += price + SEPARATE;
        result += total + SEPARATE;
        result += itemType + SEPARATE;
        result += itemCode + SEPARATE;
        result += modifierInt + SEPARATE;
        result += masterCode + SEPARATE;
        result += comboClass + SEPARATE;
        result += hidden + SEPARATE;
        result += instruction;
        return result;
    }

    public String getSplited() {
        return splited;
    }

    public void setSplited(String splited) {
        this.splited = splited;
    }
}
