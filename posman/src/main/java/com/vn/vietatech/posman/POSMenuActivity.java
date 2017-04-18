package com.vn.vietatech.posman;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.vn.vietatech.api.AbstractAPI;
import com.vn.vietatech.api.OrderAPI;
import com.vn.vietatech.api.PosMenuAPI;
import com.vn.vietatech.api.TableAPI;
import com.vn.vietatech.api.async.TableMoveAsync;
import com.vn.vietatech.api.async.TableSendOrderAsync;
import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.PosMenu;
import com.vn.vietatech.model.Remark;
import com.vn.vietatech.model.SubMenu;
import com.vn.vietatech.model.Table;
import com.vn.vietatech.posman.adapter.MainMenuAdapter;
import com.vn.vietatech.posman.adapter.RemarkAdapter;
import com.vn.vietatech.posman.adapter.SubMenuAdapter;
import com.vn.vietatech.posman.adapter.TableListAdapter;
import com.vn.vietatech.posman.dialog.DialogConfirm;
import com.vn.vietatech.posman.view.ItemRow;
import com.vn.vietatech.posman.view.TableOrder;
import com.vn.vietatech.utils.SettingUtil;
import com.vn.vietatech.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class POSMenuActivity extends ActionBarActivity {
    final Context context = this;
    MyApplication globalVariable;

    HorizontalScrollView horizontalView;
    LinearLayout ll_main, MTLayout, parentView;
    ScrollView vBody;
    TableOrder tblOrder;
    Button btnIPlus, btnIR, btnISub;
    Button btnIx;
    Button btnMT;
    Button btnSend;
    Button btnX;
    EditText txtPeople;
    EditText txtMoney;
    GridView gridMainMenu;
    MainMenuAdapter posMnuAdapter = null;
    GridView gridSubMenu;
    SubMenuAdapter subMnuAdapter = null;
    String tableNo;
    String tableStatus;
    String tableGroupNo = "";
    Spinner spinRemark;
    EditText txtRemark;
    String currentOrderNo = "";
    String currentExtNo = "0";
    String currentPosNo = "0";
    String currentPerNo = "0";
    String splited = "0";
    Spinner spinTableListMT;
    Remark selectedRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posmenu);

        initControl();

        // load table list to move
        new TableMoveAsync(context).execute();

        loadEvent();

        try {
            boolean result = new AbstractAPI(this).isKitFolderExist();
            if (!result) {
                Utils.showAlert(this, "Can not find kit folder");
            }
            loadItems();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(TableActivity.KEY_REFRESH_CODE,
                TableActivity.REFRESH_TABLE);
        intent.putExtra(TableActivity.KEY_SELECTED_TABLE, tableNo);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private void initControl() {
        globalVariable = (MyApplication) getApplicationContext();
        // get table infor
        tableNo = getIntent().getExtras().getString(
                TableActivity.KEY_SELECTED_TABLE);
        tableStatus = getIntent().getExtras().getString(
                TableActivity.KEY_STATUS);
        tableGroupNo = getIntent().getExtras().getString(
                TableActivity.KEY_TABLE_GROUP);

        horizontalView = (HorizontalScrollView) findViewById(R.id.horizontalView);
        parentView = (LinearLayout) findViewById(R.id.parentView);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        MTLayout = (LinearLayout) findViewById(R.id.MTLayout);
        vBody = (ScrollView) findViewById(R.id.vBody);
        btnIPlus = (Button) findViewById(R.id.btnIPlus);
        btnISub = (Button) findViewById(R.id.btnISub);
        btnIx = (Button) findViewById(R.id.btnIx);
        btnMT = (Button) findViewById(R.id.btnMT);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnIR = (Button) findViewById(R.id.btnIR);
        btnX = (Button) findViewById(R.id.btnX);
        btnX.setWidth(btnIPlus.getWidth());
        txtPeople = (EditText) findViewById(R.id.txtPeople);
        txtMoney = (EditText) findViewById(R.id.txtMoney);
        gridMainMenu = (GridView) findViewById(R.id.gridMainMenu);
        gridSubMenu = (GridView) findViewById(R.id.gridSubMenu);
        spinRemark = (Spinner) findViewById(R.id.spinRemark);
        txtRemark = (EditText) findViewById(R.id.txtRemark);
        spinTableListMT = (Spinner) findViewById(R.id.spinTableListMT);

        tblOrder = new TableOrder(this, ll_main);

        ll_main.addView(tblOrder.getTable().getHeader(), 0);
        vBody.addView(tblOrder.getTable().getBody());
    }

    private void loadEvent() {
        spinRemark.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedRemark = (Remark) parent.getItemAtPosition(position);
                String instruction = tblOrder.getRemark(selectedRemark);
                if (txtRemark.getText().toString().trim().isEmpty()
                        || (instruction != null && !instruction.trim().isEmpty())) {
                    txtRemark.setText(instruction);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedRemark = null;
            }
        });

        // IPlus click
        btnIPlus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                tblOrder.plus();
                txtMoney.setText(tblOrder.getAllTotal());
            }
        });

        // ISub click
        btnISub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                tblOrder.sub();
                txtMoney.setText(tblOrder.getAllTotal());
            }
        });

        // Ix click
        btnIx.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                tblOrder.removeRow();
                txtMoney.setText(tblOrder.getAllTotal());
            }
        });

        // insert remark
        btnIR.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                tblOrder.insertRemark(txtRemark.getText().toString());

                txtRemark.setFocusable(false);
                txtRemark.setFocusableInTouchMode(false);

                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtRemark.getWindowToken(), 0);
            }
        });

        btnMT.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                loadMoveTableForm();
            }

        });

        btnSend.setOnClickListener(new OnClickListener() {
            String sendNewOrder = "0";
            String reSendOrder = "0";

            @Override
            public void onClick(View v) {
                final String status = tblOrder.checkStatus(tableStatus);
                if (status == null || status.equals(TableOrder.STATUS_DATATABLE_SEND_ALL)) {
                    sendNewOrder = "1";
                    new TableSendOrderAsync(context).execute(sendNewOrder, reSendOrder);
                } else {
                    if (status.equals(TableOrder.STATUS_DATATABLE_NO_DATA)) {
                        Utils.showAlert(context, status);
                    } else if (status.equals(TableOrder.STATUS_DATATABLE_RESEND)) {
                        new DialogConfirm(context, status) {
                            public void run() {
                                reSendOrder = "1";
                                new TableSendOrderAsync(context).execute(sendNewOrder, reSendOrder);
                            }

                            public void no() {
                                new TableSendOrderAsync(context).execute(sendNewOrder, reSendOrder);
                            }
                        };
                    }
                }
            }

        });

        btnX.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new DialogConfirm(context, "are you sure?") {
                    public void run() {
                        backForm();
                    }
                };
            }

        });

        txtPeople.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (posMnuAdapter == null) {
                    posMnuAdapter = new MainMenuAdapter(context);
                    gridMainMenu.setAdapter(posMnuAdapter);
                }
            }
        });

        // txtRemark click
        txtRemark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        // txtPeople click
        txtPeople.setClickable(true);
        txtPeople.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                loadPickerDialog();
            }
        });

        // txtMoney click
        txtMoney.setClickable(true);
        txtMoney.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    int VAT = Integer.parseInt(SettingUtil.read(context).getVat());
                    int price = Utils.parseStringToInt(txtMoney.getText().toString());
                    int newPrice = price + (int) (price * (float) (VAT / 100.0));
                    String truePrice = String.valueOf(Utils.formatPrice(newPrice));
                    Utils.showAlert(context, "TOTAL BILL AFTER TAX " + truePrice + ".");
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });


    }

    public void loadSubMenu(PosMenu selectedPOSMenu) {
        subMnuAdapter = new SubMenuAdapter(context, selectedPOSMenu);
        gridSubMenu.setAdapter(subMnuAdapter);
    }

    public void addItem(SubMenu selectedSubMenu) {
        try {
            if (tblOrder.createNewRow(selectedSubMenu.getItem())) {
                vBody.post(new Runnable() {
                    @Override
                    public void run() {
                        vBody.fullScroll(View.FOCUS_DOWN);  // scroll to bottom
                    }
                });
            } else {
                vBody.post(new Runnable() {
                    ItemRow item = tblOrder.getCurrentRow();

                    @Override
                    public void run() {
                        vBody.smoothScrollTo(0, item.getTop());  // scroll to current row
                    }
                });
            }

            txtMoney.setText(tblOrder.getAllTotal());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * load Items
     * <p>
     * load all item rows
     *
     * @throws Exception
     */
    private void loadItems() throws Exception {
        if (tableStatus.equals(Table.ACTION_EDIT)) {
            this.addRowByOrder();
        } else {
            // get order number
            currentPosNo = SettingUtil.read(context).getPosId();
            int orderNo = new OrderAPI(context).getNewOrderNumberByPOS(currentPosNo);
            currentOrderNo = String.valueOf(orderNo);
            // open form set people
            txtPeople.performClick();
            updateTitle();

            btnMT.setEnabled(false);
            btnMT.setTextColor(Color.GRAY);
        }
    }

    /**
     * update Form title
     */
    private void updateTitle() {
        String table = tableNo.trim();
        if (tableGroupNo.trim().length() != 0) {
            table += "/" + tableGroupNo.trim();
        }
        setTitle(table + " (" + tblOrder.getAllRows().size() + ")-"
                + globalVariable.getCashier().getName());
    }

    /**
     * load number picker when set people
     */
    private void loadPickerDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.number_picker_dialog,
                null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        final NumberPicker np = (NumberPicker) promptView
                .findViewById(R.id.npPeople);
        np.setMaxValue(30);
        np.setMinValue(1);
        if (txtPeople.getText().toString().length() != 0) {
            np.setValue(Integer.parseInt(txtPeople.getText().toString()));
        }

        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle("Set people");
        alertDialogBuilder.setPositiveButton("Set",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        txtPeople.setText(String.valueOf(np.getValue()));
                        dialog.cancel();
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    //##### ZONE MOVE TABLE #####///
    private void restoreGrid() {
        gridMainMenu.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 300));
        gridSubMenu.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        MTLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0));
    }

    private void loadMoveTableForm() {
        MTLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 200));
        gridMainMenu.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        gridSubMenu.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

        Button btnOkMT = (Button) findViewById(R.id.btnOkMT);
        Button btnCancelMT = (Button) findViewById(R.id.btnCancelMT);
        btnOkMT.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Table table = (Table) spinTableListMT.getItemAtPosition(spinTableListMT.getSelectedItemPosition());
                String moveTable = table.getTableNo();

                if (moveTable.isEmpty() || moveTable.equals(tableNo)) {
                    Utils.showAlert(context, "This is current table");
                    return;
                }

                HashMap<String, String> result;
                try {
                    result = new TableAPI(context)
                            .getStatusOfMoveTable(moveTable);

                    moveTable = moveTable.trim();
                    if (result.containsKey("Status")
                            && result.containsKey("OpenBy")) {
                        String status = result.get("Status").toString();
                        String openBy = result.get("OpenBy").toString();
                        switch (status) {
                            case "O":
                                if (openBy.isEmpty()) {
                                    Utils.showAlert(context, "Table " + moveTable + " is having guests");
                                } else {
                                    Utils.showAlert(context, "Table " + moveTable + " is having guests and editting by cashier ("
                                            + openBy + ")");
                                }
                                break;
                            case "A":
                                String posNo = SettingUtil.read(context).getPosId();
                                if (new TableAPI(context).moveTable(tableNo, moveTable, tableGroupNo.trim(), posNo, currentOrderNo)) {
                                    String oldTableNo = tableNo;
                                    tableNo = moveTable;
                                    updateTitle();
                                    restoreGrid();

                                    Utils.showAlert(context, "Successfull move table " + oldTableNo.trim() + " to " + moveTable.trim());
                                } else {
                                    Utils.showAlert(context, "Table " + moveTable + " is having guests");
                                }
                                break;
                        }
                    } else {
                        Toast.makeText(context, "Can not move table", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelMT.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                restoreGrid();
            }
        });
    }

    public void loadLayoutMoveTable() {
        // layout move table
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(2, Color.BLACK);
        drawable.setColor(Color.parseColor("#dddddd"));
        MTLayout.setBackgroundDrawable(drawable);

        ArrayList<Table> tableList = globalVariable.getTables();
        if (tableList != null) {
            TableListAdapter tableListAdapter = new TableListAdapter(context,
                    android.R.layout.simple_spinner_item, tableList);

            spinTableListMT.setAdapter(tableListAdapter);
        }
    }
    //##### ZONE MOVE TABLE #####///

    public void backForm() {
        Intent intent = new Intent();
        intent.putExtra(TableActivity.KEY_REFRESH_CODE, 1);
        intent.putExtra(TableActivity.KEY_SELECTED_TABLE,
                tableNo);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void addRowByOrder() throws Exception {
        currentOrderNo = getIntent().getExtras().getString(TableActivity.KEY_ORD);
        currentExtNo = getIntent().getExtras().getString(TableActivity.KEY_EXT);
        currentPosNo = getIntent().getExtras().getString(TableActivity.KEY_POS);
        currentPerNo = getIntent().getExtras().getString(TableActivity.KEY_PER);

        ArrayList<Item> items = new OrderAPI(context).getEditOrderNumberByPOS(
                currentOrderNo, currentPosNo, currentExtNo);
        for (Item item : items) {
            tblOrder.createNewRow(item);
            // get splited params
            splited = item.getSplited();
        }
        vBody.fullScroll(ScrollView.FOCUS_DOWN);

        txtMoney.setText(tblOrder.getAllTotal());
        txtPeople.setText(currentPerNo);

        // update title
        updateTitle();
    }

    public void loadRemarks(Item item) {
        RemarkAdapter remarkAdapter = new RemarkAdapter(context,
                android.R.layout.simple_spinner_item, item);
        spinRemark.setAdapter(remarkAdapter);
        spinRemark.setBackgroundResource(R.drawable.spinner_background_remark_with_data);

        txtRemark.setText(item.getInstruction());
    }

    public boolean sendOrder(String sendNewOrder, String reSendOrder) throws Exception {
        try {
            String dataTableString = tblOrder.toString();
            String typeLoad = tableStatus.equals(Table.ACTION_EDIT) ? "EditOrder" : "NewOrder";
            String posNo = currentPosNo;
            String orderNo = currentOrderNo;
            String extNo = currentExtNo;
            String currTable = tableNo;
            String POSBizDate = Utils.getCurrentDate("yyyyMMdd");
            String currTableGroup = tableGroupNo;
            String noOfPerson = txtPeople.getText().toString();
            String salesCode = SettingUtil.read(context).getSalesCode();
            String cashierID = globalVariable.getCashier().getId();

            return new PosMenuAPI(context).sendOrder(dataTableString, sendNewOrder, reSendOrder
                    , typeLoad, posNo, orderNo, extNo, currTable, POSBizDate, currTableGroup
                    , splited, noOfPerson, salesCode, cashierID);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
