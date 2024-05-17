package OBUSDK.PerEncDec;

public class RoadSignCode {
    private Long layoutComponentId;
    private CodeType code;

    public Long getLayoutComponentId() {
        return layoutComponentId;
    }

    public void setLayoutComponentId(Long layoutComponentId) {
        this.layoutComponentId = layoutComponentId;
    }

    public CodeType getCode() {
        return code;
    }

    public void setCode(CodeType code) {
        this.code = code;
    }

    // troquei CodeType para uma classe separada
}
