package backend.academy.model;

public enum ImageFormat {
    JPEG("jpeg"),
    BMP("bmp"),
    PNG("png");

    private final String extension;

    ImageFormat(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return extension;
    }
}
