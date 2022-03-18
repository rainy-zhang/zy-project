package org.rainy.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
public class JsonMapper {

    private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    private static final ObjectMapper objectMapper;
    static {
        objectMapper = new Jackson2ObjectMapperBuilder()
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)))
                .build();
    }


    public static <T> String object2String(T src) {
        if (src == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            log.error("parse object to string error: {}", e.getMessage());
            return null;
        }
    }

    public static <T> T string2Object(String src, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(src) || Objects.isNull(typeReference)) {
            return null;
        }
        try {
            return typeReference.getType().equals(String.class) ? (T) src : objectMapper.readValue(src, typeReference);
        } catch (JsonProcessingException e) {
            log.error("parse string to object error: {}", e.getMessage());
            return null;
        }
    }

}
