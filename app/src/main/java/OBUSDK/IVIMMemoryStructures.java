package OBUSDK;

import java.util.ArrayList;
import java.util.List;

public class IVIMMemoryStructures {
    private List<InternalIVIMMessage> internalIVIMessages;

    public IVIMMemoryStructures() {
        this.internalIVIMessages = new ArrayList<>();
    }

    public List<InternalIVIMMessage> getInternalIVIMessages() {
        return internalIVIMessages;
    }
}
