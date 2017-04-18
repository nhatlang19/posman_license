package com.vn.vietatech.api.async;

import android.content.Context;
import android.os.AsyncTask;

import com.vn.vietatech.posman.POSMenuActivity;
import com.vn.vietatech.posman.R;
import com.vn.vietatech.posman.dialog.TransparentProgressDialog;
import com.vn.vietatech.utils.Utils;

public class TableSendOrderAsync extends AsyncTask<String, String, String> {
    private Context mContext;
    private TransparentProgressDialog pd;

    public TableSendOrderAsync(Context context) {
        this.mContext = context;

        pd = new TransparentProgressDialog(mContext, R.drawable.spinner);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String sendNewOrder = params[0];
        String reSendOrder = params[1];

        boolean resultSend = false;
        try {
            POSMenuActivity act = (POSMenuActivity) mContext;
            resultSend = act.sendOrder(sendNewOrder, reSendOrder);
        } catch (Exception e) {
            return e.getMessage();
        }

        String result = "";
        if (!resultSend) {
            result = "Can not send order";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        pd.dismiss();
        if (result.length() == 0) {
            POSMenuActivity act = (POSMenuActivity) mContext;
            act.backForm();
        } else {
            Utils.showAlert(mContext, result);
        }
        super.onPostExecute(result);
    }
}
