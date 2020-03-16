package top.snailclimb.bus.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class BusStopDto {

    private String BusInfo;
    private String Code;
    private String ID;
    private String InTime;
    private String OutTime;
    private String StationCName;;


    @Data
    private static class ResultDto{
        private String status;
        private String data;
    }

    public static List<BusStopDto> parse(String result) {
        ResultDto dto =  JSONObject.parseObject(result, ResultDto.class);
        return JSONObject.parseArray(dto.data, BusStopDto.class);
    }
}
