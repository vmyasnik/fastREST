package utils.http;

public interface UrlResolver {
     default String getResolvedUrl(String base) {
        return base;
    }
}
