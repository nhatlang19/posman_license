package com.vn.vietatech.api.async;


import android.content.Context;
import android.os.AsyncTask;

import com.vn.vietatech.api.AbstractAPI;

public class UpdateTimeAsync extends AsyncTask<String, String, String> {
    private Context mContext;

    public UpdateTimeAsync(Context context) {
        this.mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return new AbstractAPI(mContext).getTimeServer();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
//			try {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//				Calendar c = Calendar.getInstance();
//				c.setTime(sdf.parse(result));
//			    AlarmManager am = (AlarmManager) this.mContext.getSystemService(Context.ALARM_SERVICE);
//			    am.setTime(c.getTimeInMillis());
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
        }
        super.onPostExecute(result);
    }
}
