package top.snailclimb.bus.utils;
  
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;
import top.snailclimb.bus.dto.BusStop;
import top.snailclimb.bus.dto.InCommingBusInfo;

@Slf4j
public class BusStopArrival {

	private static final String BUS_STOP_ARRIVAL_URL = "http://bus.2500.tv/stationList.php?1=1";

	public static BusStop getArrival(String stationId){
		return getArrival(stationId, null);
	}
	
	public static BusStop getArrival(String stationId, String name){
		String reqUrl = BUS_STOP_ARRIVAL_URL;
		BusStop bs = new BusStop();
		if(!StringUtils.isEmpty(stationId)){
			reqUrl += "&stationID=" + stationId;
			bs.setStationId(stationId);
		}
		if(!StringUtils.isEmpty(name)){
			reqUrl += "&name=" + name;
			bs.setName(name);
		}
		try {
			parseAndFill(reqUrl, bs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  bs;
	}

	private static void parseAndFill(String reqUrl, BusStop bStop) throws IOException {
		Document doc = Jsoup.connect(reqUrl).get();
		Elements elements = doc.getElementsByTag("dl");
//		String stopName = doc.select("div.tMsg").get(0).html();
//		bStop.setName(stopName);

		for(Element one : elements) {
			Element p1 = one.select("dd p:first-child").get(0);
			Element p2 = one.select("dd p:last-child").get(0);
			Elements bs = p1.select("b");
			InCommingBusInfo info = new InCommingBusInfo();
			String lineName = "";
			int stillLeft = -1;
			String lineId = one.select("a:first-child").attr("lineID");
			if(bs.size() == 2){ //has still left
				stillLeft = Integer.parseInt(bs.get(1).text());
				info.setStillLeft(stillLeft);
			}
			lineName = bs.get(0).text();
			info.setLineName(lineName);
			info.setTargetStationName(p2.text());
			info.setLineId(lineId);
			String backup = p1.select("span").text();

			info.setBackup(backup);
			bStop.add(info);
			log.info(info.toString());
		}
	}

	public static void main(String[] args) throws IOException {
//		BusStop x = getArrival("10003033");
		BusStop x = getArrival("10004759");
	}

} 
