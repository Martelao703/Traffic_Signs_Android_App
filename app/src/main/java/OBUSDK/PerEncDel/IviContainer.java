package OBUSDK.PerEncDel;

public class IviContainer {

    public enum Id {
        Unselected,
        GlcChosen,
        GivChosen
    }

    private Id id;
    private Object contained;

    public Id getSelected() {
        return id;
    }

    public GeographicLocationContainer getGlc() {
        return (id == Id.GlcChosen) ? (GeographicLocationContainer) contained : null;
    }

    public void setGlc(GeographicLocationContainer value) {
        contained = value;
        id = Id.GlcChosen;
    }

    public GeneralIviContainer getGiv() {
        return (id == Id.GivChosen) ? (GeneralIviContainer) contained : null;
    }

    public void setGiv(GeneralIviContainer value) {
        contained = value;
        id = Id.GivChosen;
    }
}
