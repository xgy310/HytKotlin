package com.iblueroad.hyt.data.local;

import android.support.v4.util.SparseArrayCompat;
import com.iblueroad.hyt.util.AUtils;
import com.iblueroad.hyt.z_other.ACache;
import com.orhanobut.hawk.Hawk;

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2018/03/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class AMemoryJava {
    //Acache的使用
    //    存储数据
    //    ACache mCache = ACache.get(this);
    //    mCache.put("test_key1", "test value");
    //    mCache.put("test_key2", "test value", 10);//保存10秒，如果超过10秒去获取这个key，将为null
    //    mCache.put("test_key3", "test value", 2 * ACache.TIME_DAY);//保存两天，如果超过两天去获取这个key，将为null
    //    获取数据
    //    ACache mCache = ACache.get(this);
    //    String value = mCache.getAsString("test_key1");

    private ACache aCache;
    private SparseArrayCompat<Object> tCache = new SparseArrayCompat();

    public void init() {
        Hawk.init(AUtils.sApp.get()).build();
        aCache = ACache.get(AUtils.sApp.get());
    }

    //---------------------------------------  永久存储 Top  ---------------------------------------

    /**
     * 保存至永久存储
     */
    public boolean save(String key, Object value) {
        put(key, value);
        return Hawk.put(key, value);
    }

    /**
     * 从永久存储中恢复数据
     */
    public <T> T restore(String key, T defaultValue) {
        return Hawk.get(key, defaultValue);
    }
    //---------------------------------------  永久存储 End ---------------------------------------

    //---------------------------------------  缓存 Top  ---------------------------------------

    /**
     * 保存至缓存（临时存储，不保证成功）
     */
    public void put(String key, Object value) {
        tCache.put(key.hashCode(), value);
    }

    /**
     * 从缓存中获取数据
     */
    public <T> T get(String key, T defaultValue) {
        Object value = tCache.get(key.hashCode());
        return null == value ? restore(key, defaultValue) : (T) value;
    }

    /**
     * 从缓存中获取数据
     */
    public <T> T get(String key) {
        T n = null;
        return get(key, n);
    }

    /**
     * 从缓存中恢复数据
     */
    public <T> T getByForce(String key, T defaultValue) {
        Object value = tCache.get(key.hashCode());
        return null == value ? defaultValue : (T) value;
    }

    /**
     * 从缓存中删除数据
     */
    public void delete(String key) {
        tCache.delete(key.hashCode());
        Hawk.delete(key);
    }

    private void release() {
        tCache.clear();
    }

    //---------------------------------------  缓存 End  ---------------------------------------
}