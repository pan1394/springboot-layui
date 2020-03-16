package top.snailclimb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.snailclimb.bus.dto.BusStopDto;
import top.snailclimb.bus.utils.BusLineUtils;

import java.util.List;

@Slf4j
@Controller
public class TimeLineController {

    int times = 0;
    @GetMapping("/timeline")
    public String timeLineTest(Model model){
        return "timeline";
    }

    @ResponseBody
    @GetMapping("/bus/{lineId}")
    public List<BusStopDto> getBus(@PathVariable("lineId") String lineId){
        log.info("line Id is {}", lineId);
        List<BusStopDto> res =  BusLineUtils.getRuningBus(lineId);
        return res;
    }

    @GetMapping("/line/{lineId}")
    public String getLine(@PathVariable("lineId") String lineId, Model model){
        log.info("line Id is {}", lineId);
        List<BusStopDto> res =  BusLineUtils.getLineInfo(lineId);
        model.addAttribute("stops", res);
        model.addAttribute("lineId", lineId);
        return "busline";
    }
}
