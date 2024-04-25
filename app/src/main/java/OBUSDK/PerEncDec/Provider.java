package OBUSDK.PerEncDec;

public class Provider {
    private A5 countryCode;
    private int providerIdentifier;

    public A5 getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(A5 countryCode) {
        this.countryCode = countryCode;
    }

    public int getProviderIdentifier() {
        return providerIdentifier;
    }

    public void setProviderIdentifier(int providerIdentifier) {
        this.providerIdentifier = providerIdentifier;
    }
}
