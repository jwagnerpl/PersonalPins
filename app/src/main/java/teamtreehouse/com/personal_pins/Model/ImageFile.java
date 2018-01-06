package teamtreehouse.com.personal_pins.Model;

import android.net.Uri;

public class ImageFile {
    private String fileName;
    private byte[] fileBytes;
    private Uri uri;

    public ImageFile(String fileName, byte[] fileBytes, Uri uri) {
        this.fileName = fileName;
        this.fileBytes = fileBytes;
        this.uri = uri;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public Uri getUri() {
        return uri;
    }
}
