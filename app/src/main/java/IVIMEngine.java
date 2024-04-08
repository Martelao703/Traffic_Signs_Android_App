import java.util.ArrayList;
import java.util.List;

public interface Notify1 {
    void invoke(Object sender, IVIMDataEventArgs e);
}

public interface Notify2 {
    void invoke(Object sender, IVIMDataEventArgs e);
}

public interface Notify3 {
    void invoke(Object sender, IVIMDataEventArgs e);
}

public interface Notify4 {
    void invoke(Object sender, IVIMDataEventArgs e);
}

public interface Notify5 {
    void invoke(Object sender, IVIMDataEventArgs e);
}

public interface Notify6 {
    void invoke(Object sender, IVIMDataEventArgs e);
}

public enum iviUIStateEnum {
    iviStateCurrentlyInZone,
    iviStateCurrentlyNotInZone
}

public enum iviZoneEnum {
    iviZoneAwareness,
    iviZoneDetection,
    iviZoneRelevance
}

public class IVIMEngine {

}