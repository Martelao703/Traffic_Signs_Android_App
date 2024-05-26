package OBUSDK.JsonController;

import java.util.ArrayList;
import java.util.List;

import OBUSDK.JsonData.GlcPart;
import OBUSDK.JsonData.IVIM;
import OBUSDK.JsonData.Header;
import OBUSDK.JsonData.IviContainer;
import OBUSDK.JsonData.IviManagementContainer;
import OBUSDK.JsonData.Optional;

public class DataExtracter {
    private IVIM rootIVI;

    public DataExtracter(IVIM rootIVI) {
        this.rootIVI = rootIVI;
    }

    public Header getHeader() {
        return this.rootIVI.getHeader();
    }

    public IviManagementContainer getMandatoryContainer() {
        return this.rootIVI.getIvi().getMandatory();
    }

    public List<IviContainer> getAllGivContainers() {
        List<IviContainer> givContainers = new ArrayList<>();
        for (Optional optional : this.rootIVI.getIvi().getOptional()) {
            if (optional.getIviContainer().getGiv() != null) {
                givContainers.add(optional.getIviContainer());
            }
        }
        return givContainers;
    }

    public IviContainer getGivContainer() {
        for (Optional optional : this.rootIVI.getIvi().getOptional()) {
            if (optional.getIviContainer().getGiv() != null) {
                return optional.getIviContainer();
            }
        }
        return null;
    }

    public IviContainer getGlcContainer() {
        for (Optional optional : this.rootIVI.getIvi().getOptional()) {
            if (optional.getIviContainer().getGlc() != null) {
                return optional.getIviContainer();
            }
        }
        return null;
    }

    public List<GlcPart> getZonesById(int zoneId) {
        List<GlcPart> glcPartes = new ArrayList<GlcPart>();
        IviContainer glcContainer = this.getGlcContainer();

        if (glcContainer != null) {
            for (GlcPart glcPart : glcContainer.getGlc().getParts().getGlcPart()) {
                if (glcPart.getZoneId() == zoneId) {
                    glcPartes.add(glcPart);
                }
            }
        }
        return glcPartes;
    }

    //public long getZoneLaneWidthById(long zoneId)
    //{
    //    IVI.IVIModule.IviContainer glcContainer = this.GetGlcContainer();
    //    if (glcContainer != null)
    //    {
    //        foreach (IVI.IVIModule.GlcPart glcPart in glcContainer.Glc.Parts)
    //        {
    //            if (glcPart.ZoneId == zoneId)
    //            {
    //                return glcPart;
    //            }
    //        }
    //    }
    //}

    public GlcPart getGlcPartByZoneId(long zoneId) {
        IviContainer glcContainer = this.getGlcContainer();
        if (glcContainer != null) {
            for (GlcPart glcPart : glcContainer.getGlc().getParts().getGlcPart()) {
                if (glcPart.getZoneId() == zoneId) {
                    return glcPart;
                }
            }
        }
        return null;
    }

    public boolean glcPartsHaveSegment() {
        IviContainer glcContainer = this.getGlcContainer();

        if (glcContainer != null) {
            for (GlcPart glcPart : glcContainer.getGlc().getParts().getGlcPart()) {
                if (glcPart.getZone().getSegment() != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
