package OBUSDK.JsonController;

import OBUSDK.IControllerAdapter;
import OBUSDK.PerEncDel.IVIM;
import OBUSDK.IVIMMemoryStructures;

public class JsonAdapter implements IControllerAdapter {
    private IVIM rootIVI;

    @Override
    public IVIMMemoryStructures buildIVIMStructures() {
        return null;
    }
}
