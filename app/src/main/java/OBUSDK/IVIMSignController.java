package OBUSDK;

import OBUSDK.JsonData.IVIM;

public class IVIMSignController implements IIVIMController {
    @Override
    public IVIMMemoryStructures readNewIVIMMessages(IVIM ivim) {
        throw new UnsupportedOperationException();
    }

    @Override
    public InternalIVIMMessage readNewIVIMMessage(IVIM ivim) {
        throw new UnsupportedOperationException();
    }

    /*
    @Override
    public void rebootController() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disconnectFromServer() {
        throw new UnsupportedOperationException();
    }*/
}
