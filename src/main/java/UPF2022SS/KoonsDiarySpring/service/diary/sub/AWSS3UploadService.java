package UPF2022SS.KoonsDiarySpring.service.diary.sub;

import UPF2022SS.KoonsDiarySpring.common.CommonUtils;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AWSS3UploadService implements UploadService{

    private final AmazonS3Client amazonS3Client;
    private final S3Component component;

    @Autowired
    private final JwtService jwtService;

    @Override
    public String uploadFile(MultipartFile multipartFile, String accessToken) {

        String userId = jwtService.decodeAccessToken(accessToken).toString();
        String fileName = CommonUtils.buildFileName(userId, multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()){
            amazonS3Client.putObject(
                    new PutObjectRequest(
                            component.getBucket(),
                            fileName,
                            inputStream,
                            objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));

            return getFileUrl(fileName);

        } catch (AmazonClientException | IOException ace) {
            log.error(ace.getMessage());
            return ace.getMessage();
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        return amazonS3Client.getUrl(component.getBucket(), fileName).toString();
    }
}