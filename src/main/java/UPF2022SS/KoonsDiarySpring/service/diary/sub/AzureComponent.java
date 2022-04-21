package UPF2022SS.KoonsDiarySpring.service.diary.sub;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@ConfigurationProperties(prefix = "cloud.azure")
public class AzureComponent {

    private String key;
    private String endPoint;
}
