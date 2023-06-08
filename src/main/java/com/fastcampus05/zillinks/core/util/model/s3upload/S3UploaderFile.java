package com.fastcampus05.zillinks.core.util.model.s3upload;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "s3_uploader_file_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class S3UploaderFile extends TimeBaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "s3_uploader_file_id")
    private Long id;

    @Column(name = "original_path")
    private String originalPath;
    @Column(name = "encoding_path")
    private String encodingPath;
}
