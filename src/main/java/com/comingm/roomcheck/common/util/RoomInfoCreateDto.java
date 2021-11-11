package com.comingm.roomcheck.common.util;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="RoomInfoCreateDto")
public class RoomInfoCreateDto {

	@ApiModelProperty(position=1,value="방 이름")
	private String name;
	
	@ApiModelProperty(position=2,value="장면 id")
	private String sceneId;
	

	
}
