package OBUSDK.JsonData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TcPart {
    @SerializedName("DetectionZone")
    private List<ZoneIds> detectionZone;
    @SerializedName("RelevanceZone")
    private List<ZoneIds> relevanceZone;
    @SerializedName("Direction")
    private Integer direction;
    private Integer minimumAwarenessZoneIds;
    private Long layoutId;
    @SerializedName("preStoredlayoutId")
    private Long preStoredLayoutId;
    @SerializedName("text")
    private List<ExtraText> extraText;
    private int data;

    public List<ZoneIds> getDetectionZone() {
        return detectionZone;
    }

    public void setDetectionZone(List<ZoneIds> detectionZone) {
        this.detectionZone = detectionZone;
    }

    public List<ZoneIds> getRelevanceZone() {
        return relevanceZone;
    }

    public void setRelevanceZone(List<ZoneIds> relevanceZone) {
        this.relevanceZone = relevanceZone;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getMinimumAwarenessZoneIds() {
        return minimumAwarenessZoneIds;
    }

    public void setMinimumAwarenessZoneIds(Integer minimumAwarenessZoneIds) {
        this.minimumAwarenessZoneIds = minimumAwarenessZoneIds;
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

    public List<ExtraText> getText() {
        return extraText;
    }

    public void setText(List<ExtraText> text) {
        this.extraText = text;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
