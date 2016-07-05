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

import com.ldzs.recyclerlibrary.PullToRefreshRecyclerView;
import com.ldzs.recyclerlibrary.anim.SlideInLeftAnimator;
import com.ldzs.recyclerlibrary.callback.OnItemClickListener;
import com.ldzs.recyclerlibrary.header.BaseRefresh;

import java.util.ArrayList;

import product.app.xmqq.com.myrecycleview.adapter.NormalAdapter;
import product.app.xmqq.com.myrecycleview.module.ItemData;
import product.app.xmqq.com.myrecycleview.myanimal.SlideInOutBottomItemAnimator;
import product.app.xmqq.com.myrecycleview.myanimal.SlideInOutRightItemAnimator;

/**
 * 1:示例添加头,添加信息,以及自定义的Adapter使用.
 * 2:示例底部加载情况,加载中/加载异常/加载完毕
 */
public class PullToRefreshActivity extends AppCompatActivity {
    private PullToRefreshRecyclerView mRecyclerView;
    private NormalAdapter mAdapter;
    ArrayList data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        setTitle("demo");

        mRecyclerView = (PullToRefreshRecyclerView) this.findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());//原有动画

        //从右到左动画
        mRecyclerView.setItemAnimator(new SlideInOutRightItemAnimator(mRecyclerView));
        //mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));
        //从上到下动画
        //mRecyclerView.setItemAnimator(new SlideInOutTopItemAnimator(mRecyclerView));
        mRecyclerView.setItemAnimator(new SlideInOutBottomItemAnimator(mRecyclerView));

        //渐变效果
        //mRecyclerView.setItemAnimator(new ScaleInOutItemAnimator(mRecyclerView));
//        mRecyclerView.setItemAnimator(new SlideScaleInOutRightItemAnimator(mRecyclerView));


        mRecyclerView.getItemAnimator().setAddDuration(300);
        mRecyclerView.getItemAnimator().setRemoveDuration(300);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//可以设置横向或者竖直方向
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(PullToRefreshActivity.this,"AAA",Toast.LENGTH_SHORT).show();
            }
        });

        View headerView = getHeaderView();
        headerView.setBackgroundColor(Color.BLUE);
        mRecyclerView.addHeaderView(headerView);


        //下拉刷新
        mRecyclerView.setOnPullUpToRefreshListener(new PullToRefreshRecyclerView.OnPullUpToRefreshListener() {
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

        //初始设置2个,考虑其不满一屏加载状态
        //mRecyclerView.setFooterComplete();
        mAdapter = new NormalAdapter(this, data);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 获得一个顶部控件
     */
    public View getHeaderView() {
        View header = LayoutInflater.from(this).inflate(
                R.layout.recyclerview_header, (ViewGroup) findViewById(android.R.id.content), false);
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
