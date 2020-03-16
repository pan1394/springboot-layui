package top.snailclimb.api;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.snailclimb.bus.utils.RestTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ApiTest {

    @Test
    public void testApi(){
        log.info("hello, we start...");
        Map<String, String> req = new HashMap<>();
        req.put("lineID", "10000156");
        String res = RestTemplateUtils.post("http://bus.2500.tv/api_line_status.php", req);
        log.info("result is {}", res);
    }
}
