package predictions.views.Enums;

public enum DomainUrl {
    DOMAIN_URL("http://localhost:8080/Predictions");

    private final String url;

    DomainUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}