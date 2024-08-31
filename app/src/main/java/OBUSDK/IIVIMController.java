package OBUSDK;

import OBUSDK.JsonData.IVIM;

public interface IIVIMController {
    IVIMMemoryStructures readNewIVIMMessages(IVIM ivim);

    InternalIVIMMessage readNewIVIMMessage(IVIM ivim);
}
