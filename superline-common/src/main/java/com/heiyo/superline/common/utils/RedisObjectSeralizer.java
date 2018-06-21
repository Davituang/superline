package com.heiyo.superline.common.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 实现对象的序列化接口，包含对象序列化和反序列化
 * @author txz
 * @date
 */
public class RedisObjectSeralizer implements RedisSerializer<Object> {

    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();

    static final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return null;
        }
        return serializer.convert(o);

    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return EMPTY_ARRAY;
        }
        return deserializer.convert(bytes);
    }
}
