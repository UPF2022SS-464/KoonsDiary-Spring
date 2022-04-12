package UPF2022SS.KoonsDiarySpring.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
//        List<String> realProfiles = Arrays.asList("real1", "real2");

        List<String> testProfiles = Arrays.asList("test1", "test2");
        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

        // test 값 반환
        return profiles.stream()
                .filter(testProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
