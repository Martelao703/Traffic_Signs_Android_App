package OBUSDK;

public class IVIMServiceProvider {
    private int countryCode;
    private int providerIdentifier;

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
