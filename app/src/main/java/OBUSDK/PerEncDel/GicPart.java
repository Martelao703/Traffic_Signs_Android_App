package OBUSDK.PerEncDel;

public class GicPart {
    private ZoneIds detectionZoneIds;
    private VarLengthNumber itsRrid;
    private ZoneIds relevanceZoneIds;
    private Integer direction;
    private ZoneIds driverAwarenessZoneIds;
    private Integer minimumAwarenessTime;
    private LanePositions applicableLanes;
    private int iviType;
    private Integer iviPurpose;
    private Long laneStatus;
    private VehicleCharacteristicsList vehicleCharacteristics;
    private Integer driverCharacteristics;
    private Long layoutId;
    private Long preStoredLayoutId;
    private RoadSignCodes roadSignCodes;
    private ConstraintTextLines1 extraText;

    public ZoneIds getDetectionZoneIds() {
        return detectionZoneIds;
    }

    public void setDetectionZoneIds(ZoneIds detectionZoneIds) {
        this.detectionZoneIds = detectionZoneIds;
    }

    public VarLengthNumber getItsRrid() {
        return itsRrid;
    }

    public void setItsRrid(VarLengthNumber itsRrid) {
        this.itsRrid = itsRrid;
    }

    public ZoneIds getRelevanceZoneIds() {
        return relevanceZoneIds;
    }

    public void setRelevanceZoneIds(ZoneIds relevanceZoneIds) {
        this.relevanceZoneIds = relevanceZoneIds;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public ZoneIds getDriverAwarenessZoneIds() {
        return driverAwarenessZoneIds;
    }

    public void setDriverAwarenessZoneIds(ZoneIds driverAwarenessZoneIds) {
        this.driverAwarenessZoneIds = driverAwarenessZoneIds;
    }

    public Integer getMinimumAwarenessTime() {
        return minimumAwarenessTime;
    }

    public void setMinimumAwarenessTime(Integer minimumAwarenessTime) {
        this.minimumAwarenessTime = minimumAwarenessTime;
    }

    public LanePositions getApplicableLanes() {
        return applicableLanes;
    }

    public void setApplicableLanes(LanePositions applicableLanes) {
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

    public VehicleCharacteristicsList getVehicleCharacteristics() {
        return vehicleCharacteristics;
    }

    public void setVehicleCharacteristics(VehicleCharacteristicsList vehicleCharacteristics) {
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

    public RoadSignCodes getRoadSignCodes() {
        return roadSignCodes;
    }

    public void setRoadSignCodes(RoadSignCodes roadSignCodes) {
        this.roadSignCodes = roadSignCodes;
    }

    public ConstraintTextLines1 getExtraText() {
        return extraText;
    }

    public void setExtraText(ConstraintTextLines1 extraText) {
        this.extraText = extraText;
    }
}
