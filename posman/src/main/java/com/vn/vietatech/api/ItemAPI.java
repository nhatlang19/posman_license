package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.Remark;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemAPI extends AbstractAPI {

    public ItemAPI(Context context) throws Exception {
        super(context);
    }

    public ArrayList<Remark> getRemarkByItem(String itemCode) throws Exception {
        setMethod(METHOD_GET_REMARK_BY_ITEM);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("itemcode", itemCode);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");

        ArrayList<Remark> remarks = new ArrayList<Remark>();
        if (soapObject.getPropertyCount() != 0) {

            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                Remark remark = new Remark();
                remark.setName(tableObject.getProperty("Description").toString());

                remarks.add(remark);
            }
        }
        return remarks;
    }

    public Item getItemBySubMenuSelected(String currSubItem) throws Exception {
        setMethod(METHOD_GET_ITEM);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("currSubItem", currSubItem);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");

        Item item = new Item();
        if (soapObject.getPropertyCount() != 0) {

            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");
            SoapObject tableObject = (SoapObject) webServiceResponse
                    .getProperty("Table");

            item.setItemCode(tableObject.getProperty("ItemCode").toString());
            item.setItemName(tableObject.getProperty("RecptDesc").toString());
            item.setPrice(tableObject.getProperty("UnitSellPrice").toString());
            item.setComboClass(tableObject.getProperty("ComboPack").toString());
            item.setItemType(tableObject.getProperty("WeightItem").toString());
        }
        return item;
    }
}
