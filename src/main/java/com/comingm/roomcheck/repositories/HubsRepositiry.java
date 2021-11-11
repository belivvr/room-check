package com.comingm.roomcheck.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.comingm.roomcheck.entities.ApiKey;
import com.comingm.roomcheck.entities.Hubs;


@Repository
public interface HubsRepositiry extends JpaRepository<Hubs, Long> {

	List<Hubs> findAllByApiKey(ApiKey apiKey);

	List<Hubs> findAllByApiKeyAndSceneSidAndDeleteYn(ApiKey apiKey, String sceneSid, String deleteYn);
	
	@Query(value="select Max(h.hubs_id) from xrcloud.hubs h" ,nativeQuery = true)
	Long findMaxId();

}
