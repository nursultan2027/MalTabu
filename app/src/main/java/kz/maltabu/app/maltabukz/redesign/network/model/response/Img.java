package kz.maltabu.app.maltabukz.redesign.network.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Img {

    @SerializedName("web")
    @Expose
    private List<Web> web = null;
    @SerializedName("rel")
    @Expose
    private List<Rel> rel = null;
    @SerializedName("dir")
    @Expose
    private String dir;

    public List<Web> getWeb() {
        return web;
    }

    public void setWeb(List<Web> web) {
        this.web = web;
    }

    public List<Rel> getRel() {
        return rel;
    }

    public void setRel(List<Rel> rel) {
        this.rel = rel;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

}
