package top.snailclimb.bus.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BusStop {

    private String stationId;

    private String name;

    private List<InCommingBusInfo> comings = new ArrayList<>();

    public void add(InCommingBusInfo info){
        comings.add(info);
    }
}
