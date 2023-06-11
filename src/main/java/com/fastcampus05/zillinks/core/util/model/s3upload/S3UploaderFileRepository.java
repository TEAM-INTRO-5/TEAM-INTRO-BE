package com.fastcampus05.zillinks.core.util.model.s3upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface S3UploaderFileRepository extends JpaRepository<S3UploaderFile, Long> {

    @Query("select s from S3UploaderFile s where s.encodingPath = :encodingPath")
    Optional<S3UploaderFile> findByEncodingPath(@Param("encodingPath") String encodingPath);

    @Query("select s from S3UploaderFile s where s.encodingPath in :encodingPaths")
    Optional<List<S3UploaderFile>> findByEncodingPaths(@Param("encodingPaths") List<String> encodingPaths);

}
