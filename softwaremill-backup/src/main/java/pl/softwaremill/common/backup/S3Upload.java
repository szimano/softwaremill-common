package pl.softwaremill.common.backup;

import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class S3Upload {
    private final String accessKeyId;
    private final String secretAccessKey;
    private final String bucketName;
    private final String fileName;

    public S3Upload(String accessKeyId, String secretAccessKey, String bucketName, String fileName) {
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
        this.bucketName = bucketName;
        this.fileName = fileName;
    }

    public void upload() throws S3ServiceException, IOException, NoSuchAlgorithmException {
        RestS3Service s3 = new RestS3Service(new AWSCredentials(accessKeyId, secretAccessKey));
        S3Object object = new S3Object(new File(fileName));
        s3.putObject(bucketName, object);
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, S3ServiceException {
        if (args.length != 4) {
            System.out.println("Usage: S3Upload accessKeyId secretAccessKey bucketName fileName");
        } else {
            new S3Upload(args[0], args[1], args[2], args[3]).upload();
        }
    }
}
