package product.app.xmqq.com.myrecycleview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import product.app.xmqq.com.myrecycleview.adapter.HomeAdapter;

public class RecycleGridViewActivity extends AppCompatActivity {
	private RecyclerView mRecyclerView;
	private List<String> mDatas;
	private HomeAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_recyclerview);

		initData();

		mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
		mAdapter = new HomeAdapter(this, mDatas);

		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
				StaggeredGridLayoutManager.VERTICAL));
		mRecyclerView.setAdapter(mAdapter);

		//gridview需要设置两次分割线 坑啊
//		mRecyclerView.addItemDecoration(new RecycleViewDivider(
//				this, LinearLayout.VERTICAL,30, Color.RED));
//		mRecyclerView.addItemDecoration(new RecycleViewDivider(
//				this, LinearLayout.HORIZONTAL,30, Color.BLUE));


//		mRecyclerView.addItemDecoration(new DividerItemDecoration(
//				this, LinearLayout.VERTICAL));
//		mRecyclerView.addItemDecoration(new DividerItemDecoration(
//				this, LinearLayout.HORIZONTAL));

		mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));


		// 设置item动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());

		initEvent();

	}

	private void initEvent() {
		mAdapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(RecycleGridViewActivity.this, position + " click",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onItemLongClick(View view, int position) {
				Toast.makeText(RecycleGridViewActivity.this, position + " long click",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	protected void initData() {
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i < 'z'; i++) {
			mDatas.add("" + (char) i);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.id_action_add:
			mAdapter.addData(1);
			break;
		case R.id.id_action_delete:
			mAdapter.removeData(1);
			break;
		case R.id.id_action_gridview:
			mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
			break;
		case R.id.id_action_listview:
			mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
			break;
		case R.id.id_action_horizontalGridView:
			mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
					StaggeredGridLayoutManager.HORIZONTAL));
			break;

		case R.id.id_action_staggeredgridview:
			break;
		}
		return true;
	}

}
