package product.app.xmqq.com.myrecycleview.module;

/**
 * Created by gxj on 2016/7/1.
 */
public class ItemData {

    private int resources;
    private String content;

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public ItemData(){

    }

    public ItemData(int resources,String content){
        this.resources = resources;
        this.content = content;
    }
}
