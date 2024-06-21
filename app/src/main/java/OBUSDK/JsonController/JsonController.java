package OBUSDK.JsonController;

import OBUSDK.IIVIMController;
import OBUSDK.IVIMMemoryStructures;
import OBUSDK.InternalIVIMMessage;
import OBUSDK.JsonData.IVIM;

public class JsonController implements IIVIMController {
    private JsonAdapter jsonAdapter;
    private boolean isJsonAdapterInitialized = false;
    private IVIMMemoryStructures ivimMemoryStructures;
    private InternalIVIMMessage internalIVIMMessage;

    public JsonController() {
        this.ivimMemoryStructures = new IVIMMemoryStructures();
    }

    public IVIMMemoryStructures readNewIVIMMessages(IVIM ivim) {
        if (isJsonAdapterInitialized) {
            return null;
        }
        isJsonAdapterInitialized = true;
        jsonAdapter = new JsonAdapter(ivim);
        internalIVIMMessage = jsonAdapter.buildIVIMStructure();
        ivimMemoryStructures.getInternalIVIMessages().add(internalIVIMMessage);

        return ivimMemoryStructures;
    }

    public JsonAdapter getJsonAdapter() {
        return jsonAdapter;
    }
}
