package top.ersut.mail.send.sdk.config;


import com.google.gson.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Gson配置
 * @author 王二飞
 */
public class GsonConfig {
    public static final Gson gson = new GsonConfig().gson();

    private GsonConfig(){}

    private String dateTimeFormatValue = "yyyy-MM-dd HH:mm:ss.SSS";

    private String dateFormatValue = "yyyy-MM-dd";

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



    private Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class,new DateCustomDeserializer(new SimpleDateFormat(getDateTimeFormatValue())))
                .create();
    }


    class DateCustomDeserializer implements JsonDeserializer<Date> {
        SimpleDateFormat simpleDateFormat;

        private DateCustomDeserializer(SimpleDateFormat simpleDateFormat) {
            this.simpleDateFormat = simpleDateFormat;
        }

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            String dataStr = json.getAsString();
            if(dataStr == null) {
                return null;
            }

            try {
                return simpleDateFormat.parse(dataStr);
            } catch (ParseException e) {
                throw new JsonParseException("时间转换错误",e);
            }
        }
    }

}
