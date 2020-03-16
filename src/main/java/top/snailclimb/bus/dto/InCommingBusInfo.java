package top.snailclimb.bus.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InCommingBusInfo {
    //到站
//    private String targetStationId;

    //开往方向站台
    private String targetStationName;

    private String lineName;

    private String lineId;

    private int stillLeft =-1;

    private String backup;

    public String getBackup() {
        if(stillLeft != -1){
            backup = String.format("还有%d站", stillLeft);
        }
        return backup;
    }
}
