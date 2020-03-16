package top.snailclimb.bus.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class BusLine {

    private String lineId;

    private String name;

    List<BusStop> stops;
}
