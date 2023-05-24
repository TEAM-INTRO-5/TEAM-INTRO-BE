package com.fastcampus05.zillinks.core.auth.token.model;

import com.fastcampus05.zillinks.core.auth.token.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
