package product.app.xmqq.com.myrecycleview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldzs.recyclerlibrary.adapter.BaseViewHolder;
import com.ldzs.recyclerlibrary.adapter.expand.ExpandAdapter;
import java.util.ArrayList;

import product.app.xmqq.com.myrecycleview.R;
import product.app.xmqq.com.myrecycleview.module.ItemData;

public class ExpandRecycleAdapter extends ExpandAdapter<String, ItemData> {
    private static final String TAG = "ExpandRecycleAdapter";

    public ExpandRecycleAdapter(Context context,
                                ArrayList<Entry<String, ArrayList<ItemData>>> items) {
        super(context, items);
    }

    public ExpandRecycleAdapter(Context context, ArrayList<Entry<String,
            ArrayList<ItemData>>> items, boolean expand) {
        super(context, items, expand);
    }


    @Override
    public BaseViewHolder createGroupHolder(ViewGroup parent) {
        return new GroupHolder(createView(parent, R.layout.expand_group_item));
    }

    @Override
    public BaseViewHolder createChildHolder(ViewGroup parent) {
        return new ItemHolder(createView(parent, R.layout.expand_recycle_item));
    }

    @Override
    public void onBindGroupHolder(BaseViewHolder holder, int groupPosition) {
        GroupHolder groupHolder = (GroupHolder) holder;
        groupHolder.imageFlag.setSelected(getGroupExpand(groupPosition));//当前分组展开状态
        groupHolder.textView.setText(getGroup(groupPosition));
        groupHolder.count.setText("(" + getChildrenCount(groupPosition) + ")");//子孩子个数
    }

    @Override
    public BaseViewHolder onBindChildHolder(BaseViewHolder holder,
                               int groupPosition, int childPosition) {
        ItemHolder itemHolder = (ItemHolder) holder;
        ItemData item = getChild(groupPosition, childPosition);

        itemHolder.image.setImageResource(item.getResources());
        itemHolder.textView.setText(item.getContent());
        return null;
    }

    @Override
    protected void onGroupExpand(BaseViewHolder holder, boolean expand, int groupPosition) {
        super.onGroupExpand(holder, expand, groupPosition);
        GroupHolder groupHolder = (GroupHolder) holder;
        groupHolder.imageFlag.setSelected(expand);
    }

    public static class GroupHolder extends BaseViewHolder {
        public ImageView imageFlag;
        public TextView textView;
        public TextView count;

        public GroupHolder(View itemView) {
            super(itemView);
            imageFlag = (ImageView) itemView.findViewById(R.id.iv_group_flag);
            textView = (TextView) itemView.findViewById(R.id.tv_group_name);
            count = (TextView) itemView.findViewById(R.id.tv_group_count);
        }
    }

    public static class ItemHolder extends BaseViewHolder {
        public TextView textView;
        public ImageView image;

        public ItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
