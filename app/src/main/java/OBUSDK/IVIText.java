package OBUSDK;

public class IVIText {
    private int layoutComponentId;
    private int language;
    private String textContent;

    public int getLayoutComponentId() {
        return layoutComponentId;
    }

    public void setLayoutComponentId(int layoutComponentId) {
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