package com.sd.eos.rpc.other;

import com.google.gson.Gson;
import com.sd.lib.cache.Cache;

/**
 * Created by Administrator on 2017/8/29.
 */
public class CacheObjectConverter implements Cache.ObjectConverter
{
    private final Gson mGson = new Gson();

    @Override
    public byte[] objectToByte(Object object)
    {
        // 对象转byte
        return mGson.toJson(object).getBytes();
    }

    @Override
    public <T> T byteToObject(byte[] bytes, Class<T> clazz)
    {
        // byte转对象
        return mGson.fromJson(new String(bytes), clazz);
    }
}
