package product.app.xmqq.com.myrecycleview.drag;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ldzs.recyclerlibrary.DragRecyclerView;
import com.ldzs.recyclerlibrary.anim.SlideInLeftAnimator;
import com.ldzs.recyclerlibrary.callback.OnDragItemEnableListener;
import com.ldzs.recyclerlibrary.callback.OnItemClickListener;

import java.util.ArrayList;

import product.app.xmqq.com.myrecycleview.R;
import product.app.xmqq.com.myrecycleview.adapter.GridAdapter;
import product.app.xmqq.com.myrecycleview.module.ItemData;

/**
 * 有点今日头条的效果
 */
public class DynamicDragActivity extends AppCompatActivity {
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
        mNormalAdapter = new GridAdapter(DynamicDragActivity.this,data);
        mRecyclerView.setAdapter(mNormalAdapter);

        mRecyclerView.addDynamicView(getHeaderItemView(this), 2);
        mRecyclerView.addDynamicView(getHeaderItemView(this), 10);

        mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(DynamicDragActivity.this,
                        "QQ" + position,Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setLongPressDrawEnable(true);
        mRecyclerView.setOnDragItemEnableListener(new OnDragItemEnableListener() {
            @Override
            public boolean itemEnable(int position) {
                return true;
            }
        });
    }


    public static View getHeaderItemView(Activity activity) {
        View header = LayoutInflater.from(activity).inflate(R.layout.recyclerview_header,
                (ViewGroup) activity.findViewById(android.R.id.content), false);
        TextView headerView = (TextView) header;
        header.setBackgroundColor(Color.BLUE);
        headerView.setTextColor(Color.BLACK);
        return headerView;
    }

}
