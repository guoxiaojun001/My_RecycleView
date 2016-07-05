package product.app.xmqq.com.myrecycleview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ldzs.recyclerlibrary.Mode;
import com.ldzs.recyclerlibrary.PullToRefreshRecyclerView;
import com.ldzs.recyclerlibrary.anim.SlideInLeftAnimator;
import com.ldzs.recyclerlibrary.callback.OnItemClickListener;

import java.util.ArrayList;

import product.app.xmqq.com.myrecycleview.adapter.NormalAdapter;
import product.app.xmqq.com.myrecycleview.adapteranimal.AnimationGridActivity;
import product.app.xmqq.com.myrecycleview.adapteranimal.AnimationListActivity;
import product.app.xmqq.com.myrecycleview.drag.CustomDragActivity;
import product.app.xmqq.com.myrecycleview.drag.DynamicDragActivity;
import product.app.xmqq.com.myrecycleview.drag.GridDragActivity;
import product.app.xmqq.com.myrecycleview.drag.LinearDragActivity;
import product.app.xmqq.com.myrecycleview.module.ItemData;

public class NormalRecycleListActivity extends AppCompatActivity {

    PullToRefreshRecyclerView recycler_view;
    ArrayList data = new ArrayList();
    NormalAdapter adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view = (PullToRefreshRecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setItemAnimator(new SlideInLeftAnimator());
        recycler_view.getItemAnimator().setAddDuration(300);
        recycler_view.getItemAnimator().setRemoveDuration(300);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(layoutManager);

        //设置 不能上拉和下拉加载 或者在xml中配置 auto:pv_refreshMode="none"
        recycler_view.setRefreshMode(Mode.DISABLED);

        String [] activity_list = getResources().getStringArray(R.array.activity_list);
        for(int a = 0; a < activity_list.length ;a++){
            data.add(new ItemData(R.mipmap.other_type,activity_list[a]));
        }

        adapter = new NormalAdapter(NormalRecycleListActivity.this,data);

        recycler_view.setAdapter(adapter);

        recycler_view.onRefreshComplete();


        recycler_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (position){
                    case 0:
                        intent = new Intent(
                                NormalRecycleListActivity.this,PullToRefreshActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(
                                NormalRecycleListActivity.this,GridPullToRefreshActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(
                                NormalRecycleListActivity.this,PullToRefreshExpandActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(
                                NormalRecycleListActivity.this,LinearDragActivity.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent = new Intent(
                                NormalRecycleListActivity.this,GridDragActivity.class);
                        startActivity(intent);
                        break;

                    case 5:
                        intent = new Intent(
                                NormalRecycleListActivity.this,DynamicDragActivity.class);
                        startActivity(intent);
                        break;

                    case 6:
                        intent = new Intent(
                                NormalRecycleListActivity.this,CustomDragActivity.class);
                        startActivity(intent);
                        break;

                    case 7:
                        intent = new Intent(
                                NormalRecycleListActivity.this,AnimationGridActivity.class);
                        startActivity(intent);
                        break;

                    case 8:
                        intent = new Intent(
                                NormalRecycleListActivity.this,AnimationListActivity.class);
                        startActivity(intent);
                        break;

                    case 9:
                        intent = new Intent(
                                NormalRecycleListActivity.this,RecycleGridViewActivity.class);
                        startActivity(intent);
                        break;

                    case 10:
                        intent = new Intent(
                                NormalRecycleListActivity.this,TreeAdapterViewActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });
    }
}
