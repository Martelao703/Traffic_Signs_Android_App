package OBUSDK.JsonData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IviManagementContainer {
    @SerializedName("serviceProviderId")
    private ServiceProvider serviceProvider;
    private long iviIdentificationNumber;
    private String timeStamp;
    private String validFrom;
    private String validTo;
    private Integer connectedIviStructures;
    private int iviStatus;

    public IviManagementContainer() {
        this.serviceProvider = new ServiceProvider();
    }

    public IviManagementContainer(ServiceProvider serviceProvider, long iviIdentificationNumber, String timeStamp, String validFrom, String validTo, Integer connectedIviStructures, int iviStatus) {
        this.serviceProvider = serviceProvider;
        this.iviIdentificationNumber = iviIdentificationNumber;
        this.timeStamp = timeStamp;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.connectedIviStructures = connectedIviStructures;
        this.iviStatus = iviStatus;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public long getIviIdentificationNumber() {
        return iviIdentificationNumber;
    }

    public void setIviIdentificationNumber(long iviIdentificationNumber) {
        this.iviIdentificationNumber = iviIdentificationNumber;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public Integer getConnectedIviStructures() {
        return connectedIviStructures;
    }

    public void setConnectedIviStructures(Integer connectedIviStructures) {
        this.connectedIviStructures = connectedIviStructures;
    }

    public int getIviStatus() {
        return iviStatus;
    }

    public void setIviStatus(int iviStatus) {
        this.iviStatus = iviStatus;
    }
}