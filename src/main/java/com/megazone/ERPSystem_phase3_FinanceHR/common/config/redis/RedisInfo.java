package com.megazone.ERPSystem_phase3_FinanceHR.common.config.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "spring.data.redis")
@Configuration
public class RedisInfo {
//    private List<String> clusterNodes;
//    private String password;
//    private String readFrom = "REPLICA_PREFERRED";
//    private String clientName;
//    private RedisInfo cluster;

    private String host; // Redis 호스트
    private int port; // Redis 포트
    private String password; // Redis 비밀번호
    private String clientName = "default-client"; // 클라이언트 이름

}
