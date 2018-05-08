package com.mooc.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mooc.house.common.model.Community;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.HouseUser;
import com.mooc.house.common.model.UserMsg;
import com.mooc.house.common.page.PageParams;

@Mapper
public interface HouseMapper {

    public List<House>  selectPageHouses(@Param("house")House house,@Param("pageParams")PageParams pageParams);
    
    public Long selectPageCount(@Param("house") House query);

	public List<Community> selectCommunity(Community community);

	public int insertUserMsg(UserMsg userMsg);
	
	public HouseUser selectSaleHouseUser(@Param("id") Long houseId);

	public int insert(House house);
	
	
}
