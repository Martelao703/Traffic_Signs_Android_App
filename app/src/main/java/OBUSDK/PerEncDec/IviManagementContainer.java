package OBUSDK.PerEncDec;

public class IviManagementContainer {
    private Provider serviceProviderId;
    private long iviIdentificationNumber;
    private Long timeStamp;
    private Long validFrom;
    private Long validTo;
    private IviIdentificationNumbers connectedIviStructures;
    private int iviStatus;
    private ConnectedDenms connectedDenms;

    public Provider getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Provider serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public long getIviIdentificationNumber() {
        return iviIdentificationNumber;
    }

    public void setIviIdentificationNumber(long iviIdentificationNumber) {
        this.iviIdentificationNumber = iviIdentificationNumber;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Long validFrom) {
        this.validFrom = validFrom;
    }

    public Long getValidTo() {
        return validTo;
    }

    public void setValidTo(Long validTo) {
        this.validTo = validTo;
    }

    public IviIdentificationNumbers getConnectedIviStructures() {
        return connectedIviStructures;
    }

    public void setConnectedIviStructures(IviIdentificationNumbers connectedIviStructures) {
        this.connectedIviStructures = connectedIviStructures;
    }

    public int getIviStatus() {
        return iviStatus;
    }

    public void setIviStatus(int iviStatus) {
        this.iviStatus = iviStatus;
    }

    public ConnectedDenms getConnectedDenms() {
        return connectedDenms;
    }

    public void setConnectedDenms(ConnectedDenms connectedDenms) {
        this.connectedDenms = connectedDenms;
    }
}