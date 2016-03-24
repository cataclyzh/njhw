package com.cosmosource.app.threedimensional.service;

import com.cosmosource.base.service.BaseManager;

public class RoomManager extends BaseManager 
{
   //根据房间编号获取房间ID
   public String getRoomIdByRoomNum(String keyword)
   {
	   return (String)sqlDao.getSqlMapClientTemplate().queryForObject("ThreeDimensionalSQL.SELECT_ROOM_ID_BY_ROOMNUM", keyword);
   }
   
}
