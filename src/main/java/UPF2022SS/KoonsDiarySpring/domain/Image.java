package UPF2022SS.KoonsDiarySpring.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name = "image")
@Entity
@Getter
@Builder
@AllArgsConstructor
@ToString
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id",nullable = false)
    private Long id;

    @NotBlank()
    @Column(length = 500)
    private String path;
}
