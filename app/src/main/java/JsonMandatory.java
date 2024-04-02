import java.time.LocalDateTime;

public class JsonMandatory {

    private JsonServiceProvider serviceProvider;
    private long iviIdentificationNumber;
    private LocalDateTime timestamp;
    private LocalDateTime validForm;
    private LocalDateTime validTo;
    private int connectedJsonStructures;
    private int JsonStatus;


    public JsonMandatory() {
        this.serviceProvider = new JsonServiceProvider();
    }

    public JsonServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(JsonServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public long getIviIdentificationNumber() {
        return iviIdentificationNumber;
    }

    public void setIviIdentificationNumber(long iviIdentificationNumber) {
        this.iviIdentificationNumber = iviIdentificationNumber;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getValidForm() {
        return validForm;
    }

    public void setValidForm(LocalDateTime validForm) {
        this.validForm = validForm;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public int getConnectedJsonStructures() {
        return connectedJsonStructures;
    }

    public void setConnectedJsonStructures(int connectedJsonStructures) {
        this.connectedJsonStructures = connectedJsonStructures;
    }

    public int getJsonStatus() {
        return JsonStatus;
    }

    public void setJsonStatus(int jsonStatus) {
        this.JsonStatus = jsonStatus;
    }

    public void IVIMandatory(){
        this.serviceProvider = new JsonServiceProvider();
    }
}
