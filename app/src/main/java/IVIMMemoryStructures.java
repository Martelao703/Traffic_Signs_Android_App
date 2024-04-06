import java.util.ArrayList;
import java.util.List;

public class IVIMMemoryStructures {
    private List<InternalJsonMessage> internalIVIMessages;

    public IVIMMemoryStructures() {
        this.internalIVIMessages = new ArrayList<>();
    }

    public List<InternalJsonMessage> getInternalIVIMessages() {
        return internalIVIMessages;
    }
}
