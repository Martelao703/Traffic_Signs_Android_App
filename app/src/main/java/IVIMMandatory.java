import java.util.Date;

public class IVIMMandatory {
    private IVIMServiceProvider serviceProvider;
    private long iviIdentificationNumber;
    private Date timeStamp;
    private Date validFrom;
    private Date validTo;
    private int connectedIviStructures;
    private int iviStatus;

    public IVIMMandatory() {
        this.serviceProvider = new IVIMServiceProvider();
    }

    public long getIviIdentificationNumber() {
        return iviIdentificationNumber;
    }

    public void setIviIdentificationNumber(long iviIdentificationNumber) {
        this.iviIdentificationNumber = iviIdentificationNumber;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public IVIMServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(IVIMServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public int getConnectedIviStructures() {
        return connectedIviStructures;
    }

    public void setConnectedIviStructures(int connectedIviStructures) {
        this.connectedIviStructures = connectedIviStructures;
    }

    public int getIviStatus() {
        return iviStatus;
    }

    public void setIviStatus(int iviStatus) {
        this.iviStatus = iviStatus;
    }
}