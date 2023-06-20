package com.fastcampus05.zillinks.core.util.dto.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoAddress {
    private Meta meta;
    private List<Documents> documents;

    @Getter
    @Setter
    public static class Meta {
        @JsonProperty("total_count")
        private Integer totalCount;
        @JsonProperty("pageable_count")
        private Integer pageableCount;
        @JsonProperty("is_end")
        private Boolean isEnd;
    }

    @Getter
    @Setter
    public static class Documents {
        @JsonProperty("address_name")
        private String addressName;
        @JsonProperty("address_type")
        private String addressType;
        private Address address;
        private String x;
        private String y;

        @JsonProperty("road_address")
        private RoadAddress roadAddress;
    }

    @Getter
    @Setter
    public static class Address {
        @JsonProperty("address_name")
        private String addressName;
        @JsonProperty("b_code")
        private String bCode;
        @JsonProperty("h_code")
        private String hCode;
        @JsonProperty("main_address_no")
        private String mainAddressNo;
        @JsonProperty("mountain_yn")
        private String mountainYn;
        @JsonProperty("region_1depth_name")
        private String region1depthName;
        @JsonProperty("region_2depth_name")
        private String region2depthName;
        @JsonProperty("region_3depth_h_name")
        private String region3depthHName;
        @JsonProperty("region_3depth_name")
        private String region3depthName;
        @JsonProperty("sub_address_no")
        private String subAddressNo;
        private String x;
        private String y;
    }

    @Getter
    @Setter
    public static class RoadAddress {
        @JsonProperty("address_name")
        private String addressName;
        @JsonProperty("building_name")
        private String buildingName;
        @JsonProperty("main_building_no")
        private String mainBuildingNo;
        @JsonProperty("region_1depth_name")
        private String region1depthName;
        @JsonProperty("region_2depth_name")
        private String region2depthName;
        @JsonProperty("region_3depth_name")
        private String region3depthName;
        @JsonProperty("road_name")
        private String roadName;
        @JsonProperty("sub_building_no")
        private String subBuildingNo;
        @JsonProperty("underground_yn")
        private String undergroundYn;
        private String x;
        private String y;
        @JsonProperty("zone_no")
        private String zoneNo;
    }
}