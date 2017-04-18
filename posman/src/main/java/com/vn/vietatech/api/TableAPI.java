package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.model.Section;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.posman.MyApplication;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TableAPI extends AbstractAPI {
    public TableAPI(Context context) throws Exception {
        super(context);
    }

    @Override
    protected String doInBackground(String... params) {

        final MyApplication globalVariable = (MyApplication) mContext;
        ArrayList<Table> tables = globalVariable.getTables();
        if (tables == null) {
            try {
                tables = getTableList();
                globalVariable.setTables(tables);
            } catch (Exception e) {
            }
        }

        return super.doInBackground(params);
    }

    public ArrayList<Table> getTableList() throws Exception {
        setMethod(METHOD_GET_TABLELIST);

        ArrayList<Table> tables = new ArrayList<Table>();

        SoapObject response = (SoapObject) this.callService();
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");

        if (soapObject.getPropertyCount() != 0) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                Table table = new Table();
                table.setTableNo(tableObject.getProperty("TableNo").toString());

                tables.add(table);
            }
        }
        return tables;
    }

    public ArrayList<Table> getTableBySection(Section section) throws Exception {
        setMethod(METHOD_GET_TABLE_BY_SECTION);

        ArrayList<Table> tables = new ArrayList<Table>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("section", section.getId());

        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");

        if (soapObject.getPropertyCount() != 0) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject tableObject = (SoapObject) webServiceResponse
                        .getProperty(i);

                Table table = new Table();
                table.setTableNo(tableObject.getProperty("TableNo").toString());
                table.setStatus(tableObject.getProperty("Status").toString());
                table.setOpenBy(tableObject.getProperty("OpenBy").toString());
                table.setDescription2(tableObject.getProperty("Description2")
                        .toString());
                table.setSection(section);
                tables.add(table);
            }
        }
        return tables;
    }

    public boolean updateTableStatus(String status, String cashierId,
                                     String currentTable) throws Exception {
        setMethod(METHOD_UPDATE_TABLE_STATUS);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tableStatus", status);
        params.put("cashierID", cashierId);
        params.put("currentTable", currentTable);

        return Boolean.parseBoolean(callService(params).toString());
    }

    public HashMap<String, String> getStatusOfMoveTable(String moveTable)
            throws Exception {
        setMethod(METHOD_GET_STATUS_MOVE_TABLE);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("moveTable", moveTable);
        SoapObject response = (SoapObject) this.callService(params);
        SoapObject soapObject = (SoapObject) response.getProperty("diffgram");
        HashMap<String, String> result = new HashMap<String, String>();
        if (soapObject.getPropertyCount() != 0) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");

            SoapObject tableObject = (SoapObject) webServiceResponse
                    .getProperty("Table");

            result.put("Status", tableObject.getProperty("Status").toString()
                    .trim());
            result.put("OpenBy", tableObject.getProperty("OpenBy").toString()
                    .trim());
        }

        return result;
    }

    public boolean moveTable(String currTable, String moveTable,
                             String currTableGroup, String posNo, String orderNo) throws Exception {
        setMethod(METHOD_MOVE_TABLE);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("currTable", currTable);
        params.put("moveTable", moveTable);
        params.put("currTableGroup", currTableGroup);
        params.put("posNo", posNo);
        params.put("orderNo", orderNo);

        return Boolean.parseBoolean(callService(params).toString());
    }

    public boolean groupTable(String currTable, String currTableGroup) throws Exception {
        setMethod(METHOD_GROUP_TABLE);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("currTable", currTable);
        params.put("currTableGroup", currTableGroup);

        return Boolean.parseBoolean(callService(params).toString());
    }
}
