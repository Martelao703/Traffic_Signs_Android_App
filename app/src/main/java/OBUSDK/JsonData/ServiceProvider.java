package OBUSDK.JsonData;

public class ServiceProvider {
    private int countryCode;
    private int providerIdentifier;

    public ServiceProvider() {
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
