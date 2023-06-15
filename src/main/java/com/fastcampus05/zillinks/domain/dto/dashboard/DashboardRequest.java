package com.fastcampus05.zillinks.domain.dto.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Pattern;

public class DashboardRequest {

    @Getter
    public static class ExcelContactUsInDTO {
        @Schema(description = "확인 필요/완료", example = "UNCONFIRMED")
        @Pattern(regexp = "UNCONFIRMED|CONFIRM")
        private String status;
    }
}
