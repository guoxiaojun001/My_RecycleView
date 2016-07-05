package product.app.xmqq.com.myrecycleview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ldzs.recyclerlibrary.adapter.tree.TreeAdapter;
import com.ldzs.recyclerlibrary.anim.FadeInDownAnimator;
import java.io.File;
import java.util.LinkedList;
import product.app.xmqq.com.myrecycleview.adapter.FileAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by cz on 16/1/22.
 * 一个无限展开的RecyclerView数据适配器演示
 */
public class TreeAdapterViewActivity extends AppCompatActivity {
    private boolean isDestroy;
    private RecyclerView mRecyclerView;
    private FileAdapter mAdapter;
    private ProgressDialog progressDialog;

    TreeAdapter.TreeNode<File> node;

    public Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {

            switch (msg.what){
                case 0:
                    progressDialog.dismiss();
                    mAdapter = new FileAdapter(TreeAdapterViewActivity.this, node);
                    mRecyclerView.setAdapter(mAdapter);
                    break;

                default:
                    super.dispatchMessage(msg);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);
        setTitle(getIntent().getStringExtra("title"));
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new FadeInDownAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(100);
        mRecyclerView.getItemAnimator().setRemoveDuration(100);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("scan_files");
        progressDialog.show();


        new Thread(){
            @Override
            public void run() {
                node = getAllFileNode();
                handler.sendEmptyMessage(0);

            }
        }.start();

    }

    /**
     * 获取第一级文件树.懒加载,待实现
     *
     * @return
     */
    private TreeAdapter.TreeNode<File> getFileNode() {
        File rootFile = Environment.getExternalStorageDirectory();
        TreeAdapter.TreeNode<File> rootNode = new TreeAdapter.TreeNode<>(rootFile);
        File[] filesArray = rootFile.listFiles();
        if (null != filesArray) {
            int length = filesArray.length;
            for (int i = length - 1; i >= 0; i--) {
                TreeAdapter.TreeNode<File> childNode = new TreeAdapter.TreeNode<>(rootNode, filesArray[i]);
                rootNode.child.add(childNode);
            }
        }
        return rootNode;
    }

    /**
     * 获取整棵文件树,较耗时
     *
     * @return
     */
    private TreeAdapter.TreeNode<File> getAllFileNode() {
        File rootFile = Environment.getExternalStorageDirectory();
        LinkedList<File> files = new LinkedList<>();
        LinkedList<TreeAdapter.TreeNode<File>> fileNodes = new LinkedList<>();
        TreeAdapter.TreeNode<File> rootNode = new TreeAdapter.TreeNode<>(rootFile);
        fileNodes.offerFirst(rootNode);
        files.offerFirst(rootFile);
        while (!files.isEmpty()) {
            File file = files.pollFirst();
            TreeAdapter.TreeNode<File> fileTreeNode = fileNodes.removeFirst();
            if (file.isDirectory()) {
                File[] filesArray = file.listFiles();
                if (null != filesArray) {
                    int length = filesArray.length;
                    for (int i = length - 1; i >= 0; i--) {
                        files.addFirst(filesArray[i]);
                        TreeAdapter.TreeNode<File> childNode = new TreeAdapter.TreeNode<>(fileTreeNode, filesArray[i]);
                        fileNodes.addFirst(childNode);
                        fileTreeNode.child.add(childNode);
                    }
                }
            }
        }
        return rootNode;
    }

    @Override
    protected void onDestroy() {
        isDestroy = true;
        if (null != progressDialog) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }
}
