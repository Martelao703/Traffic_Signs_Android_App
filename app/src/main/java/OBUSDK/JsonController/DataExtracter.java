package OBUSDK.JsonController;

import java.util.ArrayList;
import java.util.List;

import OBUSDK.JsonData.Giv;
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

    public Header getItsPduHeader() {
        return this.rootIVI.getHeader();
    }

    public IviManagementContainer getMandatoryContainer() {
        return this.rootIVI.getIvi().getMandatory();
    }

    public List<Giv> getAllGivContainers() {
        List<Giv> givContainers = new ArrayList<>();
        for (Optional optional : this.rootIVI.getIvi().getOptional()) {
            if (optional.getIviContainer().getGiv() != null) {
                givContainers.add(optional.getIviContainer().getGiv().get(0));
            }
        }
        return givContainers;
    }

    public IviContainer GetGivContainer() {
        for (Optional optional : this.rootIVI.getIvi().getOptional()) {
            /*if (container.getSelected() == IviContainer.Id.GivChosen) {
                return container;
            }*/
        }
        return null;
    }

    public IviContainer GetGlcContainer() {
        for (Optional optional : this.rootIVI.getIvi().getOptional()) {
            /*if (optional.getIviContainer().getSelected() == IviContainer.Id.GlcChosen) {
                return container;
            }*/
        }
        return null;
    }

    public List<GlcPart> GetZonesById(int zoneId) {
        List<GlcPart> glcPartes = new ArrayList<>();
        IviContainer glcContainer = this.GetGlcContainer();

        if (glcContainer != null) {
            for (GlcPart glcPart : glcContainer.getGlc().getParts().getGlcPart()) {
                if (glcPart.getZoneId() == zoneId) {
                    glcPartes.add(glcPart);
                }
            }
        }
        return glcPartes;
    }

    /*
    public long GetZoneLaneWidthById(long zoneId)
    {
        IVI.IVIModule.IviContainer glcContainer = this.GetGlcContainer();
        if (glcContainer != null)
        {
            foreach (IVI.IVIModule.GlcPart glcPart in glcContainer.Glc.Parts)
            {
                if (glcPart.ZoneId == zoneId)
                {
                    return glcPart;
                }
            }
        }
    }
    */

    public GlcPart GetZoneById(long zoneId) {
        IviContainer glcContainer = this.GetGlcContainer();
        if (glcContainer != null) {
            for (GlcPart glcPart : glcContainer.getGlc().getParts().getGlcPart()) {
                if (glcPart.getZoneId() == zoneId) {
                    return glcPart;
                }
            }
        }
        return null;
    }

    public boolean GlcPartsHaveSegment() {
        List<GlcPart> glcParts = new ArrayList<GlcPart>();
        IviContainer glcContainer = this.GetGlcContainer();

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
