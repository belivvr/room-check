package com.comingm.roomcheck.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comingm.roomcheck.entities.ApiKey;




@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
	public ApiKey findByApiKey(String apiKey); 
}
