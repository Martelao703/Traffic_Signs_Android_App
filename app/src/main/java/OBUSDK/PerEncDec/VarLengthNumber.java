package OBUSDK.PerEncDec;

public class VarLengthNumber {
    public enum Id {
        Unselected,
        ContentChosen,
        ExtensionChosen
    }

    private Id id;
    private Object contained;

    public Id getSelected() {
        return id;
    }
}
