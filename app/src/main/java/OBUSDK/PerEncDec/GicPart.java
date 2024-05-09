package OBUSDK.PerEncDec;

import java.util.ArrayList;
import java.util.List;

public class GicPart {
    private ArrayList<Long> detectionZoneIds;
    private VarLengthNumber itsRrid;
    private ArrayList<Long> relevanceZoneIds;
    private Integer direction;
    private ArrayList<Long> driverAwarenessZoneIds;
    private Integer minimumAwarenessTime;
    private ArrayList<Long> applicableLanes;
    private int iviType;
    private Integer iviPurpose;
    private Long laneStatus;
    private List<CompleteVehicleCharacteristics> vehicleCharacteristics;
    private Integer driverCharacteristics;
    private Long layoutId;
    private Long preStoredLayoutId;
    private ArrayList<RoadSignCode> roadSignCodes;
    private ArrayList<Text> extraText;

    public ArrayList<Long> getDetectionZoneIds() {
        return detectionZoneIds;
    }

    public void setDetectionZoneIds(ArrayList<Long> detectionZoneIds) {
        this.detectionZoneIds = detectionZoneIds;
    }

    public VarLengthNumber getItsRrid() {
        return itsRrid;
    }

    public void setItsRrid(VarLengthNumber itsRrid) {
        this.itsRrid = itsRrid;
    }

    public ArrayList<Long> getRelevanceZoneIds() {
        return relevanceZoneIds;
    }

    public void setRelevanceZoneIds(ArrayList<Long> relevanceZoneIds) {
        this.relevanceZoneIds = relevanceZoneIds;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public ArrayList<Long> getDriverAwarenessZoneIds() {
        return driverAwarenessZoneIds;
    }

    public void setDriverAwarenessZoneIds(ArrayList<Long> driverAwarenessZoneIds) {
        this.driverAwarenessZoneIds = driverAwarenessZoneIds;
    }

    public Integer getMinimumAwarenessTime() {
        return minimumAwarenessTime;
    }

    public void setMinimumAwarenessTime(Integer minimumAwarenessTime) {
        this.minimumAwarenessTime = minimumAwarenessTime;
    }

    public ArrayList<Long> getApplicableLanes() {
        return applicableLanes;
    }

    public void setApplicableLanes(ArrayList<Long> applicableLanes) {
        this.applicableLanes = applicableLanes;
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

    public List<CompleteVehicleCharacteristics> getVehicleCharacteristics() {
        return vehicleCharacteristics;
    }

    public void setVehicleCharacteristics(List<CompleteVehicleCharacteristics> vehicleCharacteristics) {
        this.vehicleCharacteristics = vehicleCharacteristics;
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

    public ArrayList<RoadSignCode> getRoadSignCodes() {
        return roadSignCodes;
    }

    public void setRoadSignCodes(ArrayList<RoadSignCode> roadSignCodes) {
        this.roadSignCodes = roadSignCodes;
    }

    public ArrayList<Text> getExtraText() {
        return extraText;
    }

    public void setExtraText(ArrayList<Text> extraText) {
        this.extraText = extraText;
    }
}
