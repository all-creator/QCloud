package easy.stars.exceptions;

public class URLNotFound extends RuntimeException {
    public URLNotFound(Throwable cause, String url) {
        super(url, cause);
    }
}
