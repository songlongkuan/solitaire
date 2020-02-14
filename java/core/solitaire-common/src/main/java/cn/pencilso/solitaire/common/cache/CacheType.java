package cn.pencilso.solitaire.common.cache;

import cn.pencilso.solitaire.common.toolkit.DateUtils;

/**
 * @author pencilso
 * @date 2020/2/12 9:02 下午
 */
public enum CacheType {

    jwtsalt(1000, DateUtils.MillisecondConst.MINUTE * 10);












    private long expires;
    private int maximumSize;

    CacheType(int maximumSize, long expires) {
        this.expires = expires;
        this.maximumSize = maximumSize;
    }

    public long getExpires() {
        return expires;
    }

    public long getMaximumSize() {
        return maximumSize;
    }
}
