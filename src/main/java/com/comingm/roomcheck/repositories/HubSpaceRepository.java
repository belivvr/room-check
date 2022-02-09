package com.comingm.roomcheck.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.comingm.roomcheck.entities.HubScene;
import com.comingm.roomcheck.entities.HubSpace;

@Repository
public interface HubSpaceRepository extends JpaRepository<HubSpace,Long> {

	@Query("SELECT h FROM HubSpace h join fetch h.hubScene WHERE h.hubSid = ?1")
	HubSpace findByFetchHubSid(String hubSid);

	@Query(value = "SELECT hub_sid FROM hub_space WHERE resource_id = ?1 and active='Y' and publish_type ='public'",nativeQuery = true)
	List<String> findAllHubSidByResourceId(Long resourceId);
	
	@Query(value = "SELECT hub_space_id FROM hub_space WHERE and resource_id = ?1 and active='Y' and publish_type ='public'",nativeQuery = true)
	List<String> findAllSpaceIdByHubScene(Long hubSceneId);
	
	@Query(value="select Max(h.hub_space_id) FROM hub_space h" ,nativeQuery = true)
	Long findMaxId();

	List<HubSpace> findAllByActive(String active);

	HubSpace findByHubSid(String hubSid);

	List<HubSpace> findAllByActiveAndHubScene(String active, HubScene hubScene);

	List<HubSpace> findAllByActiveAndHubSceneAndPublishType(String string, HubScene hubScene, String string2);

}
