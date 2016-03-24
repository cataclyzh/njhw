package com.cosmosource.app.energyint.model;

/**
 * 自己所在楼层信息 <一句话功能简述> <功能详细描述>
 * 
 * @author Administrator
 * @version [版本号, 2013-7-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class EiMyselfInfo
{
    // 总人数
    private int peopleCount;
    
    // 我所在楼层的人数
    private int floorPeopleCount;
    
    // 我所在房间的人数
    private int roomPeopleCount;
    
    // 我所在的楼
    private String seat;
    
    // 我所在的楼层
    private String floor;
    
    // 我所在的房间
    private String room;
    
    //大厦总房间数
    private int buildingAllRooms;
    
	//获取当前楼层的总房间数
	private int floorRoomsCount;
    
    // 大厦总面积 平方米
    private int buildingArea;
    
    public int getPeopleCount()
    {
        return peopleCount;
    }
    
    public void setPeopleCount(int peopleCount)
    {
        this.peopleCount = peopleCount;
    }
    
    public int getFloorPeopleCount()
    {
        return floorPeopleCount;
    }
    
    public void setFloorPeopleCount(int floorPeopleCount)
    {
        this.floorPeopleCount = floorPeopleCount;
    }
    
    public int getRoomPeopleCount()
    {
        return roomPeopleCount;
    }
    
    public void setRoomPeopleCount(int roomPeopleCount)
    {
        this.roomPeopleCount = roomPeopleCount;
    }
    
    public String getSeat()
    {
        return seat;
    }
    
    public void setSeat(String seat)
    {
        this.seat = seat;
    }
    
    public String getFloor()
    {
        return floor;
    }
    
    public void setFloor(String floor)
    {
        this.floor = floor;
    }
    
    public String getRoom()
    {
        return room;
    }
    
    public void setRoom(String room)
    {
        this.room = room;
    }
    
    public int getBuildingArea()
    {
        return buildingArea;
    }
    
    public void setBuildingArea(int buildingArea)
    {
        this.buildingArea = buildingArea;
    }

	public int getBuildingAllRooms() {
		return buildingAllRooms;
	}

	public void setBuildingAllRooms(int buildingAllRooms) {
		this.buildingAllRooms = buildingAllRooms;
	}

	public int getFloorRoomsCount() {
		return floorRoomsCount;
	}

	public void setFloorRoomsCount(int floorRoomsCount) {
		this.floorRoomsCount = floorRoomsCount;
	}
    
}
