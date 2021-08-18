package top.ersut.mail.send.sdk.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Jackson配置
 * @author 王二飞
 */
//@Slf4j
public class JacksonConfig {

    private String dateTimeFormatValue = "yyyy-MM-dd HH:mm:ss.SSS";
//    @Value("${project.date-format:yyyy-MM-dd}")
    private String dateFormatValue = "yyyy-MM-dd";
//    @Value("${project.time-format:HH:mm:ss.SSS}")
    private String timeFormatValue = "HH:mm:ss.SSS";


    private String getDateTimeFormatValue(){
        boolean dateFormatValueNotEmpty = dateFormatValue != null && !"".equals(dateFormatValue);
        boolean timeFormatValueNotEmpty = timeFormatValue != null && !"".equals(timeFormatValue);
        if(dateFormatValueNotEmpty && timeFormatValueNotEmpty){
            return dateFormatValue + " " + timeFormatValue;
        } else {
            return dateTimeFormatValue;
        }
    }



    DateTimeFormatter dataTimeFormat() {
        return DateTimeFormatter.ofPattern(getDateTimeFormatValue());
    }

    DateTimeFormatter dataFormat() {
        return DateTimeFormatter.ofPattern(dateFormatValue);
    }

    DateTimeFormatter timeFormat() {
        return DateTimeFormatter.ofPattern(timeFormatValue);
    }

    //jackson支持LocalDate等
    public ObjectMapper objectMapper() {

        //log.info("jacksonConfig ===> dateTimeFormatValue:"+dateTimeFormatValue+"\tdateFormatValue:"+dateFormatValue+"\ttimeFormatValue:"+timeFormatValue);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dataTimeFormat()));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dataFormat()));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormat()));
        javaTimeModule.addDeserializer(Instant.class, new InstantCustomDeserializer(dataTimeFormat()));
        javaTimeModule.addDeserializer(Date.class, new DateCustomDeserializer(new SimpleDateFormat(getDateTimeFormatValue())));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(javaTimeModule);

        // 允许没有引号的字段名（非标准）
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号（非标准）
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 强制JSON 空字符串("")转换为null对象值:
//        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        //null 不参加序列化
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //允许特殊字符？
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
        //反序列化时,遇到未知属性不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    class InstantCustomDeserializer extends JsonDeserializer<Instant> {
        DateTimeFormatter dateTimeFormatter;

        private InstantCustomDeserializer(DateTimeFormatter dateTimeFormatter) {
            this.dateTimeFormatter = dateTimeFormatter;
        }

        @Override
        public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String dataStr = jsonParser.getText();
            if(dataStr == null){
                return null;
            }
            return LocalDateTime.parse(dataStr,dateTimeFormatter).atZone(ZoneId.systemDefault()).toInstant();
        }
    }


    class DateCustomDeserializer extends JsonDeserializer<Date> {
        SimpleDateFormat simpleDateFormat;

        private DateCustomDeserializer(SimpleDateFormat simpleDateFormat) {
            this.simpleDateFormat = simpleDateFormat;
        }

        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String dataStr = jsonParser.getText();
            if(dataStr == null) {
                return null;
            }

            try {
                return simpleDateFormat.parse(dataStr);
            } catch (ParseException e) {
                throw new JsonProcessingException("时间转换错误",e){};
            }
        }
    }

}
