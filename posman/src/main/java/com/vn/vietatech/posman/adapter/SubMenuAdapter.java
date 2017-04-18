package com.vn.vietatech.posman.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.vn.vietatech.api.ItemAPI;
import com.vn.vietatech.api.PosMenuAPI;
import com.vn.vietatech.model.Item;
import com.vn.vietatech.model.PosMenu;
import com.vn.vietatech.model.SubMenu;
import com.vn.vietatech.posman.POSMenuActivity;
import com.vn.vietatech.utils.SettingUtil;
import com.vn.vietatech.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class SubMenuAdapter extends BaseAdapter {
    ArrayList<SubMenu> listSubMenu = new ArrayList<SubMenu>();
    ArrayList<Button> listButtonMenu = new ArrayList<Button>();
    private Context mContext;
    private PosMenu selectedPOSMenu;

    public SubMenuAdapter(Context c, PosMenu selectedPOSMenu) {
        this.mContext = c;
        this.selectedPOSMenu = selectedPOSMenu;

        String POSGroup;
        try {
            POSGroup = SettingUtil.read(mContext).getPosGroup();
            if (selectedPOSMenu.getSubMenu().size() == 0) {
                // load form server
                listSubMenu = new PosMenuAPI(mContext).getSubMenu(selectedPOSMenu.getDefaultValue(), POSGroup);
                selectedPOSMenu.setSubMenu(listSubMenu);
            } else {
                // load from local
                listSubMenu = selectedPOSMenu.getSubMenu();
            }
        } catch (IOException e) {
            Toast.makeText(this.mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this.mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public int getCount() {
        return listSubMenu.size();
    }

    public SubMenu getItem(int position) {
        return listSubMenu.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Button btn;
        final SubMenu subMenu = listSubMenu.get(position);

        btn = new Button(mContext);
        btn.setLayoutParams(new GridView.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(2, Color.BLACK);
        drawable.setColor(Utils.parseColor(selectedPOSMenu.getBtnColor()));
        btn.setBackgroundDrawable(drawable);
        btn.setTextColor(Utils.parseColor(selectedPOSMenu.getFontColor()));
        btn.setTextSize(12);
        btn.setText(subMenu.getDescription());
        btn.setLines(2);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Item item = new ItemAPI(mContext).getItemBySubMenuSelected(subMenu.getDefaultValue());
                    subMenu.setItem(item);
                } catch (Exception e) {
                    Toast.makeText(mContext, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                POSMenuActivity activity = (POSMenuActivity) mContext;
                activity.addItem(subMenu);

                clearAllButton();

                GradientDrawable drawable = (GradientDrawable) btn.getBackground();
                drawable.setStroke(10, Color.BLACK);
                btn.setBackgroundDrawable(drawable);
            }
        });
        listButtonMenu.add(btn);

        return btn;
    }

    private void clearAllButton() {
        for (Button button : listButtonMenu) {
            GradientDrawable drawable = (GradientDrawable) button.getBackground();
            drawable.setStroke(2, Color.BLACK);
            button.setBackgroundDrawable(drawable);
        }
    }

}