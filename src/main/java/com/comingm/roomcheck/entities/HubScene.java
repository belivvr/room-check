package com.comingm.roomcheck.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@DiscriminatorValue("scene")
@Table( name="hub_scene")
public class HubScene extends Resource implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1080343704784209957L;

	
	//공간 목적​
	@Column(name="category",columnDefinition = "varchar(100) NOT NULL")
	private String category;
	
	
	//썸네일 패스​
	@Column(name="thumb_path",columnDefinition = "varchar(255) NULL")
	private String thumbPath; 
    
    //최소 운영채널
    @Column(name="min_channel",columnDefinition = "int8 NOT NULL DEFAULT 1")
    private int minChannel;
    
    //최대 운영채널
    @Column(name="max_channel",columnDefinition = "int8 NOT NULL DEFAULT 10")
    private int maxChannel;
    
    //채널 증가 가중치
    @Column(name="channel_increase_weight",columnDefinition = "float8 NOT NULL DEFAULT 0.7")
    private float channelIncreaseWeight;
    
    //채널 감소 가중치
    @Column(name="channel_decrease_weight",columnDefinition = "float8 NOT NULL DEFAULT 0.3")
    private float channelDecreaseWeight;
    
    //채널 큐 최대값
    @Column(name="channel_queue_max",columnDefinition = "int8 NOT NULL DEFAULT 10000")
    private int channelQueueMax;
    
    //방 자동 증감
    @Column(name="room_auto_scaling",columnDefinition = "bpchar NOT NULL")
    private String roomAutoScaling;
			

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getMinChannel() {
		return minChannel;
	}

	public void setMinChannel(int minChannel) {
		this.minChannel = minChannel;
	}

	public int getMaxChannel() {
		return maxChannel;
	}

	public void setMaxChannel(int maxChannel) {
		this.maxChannel = maxChannel;
	}

	public float getChannelIncreaseWeight() {
		return channelIncreaseWeight;
	}

	public void setChannelIncreaseWeight(float channelIncreaseWeight) {
		this.channelIncreaseWeight = channelIncreaseWeight;
	}

	public float getChannelDecreaseWeight() {
		return channelDecreaseWeight;
	}

	public void setChannelDecreaseWeight(float channelDecreaseWeight) {
		this.channelDecreaseWeight = channelDecreaseWeight;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getChannelQueueMax() {
		return channelQueueMax;
	}

	public void setChannelQueueMax(int channelQueueMax) {
		this.channelQueueMax = channelQueueMax;
	}

	public String getRoomAutoScaling() {
		return roomAutoScaling;
	}

	public void setRoomAutoScaling(String roomAutoScaling) {
		this.roomAutoScaling = roomAutoScaling;
	}
	
	
	
	
}
