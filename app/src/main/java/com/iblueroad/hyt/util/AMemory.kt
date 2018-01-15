package com.iblueroad.hyt.util

import android.support.v4.util.SparseArrayCompat
import com.iblueroad.hyt.z_other.ACache
import com.orhanobut.hawk.Hawk


/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2017/12/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object AMemory {

    //Acache的使用
    //    存储数据
    //    ACache mCache = ACache.get(this);
    //    mCache.put("test_key1", "test value");
    //    mCache.put("test_key2", "test value", 10);//保存10秒，如果超过10秒去获取这个key，将为null
    //    mCache.put("test_key3", "test value", 2 * ACache.TIME_DAY);//保存两天，如果超过两天去获取这个key，将为null
    //    获取数据
    //    ACache mCache = ACache.get(this);
    //    String value = mCache.getAsString("test_key1");

    private lateinit var aCache: ACache
    private val tCache: SparseArrayCompat<Any> = SparseArrayCompat()


    fun init() {
        Hawk.init(AUtils.appContext).build()
        aCache = ACache.get(AUtils.appContext)
    }

    private fun release() {
        tCache.clear()
    }

    //---------------------------------------  永久存储 Top  ---------------------------------------
    /**
     * 保存至永久存储
     */
    fun <T> save(key: String, value: T): Boolean {
        tCache.put(key.hashCode(), value)
        return Hawk.put(key, value)
    }

    /**
     *从永久存储中恢复数据
     */
    fun <T> restore(key: String, defaultValue: T): T {
        return Hawk.get(key, defaultValue)
    }
    //---------------------------------------  永久存储 End ---------------------------------------

    //---------------------------------------  缓存 Top  ---------------------------------------
    /**
     * 保存至缓存（临时存储，不保证成功）
     */
    fun put(key: String, value: Any) {
        tCache.put(key.hashCode(), value)
    }

    /**
     * 从缓存中恢复数据
     */
    fun get(key: String, defaultValue: Any): Any? {
        return tCache.get(key.hashCode()) ?: restore(key, defaultValue)
    }

    /**
     * 从缓存中恢复数据
     */
    fun get(key: String): Any? {
        return tCache.get(key.hashCode())
    }

    /**
     * 从缓存中恢复数据
     */
    fun delete(key: String) {
        tCache.delete(key.hashCode())
        Hawk.delete(key)
    }

    /**
     * 从缓存中恢复数据
     */
    fun get4Force(key: String, defaultValue: Any): Any? {
        return tCache.get(key.hashCode()) ?: defaultValue
    }

    //---------------------------------------  缓存 End  ---------------------------------------
}