package com.vn.vietatech.model;

public class Section {
    private String _id;
    private String _name;
    private String _revCtr;

    public Section() {
    }

    public Section(String id, String name) {
        this._id = id;
        this._name = name;
    }

    public String getId() {
        return this._id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getRevCtr() {
        return this._revCtr;
    }

    public void setRevCtr(String revCtr) {
        this._revCtr = revCtr;
    }
}
