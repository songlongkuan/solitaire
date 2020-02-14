package cn.pencilso.solitaire.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author pencilso
 * @date 2020/2/11 9:21 下午
 */
@Data
@ConfigurationProperties(prefix = "aliyun")
public class AliyunOSSProperties {

    private String ossAccesskey;
    private String ossAccessecret;
    private String ossEndpoint;
    private String ossBucket;
    private String ossBinddoman;

}
