package com.comingm.roomcheck.common.util;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.comingm.roomcheck.entities.ApiKey;
import com.comingm.roomcheck.entities.Hubs;
import com.comingm.roomcheck.repositories.ApiKeyRepository;
import com.comingm.roomcheck.repositories.HubsRepositiry;

@Component
public class RunAfterApplicationStart implements ApplicationRunner {

	
	@Autowired
	private ApiKeyRepository apiKeyRepository;
	
	@Autowired
	private HubsRepositiry hubsRepositiry;
	
	@Autowired
	private WebClientUtil webClientUtil;
		
	@Value("${hubs.max.room.object}")
	private int maxRoomObject;
	
	@Value("${hubs.room.high.weight}")
	private float hubsRoomHighWeight;
	
	@Value("${hubs.room.low.weight}")
	private float hubsRoomLowWeight;
	
	//sceneSid,hubsPrefixName,minRoomCount
	private Object[][] ObjArr= {
				{"Lk5Ffoi",		"김지현"		,1},
				{"EXM2naB",		"박상혁"		,1},
				{"DVc8ejT",		"아트놈"		,1},
				{"X6ENysV",		"요요진"		,1},
				{"N5thun9",		"레이레이"		,1},
				{"gQ4eQS8",		"그룹(찰스장,코마,구준엽,홍지윤,김규리)"		,1},
				{"WC9A5e5",		"미디어아트"	,1},
				
				{"8dYciVJ",		"디자이너(강동준,고태용,이청정,한상혁,곽현주)"	,1},
				
				{"ZJrSuiM",		"쟈코비"		,1},
				{"SdFb3UG",		"조준호"		,1},
				{"fvZyYUi",		"애쉬"		,1},
				{"ksmm5ta",		"아이러닉"		,1},
				
				{"nibzdbD",		"웹툰,영화"		,1},
	}; 
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		
		while(true) {
			try {
				for(int i=0; i<ObjArr.length; i++) {
					ApiKey apiKey = apiKeyRepository.findByApiKey("ZZVL9FNGXpinrXq1olsEdD");
	
					Long maxId = hubsRepositiry.findMaxId();
					System.out.println(maxId);		
					List<Hubs> data = hubsRepositiry.findAllByApiKeyAndSceneSidAndDeleteYn(apiKey,(String) ObjArr[i][0],"N");
			
					int totalRoomSize = 0;
					int totalLobbyMemberCount = 0;
					
					//최대 방보다 적을떄
					if(data.size() < maxRoomObject) {
					
						for(Iterator<Hubs> itr = data.iterator(); itr.hasNext();) {
							Hubs hubs = itr.next();
							
							totalLobbyMemberCount += hubs.getLobbyCount() + hubs.getMemberCount();
							totalRoomSize += hubs.getRoomSize();					
						}
						
						//증가 프로세스
						if((float)totalLobbyMemberCount/totalRoomSize > hubsRoomHighWeight) {
						
						
							RoomInfoCreateDto roomInfoCreateDto = new RoomInfoCreateDto();
							String name = (String) ObjArr[i][1]+" "+(maxId+1) +" 관";
						
							roomInfoCreateDto.setName(name);
							roomInfoCreateDto.setSceneId((String) ObjArr[i][0]);
						
							webClientUtil.post("/hubs/create","ZZVL9FNGXpinrXq1olsEdD",roomInfoCreateDto);
						
						}
						
						//삭제 프로세스
						if((float)totalLobbyMemberCount/totalRoomSize < hubsRoomLowWeight) {
								
								int dataSize = data.size();
								for(Iterator<Hubs> itr = data.iterator(); itr.hasNext();) {
									
									if(dataSize <= (int) ObjArr[i][2]) 
										break;
									
									Hubs hubs = itr.next();
									
									if(hubs.getLobbyCount() + hubs.getMemberCount() == 0) {
																			
										hubs.setDeleteYn("Y");
										hubs.setUpdatedAt(null);
										
										hubsRepositiry.save(hubs);
										--dataSize;
										
									if((float)totalLobbyMemberCount/(totalRoomSize-hubs.getRoomSize()) > hubsRoomLowWeight)
											break;
										
									}			
								}
						
						}
							
							
					}		
						
					
						
				
				
					Thread.sleep(100);
				
				
					System.out.println("222222222222222");	
					List<Hubs> data2 = hubsRepositiry.findAllByApiKeyAndSceneSidAndDeleteYn(apiKey,(String) ObjArr[i][0],"Y");
				
					for(Iterator<Hubs> itr = data2.iterator(); itr.hasNext();) {
						Hubs hubs = itr.next();
					
						if(hubs.getLobbyCount() + hubs.getMemberCount() > 0) {
							hubs.setDeleteYn("N");
							hubs.setUpdatedAt(null);
						
							hubsRepositiry.save(hubs);
						}
									
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				this.run(args);
			}
			
		}		
		
	}

}
