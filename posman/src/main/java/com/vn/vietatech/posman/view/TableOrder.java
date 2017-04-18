package com.vn.vietatech.posman.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.Remark;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.posman.view.table.DataTable;
import com.vn.vietatech.posman.view.table.MyTable;
import com.vn.vietatech.utils.Utils;

import java.util.ArrayList;

public class TableOrder extends TableLayout {
    public static String STATUS_DATATABLE_NO_DATA = "No Data";
    public static String STATUS_DATATABLE_SEND_ALL = "send all";
    public static String STATUS_DATATABLE_RESEND = "Do you want to resend order?";
    private String[] headers = new String[]{"Q", "P", "ItemName", "Price",
            "Total", "ItemType", "ItemCode", "Instruction", "ModifierInt",
            "MasterCode", "ComboClass", "Hidden"};
    private Integer[] headerWidth = new Integer[]{80, 60, 300, 200, 200, 200,
            200, 200, 200, 230, 230, 200};
    private Integer[] headerGravity = new Integer[]{Gravity.CENTER,
            Gravity.CENTER, Gravity.LEFT, Gravity.CENTER, Gravity.CENTER,
            Gravity.CENTER, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER,
            Gravity.CENTER, Gravity.CENTER, Gravity.CENTER};
    private MyTable table;

    public TableOrder(Context context, LinearLayout parent) {
        super(context);

        setLayoutParams(parent.getLayoutParams());
        table = new MyTable(context, initDataTable());
    }

    public MyTable getTable() {
        return table;
    }

    private ArrayList<DataTable> initDataTable() {
        ArrayList<DataTable> data = new ArrayList<DataTable>();
        int size = headers.length;

        for (int i = 0; i < size; i++) {
            String name = headers[i];
            int width = headerWidth[i];
            int gravity = headerGravity[i];

            data.add(new DataTable(name, width, gravity));
        }
        return data;
    }

    public ArrayList<ItemRow> getAllRows() {
        return table.getBody().getAllRows();
    }

    public ItemRow getCurrentRow() {
        return table.getBody().getCurrentRow();
    }

    public ItemRow getRowIndex(int index) {
        return table.getBody().getRowIndex(index);
    }

