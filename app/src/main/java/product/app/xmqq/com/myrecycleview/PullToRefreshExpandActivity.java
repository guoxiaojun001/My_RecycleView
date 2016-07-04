package product.app.xmqq.com.myrecycleview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ldzs.recyclerlibrary.Mode;
import com.ldzs.recyclerlibrary.PullToRefreshExpandRecyclerView;
import com.ldzs.recyclerlibrary.PullToRefreshRecyclerView;
import com.ldzs.recyclerlibrary.adapter.expand.ExpandAdapter;
import com.ldzs.recyclerlibrary.anim.SlideInLeftAnimator;
import com.ldzs.recyclerlibrary.callback.OnItemClickListener;
import java.util.ArrayList;
import java.util.Random;

import product.app.xmqq.com.myrecycleview.adapter.ExpandRecycleAdapter;
import product.app.xmqq.com.myrecycleview.module.ItemData;

/**
 * 可展开的RecyclerView
 */
public class PullToRefreshExpandActivity extends AppCompatActivity {
    private PullToRefreshExpandRecyclerView mRecyclerView;
    private ExpandRecycleAdapter mAdapter;
    private static int COUNT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_recycler_view);
        setTitle(getIntent().getStringExtra("title"));
        mRecyclerView = (PullToRefreshExpandRecyclerView) this.findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(300);
        mRecyclerView.getItemAnimator().setRemoveDuration(300);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(getApplicationContext(),
                        "Click:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.addHeaderView(getHeaderView());


        mRecyclerView.setOnPullUpToRefreshListener(new
                      PullToRefreshRecyclerView.OnPullUpToRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addGroupItem(true);
                        mRecyclerView.onRefreshComplete();
                    }
                },1000);
            }
        });

        mRecyclerView.setOnPullDownToRefreshListener(
                new PullToRefreshRecyclerView.OnPullDownToRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addGroupItem(false);
                        mRecyclerView.onRefreshComplete();

                        //mRecyclerView.setFooterComplete();
                    }
                }, 1000);
            }
        });

        ArrayList<ExpandAdapter.Entry<String, ArrayList<ItemData>>> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ArrayList<ItemData> childItems = new ArrayList<ItemData>();
            for (int k = 0; k < 5; k++) {
                childItems.add(new ItemData(R.mipmap.other_type,"Group:" + i + " Child:" + k));
            }
            items.add(new ExpandAdapter.Entry("Group:" + i, childItems));
        }

        mAdapter = new ExpandRecycleAdapter(this,items);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshMode(Mode.BOTH);//设置下拉上拉 加载
    }

    /**
     * 获得一个顶部控件
     */
    public View getHeaderView() {
        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header,
                (ViewGroup) findViewById(android.R.id.content), false);
        TextView headerView = (TextView) header;
        headerView.setTextColor(Color.GREEN);
        headerView.setText("HeaderView:" + mRecyclerView.getHeaderViewCount());
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.addHeaderView(getHeaderView());
            }
        });

        return headerView;
    }


    private void addGroupItem(boolean top) {
        Random random = new Random();
        mAdapter.addGroupItems("Group:" + mAdapter.getGroupCount(),
                createItems(this, 2), top ? 0 : mAdapter.getGroupCount(), 0 == random.nextInt(2));
    }


    public static ArrayList<ItemData> createItems(Object object, int count) {
        COUNT = 0;
        String tag = object.toString();
        ArrayList<ItemData> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add(new ItemData(R.mipmap.other_type,"Item:" + COUNT++));
        }
        return items;
    }

    public static ArrayList<ExpandAdapter.Entry<String, ArrayList<ItemData>>>
    createExpandItems(Object object, int count, int childCount) {
        String tag = object.toString();
        ArrayList<ExpandAdapter.Entry<String, ArrayList<ItemData>>> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ArrayList<ItemData> childItems = new ArrayList<ItemData>();
            for (int k = 0; k < childCount; k++) {
                childItems.add(new ItemData(R.mipmap.other_type,"Group:" + i + " Child:" + k));
            }
            items.add(new ExpandAdapter.Entry("Group:" + i, childItems));
        }
        return items;
    }

}
