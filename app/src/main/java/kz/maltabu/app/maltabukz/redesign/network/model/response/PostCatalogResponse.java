package kz.maltabu.app.maltabukz.redesign.network.model.response;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostCatalogResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;
    @SerializedName("promos")
    @Expose
    private List<Post> promos = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPromos() {
        return promos;
    }

    public void setPromos(List<Post> promos) {
        this.promos = promos;
    }

}