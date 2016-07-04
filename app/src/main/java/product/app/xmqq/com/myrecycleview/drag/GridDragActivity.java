package product.app.xmqq.com.myrecycleview.drag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.ldzs.recyclerlibrary.DragRecyclerView;
import com.ldzs.recyclerlibrary.anim.SlideInLeftAnimator;
import com.ldzs.recyclerlibrary.callback.OnDragItemEnableListener;

import java.util.ArrayList;

import product.app.xmqq.com.myrecycleview.R;
import product.app.xmqq.com.myrecycleview.adapter.GridAdapter;
import product.app.xmqq.com.myrecycleview.module.ItemData;

/**
 * Created by cz on 16/1/25.
 */
public class GridDragActivity extends AppCompatActivity {
    private DragRecyclerView mRecyclerView;
    GridAdapter mNormalAdapter;
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
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);


        for(int a = 0; a < 10 ;a++){
            data.add(new ItemData(R.mipmap.other_type,"QQQ" + a));
        }
        mNormalAdapter = new GridAdapter(GridDragActivity.this,data);
        mRecyclerView.setAdapter(mNormalAdapter);

        mRecyclerView.setLongPressDrawEnable(true);
        mRecyclerView.setOnDragItemEnableListener(new OnDragItemEnableListener() {
            @Override
            public boolean itemEnable(int position) {
                return true;
            }
        });
    }

}
