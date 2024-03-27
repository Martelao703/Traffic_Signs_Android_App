public class JsonServiceProvider {

    private int countryCode;
    private int providerIdentifier;

    public JsonServiceProvider(int countryCode, int providerIdentifier) {
        this.countryCode = countryCode;
        this.providerIdentifier = providerIdentifier;
    }

    public JsonServiceProvider() {
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public int getProviderIdentifier() {
        return providerIdentifier;
    }

    public void setProviderIdentifier(int providerIdentifier) {
        this.providerIdentifier = providerIdentifier;
    }
}
