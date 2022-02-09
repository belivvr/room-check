package com.comingm.roomcheck.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.comingm.roomcheck.entities.HubScene;
import com.comingm.roomcheck.entities.Resource;

;

public interface HubSceneRepository extends JpaRepository<HubScene,Long> {

	HubScene findByResourceSid(String string);

	Page<Resource> findAll(Specification<Resource> searchWith, Pageable pageable);

	List<HubScene> findAllByRoomAutoScaling(String string);

	List<HubScene> findAllByActive(String active);

}
