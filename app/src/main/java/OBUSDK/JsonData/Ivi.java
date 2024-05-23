package OBUSDK.JsonData;

import java.util.List;

public class Ivi {
    private IviManagementContainer mandatory;
    private List<Optional> optional;

    public IviManagementContainer getMandatory() {
        return mandatory;
    }

    public void setMandatory(IviManagementContainer mandatory) {
        this.mandatory = mandatory;
    }

    public List<Optional> getOptional() {
        return optional;
    }

    public void setOptional(List<Optional> optional) {
        this.optional = optional;
    }
}

