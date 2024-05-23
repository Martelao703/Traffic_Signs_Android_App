package OBUSDK.JsonData;

public class ViennaConventionCode {
    private long roadSignClass;
    private long roadSignCode;
    private long vcOption;
    private Long value;

    public long getRoadSignClass() {
        return roadSignClass;
    }

    public void setRoadSignClass(long roadSignClass) {
        this.roadSignClass = roadSignClass;
    }

    public long getRoadSignCode() {
        return roadSignCode;
    }

    public void setRoadSignCode(long roadSignCode) {
        this.roadSignCode = roadSignCode;
    }

    public long getVcOption() {
        return vcOption;
    }

    public void setVcOption(long vcOption) {
        this.vcOption = vcOption;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
