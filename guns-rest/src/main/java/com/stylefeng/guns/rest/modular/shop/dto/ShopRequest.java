package com.stylefeng.guns.rest.modular.shop.dto;

/**
 * 门店
 * @author 54476
 *
 */
public class ShopRequest {

	private Long proId;
	
	private Long cityId;
	
	private double lng;
	
	private double lat;

	public Long getProId() {
		return proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
}
