package product.app.xmqq.com.myrecycleview.drag;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ldzs.recyclerlibrary.DragRecyclerView;
import com.ldzs.recyclerlibrary.anim.FadeInDownAnimator;
import com.ldzs.recyclerlibrary.callback.OnDragItemEnableListener;
import com.ldzs.recyclerlibrary.callback.OnItemClickListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import product.app.xmqq.com.myrecycleview.R;
import product.app.xmqq.com.myrecycleview.adapter.ChannelAdapter;
import product.app.xmqq.com.myrecycleview.module.Channel;
import product.app.xmqq.com.myrecycleview.util.IOUtils;
import product.app.xmqq.com.myrecycleview.util.JsonUtils;


/**
 * 今日头条效果
 */
public class CustomDragActivity extends Activity {
    private static final String TAG = "CustomDragActivity";
    private DragRecyclerView mRecyclerView;
    private ChannelAdapter mAdapter;
    private ArrayList<Channel> channels;
    private long st;
    private boolean mEdit;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costom_drag);
        setTitle(getIntent().getStringExtra("title"));
        channels = new ArrayList<>();
        mRecyclerView = (DragRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new FadeInDownAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(300);
        mRecyclerView.getItemAnimator().setRemoveDuration(300);
        mRecyclerView.getItemAnimator().setMoveDuration(300);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setRecycleChildrenOnDetach(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        String data =   getContentFromAssets(getResources(), "item.json");
        ArrayList<Channel> items = JsonUtils.getLists(data, Channel.class);
        for (int i = 0; i < items.size(); i++) {
            Channel channel = items.get(i);
            if (channel.use) {
                channels.add(channel);
            }
        }

        mAdapter = new ChannelAdapter(this, items);
        mRecyclerView.setAdapter(mAdapter);
        View view = View.inflate(this, R.layout.recyclerview_header3, null);
        final TextView editView = (TextView) view.findViewById(R.id.tv_edit);

        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setDragStatus(mEdit = !mEdit);
                editView.setText(mEdit ? R.string.complete : R.string.channel_sort_delete);
            }
        });


        mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                long l = System.currentTimeMillis();
                Log.e(TAG, "click" + position + " time:" + (l - st));
                st = l;
                int itemPosition = mRecyclerView.getItemPosition(position);//获得当前条目的位置
                int count = channels.size();
                Channel item = mAdapter.getItem(itemPosition);
                if (itemPosition < count) {
                    if (mEdit) {
                        item.use = false;
                        channels.remove(item);
                        mRecyclerView.setItemMove(position, count + 1);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Click:" + item.name, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    item.use = true;
                    channels.add(item);
                    mRecyclerView.setItemMove(position, count + 1);
                }
            }
        });


        mRecyclerView.addDynamicView(view, 0);
        mRecyclerView.addDynamicView(R.layout.recyclerview_header4, channels.size() + 1);

        mRecyclerView.setOnDragItemEnableListener(new OnDragItemEnableListener() {
            @Override
            public boolean itemEnable(int position) {
                return mEdit && mAdapter.getItem(position).use;
            }
        });


    }

    /**
     * 从asset内读取文件内容
     *
     * @param resource
     * @param fileName
     * @return
     */
    public String getContentFromAssets(Resources resource, String fileName) {
        String result = new String();
        BufferedReader bufReader = null;
        try {
            bufReader = new BufferedReader(new InputStreamReader(resource.getAssets().open(fileName)));
            String line;
            while ((line = bufReader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            result = new String();
        } finally {
            IOUtils.closeStream(bufReader);
        }
        return result;
    }


}
