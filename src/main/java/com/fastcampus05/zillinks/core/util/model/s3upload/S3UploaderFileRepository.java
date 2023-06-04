package com.fastcampus05.zillinks.core.util.model.s3upload;

import org.springframework.data.jpa.repository.JpaRepository;

public interface S3UploaderFileRepository extends JpaRepository<S3UploaderFile, Long> {
}
