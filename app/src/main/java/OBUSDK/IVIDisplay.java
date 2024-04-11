package OBUSDK;

public class IVIDisplay {
    private long layoutId;
    private ISO14823 iso14823;
    private IVIText IVIText;

    public IVIDisplay() {
        this.iso14823 = new ISO14823();
        this.IVIText = new IVIText();
    }

    public long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(long layoutId) {
        this.layoutId = layoutId;
    }

    public ISO14823 getIso14823() {
        return iso14823;
    }

    public void setIso14823(ISO14823 iso14823) {
        this.iso14823 = iso14823;
    }

    public IVIText getIviText() {
        return IVIText;
    }

    public void setIviText(IVIText IVIText) {
        this.IVIText = IVIText;
    }
}