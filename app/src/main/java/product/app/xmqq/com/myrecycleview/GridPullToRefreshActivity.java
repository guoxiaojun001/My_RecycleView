package product.app.xmqq.com.myrecycleview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ldzs.recyclerlibrary.PullToRefreshRecyclerView;
import java.util.ArrayList;

import product.app.xmqq.com.myrecycleview.adapter.GridAdapter;
import product.app.xmqq.com.myrecycleview.module.ItemData;
import product.app.xmqq.com.myrecycleview.myanimal.SlideInOutTopItemAnimator;

public class GridPullToRefreshActivity extends AppCompatActivity {
    private PullToRefreshRecyclerView mRecyclerView;
    private GridAdapter mAdapter;
    ArrayList data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_recycler_view);
        mRecyclerView = (PullToRefreshRecyclerView) this.findViewById(R.id.recycler_view);

        mRecyclerView.setItemAnimator(new SlideInOutTopItemAnimator(mRecyclerView));
        mRecyclerView.getItemAnimator().setAddDuration(300);
        mRecyclerView.getItemAnimator().setRemoveDuration(300);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);

        View headerView = getHeaderView();
        headerView.setBackgroundColor(Color.BLUE);
        mRecyclerView.addHeaderView(headerView);


        //下拉刷新
        mRecyclerView.setOnPullUpToRefreshListener(
                new PullToRefreshRecyclerView.OnPullUpToRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addItem(new ItemData(R.mipmap.other_type,"PullUp"),0);
                        mRecyclerView.onRefreshComplete();//顶部刷新完毕
                    }
                },1000);
            }
        });

        //上拉加载
        mRecyclerView.setOnPullDownToRefreshListener(new PullToRefreshRecyclerView.OnPullDownToRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //默认在最底部添加
                        mAdapter.addItem(new ItemData(R.mipmap.other_type,"PullDown"));
                        mRecyclerView.onRefreshComplete();//顶部刷新完毕

                        // 如果加载完所有数据，再没有更多数据是，调用setFooterComplete
                        if(mAdapter.getItemCount() >= 15){
                            mRecyclerView.setFooterComplete();
                        }
                    }
                },1000);

            }
        });

        for(int a = 0; a < 5 ;a++){
            data.add(new ItemData(R.mipmap.ic_launcher,"QQQ" + a));
        }
        mAdapter = new GridAdapter(this,R.layout.grid_item,data);//外部设置布局文件

        mRecyclerView.setAdapter(mAdapter);
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

}
