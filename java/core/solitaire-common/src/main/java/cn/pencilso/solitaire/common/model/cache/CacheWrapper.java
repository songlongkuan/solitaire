package cn.pencilso.solitaire.common.model.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author pencilso
 * @date 2020/2/12 7:58 下午
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CacheWrapper<T> {


    /**
     * 缓存的数据
     */
    private T data;
    /**
     * 过期时间
     */
    private long expireAt;
    /**
     * 创建时间
     */
    private long createAt;


}
