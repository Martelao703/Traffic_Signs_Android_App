package OBUSDK.PerEncDec;

public class IviStructure {
    private IviManagementContainer mandatory;
    private IviContainers optional;

    public IviManagementContainer getMandatory() {
        return mandatory;
    }

    public void setMandatory(IviManagementContainer mandatory) {
        this.mandatory = mandatory;
    }

    public IviContainers getOptional() {
        return optional;
    }

    public void setOptional(IviContainers optional) {
        this.optional = optional;
    }
}

