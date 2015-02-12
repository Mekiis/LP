package banand.lionel.com.tpandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import banand.lionel.com.tpandroid.Items.MenuMediasItem;
import banand.lionel.com.tpandroid.R;

/**
 * Created by lionel on 30/09/14.
 */
public class MenuAdapter extends BaseAdapter {

    ArrayList<MenuMediasItem> mMenuMediasItems;
    Context mContext;

    public MenuAdapter(Context context, ArrayList<MenuMediasItem> menuMediasItems) {
        mContext = context;
        mMenuMediasItems = menuMediasItems;
    }


    @Override
    public int getCount() {
        return mMenuMediasItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        RelativeLayout layout;

        if(view == null) {
            layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.menu_item_row, null);
        } else {
            layout = (RelativeLayout)view;
        }

        TextView tvName = (TextView)layout.findViewById(R.id.tvName);
        ImageView ivIcon = (ImageView)layout.findViewById(R.id.ivIcon);

        tvName.setText(mMenuMediasItems.get(i).mName);
        ivIcon.setImageResource(mMenuMediasItems.get(i).mIcon);

        return layout;
    }
}
