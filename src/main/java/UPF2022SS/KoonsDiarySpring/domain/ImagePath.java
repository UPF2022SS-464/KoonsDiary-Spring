package UPF2022SS.KoonsDiarySpring.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name = "image_path")
@Getter
@Builder
@AllArgsConstructor
@Entity
public class ImagePath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id",nullable = false)
    private Long id;

    @NotBlank()
    @Column(length = 500)
    private String path;
}
