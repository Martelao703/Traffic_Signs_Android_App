package OBUSDK.JsonData;

public class Header {
    private int protocolVersion;
    private int messageID;
    private long stationID;

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public long getStationID() {
        return stationID;
    }

    public void setStationID(long stationID) {
        this.stationID = stationID;
    }
}
