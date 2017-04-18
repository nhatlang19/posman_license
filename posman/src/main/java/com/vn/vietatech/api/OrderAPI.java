package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.Order;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderAPI extends AbstractAPI {

    public OrderAPI(Context context) throws Exception {
        super(context);
    }

    public int getNewOrderNumberByPOS(String POSId)
            throws NumberFormatException, Exception {
        setMethod(METHOD_GET_NEW_ORDER_BY_POS);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("POSId", POSId);

        return Integer.parseInt(callService(params).toString());
    }

    public ArrayList<Item> getEditOrderNumberByPOS(String orderNo, String posNo,
                                                   String extNo) throws Exception {
        setMethod(METHOD_GET_EDIT_ORDER_BY_POS);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("orderNo", orderNo);
        params.put("posNo", posNo);
        params.put("extNo", extNo);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");
        System.out.println(soapObject.toString());

        ArrayList<Item> items = new ArrayList<Item>();
        if (soapObject.getPropertyCount() != 0) {

            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                Item item = new Item();
                item.setId(tableObject.getProperty("OrderNo").toString());
                item.setQty(tableObject.getProperty("Qty").toString());
                item.setSplited(tableObject.getProperty("Splited").toString());
                item.setPrintStatus(tableObject.getProperty("Status").toString());
                item.setItemName(tableObject.getProperty("RecptDesc").toString());
                item.setPrice(tableObject.getProperty("OrgPrice").toString());
                item.setItemType(tableObject.getProperty("ItemType").toString());
                item.setItemCode(tableObject.getProperty("ItemCode").toString());
                item.setModifier(tableObject.getProperty("Modifier").toString());
                item.setMasterCode(tableObject.getProperty("MasterCode").toString());
                item.setComboClass(tableObject.getProperty("ComboClass").toString());
                item.setHidden(tableObject.getProperty("Hidden").toString());
                if (tableObject.hasProperty("Instruction")) {
                    item.setInstruction(tableObject.getProperty("Instruction").toString());
                }

                items.add(item);
            }
        }
        return items;
    }

    public ArrayList<Order> getOrderEditType(String POSBizDate,
                                             String currentTable) throws Exception {
        setMethod(METHOD_GET_ORDER_EDIT_TYPE);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("POSBizDate", POSBizDate);
        params.put("currentTable", currentTable);

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");

        ArrayList<Order> orders = new ArrayList<Order>();
        if (soapObject.getPropertyCount() != 0) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");
            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                Order order = new Order();
                order.setOrdExt(tableObject.getProperty("OrdExt").toString());
                order.setPosPer(tableObject.getProperty("PosPer").toString());

                orders.add(order);
            }

        }
        return orders;
    }
}
