package OBUSDK.JsonController;

import OBUSDK.CoordinateConverter;
import OBUSDK.IControllerAdapter;
import OBUSDK.IVIZone;
import OBUSDK.InternalIVIMMessage;
import OBUSDK.InternalIVIMMessageBuilder;
import OBUSDK.PerEncDel.IVIM;
import OBUSDK.IVIMMemoryStructures;
import OBUSDK.PerEncDel.ItsPduHeader;
import OBUSDK.SafeByteConverter;

public class JsonAdapter implements IControllerAdapter {
    private IVIM rootIVI;

    public JsonAdapter(IVIM rootIVI) {
        this.rootIVI = rootIVI;
    }

    public IVIMMemoryStructures buildIVIMStructures() {
        IVIMMemoryStructures ivimMemoryStructures = new IVIMMemoryStructures();
        InternalIVIMMessage message = this.buildIVIMStructure();
        ivimMemoryStructures.getInternalIVIMessages().add(message);
        return ivimMemoryStructures;
    }

    public InternalIVIMMessage buildIVIMStructure() {
        boolean iVIMessageComplete = true;
        DataExtracter extracter = new DataExtracter(this.rootIVI);
        CoordinateConverter coordConverter = new CoordinateConverter();
        DataTransformer transformer = new DataTransformer(extracter);
        int serviceCategoryCode;
        int pictogramCategoryCode;
        int countryCategoryCode;
        int textLanguage;
        String textContent;
        IVIZone zone;
        long[] detectionZoneIDs;
        long[] awarenessZoneIDs;
        long[] relevanceZoneIDs;
        Integer laneWidth;
        SafeByteConverter byteConverter = new SafeByteConverter();

        InternalIVIMMessageBuilder builder = new SafeByteConverter();
        ItsPduHeader header = extracter.getItsPduHeader();
}
