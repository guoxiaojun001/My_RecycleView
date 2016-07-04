package product.app.xmqq.com.myrecycleview.adapter;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ldzs.recyclerlibrary.adapter.BaseViewAdapter;
import com.ldzs.recyclerlibrary.adapter.BaseViewHolder;

import java.util.Arrays;
import java.util.List;

import product.app.xmqq.com.myrecycleview.R;
import product.app.xmqq.com.myrecycleview.module.ItemData;

/**
 * Created by cz on 16/1/23.
 */
public class NormalAdapter<E> extends BaseViewAdapter<BaseViewHolder, E> {
    private int layout;

    public static NormalAdapter createFromResource(Context context, @ArrayRes int res) {
        return new NormalAdapter(context, context.getResources().getStringArray(res));
    }


    public NormalAdapter(Context context, E[] items) {
        this(context, R.layout.normal_text_item, Arrays.asList(items));
    }

    public NormalAdapter(Context context, @LayoutRes int layout, E[] items) {
        this(context, layout, Arrays.asList(items));
    }

    public NormalAdapter(Context context, List<E> items) {
        this(context, R.layout.normal_text_item, items);
    }

    public NormalAdapter(Context context, @LayoutRes int layout, List<E> items) {
        super(context, items);
        this.layout = layout;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(createView(parent, layout));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        RelativeLayout layout = (RelativeLayout) holder.itemView;

        ImageView imageView = (ImageView) layout.findViewById(R.id.image);
        TextView textView = (TextView) layout.findViewById(R.id.text);

        ItemData item = (ItemData) getItem(position);
        if (null != item) {
            imageView.setImageResource(item.getResources());
            textView.setText(item.getContent());
        }
    }
}