    public boolean createNewRow(Item item) {
        ArrayList<ItemRow> listRow = table.getBody().getAllRows();
        int index = -1;
        for (int i = listRow.size() - 1; i >= 0; i--) {
            ItemRow row = listRow.get(i);
            if (row.getCurrentItem().getItemCode().equals(item.getItemCode())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            // update quality
            TextView txtStatus = (TextView) getColumnByRow(index, "P");
            if (txtStatus != null
                    && !txtStatus.getText().equals(Item.STATUS_OLD)
                    && !txtStatus.getText().equals(Item.STATUS_CANCEL)) {

                table.getBody().clearBgRow();
                table.getBody().getAllRows().get(index)
                        .setBackgroundColor(Color.parseColor("#edf0fe"));
                table.getBody().setCurrentIndex(index);

                TextView txtQ = (TextView) getColumnByRow(index, "Q");
                int q = Integer.parseInt(txtQ.getText().toString()) + 1;
                txtQ.setText(String.valueOf(q));

                getCurrentRow().getCurrentItem().setQty(String.valueOf(q));
                return false;
            } else {
                table.getBody().addRow(item);
            }
        } else {
            table.getBody().addRow(item);
        }
        return true;
    }

    public void sub() {
        TextView txtStatus = (TextView) getColumnCurrentRow("P");
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL)) {
            TextView txtQty = (TextView) getColumnCurrentRow("Q");
            if (txtQty != null) {
                int qty = Integer.parseInt(txtQty.getText().toString());
                if (qty - 1 <= 0) {
                    ItemRow row = getCurrentRow();
                    if (row != null) {
                        table.getBody().removeView(row);
                    }
                } else {
                    String sQty = (qty - 1) + "";
                    txtQty.setText(sQty);
                    getCurrentRow().getCurrentItem().setQty(sQty);
                }
            }
        }
    }

    public void plus() {
        TextView txtStatus = (TextView) getColumnCurrentRow("P");
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL)) {
            TextView txtQty = (TextView) getColumnCurrentRow("Q");
            if (txtQty != null) {
                int qty = Integer.parseInt(txtQty.getText().toString());
                String sQty = (qty + 1) + "";
                txtQty.setText(sQty);

                getCurrentRow().getCurrentItem().setQty(sQty);
            }
        }
    }

    public void removeRow() {
        TextView txtStatus = (TextView) getColumnCurrentRow("P");
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL)) {
            ItemRow row = getCurrentRow();
            if (row != null) {
                table.getBody().removeView(row);
            }
        }
    }

    public String getRemark(Remark selectedRemark) {
        TextView txtStatus = (TextView) getColumnCurrentRow("P");
        String instruction = null;
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL)) {
            TextView txtInstruction = (TextView) getColumnCurrentRow("Instruction");
            if (txtInstruction != null) {
                if (!selectedRemark.getName().isEmpty()) {
                    instruction = txtInstruction.getText().toString();
                    if (instruction.length() != 0) {
                        instruction = instruction + ";"
                                + selectedRemark.getName();
                    } else {
                        instruction = selectedRemark.getName();
                    }
                } else {
                    instruction = "";
                }
            }
        }
        return instruction;
    }

    public void insertRemark(String instruction) {
        TextView txtStatus = (TextView) getColumnCurrentRow("P");
        if (txtStatus != null && !txtStatus.getText().equals(Item.STATUS_OLD)
                && !txtStatus.getText().equals(Item.STATUS_CANCEL)) {
            TextView txtInstruction = (TextView) getColumnCurrentRow("Instruction");
            txtInstruction.setText(instruction);
            getCurrentRow().getCurrentItem().setInstruction(instruction);
        }
    }

    public Object getColumnCurrentRow(String name) {
        return table.getBody().getColumnCurrentRow(name);
    }

    public Object getColumnByRow(int index, String name) {
        return table.getBody().getColumnByRow(index, name);
    }

    public String getAllTotal() {
        ArrayList<ItemRow> listRow = table.getBody().getAllRows();

        int total = 0;
        for (int i = listRow.size() - 1; i >= 0; i--) {
            TextView txtQ = (TextView) getColumnByRow(i, "Q");
            int q = Integer.parseInt(txtQ.getText().toString());

            TextView txtPrice = (TextView) getColumnByRow(i, "Price");
            TextView txtTotal = (TextView) getColumnByRow(i, "Total");
            int t = Integer.parseInt(txtPrice.getText().toString()) * q;
            txtTotal.setText(String.valueOf(t));

            getRowIndex(i).getCurrentItem().setTotal(String.valueOf(t));
            total += t;
        }
        return Utils.formatPrice(total);
    }

    public String checkStatus(String tableStatus) {
        String result = null;
        ArrayList<ItemRow> listRow = table.getBody().getAllRows();
        if (listRow.size() == 0) {
            result = STATUS_DATATABLE_NO_DATA;
        } else {
            if (tableStatus.equals(Table.ACTION_EDIT)) {
                boolean isNew = false;
                for (int i = listRow.size() - 1; i >= 0; i--) {
                    TextView txtStatus = (TextView) getColumnByRow(i, "P");
                    if (txtStatus != null
                            && !txtStatus.getText().equals(Item.STATUS_OLD)
                            && !txtStatus.getText().equals(Item.STATUS_CANCEL)) {
                        isNew = true;
                        break;
                    }
                }

                if (isNew) {
                    result = STATUS_DATATABLE_SEND_ALL;
                } else { // resend
                    result = STATUS_DATATABLE_RESEND;
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return table.toString();
    }
}
