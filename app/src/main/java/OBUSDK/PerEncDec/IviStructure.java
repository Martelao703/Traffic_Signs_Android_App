package OBUSDK.PerEncDec;

import java.util.List;

public class IviStructure {
    private IviManagementContainer mandatory;
    private List<IviContainer> optional;

    public IviManagementContainer getMandatory() {
        return mandatory;
    }

    public void setMandatory(IviManagementContainer mandatory) {
        this.mandatory = mandatory;
    }

    public List<IviContainer> getOptional() {
        return optional;
    }

    public void setOptional(List<IviContainer> optional) {
        this.optional = optional;
    }
}

