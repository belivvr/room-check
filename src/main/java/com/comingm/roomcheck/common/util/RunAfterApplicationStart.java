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
	
	@Value("${hubs.scene.sid}")
	private String hubsSceneSid;
		
	@Value("${hubs.init.room.object}")
	private int initRoomObject;
	
	@Value("${hubs.max.room.object}")
	private int maxRoomObject;
	
	@Value("${hubs.room.high.weight}")
	private float hubsRoomHighWeight;
	
	@Value("${hubs.room.low.weight}")
	private float hubsRoomLowWeight;
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		while(true) {
			try {
				ApiKey apiKey = apiKeyRepository.findByApiKey("ZZVL9FNGXpi$nrXq1&olsE*dD");

				Long maxId = hubsRepositiry.findMaxId();
				System.out.println(maxId);		
				List<Hubs> data = hubsRepositiry.findAllByApiKeyAndSceneSidAndDeleteYn(apiKey,hubsSceneSid,"N");
		
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
						String name = "이벤트 " + (maxId+1) +" 관";
					
						roomInfoCreateDto.setName(name);
						roomInfoCreateDto.setSceneId(hubsSceneSid);
					
						webClientUtil.post("/hubs/create","ZZVL9FNGXpi$nrXq1&olsE*dD",roomInfoCreateDto);
					
					}
					
					//삭제 프로세스
					if((float)totalLobbyMemberCount/totalRoomSize < hubsRoomLowWeight) {
							
							int dataSize = data.size();
							for(Iterator<Hubs> itr = data.iterator(); itr.hasNext();) {
								
								if(dataSize <= initRoomObject) 
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
						
				
				
				Thread.sleep(3000);
				
				
				System.out.println("222222222222222");	
				List<Hubs> data2 = hubsRepositiry.findAllByApiKeyAndSceneSidAndDeleteYn(apiKey,hubsSceneSid,"Y");
				
				for(Iterator<Hubs> itr = data2.iterator(); itr.hasNext();) {
					Hubs hubs = itr.next();
					
					if(hubs.getLobbyCount() + hubs.getMemberCount() > 0) {
						hubs.setDeleteYn("N");
						hubs.setUpdatedAt(null);
						
						hubsRepositiry.save(hubs);
					}
									
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				this.run(args);
			}
			
		}		
		
	}

}
