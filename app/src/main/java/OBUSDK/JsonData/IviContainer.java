package OBUSDK.JsonData;

import java.util.List;

public class IviContainer {
    private Glc glc;
    private List<Giv> giv;
    private List<TC> tc;

    public Glc getGlc() {
        return glc;
    }

    public void setGlc(Glc glc) {
        this.glc = glc;
    }

    public List<Giv> getGiv() {
        return giv;
    }

    public void setGiv(List<Giv> giv) {
        this.giv = giv;
    }

    public List<TC> getTc() {
        return tc;
    }

    public void setTc(List<TC> tc) {
        this.tc = tc;
    }
}
