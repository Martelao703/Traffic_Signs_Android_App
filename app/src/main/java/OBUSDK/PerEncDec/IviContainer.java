package OBUSDK.PerEncDec;

import java.util.List;

public class IviContainer {
    private Glc glc;
    private List<Giv> giv;
    private List<TcPart> tc;

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

    public List<TcPart> getTc() {
        return tc;
    }

    public void setTc(List<TcPart> tc) {
        this.tc = tc;
    }
}
