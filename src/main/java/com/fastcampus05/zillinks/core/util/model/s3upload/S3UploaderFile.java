package com.fastcampus05.zillinks.core.util.model.s3upload;

import com.fastcampus05.zillinks.core.util.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "s3_uploader_file_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class S3UploaderFile extends TimeBaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "s3_uploader_file_id")
    private Long id;

    private String originalPath;
    private String encodingPath;
}
