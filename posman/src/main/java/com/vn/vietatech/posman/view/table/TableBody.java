package com.vn.vietatech.posman.view.table;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TableLayout;

import com.vn.vietatech.model.Item;
import com.vn.vietatech.posman.POSMenuActivity;
import com.vn.vietatech.posman.view.ItemRow;

import java.util.ArrayList;

public class TableBody extends TableLayout {
    private TableHeader tblHeader;
    private Context mContext;
    private ArrayList<ItemRow> listRow = new ArrayList<ItemRow>();
    private int currentIndex = -1;
    private ArrayList<Integer> listKey = new ArrayList<Integer>();

    public TableBody(Context context, TableHeader header) {
        super(context);

        tblHeader = header;
        mContext = context;
    }

    public ArrayList<ItemRow> getAllRows() {
        return listRow;
    }

    public void removeRow(ItemRow deletedRow) {
        for (int i = listRow.size() - 1; i >= 0; i--) {
            ItemRow row = listRow.get(i);
            if (row.getId() == deletedRow.getId()) {
                listRow.remove(row);
                break;
            }
        }

        setCurrentIndex(-1);
    }

    public ItemRow getCurrentRow() {
        if (currentIndex != -1) {
            return listRow.get(currentIndex);
        }
        return null;
    }

    public ItemRow getRowIndex(int index) {
        if (index >= 0) {
            return listRow.get(index);
        }
        return null;
    }

    public Object getColumnCurrentRow(String name) {
        int index = tblHeader.getColumnIndex(name);
        if (index != -1 && currentIndex != -1) {
            return listRow.get(currentIndex).getChildAt(index);
        }
        return null;
    }

    public Object getColumnByRow(int index, String name) {
        int indexCol = tblHeader.getColumnIndex(name);
        if (indexCol != -1 && index >= 0) {
            return listRow.get(index).getChildAt(indexCol);
        }
        return null;
    }

    public void clearBgRow() {
        for (ItemRow row : listRow) {
            row.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);

        ItemRow deletedRow = (ItemRow) view;
        this.removeRow(deletedRow);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void addRow(final Item item) {
        if (item != null) {
            // add new row
            final ItemRow newRow = new ItemRow(mContext);
            newRow.addAllColumns(item, tblHeader);
            newRow.setId(listKey.size());
            newRow.setLayoutParams(new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            newRow.setPadding(0, 5, 0, 5);
            newRow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // set other row : #ffffff
                    clearBgRow();

                    newRow.setBackgroundColor(Color.parseColor("#edf0fe"));

                    for (int i = listRow.size() - 1; i >= 0; i--) {
                        ItemRow row = listRow.get(i);
                        if (row.getId() == newRow.getId()) {
                            setCurrentIndex(i);

                            // load remarks
                            POSMenuActivity act = (POSMenuActivity) mContext;
                            act.loadRemarks(row.getCurrentItem());
                            break;
                        }
                    }

                }
            });

            this.addView(newRow);
            // add into array list
            listRow.add(newRow);
            listKey.add(listKey.size());
        }
    }

}
