package OBUSDK.PerEncDec;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GicPart {
    private List<ZoneIds> detectionZoneIds;
    private List<ZoneIds> relevanceZoneIds;
    private List<ZoneIds> driverAwarenessZoneIds;
    private Integer direction;
    private Integer minimumAwarenessTime;
    private int iviType;
    private Integer iviPurpose;
    private Long laneStatus;
    private Integer driverCharacteristics;
    private Long layoutId;
    @SerializedName("preStoredlayoutId")
    private Long preStoredLayoutId;
    private List<RSCode> RSCodes;
    private List<Text> extraText;
    private VarLengthNumber itsRrid;
    private ArrayList<Long> applicableLanes;
    private List<CompleteVehicleCharacteristics> vehicleCharacteristics;

    public List<ZoneIds> getDetectionZoneIds() {
        return detectionZoneIds;
    }

    public void setDetectionZoneIds(List<ZoneIds> detectionZoneIds) {
        this.detectionZoneIds = detectionZoneIds;
    }

    public List<ZoneIds> getRelevanceZoneIds() {
        return relevanceZoneIds;
    }

    public void setRelevanceZoneIds(List<ZoneIds> relevanceZoneIds) {
        this.relevanceZoneIds = relevanceZoneIds;
    }

    public List<ZoneIds> getDriverAwarenessZoneIds() {
        return driverAwarenessZoneIds;
    }

    public void setDriverAwarenessZoneIds(List<ZoneIds> driverAwarenessZoneIds) {
        this.driverAwarenessZoneIds = driverAwarenessZoneIds;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getMinimumAwarenessTime() {
        return minimumAwarenessTime;
    }

    public void setMinimumAwarenessTime(Integer minimumAwarenessTime) {
        this.minimumAwarenessTime = minimumAwarenessTime;
    }

    public int getIviType() {
        return iviType;
    }

    public void setIviType(int iviType) {
        this.iviType = iviType;
    }

    public Integer getIviPurpose() {
        return iviPurpose;
    }

    public void setIviPurpose(Integer iviPurpose) {
        this.iviPurpose = iviPurpose;
    }

    public Long getLaneStatus() {
        return laneStatus;
    }

    public void setLaneStatus(Long laneStatus) {
        this.laneStatus = laneStatus;
    }

    public Integer getDriverCharacteristics() {
        return driverCharacteristics;
    }

    public void setDriverCharacteristics(Integer driverCharacteristics) {
        this.driverCharacteristics = driverCharacteristics;
    }

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    public Long getPreStoredLayoutId() {
        return preStoredLayoutId;
    }

    public void setPreStoredLayoutId(Long preStoredLayoutId) {
        this.preStoredLayoutId = preStoredLayoutId;
    }

    public List<RSCode> getRoadSignCodes() {
        return RSCodes;
    }

    public void setRoadSignCodes(List<RSCode> RSCodes) {
        this.RSCodes = RSCodes;
    }

    public List<Text> getExtraText() {
        return extraText;
    }

    public void setExtraText(List<Text> extraText) {
        this.extraText = extraText;
    }

    public VarLengthNumber getItsRrid() {
        return itsRrid;
    }

    public void setItsRrid(VarLengthNumber itsRrid) {
        this.itsRrid = itsRrid;
    }

    public ArrayList<Long> getApplicableLanes() {
        return applicableLanes;
    }

    public void setApplicableLanes(ArrayList<Long> applicableLanes) {
        this.applicableLanes = applicableLanes;
    }

    public List<CompleteVehicleCharacteristics> getVehicleCharacteristics() {
        return vehicleCharacteristics;
    }

    public void setVehicleCharacteristics(List<CompleteVehicleCharacteristics> vehicleCharacteristics) {
        this.vehicleCharacteristics = vehicleCharacteristics;
    }
}
