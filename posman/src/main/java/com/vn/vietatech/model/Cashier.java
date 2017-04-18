package com.vn.vietatech.model;

public class Cashier {
    private String _id;
    private String _name;
    private String _pass;
    private String _userGroup;


    public Cashier() {
        this._id = "";
        this._name = "";
        this._pass = "";
        this._userGroup = "";
    }

    public Cashier(String id, String name, String pass, String userGroup) {
        this._id = id;
        this._name = name;
        this._pass = pass;
        this._userGroup = userGroup;
    }

    public String getId() {
        return this._id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getPass() {
        return _pass;
    }

    public void setPass(String _pass) {
        this._pass = _pass;
    }

    public String getUserGroup() {
        return _userGroup;
    }

    public void setUserGroup(String _userGroup) {
        this._userGroup = _userGroup;
    }
}
