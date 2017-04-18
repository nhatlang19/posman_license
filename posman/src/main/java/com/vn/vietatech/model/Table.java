package com.vn.vietatech.model;

import com.vn.vietatech.posman.R;

public class Table {
    public static String ACTION_ADD = "add";
    public static String ACTION_EDIT = "edit";
    public static String STATUS_OPEN = "Open";
    public static String STATUS_CLOSE = "Close";
    private String tableNo;
    private String status;
    private String openBy;
    private String description2;
    private String action;
    private Section section;

    public Table() {
        tableNo = "";
        status = "";
        openBy = "";
        description2 = "";
        section = new Section();
        action = ACTION_ADD;
    }

    public String getTableNo() {
        return this.tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getColor() {
        int color = R.drawable.table_item_a;
        switch (getStatus()) {
            case "A":
                color = R.drawable.table_item_a;
                break;
            case "O":
                color = R.drawable.table_item_o;
                break;
            case "R":
                color = R.drawable.table_item_r;
                break;
            case "B":
                color = R.drawable.table_item_b;
                break;
        }
        return color;
    }

    public String getOpenBy() {
        return openBy;
    }

    public void setOpenBy(String openBy) {
        this.openBy = openBy;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        if (description2.toLowerCase().equals("anytype{}")) {
            description2 = "";
        }
        this.description2 = description2;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isAddNew() {
        return action.equals(ACTION_ADD);
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
