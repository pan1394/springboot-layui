package top.snailclimb.bus.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import top.snailclimb.bus.dto.BusLine;
import top.snailclimb.bus.dto.BusStopDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class BusLineUtils {

    public static List<BusStopDto> getLineInfo(String lineId){
        Map<String, String> req = new HashMap<>();
        req.put("lineID", lineId);
        String res = RestTemplateUtils.post("http://bus.2500.tv/api_line_status.php", req);
        List<BusStopDto> x = BusStopDto.parse(res);
        x.forEach(System.out::println);
        return x;
    }

    public static List<BusStopDto> getRuningBus(String lineId){
        Map<String, String> req = new HashMap<>();
        req.put("lineID", lineId);
        String res = RestTemplateUtils.post("http://bus.2500.tv/api_line_status.php", req);
        List<BusStopDto> x = BusStopDto.parse(res);
        List<BusStopDto> runing = x.stream().filter( o-> !StringUtils.isEmpty(o.getBusInfo())).collect(Collectors.toList());
        return runing;
    }

    public static void main(String[] args) {
        getLineInfo("10000376");
        getLineInfo("10000381");
    }
}
