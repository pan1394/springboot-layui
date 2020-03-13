package top.snailclimb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TimeLineController {

    int times = 0;
    @GetMapping("/timeline")
    public String timeLineTest(Model model){
        return "timeline";
    }

    @ResponseBody
    @GetMapping("/bus")
    public String getBus(){
        int totalStop = 5;
        if(times > totalStop) times = 0;
        int stop = times;
        String stopName = "Bus stop " + (stop + 1);
        times ++;
        return stopName;
    }
}
