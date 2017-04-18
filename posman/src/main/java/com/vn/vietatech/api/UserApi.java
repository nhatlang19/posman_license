package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.model.Cashier;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

public class UserApi extends AbstractAPI {

    public UserApi(Context context) throws Exception {
        super(context);
    }

    public Cashier login(String username, String password) throws Exception {
        setMethod(METHOD_GET_USER);
        Cashier cashier = new Cashier();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");
        if (soapObject.getPropertyCount() != 0) {

            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            SoapObject tableObject = (SoapObject) webServiceResponse
                    .getProperty("Table");

            cashier.setId(tableObject.getProperty("CashierID").toString());
            cashier.setName(tableObject.getProperty("CashierName").toString());
            cashier.setPass(tableObject.getProperty("CashierPwd").toString());
            cashier.setUserGroup(tableObject.getProperty("UserGroup").toString());
        }
        return cashier;
    }
}
