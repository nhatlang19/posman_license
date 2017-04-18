package com.vn.vietatech.posman.view.table;

import android.content.Context;
import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.vn.vietatech.posman.R;

import java.util.ArrayList;

public class TableHeader extends TableLayout {

    protected ArrayList<DataTable> dataTable;
    private Context mContext;
    private ArrayList<String> arrHeader = new ArrayList<String>();

    public TableHeader(Context context, ArrayList<DataTable> dataTable) {
        super(context);

        this.mContext = context;
        this.dataTable = dataTable;

        addData();
    }

    public void addData() {

        TableRow tblHeader = new TableRow(mContext);
        tblHeader.setLayoutParams(new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        for (DataTable header : this.dataTable) {
            TextView textView = new TextView(mContext);
            textView.setWidth(header.getColWidth());
            textView.setBackgroundResource(R.drawable.order_table_header);
            textView.setText(header.getColName());
            textView.setGravity(header.getColGravity());
            textView.setTextColor(Color.BLACK);
            tblHeader.addView(textView);

            arrHeader.add(header.getColName());
        }
        this.addView(tblHeader);
    }

    public ArrayList<DataTable> getDataTable() {
        return this.dataTable;
    }

    public ArrayList<String> getListHeader() {
        return arrHeader;
    }

    public int getColumnIndex(String name) {
        return arrHeader.indexOf(name);
    }

    public DataTable getColHeader(String name) {
        int indexCol = arrHeader.indexOf(name);
        if (indexCol != -1) {
            return this.dataTable.get(indexCol);
        }
        return null;
    }
}
