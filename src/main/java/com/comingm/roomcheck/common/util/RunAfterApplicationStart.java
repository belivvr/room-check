package com.comingm.roomcheck.common.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.comingm.roomcheck.entities.ApiKey;
import com.comingm.roomcheck.entities.HubScene;
import com.comingm.roomcheck.entities.HubSpace;
import com.comingm.roomcheck.entities.Hubs;
import com.comingm.roomcheck.repositories.ApiKeyRepository;
import com.comingm.roomcheck.repositories.HubSceneRepository;
import com.comingm.roomcheck.repositories.HubSpaceRepository;
import com.comingm.roomcheck.repositories.HubsRepositiry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class RunAfterApplicationStart implements ApplicationRunner {

	class HeadCheck {
	    private String head_count;
	    private String hub_sid;
	    private String room_size;
	    private String scene_sid;
	}
	
	@Autowired
	private HubSceneRepository hubSceneRepository;
	
	@Autowired
	private HubSpaceRepository hubSpaceRepository;
	
	@Autowired
	private WebClientUtil webClientUtil;
		
	@Value("${hubs.max.room.object}")
	private int maxRoomObject;
	
	@Value("${hubs.room.high.weight}")
	private float hubsRoomHighWeight;
	
	@Value("${hubs.room.low.weight}")
	private float hubsRoomLowWeight;
	
	@Value("${hubs.reticulum.host}")
	private String apiReticulumServer;
	
	@Value("${xrcloud.host}")
	private String apiXrcloudServer;
	
	//sceneSid,hubsPrefixName,minRoomCount
//	private Object[][] ObjArr= {
//				{"nKPoDLc",		"김지현 & 박상혁"		,1},
//				{"aWJQJ8k",		"요요진 & 레이레이"		,1},
//				{"pL7Bk7s",		"아트놈 & 그룹방(찰스장,코마,구준엽,홍지윤,김규리)"		,1},
//				{"iXEYSup",		"Artist Group Exhibition"		,1},
//				
//				{"WrR2wSr",		"미디어아트 & 디자이너 (강동준,고태용,이청정,한상혁,곽현주)"		,1},
//			
//				{"ZJrSuiM",		"쟈코비"		,1},
//				{"SdFb3UG",		"조준호"		,1},
//				{"fvZyYUi",		"애쉬"		,1},
//				{"ksmm5ta",		"아이러닉"		,1},
//				
//				{"nibzdbD",		"브랜드"		,1},
//				
//				{"Tmd6EVy",		"무면"		,1}
//				
//	}; 
	
	@Override
	public void run(ApplicationArguments args)  {
		
		try {
		
		while(true) {	
		
			List<HubScene> hubSceneList = hubSceneRepository.findAllByActive("Y");
			
			for(HubScene hubScene : hubSceneList) {
				List<HubSpace> hubSpaceList = hubSpaceRepository.findAllByActiveAndHubSceneAndPublishType("Y",hubScene,"public");
				if(hubSpaceList.size() == 0)
					break;
				String hubSidString = "";
				for(HubSpace item : hubSpaceList) 
					hubSidString += item.getHubSid()+",";
				
				hubSidString = hubSidString.substring(0, hubSidString.length() - 1);
				
				String date = webClientUtil.get(apiReticulumServer,"/api/v1/hub_head_check", "public_active_hub_cluster_string", hubSidString);
				
				Gson gson = new Gson();
				Type type = new TypeToken<List<HeadCheck>>() {}.getType();
				
				List<HeadCheck> HeadCheckList = gson.fromJson(date, type);
				
				int totalHeadCount = 0;
				int totalRoomSize = 0;
				for(HeadCheck headCheck: HeadCheckList) {
					totalHeadCount += Integer.parseInt(headCheck.head_count);
					totalRoomSize += Integer.parseInt(headCheck.room_size);
				}
				
				//증가 프로세스
				if(HeadCheckList.size() < hubScene.getMaxChannel()) {		
					if((float)totalHeadCount/totalRoomSize > hubScene.getChannelIncreaseWeight() || HeadCheckList.size() < hubScene.getMinChannel()) {
									
						RoomInfoCreateDto roomInfoCreateDto = new RoomInfoCreateDto();
						Long maxId = hubSpaceRepository.findMaxId();
						String name = hubScene.getName()+" "+"채널"+" "+(maxId+1);
					
						roomInfoCreateDto.setName(name);
						roomInfoCreateDto.setSceneSid(hubScene.getResourceSid());				
					
						webClientUtil.get(apiXrcloudServer,"/hub-space/auto-caling/increase","nameAndSceneSid",roomInfoCreateDto.getName()+","+roomInfoCreateDto.getSceneSid());
						
//						hubSpaceList = hubSpaceRepository.findAllByActiveAndHubScene("Y",hubScene);
//						
//						hubSidString = roomInfoCreateDto.getSceneSid()+",";
//						for(HubSpace item : hubSpaceList) 
//							hubSidString += item.getHubSid()+",";
//						
//						hubSidString = hubSidString.substring(0, hubSidString.length() - 1);
						
						webClientUtil.get(apiReticulumServer,"/api/v1/hub_head_check", "increase_room_hub_cluster_string", hubScene.getResourceSid());
					
					}
				}	
				
				
				//삭제 프로세스
				if(HeadCheckList.size() > hubScene.getMinChannel()) {	
					if((float)totalHeadCount/totalRoomSize < hubScene.getChannelDecreaseWeight()) {
						int headCheckSize = HeadCheckList.size();
						for(HeadCheck headCheck: HeadCheckList) {
							if(Integer.parseInt(headCheck.head_count) == 0) {
								HubSpace hubSpace = hubSpaceRepository.findByHubSid(headCheck.hub_sid);
								
								hubSpace.setActive("N");
								hubSpace.setUpdatedAt(null);
								
								hubSpaceRepository.save(hubSpace);
								--headCheckSize;
								
								if((float)totalHeadCount/(totalRoomSize-Integer.parseInt(headCheck.room_size)) > hubScene.getChannelDecreaseWeight()
									||headCheckSize <= hubScene.getMinChannel())
									break;				
							}	
								
						}
							
					}
				}
				
				Thread.sleep(500);
				
				//사람있을경우 활성화 제외된 방 열기
				List<HubSpace> unActiveHubSpaceList = hubSpaceRepository.findAllByActiveAndHubSceneAndPublishType("N",hubScene,"public");
				if(unActiveHubSpaceList.size() == 0)
					break;		
				
				hubSidString = "";
				for(HubSpace item : unActiveHubSpaceList) 
					hubSidString += item.getHubSid()+",";
				
				hubSidString = hubSidString.substring(0, hubSidString.length() - 1);
				
				date = webClientUtil.get(apiReticulumServer,"/api/v1/hub_head_check", "public_active_hub_cluster_string", hubSidString);
				
				HeadCheckList = gson.fromJson(date, type);
				
				for(HeadCheck headCheck : HeadCheckList){
					if(Integer.parseInt(headCheck.head_count) != 0) {
						HubSpace hubSpace = hubSpaceRepository.findByHubSid(headCheck.hub_sid);
						
						hubSpace.setActive("Y");
						hubSpace.setUpdatedAt(null);
						
						hubSpaceRepository.save(hubSpace);
					}
				}
				
			}

		}
	
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
}
