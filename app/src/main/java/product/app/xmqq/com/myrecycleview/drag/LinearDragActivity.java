package product.app.xmqq.com.myrecycleview.drag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.ldzs.recyclerlibrary.DragRecyclerView;
import com.ldzs.recyclerlibrary.anim.SlideInLeftAnimator;
import com.ldzs.recyclerlibrary.callback.OnDragItemEnableListener;
import java.util.ArrayList;

import product.app.xmqq.com.myrecycleview.R;
import product.app.xmqq.com.myrecycleview.adapter.NormalAdapter;
import product.app.xmqq.com.myrecycleview.module.ItemData;

/**
 * Created by cz on 16/1/27.
 */
public class LinearDragActivity extends AppCompatActivity {
    private DragRecyclerView mRecyclerView;
    NormalAdapter mNormalAdapter;
    ArrayList data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        setTitle(getIntent().getStringExtra("title"));
        mRecyclerView = (DragRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(300);
        mRecyclerView.getItemAnimator().setRemoveDuration(300);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        for(int a = 0; a < 10 ;a++){
            data.add(new ItemData(R.mipmap.ic_zi_guan,"QQQ" + a));
        }
        mNormalAdapter = new NormalAdapter(LinearDragActivity.this,data);
        mRecyclerView.setAdapter(mNormalAdapter);

        mRecyclerView.setLongPressDrawEnable(true);//设置

        mRecyclerView.setOnDragItemEnableListener(new OnDragItemEnableListener() {
            @Override
            public boolean itemEnable(int position) {
                if( 0 == position){//设置第一个不可移动，固定第一个元素不可动
                    return false;
                }
                return true;
            }
        });
    }
}
