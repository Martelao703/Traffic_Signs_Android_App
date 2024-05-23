package OBUSDK.JsonData;

public class Text {
    private Long layoutComponentId;
    private int language;
    private String textContent;

    public Long getLayoutComponentId() {
        return layoutComponentId;
    }

    public void setLayoutComponentId(Long layoutComponentId) {
        this.layoutComponentId = layoutComponentId;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
