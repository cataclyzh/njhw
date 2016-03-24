(function () {
    $.extend($.Xcds, {
        Device: function (option) {
            DefaultOptions = {
                //设备选中事件
                OnDeviceSelected: null,
                //取消设备选中事件
                UnDeviceSelected: null,
                //是否为线框图
                IsFrameMap: false,
                //选中设备的颜色
                SelectedColor: "255,0,0,20",
                //未选中设备的颜色
                UnSelectedColor: "255,180,0,20",
                //设备的透明度
                DeviceTranparent: "0.8",
                //设备类型
                DeviceType: "",
                //当前的Map
                CurrentMap: null,
                //设备是否要Cache，1：是，0：否
                DeviceCached: false,
                //默认视图坐标
                DefaultView: '118.430253,119.490899,32.004231,-244.077438,-14.733635,366.852997',

                //电梯的初始位置坐标
                PositionArray: ["118.4330307631,32.0024485584,9.697828", "118.4330059393,32.0024441632,43.603600", "118.4330145620,32.0023954807,72.860054"
                                    , "118.4330393858,32.0023998759,30.854805", "118.4335380587,32.0020933838,26.722675", "118.4335424538,32.0020685601,47.845943"
                                    , "118.4334937713,32.0020599374,64.594315", "118.4334893761,32.0020847611,0.000002", "118.4338446412,32.0025920184,4.924785"
                                    , "118.4338694650,32.0025964136,60.268898", "118.4338780876,32.0025477311,47.483833", "118.4338532639,32.0025433359,22.666307"
                                    , "118.4333458672,32.0028986345,68.454811", "118.4333414720,32.0029234583,9.682951", "118.4333901545,32.0029320809,35.222733"
                                    , "118.4333945497,32.0029072572,52.122826"]
            };

            $.extend(DefaultOptions, option);
            var DeviceInstance = new Object();
            Init_Property();
            Init_Method();

            return DeviceInstance;

            function Init_Property() {
                $.extend(DeviceInstance, {
                    //设备选中事件
                    OnDeviceSelected: DefaultOptions.OnDeviceSelected,
                    //取消设备选中事件
                    UnDeviceSelected: DefaultOptions.UnDeviceSelected,
                    //是否为线框图
                    IsFrameMap: DefaultOptions.IsFrameMap,
                    //选中设备的颜色
                    SelectedColor: DefaultOptions.SelectedColor,
                    //未选中设备的颜色
                    UnSelectedColor: DefaultOptions.UnSelectedColor,
                    //设备的透明度
                    DeviceTranparent: DefaultOptions.DeviceTranparent,
                    //设备类型
                    DeviceType: DefaultOptions.DeviceType,
                    //当前的Map
                    CurrentMap: DefaultOptions.CurrentMap,
                    //设备是否要Cache，1：是，0：否
                    DeviceCached: DefaultOptions.DeviceCached,
                    //默认视图坐标
                    DefaultView: DefaultOptions.DefaultView,
                    //电梯的初始位置坐标
                    PositionArray: DefaultOptions.PositionArray,

                    //设备的cache
                    DeviceCache: new $.Xcds.MapCache(),
                    //电梯的cache
                    LiftCache: new $.Xcds.MapCache(),
                    //访客的cache
                    VisitorCache: new $.Xcds.MapCache(),
                    //已选中设备的cache
                    SelectedDeviceCache: new $.Xcds.MapCache(),
                    //巡更的cache
                    PatrolCache: new $.Xcds.MapCache(),
                    //当前楼层
                    CurrentFloorId: 0,
                    //当前初始化函数名
                    CurrentInitFunctionName: "",
                    //当前弹出窗口的ID
                    CurrentWindowId: 0,
                    //当前提示
                    CurrentToolTip: null,
                    //人员轨迹点
                    DevicePoint: null,
                    //人员轨迹线
                    DeviceLine: null,
                    //正常设备所在的房间
                    NormalDeviceRoom: new Array(),
                    //异常设备所在的房间
                    BadDeviceRoom: new Array(),
                    //当前选中的设备
                    CurrentOverDeviceId: null,
                    //存放设备缓存的值数组
                    CacheValue: new Array(3),
                    //设备所在房间的ID
                    BelongRoomIDs: new Array(),
                    VisitorLineModel: null,
                    PatrolLineModel: null,
                    //当前鼠标的位置信息
                    CurrentMouseOption: null,
                    //初始化方法的锁
                    Locked: false,
                    //桌子的缓存
                    DeskCache: new Array()
                });
            }

            function Init_Method() {
                $.extend(DeviceInstance, {
                    /************************************以下是公用方法********************************************/
                    Test: function () {
                        $.Xcds.FlyToFloorSide($.Xcds.map, "a");
                    },
                    //设备初始化
                    InitDevice: function () {
                        if (DeviceInstance.IsFrameMap) {
                            $.Xcds.showFrameModel();
                            DeviceInstance.CurrentMap = $.Xcds.FrameMap;
                            DeviceInstance.CurrentMap.InitActiveLayer($.Xcds.DeviceLayerName);
                            DeviceInstance.BindOnRDbClickEvent();
                        } else {
                            $.Xcds.showRealModel();
                            DeviceInstance.CurrentMap = $.Xcds.map;
                        }
                        $.Xcds.bc.ShowMapEnlarge($.Xcds.map, 40);
                        $.Xcds.bc.ShowMapEnlarge($.Xcds.FrameMap, 80);
                    },
                    //线框场景重置
                    ResetFrameMap: function () {
                        DeviceInstance.ClearAllExistLift();
                        DeviceInstance.ClearAllExistDevice();
                        DeviceInstance.ClearAllExistRoom();
                        DeviceInstance.ClearAllExistVisitor();
                        DeviceInstance.ClearAllExistPatrol();
                        $.Xcds.FrameMap.FlytoPosition(118.431813782388051, 122.215919844635891, 32.002359788987391, -273.904846191406251, -26.8977985382080081);
                    },
                    //楼层切换
                    SwitchFloor: function (FloorId, callback) {
                        $.Xcds.bc.ShowFloorByFloorIdWithScence(FloorId, function (defaultView) {
                            DeviceInstance.DefaultView = defaultView;
                            $.Xcds.map.InitActiveLayer($.Xcds.DeviceLayerName);
                            $.Xcds.map.SetLayerVisible($.Xcds.DeviceLayerName, 1);
                            if ($.isFunction(callback)) {
                                callback();
                            }
                        });
                    },
                    //返回默认视角
                    BindOnRDbClickEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnRDbClick", "global", function (options) {
                            $.Xcds.FrameMap.FlytoPosition(118.43181378238805, 122.21591984463589, 32.00235978898739, -273.90484619140625, -26.897798538208008);
                        });
                    },
                    //显示线框模型
                    ShowFrameMap: function () {
                        $.Xcds.showFrameModel();
                        DeviceInstance.IsFrameMap = true;
                    },
                    //显示真实模型
                    ShowRealMap: function () {
                        $.Xcds.showRealModel();
                        DeviceInstance.IsFrameMap = false;
                    },

                    /************************************以上是公用方法********************************************/


                    /************************************以下设备相关方法********************************************/
                    //初始化设备list
                    InitDeviceList: function (FloorId, RoomId, DeviceType, InFrameMap) {
                        if (!$.Xcds.Locked) {
                            $.Xcds.Locked = true;
                            setTimeout(function () {
                                DeviceInstance.CurrentInitFunctionName = "InitDeviceList";
                                //                        $.Xcds.hideAllModel();
                                DeviceInstance.CurrentFloorId = FloorId;
                                DeviceInstance.ResetFrameMap();
                                $.Xcds.bc.GetDeviceList(FloorId, RoomId, DeviceType, function (response) {
                                    DeviceInstance.PutDeviceToCache(response, function () {
                                        //                                DeviceInstance.ShowDeviceByCache($.Xcds.map, function () {
                                        //                                    DeviceInstance.BindDeviceSelectEvent($.Xcds.map);
                                        //                                    $.Xcds.map.Redraw();
                                        //                                });
                                        DeviceInstance.ShowDeviceByCache($.Xcds.FrameMap, function () {
                                            DeviceInstance.BindDeviceOnMouseMoveEvent($.Xcds.FrameMap);
                                            DeviceInstance.BindDeviceOnLClickUpEvent($.Xcds.FrameMap);
                                            DeviceInstance.BindDeviceOnLClickDownEvent($.Xcds.FrameMap);
                                            DeviceInstance.BindDeviceOnRClickDownEvent($.Xcds.FrameMap);
                                            DeviceInstance.BindDeviceOnRClickUpEvent($.Xcds.FrameMap);
                                            DeviceInstance.BindDeviceOnMouseMoveEvent($.Xcds.map);
                                            DeviceInstance.BindDeviceOnLClickUpEvent($.Xcds.map);
                                            DeviceInstance.BindDeviceOnLClickDownEvent($.Xcds.map);
                                            DeviceInstance.BindDeviceOnRClickDownEvent($.Xcds.map);
                                            DeviceInstance.BindDeviceOnRClickUpEvent($.Xcds.map);
                                            $.Xcds.FrameMap.Redraw();
                                            $.Xcds.Locked = false;
                                        });
                                        if (InFrameMap) {
                                            DeviceInstance.ShowFrameMap();
                                        } else {
                                            DeviceInstance.ShowRealMap();
                                        }
                                    });
                                }, function (info) {

                                });
                            }, 500);
                        }
                    },
                    //初始化异常设备list
                    InitBadDeviceList: function (DeviceIds, InFrameMap) {
                        if (!$.Xcds.Locked) {
                            $.Xcds.Locked = true;
                            setTimeout(function () {
                                DeviceInstance.CurrentInitFunctionName = "InitBadDeviceList";
                                //                        $.Xcds.hideAllModel();
                                DeviceInstance.ResetFrameMap();
                                $.Xcds.bc.GetDeviceList(0, 0, 16, function (response) {
                                    DeviceInstance.PutBadDeviceToCache(response, function () {
                                        //                                DeviceInstance.ShowBadDeviceByCache($.Xcds.FrameMap, function () {
                                        //                                    DeviceInstance.BindDeviceSelectEvent($.Xcds.FrameMap);
                                        //                                    $.Xcds.FrameMap.Redraw();
                                        //                                });
                                        DeviceInstance.ShowBadDeviceByCache($.Xcds.map, function () {
                                            DeviceInstance.BindDeviceSelectEvent($.Xcds.map);
                                            DeviceInstance.BindDeviceSelectEvent($.Xcds.FrameMap);
                                            $.Xcds.map.Redraw();
                                            $.Xcds.Locked = false;
                                        });
                                        if (InFrameMap) {
                                            DeviceInstance.ShowFrameMap();
                                        } else {
                                            DeviceInstance.ShowRealMap();
                                        }
                                    });
                                }, function (info) {

                                });
                            }, 500);
                        }

                    },
                    SetModelFlash: function (MapObj, ModelId, FlashTime) {
                        MapObj.SetModelInstanceFlash($.Xcds.DeviceLayerName, ModelId, 1);
                        setTimeout(function () {
                            MapObj.SetModelInstanceFlash($.Xcds.DeviceLayerName, ModelId, 0);
                        }, FlashTime);
                    },
                    //飞回默认视角
                    FlyToDefaultPosition: function () {
                        //var ViewArray = DeviceInstance.DefaultView.split(",");
                        //$.Xcds.map.FlytoPosition(ViewArray[0], ViewArray[1], ViewArray[2], ViewArray[3], ViewArray[4]);
                        DeviceInstance.CloseDeviceDialog();
                        DeviceInstance.BindSesetViewEvent(false);
                    },
                    //飞向某个设备
                    FlyToDevice: function (DeviceId) {
                        DeviceInstance.ShowRealMap();
                        for (var i = 0; i < DeviceInstance.DeviceCache.size(); i++) {
                            var RealModel = DeviceInstance.DeviceCache.get(DeviceInstance.DeviceCache.keys[i])[2];
                            $.Xcds.map.DeleteModelInstance($.Xcds.DeviceLayerName, RealModel.GetStrID());
                            //                            $.Xcds.map.SetModelInstanceVisible($.Xcds.DeviceLayerName, RealModel.GetStrID(), 1);
                            if (DeviceInstance.DeviceCache.keys[i] == DeviceId) {
                                $.Xcds.map.AppendModelInstance($.Xcds.DeviceLayerName, RealModel);
                                //                                $.Xcds.map.SetModelInstanceVisible($.Xcds.DeviceLayerName, RealModel.GetStrID(), 0);                                
                                var CurrentDevice = DeviceInstance.DeviceCache.get(DeviceId)[0];
                                $.Xcds.map.FlytoPosition(CurrentDevice.view.x, CurrentDevice.view.y, CurrentDevice.view.z, CurrentDevice.view.h, CurrentDevice.view.v, 0);
                                //                                $.Xcds.map.FlytoPosition(CurrentDevice.view.x, CurrentDevice.view.y, CurrentDevice.view.z, $.Xcds.map.GetRoleAngle().GetX(), CurrentDevice.view.v, 0);
                                DeviceInstance.BindSesetViewEvent(true);
                                DeviceInstance.SetModelFlash($.Xcds.map, RealModel.GetStrID(), 5000);
                            }
                        }
                        for (var i = 0; i < DeviceInstance.DeskCache.length; i++) {
                            $.Xcds.map.DeleteModelInstance("desk", DeviceInstance.DeskCache[i].GetStrID());
                        }
                        DeviceInstance.DeskCache.length = 0;
                        $.Xcds.map.Refresh();
                    },
                    //清除所有设备的房间
                    ClearAllExistRoom: function () {
                        for (var j = 0; j < DeviceInstance.BelongRoomIDs.length; j++) {
                            $.Xcds.FrameMap.DeleteModelInstance("default", DeviceInstance.BelongRoomIDs[j]);
                        }
                        DeviceInstance.BelongRoomIDs.length = 0;
                        $.Xcds.ClearAllRoof();
                    },
                    //显示异常设备所在的房间
                    ShowBadDeviceBelongRoom: function (RoomIds) {
                        $.Xcds.FrameMap.InitActiveLayer($.Xcds.DeviceLayerName);
                        var RoomId = 0;
                        for (var j = 0; j < RoomIds.length; j++) {
                            RoomId = RoomId + "," + RoomIds[j];
                        }
                        $.Xcds.bc.FindRoomListByIds(RoomId, function (response) {
                            var RoomData = response.data;
                            for (var i = 0; i < RoomData.length; i++) {
                                var position = new Array();
                                for (var k = 0; k < RoomData[i].points.length; k++) {
                                    position.push(RoomData[i].points[k].x + "," + RoomData[i].points[k].y + "," + RoomData[i].points[k].z);
                                }
                                $.Xcds.FrameMap.m_AppendVector3DPoly({
                                    coords: position,
                                    height: RoomData[i].height,
                                    strid: RoomData[i].id,
                                    ambient: "246,97,55,20",
                                    diffuse: "188,120,0,255",
                                    specular: "255,255,255,180"
                                });
                                $.Xcds.FrameMap.SetModelInstanceTransparent("default", RoomData[i].id, 1, 0.2);
                            }
                        }, function (info) { });
                    },
                    //显示正常设备所在的房间
                    ShowNormalDeviceBelongRoom: function (RoomIds) {
                        $.Xcds.FrameMap.InitActiveLayer($.Xcds.DeviceLayerName);
                        var RoomId = 0;
                        for (var j = 0; j < RoomIds.length; j++) {
                            RoomId = RoomId + "," + RoomIds[j];
                        }
                        $.Xcds.bc.FindRoomListByIds(RoomId, function (response) {
                            var RoomData = response.data;
                            for (var i = 0; i < RoomData.length; i++) {
                                var position = new Array();
                                for (var k = 0; k < RoomData[i].points.length; k++) {
                                    position.push(RoomData[i].points[k].x + "," + RoomData[i].points[k].y + "," + RoomData[i].points[k].z);
                                }
                                $.Xcds.FrameMap.m_AppendVector3DPoly({
                                    coords: position,
                                    height: RoomData[i].height,
                                    strid: RoomData[i].id,
                                    ambient: "255,222,173,20",
                                    diffuse: "255,222,173,255",
                                    specular: "255,222,173,180"
                                });
                                $.Xcds.FrameMap.SetModelInstanceTransparent("default", RoomData[i].id, 1, 0.2);
                            }
                        }, function (info) { });
                    },
                    //根据缓存显示异常设备
                    ShowBadDeviceByCache: function (MapObj, callback) {
                        MapObj.InitActiveLayer($.Xcds.DeviceLayerName);
                        //                        MapObj.SetLayerVisible($.Xcds.DeviceLayerName, 0);
                        for (var i = 0; i < DeviceInstance.DeviceCache.size(); i++) {
                            var CurrentDeviceModel = DeviceInstance.DeviceCache.get(DeviceInstance.DeviceCache.keys[i])[1];
                            var RealDeviceModel = DeviceInstance.DeviceCache.get(DeviceInstance.DeviceCache.keys[i])[2];
                            var CurrentDeviceJson = DeviceInstance.DeviceCache.get(DeviceInstance.DeviceCache.keys[i])[0];
                            if (MapObj == $.Xcds.FrameMap && CurrentDeviceJson.room_id != 0) {
                                DeviceInstance.BelongRoomIDs.push(CurrentDeviceJson.room_id);
                            }
                            if (MapObj == $.Xcds.FrameMap) {
                                MapObj.AppendModelInstance($.Xcds.DeviceLayerName, CurrentDeviceModel);
                            } else {
                                MapObj.AppendModelInstance($.Xcds.DeviceLayerName, RealDeviceModel);
                            }
                        }
                        if (MapObj == $.Xcds.FrameMap) {
                            if (DeviceInstance.BelongRoomIDs.length > 0) {
                                DeviceInstance.ShowBadDeviceBelongRoom(DeviceInstance.BelongRoomIDs);
                            }
                        }
                        DeviceInstance.DeviceCached = true;
                        //                        MapObj.SetLayerVisible($.Xcds.DeviceLayerName, 1);
                        MapObj.Refresh();
                        callback();
                    },

                    //根据缓存显示设备
                    ShowDeviceByCache: function (MapObj, callback) {
                        MapObj.InitActiveLayer($.Xcds.DeviceLayerName);
                        MapObj.SetLayerVisible($.Xcds.DeviceLayerName, 0);
                        var BadDeviceBelongRoomIds = new Array();
                        var NormalDeviceBelongRoomIds = new Array()
                        for (var i = 0; i < DeviceInstance.DeviceCache.size(); i++) {
                            var CurrentDeviceModel = DeviceInstance.DeviceCache.get(DeviceInstance.DeviceCache.keys[i])[1];
                            var RealDeviceModel = DeviceInstance.DeviceCache.get(DeviceInstance.DeviceCache.keys[i])[2];
                            var CurrentDeviceJson = DeviceInstance.DeviceCache.get(DeviceInstance.DeviceCache.keys[i])[0];
                            if (MapObj == $.Xcds.FrameMap && CurrentDeviceJson.room_id != 0) {
                                if (CurrentDeviceJson.status) {
                                    NormalDeviceBelongRoomIds.push(CurrentDeviceJson.room_id);
                                } else {
                                    BadDeviceBelongRoomIds.push(CurrentDeviceJson.room_id);
                                }
                                DeviceInstance.BelongRoomIDs.push(CurrentDeviceJson.room_id);
                            }
                            if (MapObj == $.Xcds.FrameMap) {

                                //                                MapObj.AppendVectorModel($.Xcds.DeviceLayerName, CurrentDeviceModel);
                                MapObj.AppendModelInstance($.Xcds.DeviceLayerName, CurrentDeviceModel);
                                $.Xcds.FrameMap.SetModelInstanceTransparent($.Xcds.DeviceLayerName, CurrentDeviceModel.GetStrID(), 1, 0.99);
                            } else {
                                MapObj.AppendModelInstance($.Xcds.DeviceLayerName, RealDeviceModel);
                            }
                        }
                        if (MapObj == $.Xcds.FrameMap) {
                            if (BadDeviceBelongRoomIds.length > 0) {
                                DeviceInstance.ShowBadDeviceBelongRoom(BadDeviceBelongRoomIds);
                            }
                            if (NormalDeviceBelongRoomIds.length > 0) {
                                DeviceInstance.ShowNormalDeviceBelongRoom(NormalDeviceBelongRoomIds);
                            }
                        }
                        var TextArray = new Array();
                        TextArray.push(",<font color='red'>红色</font>表示该设备异常，<font color='yellow'>黄色</font>表示正常");
                        TextArray.push(",鼠标左键单击某个设备，定位并弹窗");
                        TextArray.push(",鼠标右键双击，关闭弹窗并返回线框图");
                        $.Xcds.ShowNotice(TextArray);
                        DeviceInstance.DeviceCached = true;
                        MapObj.SetLayerVisible($.Xcds.DeviceLayerName, 1);
                        MapObj.Refresh();
                        callback();
                    },
                    //把设备放入缓存
                    PutDeviceToCache: function (response, callback) {
                        var roomId = new Array();
                        for (var i = 0; i < response.data.devices.length; i++) {
                            var CurrentDevice = response.data.devices[i];
                            var position = CurrentDevice.coord.x + "," + CurrentDevice.coord.y + "," + CurrentDevice.coord.z;
                            var object3D;
                            if (CurrentDevice.status) {

                                //                                object3D = new Qmap.Object3D.QMap3DVectorSphere({
                                //                                    strid: "Device_" + CurrentDevice.id,
                                //                                    vectorsize: "0.75",
                                //                                    adposition: position,
                                //                                    groupid: "1",
                                //                                    level: "1",
                                //                                    modelinstancename: "球体",
                                //                                    linkinfo: "VectorSphere",
                                //                                    ambient: "22,79,248,255",
                                //                                    specular: "22,79,248,255",
                                //                                    //shininess: "255",
                                //                                    diffuse: "22,79,248,255"
                                //                                });
                                object3D = new Qmap.Object3D.QMap3DModelInstance({
                                    layer: $.Xcds.DeviceLayerName,
                                    model: "huangse.qta",
                                    strid: "Device_" + CurrentDevice.id,
                                    groupid: "0",
                                    level: "0",
                                    modelinstancename: "模型",
                                    linkinfo: "ModelInstance",
                                    visible: "1",
                                    afrotate: CurrentDevice.di_rotate_angle.x + "," + CurrentDevice.di_rotate_angle.z + "," + CurrentDevice.di_rotate_angle.y,
                                    rotateunit: "1",
                                    transunit: "0",
                                    transrotateunit: "0",
                                    adposition: position,
                                    afsize: "2,2,2"
                                });
                            } else {
                                object3D = new Qmap.Object3D.QMap3DModelInstance({
                                    layer: $.Xcds.DeviceLayerName,
                                    model: "hongse.qta",
                                    strid: "Device_" + CurrentDevice.id,
                                    groupid: "0",
                                    level: "0",
                                    modelinstancename: "模型",
                                    linkinfo: "ModelInstance",
                                    visible: "1",
                                    afrotate: CurrentDevice.di_rotate_angle.x + "," + CurrentDevice.di_rotate_angle.z + "," + CurrentDevice.di_rotate_angle.y,
                                    rotateunit: "1",
                                    transunit: "0",
                                    transrotateunit: "0",
                                    adposition: position,
                                    afsize: "2,2,2"
                                });
                            }
                            var RealModel = new Qmap.Object3D.QMap3DModelInstance({
                                layer: $.Xcds.DeviceLayerName,
                                model: CurrentDevice.di_file_name,
                                strid: "Device_" + CurrentDevice.id,
                                groupid: "0",
                                level: "0",
                                modelinstancename: "模型",
                                linkinfo: "ModelInstance",
                                visible: "1",
                                afrotate: CurrentDevice.di_rotate_angle.x + "," + CurrentDevice.di_rotate_angle.z + "," + CurrentDevice.di_rotate_angle.y,
                                rotateunit: "1",
                                transunit: "0",
                                transrotateunit: "0",
                                adposition: position
                            });
                            var CacheValue = new Array(3);
                            CacheValue[0] = response.data.devices[i];
                            CacheValue[1] = object3D;
                            CacheValue[2] = RealModel;
                            DeviceInstance.DeviceCache.set(CurrentDevice.id, CacheValue);
                        }
                        DeviceInstance.DeviceCached = true;
                        callback();
                    },
                    //把异常设备放入缓存
                    PutBadDeviceToCache: function (response, callback) {
                        for (var i = 0; i < response.data.devices.length; i++) {
                            var CurrentDevice = response.data.devices[i];
                            var position = CurrentDevice.coord.x + "," + CurrentDevice.coord.y + "," + CurrentDevice.coord.z;

                            var object3D = new Qmap.Object3D.QMap3DModelInstance({
                                layer: $.Xcds.DeviceLayerName,
                                model: "hongse.qta",
                                strid: "Device_" + CurrentDevice.id,
                                groupid: "0",
                                level: "0",
                                modelinstancename: "模型",
                                linkinfo: "ModelInstance",
                                visible: "1",
                                afrotate: CurrentDevice.di_rotate_angle.x + "," + CurrentDevice.di_rotate_angle.z + "," + CurrentDevice.di_rotate_angle.y,
                                rotateunit: "1",
                                transunit: "0",
                                transrotateunit: "0",
                                adposition: position,
                                afsize: "3,3,3"
                            });
                            var RealModel = new Qmap.Object3D.QMap3DModelInstance({
                                layer: $.Xcds.DeviceLayerName,
                                model: CurrentDevice.di_file_name,
                                strid: "Device_" + CurrentDevice.id,
                                groupid: "0",
                                level: "0",
                                modelinstancename: "模型",
                                linkinfo: "ModelInstance",
                                visible: "1",
                                afrotate: CurrentDevice.di_rotate_angle.x + "," + CurrentDevice.di_rotate_angle.z + "," + CurrentDevice.di_rotate_angle.y,
                                rotateunit: "1",
                                transunit: "0",
                                transrotateunit: "0",
                                adposition: position
                            });
                            var CacheValue = new Array(3);
                            CacheValue[0] = response.data.devices[i];
                            CacheValue[1] = object3D;
                            CacheValue[2] = RealModel;
                            DeviceInstance.DeviceCache.set(CurrentDevice.id, CacheValue);
                        }
                        DeviceInstance.DeviceCached = true;
                        callback();
                    },
                    //显示设备弹窗
                    ShowDeviceDialog: function (DeviceId, url) {
                        for (var i = 0; i < DeviceInstance.DeviceCache.size(); i++) {
                            if (DeviceInstance.DeviceCache.keys[i] == DeviceId) {
                                var CurrentDeviceJson = DeviceInstance.DeviceCache.get(DeviceId)[0];
                                $.Xcds.bc.ShowWinDialog(DeviceInstance.CurrentMap, $.Xcds.window.Window[CurrentDeviceJson.window_id], CurrentDeviceJson.window_position, url);
                                DeviceInstance.CurrentWindowId = $.Xcds.window.Window[CurrentDeviceJson.window_id].nID;
                                break;
                            }
                        }
                    },
                    //关闭当前弹窗
                    CloseDeviceDialog: (function () {
                        DeviceInstance.CurrentMap.CloseUserWndByID(DeviceInstance.CurrentWindowId);
                    }),
                    //绑定右键重置视角
                    BindSesetViewEvent: function (Enable) {
                        if (Enable) {
                            $.Xcds.map.doEvent("OnRDbClick", DeviceInstance.CurrentInitFunctionName, function (options) {
                                DeviceInstance.FlyToDefaultPosition();
                                DeviceInstance.ShowFrameMap();
                            })
                        }
                        else {
                            $.Xcds.map.undoEvent("OnRDbClick", DeviceInstance.CurrentInitFunctionName)
                        }
                    },
                    //从缓存中得到模型
                    GetModelInCache: function (DeviceId) {
                        for (var i = 0; i < DeviceInstance.DeviceCache.size(); i++) {
                            if (DeviceInstance.DeviceCache.keys[i] == DeviceId) {
                                var DeviceJson = DeviceInstance.DeviceCache.get(DeviceId)[1];
                                return DeviceJson;
                            }
                        }
                    },
                    //从缓存中得到JSON数据
                    GetJsonInCache: function (DeviceId) {
                        for (var i = 0; i < DeviceInstance.DeviceCache.size(); i++) {
                            if (DeviceInstance.DeviceCache.keys[i] == DeviceId) {
                                var DeviceCache = DeviceInstance.DeviceCache.get(DeviceId)[0];
                                return DeviceCache;
                            }
                        }
                    },
                    //绑定选择事件
                    BindDeviceOnMouseMoveEvent: function (MapObj) {
                        var CurrentDeviceJson;
                        var CurrentDeviceModel;
                        var SelectedDeviceId;
                        //设备的onMouseOver事件,显示房间号楼层
                        MapObj.doEvent("OnMouseMove", DeviceInstance.CurrentInitFunctionName, function (options) {
                            $.Xcds.FixToolBar(options);
                            if ($.Xcds.CurrentMouseOption == null) {
                                $.Xcds.CurrentMouseOption = options;
                            }
                            if ($.Xcds.CurrentMouseOption.logX != options.logX && $.Xcds.CurrentMouseOption.logY != options.logY) {
                                var CurrentDeviceBeenOver = MapObj.SelectModel(options.logX, options.logY, options.logX, options.logY);
                                if (CurrentDeviceBeenOver != "" && CurrentDeviceBeenOver.split(",")[0].split("_")[1] != "") {
                                    SelectedDeviceId = CurrentDeviceBeenOver.split(",")[0].split("_")[1];
                                    CurrentDeviceJson = DeviceInstance.GetJsonInCache(SelectedDeviceId);
                                    //CurrentDeviceModel = DeviceInstance.GetModelInCache(SelectedDeviceId);
                                    if (DeviceInstance.CurrentOverDeviceId != SelectedDeviceId) {
                                        var arr = new Array();
                                        //arr.push("设备名称：," + CurrentDeviceJson.bl_name);
                                        arr.push("设备编号：,A" + CurrentDeviceJson.id);
                                        if (CurrentDeviceJson.status) {
                                            arr.push("运行状态：,正常");
                                        } else {
                                            arr.push("运行状态：,<font color=\"red\">异常</font> ");
                                        }
                                        arr.push("所在楼层：," + CurrentDeviceJson.floor_name);
                                        arr.push("安装日期：,2012年12月15日");
                                        arr.push("维修日期：," + (new Date()).toLocaleDateString());
                                        //arr.push("所在房间：," + CurrentDeviceJson.bl_room_name);
                                        $.Xcds.ShowToolTip(options, arr);
                                        $.Xcds.ShowCurrentFloorSign(CurrentDeviceJson.floor_num);
                                        DeviceInstance.CurrentOverDeviceId = SelectedDeviceId;
                                    }
                                } else {
                                    DeviceInstance.CurrentOverDeviceId = 0;
                                    $.Xcds.HideToolTip();
                                    $.Xcds.HideFloorSign();
                                    $.Xcds.FrameMap.InitActiveLayer($.Xcds.DeviceLayerName);
                                }
                            }
                            $.Xcds.CurrentMouseOption = options;
                        });
                    },
                    //绑定鼠标左键弹起事件
                    BindDeviceOnLClickUpEvent: function (MapObj) {
                        var CurrentDeviceJson;
                        var CurrentDeviceModel;
                        var SelectedDeviceId;
                        MapObj.doEvent("OnLClickUp", DeviceInstance.CurrentInitFunctionName, function (options) {
                            $.Xcds.FixNotice();
                            var CurrentDeviceSelected = MapObj.SelectModel(options.logX, options.logY, options.logX, options.logY);
                            if (CurrentDeviceSelected != "" && CurrentDeviceSelected.split(",")[0].split("_")[1] != "") {
                                if (DeviceInstance.CurrentSelectedDevice != null) {
                                    $.Xcds.FrameMap.SetModelInstanceFlash($.Xcds.DeviceLayerName, DeviceInstance.CurrentSelectedDevice.GetStrID(), 0);
                                    $.Xcds.map.SetModelInstanceFlash($.Xcds.DeviceLayerName, DeviceInstance.CurrentSelectedDevice.GetStrID(), 0);
                                }
                                SelectedDeviceId = CurrentDeviceSelected.split(",")[0].split("_")[1];
                                CurrentDeviceJson = DeviceInstance.GetJsonInCache(SelectedDeviceId);
                                $.Xcds.FrameMap.SetModelInstanceFlash($.Xcds.DeviceLayerName, "device_" + SelectedDeviceId, 1);
                                $.Xcds.map.SetModelInstanceFlash($.Xcds.DeviceLayerName, "device_" + SelectedDeviceId, 1);
                                $.Xcds.FrameMap.Refresh();
                                $.Xcds.map.Refresh();
                                DeviceInstance.CurrentSelectedDevice = CurrentDeviceModel;
                                if (MapObj == $.Xcds.FrameMap) {
                                    DeviceInstance.SwitchFloor(CurrentDeviceJson.floor_id, function () {
                                        DeviceInstance.FlyToDevice(SelectedDeviceId);
                                        if (CurrentDeviceJson.type == 4) {
                                            DeviceInstance.ShowRoof(CurrentDeviceJson.floor_id, 0.9);
                                        } else if (CurrentDeviceJson.type == 16) {
                                            var RoomId = CurrentDeviceJson.room_id;
                                            var FloorId = CurrentDeviceJson.floor_id;
                                            $.Xcds.bc.GetDeviceList(FloorId, RoomId, 101, function (response) {
                                                for (var i = 0; i < response.data.devices.length; i++) {
                                                    var CurrentDevice = response.data.devices[i];
                                                    var position = CurrentDevice.coord.x + "," + CurrentDevice.coord.y + "," + CurrentDevice.coord.z;
                                                    var RealModel = new Qmap.Object3D.QMap3DModelInstance({
                                                        layer: $.Xcds.DeviceLayerName,
                                                        model: CurrentDevice.di_file_name,
                                                        strid: "Device_" + CurrentDevice.id,
                                                        groupid: "0",
                                                        level: "0",
                                                        modelinstancename: "模型",
                                                        linkinfo: "ModelInstance",
                                                        visible: "1",
                                                        afrotate: CurrentDevice.di_rotate_angle.x + "," + CurrentDevice.di_rotate_angle.z + "," + CurrentDevice.di_rotate_angle.y,
                                                        rotateunit: "1",
                                                        transunit: "0",
                                                        transrotateunit: "0",
                                                        adposition: position
                                                    });
                                                    DeviceInstance.DeskCache.push(RealModel);
                                                    $.Xcds.map.AppendModelInstance("desk", RealModel);
                                                    $.Xcds.map.Refresh();
                                                }
                                            }, function (info) { });
                                        }
                                    });
                                }
                                if ($.isFunction(DeviceInstance.OnDeviceSelected)) {
                                    DeviceInstance.ShowDeviceDialog(SelectedDeviceId, DeviceInstance.OnDeviceSelected(CurrentDeviceJson.type, SelectedDeviceId));
                                }
                            }
                            $.Xcds.FixNotice();
                            DeviceInstance.BindDeviceOnMouseMoveEvent(MapObj);
                        });
                    },
                    //绑定鼠标左键按下事件
                    BindDeviceOnLClickDownEvent: function (MapObj) {
                        MapObj.doEvent("OnLClickDown", "BindDeviceOnLClickDownEvent", function () {
                            MapObj.undoEvent("OnMouseMove");
                        });
                    },
                    //绑定鼠标右键按下事件
                    BindDeviceOnRClickDownEvent: function (MapObj) {
                        MapObj.doEvent("OnRClickDown", "BindDeviceOnRClickDownEvent", function () {
                            MapObj.undoEvent("OnMouseMove");
                        });
                    },
                    //绑定鼠标右键弹起事件
                    BindDeviceOnRClickUpEvent: function (MapObj) {
                        MapObj.doEvent("OnRClickUp", "BindDeviceOnRClickUpEvent", function () {
                            DeviceInstance.BindDeviceOnMouseMoveEvent(MapObj);
                        });
                    },
                    //显示楼层的屋顶
                    ShowRoof: function (FloorId, TransParentValue) {
                        $.Xcds.bc.GetFloorByFloorId(FloorId, function (response) {
                            var floor = response.data;
                            var floorNum = floor.str_id;
                            var adposition = floor.coord.x + "," + floor.coord.z + "," + floor.coord.y;
                            $.Xcds.AddRoof(floorNum, adposition, TransParentValue);
                        });
                    },
                    //删除所有已存在的设备
                    ClearAllExistDevice: function () {
                        for (var i = 0; i < DeviceInstance.DeviceCache.size(); i++) {
                            var CurrentDeviceModel = DeviceInstance.DeviceCache.get(DeviceInstance.DeviceCache.keys[i])[1];
                            $.Xcds.FrameMap.DeleteModelInstance($.Xcds.DeviceLayerName, CurrentDeviceModel.GetStrID());
                            $.Xcds.map.DeleteModelInstance($.Xcds.DeviceLayerName, CurrentDeviceModel.GetStrID());
                        }
                        DeviceInstance.DeviceCache.empty();
                    },
                    /************************************以上设备相关方法********************************************/

                    /************************************以下电梯相关方法********************************************/

                    //电梯管理的初始化方法
                    InitLiftList: function (liftArray) {
                        if (!$.Xcds.Locked) {
                            $.Xcds.Locked = true;
                            setTimeout(function () {
                                $.Xcds.FrameMap.InitActiveLayer($.Xcds.DeviceLayerName);
                                DeviceInstance.ResetFrameMap();
                                $.Xcds.FrameMap.SetLayerVisible("lift_well", 1);
                                DeviceInstance.CurrentInitFunctionName = "InitLiftList";
                                DeviceInstance.PutLiftToCache(liftArray, function () {
                                    DeviceInstance.ShowLiftByCache(liftArray, function () {
                                        DeviceInstance.BindLiftOnMouseMoveEvent();
                                        DeviceInstance.BindLiftOnLClickUpWatchEvent();
                                        DeviceInstance.BindLiftOnRDbClickEvent();
                                        DeviceInstance.BindLiftOnLClickDownEvent();
                                        DeviceInstance.BindLiftOnRClickDownEvent();
                                        DeviceInstance.BindLiftOnRClickUpEvent();
                                        $.Xcds.Locked = false;
                                    });
                                });
                            }, 500);
                        }
                    },
                    //根据电梯号获取电梯的当前位置
                    GetLiftPosition: function (LiftNum) {
                        switch (LiftNum) {
                            case "lift_1":
                                return DeviceInstance.PositionArray[0];
                            case "lift_2":
                                return DeviceInstance.PositionArray[1];
                            case "lift_3":
                                return DeviceInstance.PositionArray[2];
                            case "lift_4":
                                return DeviceInstance.PositionArray[3];
                            case "lift_5":
                                return DeviceInstance.PositionArray[4];
                            case "lift_6":
                                return DeviceInstance.PositionArray[5];
                            case "lift_7":
                                return DeviceInstance.PositionArray[6];
                            case "lift_8":
                                return DeviceInstance.PositionArray[7];
                            case "lift_9":
                                return DeviceInstance.PositionArray[8];
                            case "lift_10":
                                return DeviceInstance.PositionArray[9];
                            case "lift_11":
                                return DeviceInstance.PositionArray[10];
                            case "lift_12":
                                return DeviceInstance.PositionArray[11];
                            case "lift_13":
                                return DeviceInstance.PositionArray[12];
                            case "lift_14":
                                return DeviceInstance.PositionArray[13];
                            case "lift_15":
                                return DeviceInstance.PositionArray[14];
                            case "lift_16":
                                return DeviceInstance.PositionArray[15];
                            default:
                                return DeviceInstance.PositionArray[10];
                        }
                    },
                    //将电梯的数据放入缓存
                    PutLiftToCache: function (liftArray, callback) {
                        for (var i = 0; i < liftArray.length; i++) {
                            var lift = liftArray[i];
                            var liftNum = lift.liftNum.split("_")[1];
                            DeviceInstance.LiftCache.set(liftNum, lift);
                        }
                        if ($.isFunction(callback)) {
                            callback();
                        }
                    },
                    //根据缓存显示电梯
                    ShowLiftByCache: function (liftArray, callback) {
                        $.Xcds.FrameMap.defaultParam.VectorLayer.Model = $.Xcds.DeviceLayerName;
                        for (var i = 0; i < DeviceInstance.LiftCache.size(); i++) {
                            var lift = DeviceInstance.LiftCache.get(DeviceInstance.LiftCache.keys[i]);
                            var poiArrLift = new Array();
                            var liftNum = lift.liftNum;
                            var floorNum = lift.floorNum;
                            var liftDirection = lift.direction;
                            var liftStatus = lift.status;
                            var NumArr = liftNum.split("_");
                            var lift_num = NumArr[0] + "_" + NumArr[1];
                            var poi = DeviceInstance.GetLiftPosition(lift_num);
                            var poi_X = poi.split(",")[0];
                            var poi_Y = poi.split(",")[1];
                            if (parseInt(floorNum) == -1) {
                                var poi_Z = -4.400000001;
                            } else if (parseInt(floorNum) == -2) {
                                var poi_Z = -9.000000002;
                            } else if (parseInt(floorNum) == 1) {
                                var poi_Z = 0.200000001;
                            } else if (parseInt(floorNum) == 2) {
                                var poi_Z = 4.200000001;
                            } else if (parseInt(floorNum) == 3) {
                                var poi_Z = 8.800000001;
                            } else {
                                var poi_Z = (parseInt(floorNum) - 4) * 4 + 12.8000001;
                            }
                            var poi1 = poi_X + "," + poi_Y + "," + poi_Z;
                            var poi2_X = parseFloat(poi_X) + 0.00000000001;
                            var poi2_Y = parseFloat(poi_Y) + 0.00000000001;
                            var poi2_Z = -9.000000001;
                            var poi2 = poi2_X.toString() + "," + poi2_Y.toString() + "," + poi2_Z.toString();
                            var poi3_X = parseFloat(poi_X) + 0.00000000002;
                            var poi3_Y = parseFloat(poi_Y) + 0.00000000002;
                            var poi3_Z = 72.000000001;
                            var poi3 = poi3_X.toString() + "," + poi3_Y.toString() + "," + poi3_Z.toString();
                            if (liftDirection) {
                                poiArrLift.push(poi1);
                                poiArrLift.push(poi3);
                                poiArrLift.push(poi2);
                                poiArrLift.push(poi1);
                            } else {
                                poiArrLift.push(poi1);
                                poiArrLift.push(poi2);
                                poiArrLift.push(poi3);
                                poiArrLift.push(poi1);
                            }
                            var qta;
                            if (i < 9) {
                                qta = "lift0" + (i + 1).toString() + "_model.qta";
                            } else {
                                qta = "lift" + (i + 1).toString() + "_model.qta";
                            }
                            $.Xcds.FrameMap.SetModelInstanceFlash($.Xcds.DeviceLayerName, liftNum, 0);
                            if (liftStatus) {
                                var lift = $.Xcds.FrameMap.m_SetModelInstancetDynamicByLine({
                                    qta: qta,
                                    autoRun: true,
                                    lineData: poiArrLift,
                                    showLine: false,
                                    aniloop: 1,
                                    faceAngle: 0,
                                    modelID: liftNum
                                });
                                $.Xcds.FrameMap.SetModelInstanceFlash($.Xcds.DeviceLayerName, liftNum, 0);
                            } else {
                                var lift = $.Xcds.FrameMap.m_SetModelInstancetDynamicByLine({
                                    qta: qta,
                                    lineData: poiArrLift,
                                    showLine: false,
                                    aniloop: 1,
                                    modelID: liftNum
                                });
                                $.Xcds.FrameMap.SetModelInstanceFlash($.Xcds.DeviceLayerName, liftNum, 1);
                            }
                            $.Xcds.FrameMap.Redraw();
                        }
                        var TextArray = new Array();
                        TextArray.push(",闪烁表示该电梯异常");
                        TextArray.push(",鼠标左键单击某个电梯，跟踪定位");
                        TextArray.push(",鼠标右键双击，取消跟随，返回默认视角");
                        $.Xcds.ShowNotice(TextArray);
                        if ($.isFunction(callback)) {
                            callback();
                        }
                    },
                    //绑定电梯的鼠标移动事件
                    BindLiftOnMouseMoveEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnMouseMove", DeviceInstance.CurrentInitFunctionName, function (options) {
                            $.Xcds.FixToolBar(options);
                            var CurrentDeviceBeenOver = $.Xcds.FrameMap.SelectModel(options.logX, options.logY, options.logX, options.logY);
                            if (CurrentDeviceBeenOver != "") {
                                var SelectedLiftId = CurrentDeviceBeenOver.split(",")[0];
                                var CacheId = SelectedLiftId.split("_")[1];
                                var SelectedLiftInfo = DeviceInstance.LiftCache.get(CacheId);

                                if (DeviceInstance.CurrentOverDeviceId != SelectedLiftId) {
                                    var arr = new Array();
                                    arr.push("设备名称：,电梯");
                                    var NumArr = SelectedLiftInfo.liftNum.split("_");
                                    var lift_num = NumArr[0] + "_" + NumArr[1];
                                    arr.push("设备编号：," + lift_num);
                                    if (SelectedLiftInfo.status) {
                                        arr.push("运行状态：,正常");
                                    } else {
                                        arr.push("运行状态：,<font color='red'>异常</font>");
                                        $.Xcds.ShowCurrentFloorSign(SelectedLiftInfo.floorNum);
                                    }
                                    arr.push("安装日期：,2012年11月11日");
                                    arr.push("维修日期：," + (new Date()).toLocaleDateString());
                                    $.Xcds.ShowToolTip(options, arr);
                                    $.Xcds.FrameMap.InitActiveLayer($.Xcds.DeviceLayerName);
                                    DeviceInstance.CurrentOverDeviceId = SelectedLiftId;
                                }
                            } else {
                                $.Xcds.HideToolTip(DeviceInstance.CurrentOverDeviceId);
                                $.Xcds.HideFloorSign();
                                $.Xcds.FrameMap.InitActiveLayer($.Xcds.DeviceLayerName);
                                DeviceInstance.CurrentOverDeviceId = "";
                            }
                        });
                    },
                    //绑定电梯左键点击视角跟随事件
                    BindLiftOnLClickUpWatchEvent: function () {
                        $.Xcds.FrameMap.undoEvent("OnLClickUp");
                        $.Xcds.FrameMap.doEvent("OnLClickUp", DeviceInstance.CurrentInitFunctionName, function (options) {
                            DeviceInstance.BindLiftOnMouseMoveEvent();
                            var CurrentDeviceBeenOver = $.Xcds.FrameMap.SelectModel(options.logX, options.logY, options.logX, options.logY);
                            if (CurrentDeviceBeenOver != "") {
                                //$.Xcds.FrameMap.SetModelInstanceFlash($.Xcds.DeviceLayerName, DeviceInstance.CurrentOverDeviceId, 0);
                                $.Xcds.HideToolTip();

                                $.Xcds.FrameMap.SetWatchingModel($.Xcds.DeviceLayerName, DeviceInstance.CurrentOverDeviceId,
                                            20, 10, -10, 0, 0, 0);
                                DeviceInstance.ShowLiftOnly(DeviceInstance.CurrentOverDeviceId);
                                $.Xcds.FrameMap.undoEvent("OnMouseMove");
                                DeviceInstance.BindLiftOnLClickUpWindowEvent();
                            }
                            DeviceInstance.CurrentOverDeviceId = "";
                        });
                    },
                    //绑定电梯左键点击弹窗事件
                    BindLiftOnLClickUpWindowEvent: function () {
                        $.Xcds.FrameMap.undoEvent("OnLClickUp");
                        $.Xcds.FrameMap.doEvent("OnLClickUp", DeviceInstance.CurrentInitFunctionName, function (options) {
                            var CurrentDeviceBeenOver = $.Xcds.FrameMap.SelectModel(options.logX, options.logY, options.logX, options.logY);
                            if (CurrentDeviceBeenOver != "") {
                                //DeviceInstance.ShowLiftDialog(1, "给我个url，我就能显示");
                                if ($.isFunction(DeviceInstance.OnDeviceSelected)) {
                                    DeviceInstance.ShowLiftDialog(1, DeviceInstance.OnDeviceSelected(7, CurrentDeviceBeenOver.split("_")[1]));
                                }
                            }
                            DeviceInstance.CurrentOverDeviceId = "";
                        });
                    },
                    //绑定电梯右键双击返回视角事件
                    BindLiftOnRDbClickEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnRDbClick", DeviceInstance.CurrentInitFunctionName, function (options) {
                            $.Xcds.FrameMap.SetModelInstanceFlash($.Xcds.DeviceLayerName, DeviceInstance.CurrentOverDeviceId, 0);
                            $.Xcds.HideToolTip(DeviceInstance.CurrentOverDeviceId);
                            DeviceInstance.CurrentOverDeviceId = "";
                            $.Xcds.FrameMap.UnsetWatchingModel();
                            DeviceInstance.ShowLiftAll();
                            DeviceInstance.BindLiftOnLClickUpWatchEvent();
                            DeviceInstance.BindLiftOnMouseMoveEvent();
                            DeviceInstance.CloseLiftDialog();
                            $.Xcds.FrameMap.FlytoPosition(118.43181378238805, 122.21591984463589, 32.00235978898739, -273.90484619140625, -26.897798538208008);
                        });
                    },
                    //绑定鼠标左键按下事件
                    BindLiftOnLClickDownEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnLClickDown", "BindDeviceOnLClickDownEvent", function () {
                            $.Xcds.FrameMap.undoEvent("OnMouseMove");
                        });
                    },
                    //绑定鼠标右键按下事件
                    BindLiftOnRClickDownEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnRClickDown", "BindDeviceOnRClickDownEvent", function () {
                            $.Xcds.FrameMap.undoEvent("OnMouseMove");
                        });
                    },
                    //绑定鼠标右键弹起事件
                    BindLiftOnRClickUpEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnRClickUp", "BindDeviceOnRClickUpEvent", function () {
                            DeviceInstance.BindLiftOnMouseMoveEvent();
                        });
                    },
                    //删除所有已存在的电梯
                    ClearAllExistLift: function () {
                        $.Xcds.FrameMap.SetLayerVisible("lift_well", 0);
                        for (var i = 0; i < DeviceInstance.LiftCache.size(); i++) {
                            var liftNum = DeviceInstance.LiftCache.get(DeviceInstance.LiftCache.keys[i]).liftNum;
                            $.Xcds.FrameMap.DeleteModelInstance($.Xcds.DeviceLayerName, liftNum);
                        }
                        DeviceInstance.LiftCache.empty();
                    },
                    //从缓存中得到电梯数据
                    GetLiftInCache: function (liftNum) {
                        for (var i = 0; i < DeviceInstance.LiftCache.size(); i++) {
                            if (DeviceInstance.LiftCache.keys[i] == liftNum) {
                                var DeviceCache = DeviceInstance.LiftCache.get(DeviceId)[0];
                                return DeviceCache;
                            }
                        }
                    },
                    //显示所有的电梯
                    ShowLiftAll: function () {
                        for (var i = 0; i < DeviceInstance.LiftCache.size(); i++) {
                            var liftNum = DeviceInstance.LiftCache.keys[i];
                            var lift = DeviceInstance.LiftCache.get(liftNum);
                            $.Xcds.FrameMap.SetModelInstanceVisible($.Xcds.DeviceLayerName, lift.liftNum, 0);
                        }
                    },
                    //只显示一个电梯
                    ShowLiftOnly: function (liftNum) {
                        for (var i = 0; i < DeviceInstance.LiftCache.size(); i++) {
                            var liftNumAll = DeviceInstance.LiftCache.keys[i];
                            var lift = DeviceInstance.LiftCache.get(liftNumAll);
                            $.Xcds.FrameMap.SetModelInstanceVisible($.Xcds.DeviceLayerName, lift.liftNum, 1);
                        }
                        $.Xcds.FrameMap.SetModelInstanceVisible($.Xcds.DeviceLayerName, liftNum, 0);
                    },
                    //显示电梯弹窗
                    ShowLiftDialog: function (windowNum, url) {
                        $.Xcds.bc.ShowWinDialog($.Xcds.FrameMap, $.Xcds.window.Window[windowNum], "100,100", url);
                        DeviceInstance.CurrentWindowId = $.Xcds.window.Window[windowNum].nID;
                    },
                    //关闭当前弹窗
                    CloseLiftDialog: (function () {
                        $.Xcds.FrameMap.CloseUserWndByID(DeviceInstance.CurrentWindowId);
                    }),
                    /************************************以上电梯相关方法********************************************/

                    /*****************************************以下是访客管理相关方法*****************************************************/

                    //访客定位的初始化方法
                    InitVisitorPoint: function (ZoonInVisitorId, VisitorObjArray) {
                        if (!$.Xcds.Locked) {
                            $.Xcds.Locked = true;
                            setTimeout(function () {
                                DeviceInstance.CurrentFunctionName = "InitVisitorPoint";
                                DeviceInstance.ResetFrameMap();
                                DeviceInstance.ShowFrameMap();
                                var DeviceIds = "";
                                for (var i = 0; i < VisitorObjArray.length; i++) {
                                    DeviceIds = DeviceIds + "," + VisitorObjArray[i].DeviceId;
                                }
                                $.Xcds.bc.GetDevicePoints(DeviceIds, function (response) {
                                    DeviceInstance.PutVisitorToCache(response, VisitorObjArray, 1, function () {
                                        DeviceInstance.ShowVisitorPointByCache(function () {
                                            DeviceInstance.BindVisitorOnMouseMoveEvent($.Xcds.FrameMap);
                                            DeviceInstance.BindVisitorOnLClickUpEvent($.Xcds.FrameMap);
                                            DeviceInstance.BindVisitorOnLClickDownEvent();
                                            DeviceInstance.BindVisitorOnRClickUpEvent();
                                            DeviceInstance.BindVisitorOnRClickDownEvent();
                                            DeviceInstance.ZoonInVisitor(ZoonInVisitorId);
                                            $.Xcds.Locked = false;
                                        });
                                    });
                                }, function (info) {

                                });
                            }, 500);
                        }
                    },
                    //访客轨迹的初始化方法
                    InitVisitorLine: function (VisitorObj) {
                        if (!$.Xcds.Locked) {
                            $.Xcds.Locked = true;
                            setTimeout(function () {
                                DeviceInstance.CurrentFunctionName = "InitVisitorLine";
                                DeviceInstance.ResetFrameMap();
                                DeviceInstance.ShowFrameMap();
                                var DeviceIds = "";
                                DeviceIds = VisitorObj.DeviceIds;
                                $.Xcds.bc.GetVisitorRoute(DeviceIds, function (response) {
                                    DeviceInstance.PutVisitorToCache(response, VisitorObj.ShowInfo, 0, function () {
                                        DeviceInstance.ShowVisitorLineByCache(function () {
                                            DeviceInstance.BindVisitorOnMouseMoveEvent($.Xcds.FrameMap);
                                            DeviceInstance.BindVisitorOnLClickUpEvent_Line();
                                            DeviceInstance.BindVisitorOnLClickDownEvent();
                                            DeviceInstance.BindVisitorOnRClickUpEvent();
                                            DeviceInstance.BindVisitorOnRClickDownEvent();
                                            //$.Xcds.FrameMap.undoEvent("OnLClickUp");
                                            $.Xcds.Locked = false;
                                            //                                    DeviceInstance.BindVisitorOnLClickUpEvent($.Xcds.FrameMap);
                                        });
                                    });
                                }, function (info) {

                                });
                            }, 500);
                        }
                    },
                    //巡更管理初始化方法
                    InitPatrolLine: function (PatrolObj) {
                        if (!$.Xcds.Locked) {
                            $.Xcds.Locked = true;
                            setTimeout(function () {
                                DeviceInstance.CurrentFunctionName = "InitPatrolLine";
                                DeviceInstance.ResetFrameMap();
                                DeviceInstance.ShowFrameMap();
                                var PathId = PatrolObj.PathId;
                                $.Xcds.bc.GetPatrolRoute(PathId, function (response) {
                                    DeviceInstance.PutPatrolToCache(response, PatrolObj.ShowInfo, function () {
                                        DeviceInstance.ShowPatrolLineByCache(1, function () {
                                            var DeviceIds = PatrolObj.DeviceIds;
                                            $.Xcds.bc.GetVisitorRoute(DeviceIds, function (response1) {
                                                DeviceInstance.PutVisitorToCache(response1, PatrolObj.ShowInfo, 0, function () {
                                                    DeviceInstance.ShowPatrolLineByCache(0, function () {
                                                        DeviceInstance.BindPatrolOnMouseMoveEvent($.Xcds.FrameMap);
                                                        DeviceInstance.BindPatrolOnLClickDownEvent();
                                                        DeviceInstance.BindPatrolOnLClickUpEvent();
                                                        DeviceInstance.BindPatrolOnRClickDownEvent();
                                                        DeviceInstance.BindPatrolOnRClickUpEvent();
                                                        //$.Xcds.FrameMap.undoEvent("OnLClickUp");
                                                        $.Xcds.Locked = false;
                                                    });
                                                });
                                            }, function (info) {

                                            });
                                        });
                                    });
                                }, function (info) {

                                });
                            }, 500);
                        }
                    },
                    //根据缓存显示巡更人员
                    ShowPatrolLineByCache: function (LineType, callback) {
                        $.Xcds.FrameMap.InitActiveLayer($.Xcds.DeviceLayerName);
                        var poiArray = new Array();
                        if (LineType) {
                            for (var i = 0; i < DeviceInstance.PatrolCache.size(); i++) {
                                var CurrentPatrolJson = DeviceInstance.PatrolCache.get(DeviceInstance.PatrolCache.keys[i])[0];
                                var CurrentPatrolModel = DeviceInstance.PatrolCache.get(DeviceInstance.PatrolCache.keys[i])[1];
                                $.Xcds.FrameMap.AppendModelInstance($.Xcds.DeviceLayerName, CurrentPatrolModel);

                                poiArray.push(CurrentPatrolJson.point.x + "," + CurrentPatrolJson.point.y + "," + CurrentPatrolJson.point.z);
                            }
                            //                            $.Xcds.FrameMap.m_AppendVectorLineModel({
                            //                                strid: "PatrolLine",
                            //                                groupid: "0",
                            //                                linewidth: "2",
                            //                                color: "255,128,64,20",//黄
                            //                                coords: poiArray
                            //                            });
                        } else {
                            var poiArray = new Array();
                            for (var i = 0; i < DeviceInstance.VisitorCache.size() - 1; i++) {
                                var CurrentPatrolJson = DeviceInstance.VisitorCache.get(DeviceInstance.VisitorCache.keys[i])[0];
                                var CurrentPatrolModel = DeviceInstance.VisitorCache.get(DeviceInstance.VisitorCache.keys[i])[1];
                                //                                if (CurrentPatrolJson.position_desc != "标记点") {
                                //                                    $.Xcds.FrameMap.AppendModelInstance($.Xcds.DeviceLayerName, CurrentPatrolModel);
                                //                                }
                                poiArray.push(CurrentPatrolJson.point.x + "," + CurrentPatrolJson.point.y + "," + CurrentPatrolJson.point.z);
                            }
                            var CurrentPatrolJson = DeviceInstance.VisitorCache.get(DeviceInstance.VisitorCache.keys[DeviceInstance.VisitorCache.size() - 1])[0];
                            var CurrentPatrolModel = DeviceInstance.VisitorCache.get(DeviceInstance.VisitorCache.keys[DeviceInstance.VisitorCache.size() - 1])[1];
                            $.Xcds.FrameMap.AppendModelInstance($.Xcds.DeviceLayerName, CurrentPatrolModel);
                            poiArray.push(CurrentPatrolJson.point.x + "," + CurrentPatrolJson.point.y + "," + CurrentPatrolJson.point.z);
                            $.Xcds.PatrolLineModel = $.Xcds.FrameMap.m_AppendVectorLineModel({
                                strid: "PatrolLine",
                                groupid: "0",
                                linewidth: "2",
                                color: "0,255,0,255", //"255,0,0,20", //红
                                coords: poiArray
                            });
                        }
                        var TextArray = new Array();
                        TextArray.push(",<font color='#00FF00'>绿色</font>球体表示该巡更人员的巡更点");
                        TextArray.push(",<font color='#00FF00'>绿色</font>线表示该巡更人员行走的实际路线");
                        TextArray.push(",球体表示定位点");
                        TextArray.push(",人形表示该巡更人员当前位置");
                        $.Xcds.ShowNotice(TextArray);
                        $.Xcds.bc.ZoonInToPosition($.Xcds.FrameMap, 40, poiArray[poiArray.length - 1]);
                        $.Xcds.FrameMap.SetLayerVisible($.Xcds.DeviceLayerName, 1);
                        $.Xcds.FrameMap.Redraw();
                        callback();
                    },
                    //把巡更放入缓存
                    PutPatrolToCache: function (response, VisitorObjArray, callback) {
                        var object3D;
                        for (var i = 0; i < response.data.length; i++) {
                            var CurrentVisitor = response.data[i];
                            var position = CurrentVisitor.point.x + "," + CurrentVisitor.point.y + "," + CurrentVisitor.point.z;
                            object3D = new Qmap.Object3D.QMap3DModelInstance({
                                model: "lvse.qta",
                                strid: "Device_" + CurrentVisitor.node_id,
                                adposition: position,
                                afsize: "3,3,3"
                            });
                            var CacheValue = new Array(3);
                            CacheValue[0] = CurrentVisitor;
                            CacheValue[1] = object3D;
                            var ShowInfo = new Array();
                            ShowInfo.push("位置：," + CurrentVisitor.node_desc);
                            CacheValue[2] = ShowInfo;
                            DeviceInstance.PatrolCache.set(CurrentVisitor.node_id, CacheValue);
                        }
                        //                        var CurrentVisitor = response.data[response.data.length - 1];
                        //                        var position = CurrentVisitor.point.x + "," + CurrentVisitor.point.y + "," + CurrentVisitor.point.z;
                        //                        object3D = new Qmap.Object3D.QMap3DModelInstance({
                        //                            model: "xiaorennv.qta",
                        //                            strid: "Device_" + CurrentVisitor.node_id,
                        //                            adposition: position,
                        //                            afsize: "1.149508985837301, 0.725336273511251, 3.685537974039714"
                        //                        });
                        //                        var CacheValue = new Array(3);
                        //                        CacheValue[0] = CurrentVisitor;
                        //                        CacheValue[1] = object3D;
                        //                        CacheValue[2] = VisitorObjArray;
                        //                        DeviceInstance.PatrolCache.set(CurrentVisitor.node_id, CacheValue);
                        DeviceInstance.DeviceCached = true;
                        callback();
                    },
                    //绑定巡更人员的鼠标移动事件BindVisitorSelectedEvent
                    BindPatrolOnMouseMoveEvent: function (MapObj, callback) {
                        MapObj.doEvent("OnMouseMove", DeviceInstance.CurrentFunctionName, function (options) {
                            $.Xcds.FixToolBar(options);
                            if ($.Xcds.CurrentMouseOption == null) {
                                $.Xcds.CurrentMouseOption = options;
                            }
                            if ($.Xcds.CurrentMouseOption.logX != options.logX && $.Xcds.CurrentMouseOption.logY != options.logY) {
                                var CurrentDeviceBeenOver = MapObj.SelectModel(options.logX, options.logY, options.logX, options.logY);
                                if (CurrentDeviceBeenOver != "" && CurrentDeviceBeenOver.split(",")[0].split("_")[1] != "") {
                                    var SelectedDeviceId = CurrentDeviceBeenOver.split(",")[0].split("_")[1];
                                    var VisitorInfo;
                                    if (DeviceInstance.VisitorCache.get(SelectedDeviceId)) {
                                        VisitorInfo = DeviceInstance.VisitorCache.get(SelectedDeviceId)[2];
                                    } else {
                                        VisitorInfo = DeviceInstance.PatrolCache.get(SelectedDeviceId)[2];
                                    }
                                    var CurrentVisitorJson;
                                    if (DeviceInstance.VisitorCache.get(SelectedDeviceId)) {
                                        CurrentVisitorJson = DeviceInstance.VisitorCache.get(SelectedDeviceId)[0];
                                    } else {
                                        CurrentVisitorJson = DeviceInstance.PatrolCache.get(SelectedDeviceId)[0];
                                    }
                                    if (DeviceInstance.CurrentOverDeviceId != SelectedDeviceId) {
                                        $.Xcds.ShowToolTip(options, VisitorInfo);
                                        DeviceInstance.CurrentOverDeviceId = SelectedDeviceId;
                                        $.Xcds.ShowCurrentFloorSign(CurrentVisitorJson.floor_num);
                                    }
                                } else {
                                    $.Xcds.HideToolTip();
                                    $.Xcds.HideFloorSign();
                                    $.Xcds.FrameMap.InitActiveLayer($.Xcds.DeviceLayerName);
                                    DeviceInstance.CurrentOverDeviceId = -1;
                                }
                            }
                            $.Xcds.CurrentMouseOption = options;
                        });
                    },
                    //绑定鼠标左键按下事件
                    BindPatrolOnLClickDownEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnLClickDown", "BindPatrolOnLClickDownEvent", function () {
                            $.Xcds.FrameMap.undoEvent("OnMouseMove");
                        });
                    },
                    BindPatrolOnLClickUpEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnLClickUp", "BindPatrolOnLClickUpEvent", function () {
                            DeviceInstance.BindPatrolOnMouseMoveEvent($.Xcds.FrameMap);
                        });
                    },
                    //绑定鼠标右键按下事件
                    BindPatrolOnRClickDownEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnRClickDown", "BindPatrolOnRClickDownEvent", function () {
                            $.Xcds.FrameMap.undoEvent("OnMouseMove");
                        });
                    },
                    //绑定鼠标右键弹起事件
                    BindPatrolOnRClickUpEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnRClickUp", "BindPatrolOnRClickUpEvent", function () {
                            DeviceInstance.BindPatrolOnMouseMoveEvent($.Xcds.FrameMap);
                        });
                    },
                    //把访客放入缓存
                    PutVisitorToCache: function (response, VisitorObjArray, ModelType, callback) {
                        var object3D;
                        if (ModelType) {
                            for (var i = 0; i < response.data.length; i++) {
                                var CurrentVisitor = response.data[i];
                                var position = CurrentVisitor.point.x + "," + CurrentVisitor.point.y + "," + CurrentVisitor.point.z;
                                object3D = new Qmap.Object3D.QMap3DModelInstance({
                                    model: "XC man.qta",
                                    strid: "Device_" + CurrentVisitor.bl_dev_id,
                                    adposition: position,
                                    afsize: "1.145573616027832,0.5488324284553528,3.260037302970885"
                                });
                                var CacheValue = new Array(3);
                                CacheValue[0] = CurrentVisitor;
                                CacheValue[1] = object3D;
                                for (var k = 0; k < VisitorObjArray.length; k++) {
                                    if (CurrentVisitor.bl_dev_id == VisitorObjArray[k].DeviceId) {
                                        CacheValue[2] = VisitorObjArray[k].ShowInfo;
                                        break;
                                    }
                                }
                                DeviceInstance.VisitorCache.set(CurrentVisitor.bl_dev_id, CacheValue);
                            }
                        } else {
                            for (var i = 0; i < response.data.length - 1; i++) {
                                var CurrentVisitor = response.data[i];
                                var position = CurrentVisitor.point.x + "," + CurrentVisitor.point.y + "," + CurrentVisitor.point.z;
                                object3D = new Qmap.Object3D.QMap3DModelInstance({
                                    model: "luse.qta",
                                    strid: "Device_" + CurrentVisitor.route_id,
                                    adposition: position,
                                    afsize: "1,1,1"
                                });
                                //                                object3D = new Qmap.Object3D.QMap3DVectorPOI({
                                //                                    strid: "Device_" + CurrentVisitor.route_id,
                                //                                    texture: 'poi.png',
                                //                                    color: '255,255,255,0',
                                //                                    dynamic: 0,
                                //                                    fontname: '宋体',
                                //                                    fontsize: '16',
                                //                                    adposition: position,
                                //                                    diffuse: "255,255,255,255",
                                //                                    shininess: "255",
                                //                                    orient: "68",
                                //                                    billboardsize: "1,1",
                                //                                    thickness: "0.1",
                                //                                    thicknessofframe: "1",
                                //                                    bkcolor: "255,0,0,255"
                                //                                });

                                var CacheValue = new Array(3);
                                CacheValue[0] = CurrentVisitor;
                                CacheValue[1] = object3D;
                                var ShowInfo = new Array();
                                ShowInfo.push("位置：," + CurrentVisitor.position_desc);
                                CacheValue[2] = ShowInfo;
                                DeviceInstance.VisitorCache.set(CurrentVisitor.route_id, CacheValue);
                            }
                            var CurrentVisitor = response.data[response.data.length - 1];
                            var position = CurrentVisitor.point.x + "," + CurrentVisitor.point.y + "," + CurrentVisitor.point.z;
                            object3D = new Qmap.Object3D.QMap3DModelInstance({
                                model: "XC man.qta",
                                strid: "Device_" + CurrentVisitor.route_id,
                                adposition: position,
                                afsize: "1.145573616027832,0.5488324284553528,3.260037302970885"//"1.149508985837301, 0.725336273511251, 3.685537974039714"
                            });
                            var CacheValue = new Array(3);
                            CacheValue[0] = CurrentVisitor;
                            CacheValue[1] = object3D;
                            CacheValue[2] = VisitorObjArray;
                            DeviceInstance.VisitorCache.set(CurrentVisitor.route_id, CacheValue);
                        }
                        DeviceInstance.DeviceCached = true;
                        callback();
                    },
                    ZoonInVisitor: function (VisitorId) {
                        for (var i = 0; i < DeviceInstance.VisitorCache.size(); i++) {
                            var CurrentVisitorJson = DeviceInstance.VisitorCache.get(DeviceInstance.VisitorCache.keys[i])[0];
                            if (VisitorId == CurrentVisitorJson.bl_dev_id) {
                                var position = CurrentVisitorJson.point.x + "," + CurrentVisitorJson.point.y + "," + CurrentVisitorJson.point.z;
                                $.Xcds.bc.ZoonInToPosition($.Xcds.FrameMap, 40, position);
                                break;
                            }
                        }
                    },
                    //根据缓存定位访客
                    ShowVisitorPointByCache: function (callback) {
                        $.Xcds.FrameMap.InitActiveLayer($.Xcds.DeviceLayerName);
                        $.Xcds.FrameMap.SetLayerVisible($.Xcds.DeviceLayerName, 0);
                        for (var i = 0; i < DeviceInstance.VisitorCache.size(); i++) {
                            var CurrentVisitorModel = DeviceInstance.VisitorCache.get(DeviceInstance.VisitorCache.keys[i])[1];
                            $.Xcds.FrameMap.AppendModelInstance($.Xcds.DeviceLayerName, CurrentVisitorModel);
                        }
                        var TextArray = new Array();
                        TextArray.push(",人形表示访客的位置");
                        TextArray.push(",鼠标单击某个访客，可查看其行走的轨迹");
                        TextArray.push(",点击【访客定位】按钮，重新定位访客");
                        $.Xcds.ShowNotice(TextArray);
                        $.Xcds.FrameMap.SetLayerVisible($.Xcds.DeviceLayerName, 1);
                        $.Xcds.FrameMap.Redraw();
                        callback();
                    },
                    //根据缓存显示访客轨迹
                    ShowVisitorLineByCache: function (callback) {
                        $.Xcds.FrameMap.InitActiveLayer($.Xcds.DeviceLayerName);
                        $.Xcds.FrameMap.SetLayerVisible($.Xcds.DeviceLayerName, 0);
                        var poiArray = new Array();
                        for (var i = 0; i < DeviceInstance.VisitorCache.size(); i++) {
                            var CurrentVisitorJson = DeviceInstance.VisitorCache.get(DeviceInstance.VisitorCache.keys[i])[0];
                            poiArray.push(CurrentVisitorJson.point.x + "," + CurrentVisitorJson.point.y + "," + CurrentVisitorJson.point.z);
                            var CurrentVisitorModel = DeviceInstance.VisitorCache.get(DeviceInstance.VisitorCache.keys[i])[1];
                            //                                                      $.Xcds.FrameMap.AppendVectorModel($.Xcds.DeviceLayerName, CurrentVisitorModel);
                            if (CurrentVisitorJson.position_desc != "标记点") {
                                $.Xcds.FrameMap.AppendModelInstance($.Xcds.DeviceLayerName, CurrentVisitorModel);
                            }
                        }
                        var CurrentVisitorJson = DeviceInstance.VisitorCache.get(DeviceInstance.VisitorCache.keys[DeviceInstance.VisitorCache.size() - 1])[0];
                        poiArray.push(CurrentVisitorJson.point.x + "," + CurrentVisitorJson.point.y + "," + CurrentVisitorJson.point.z);
                        var CurrentVisitorModel = DeviceInstance.VisitorCache.get(DeviceInstance.VisitorCache.keys[DeviceInstance.VisitorCache.size() - 1])[1];
                        $.Xcds.FrameMap.AppendModelInstance($.Xcds.DeviceLayerName, CurrentVisitorModel);

                        $.Xcds.bc.ZoonInToPosition($.Xcds.FrameMap, 40, poiArray[0]);
                        if (poiArray.length > 1) {
                            $.Xcds.VisitorLineModel = $.Xcds.FrameMap.m_SetModelInstancetDynamicByLine({
                                autoRun: true,
                                afsize: "0.882301986694336,0.9254611587524414,3.033286193847656", //"0.7352516555786133,0.7712176322937012,2.527738494873047",
                                lineData: poiArray,
                                stopEvent: function () {
                                    $.Xcds.VisitorLineModel.hide();
                                }
                            });
                        }
                        $.Xcds.FrameMap.SetLayerVisible($.Xcds.DeviceLayerName, 1);
                        $.Xcds.FrameMap.Redraw();
                        callback();
                    },
                    //绑定访客的鼠标移动事件BindVisitorSelectedEvent
                    BindVisitorOnMouseMoveEvent: function (MapObj, callback) {
                        MapObj.doEvent("OnMouseMove", DeviceInstance.CurrentFunctionName, function (options) {
                            $.Xcds.FixToolBar(options);
                            if ($.Xcds.CurrentMouseOption == null) {
                                $.Xcds.CurrentMouseOption = options;
                            }
                            if ($.Xcds.CurrentMouseOption.logX != options.logX && $.Xcds.CurrentMouseOption.logY != options.logY) {
                                var CurrentDeviceBeenOver = MapObj.SelectModel(options.logX, options.logY, options.logX, options.logY);
                                if (CurrentDeviceBeenOver != "" && CurrentDeviceBeenOver.split(",")[0].split("_")[1] != "") {
                                    var SelectedDeviceId = CurrentDeviceBeenOver.split(",")[0].split("_")[1];

                                    var CurrentVisitorJson = DeviceInstance.VisitorCache.get(SelectedDeviceId)[0];
                                    if (DeviceInstance.CurrentOverDeviceId != SelectedDeviceId) {
                                        var VisitorInfo = new Array();
                                        VisitorInfo = DeviceInstance.VisitorCache.get(SelectedDeviceId)[2];
                                        //VisitorInfo.push("经过时间：," + (new Date()).toLocaleTimeString());
                                        $.Xcds.ShowToolTip(options, VisitorInfo);
                                        DeviceInstance.CurrentOverDeviceId = SelectedDeviceId;
                                        $.Xcds.ShowCurrentFloorSign(CurrentVisitorJson.floor_num);
                                    }

                                } else {
                                    $.Xcds.HideToolTip();
                                    $.Xcds.HideFloorSign();
                                    $.Xcds.FrameMap.InitActiveLayer($.Xcds.DeviceLayerName);
                                    DeviceInstance.CurrentOverDeviceId = -1;
                                }
                            }
                            $.Xcds.CurrentMouseOption = options;
                        });
                    },
                    //绑定访客的鼠标点击事件
                    BindVisitorOnLClickUpEvent: function (MapObj, callback) {
                        MapObj.doEvent("OnLClickUp", DeviceInstance.CurrentFunctionName, function (options) {
                            var CurrentDeviceBeenOver = MapObj.SelectModel(options.logX, options.logY, options.logX, options.logY);
                            if (CurrentDeviceBeenOver != "" && CurrentDeviceBeenOver.split(",")[0].split("_")[1] != "") {
                                var VisitorObj = new Object();
                                var SelectedDeviceId = CurrentDeviceBeenOver.split(",")[0].split("_")[1];
                                var VisitorInfo = DeviceInstance.VisitorCache.get(SelectedDeviceId)[2];
                                VisitorObj.DeviceIds = "103,115," + SelectedDeviceId;
                                VisitorObj.ShowInfo = VisitorInfo;
                                DeviceInstance.InitVisitorLine(VisitorObj);
                            }
                            DeviceInstance.BindVisitorOnMouseMoveEvent($.Xcds.FrameMap);
                        });
                    },
                    //绑定鼠标左键按下事件
                    BindVisitorOnLClickDownEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnLClickDown", "BindVisitorOnLClickDownEvent", function () {
                            $.Xcds.FrameMap.undoEvent("OnMouseMove");
                        });
                    },
                    BindVisitorOnLClickUpEvent_Line: function () {
                        $.Xcds.FrameMap.doEvent("OnLClickUp", "BindVisitorOnRClickUpEvent", function () {
                            DeviceInstance.BindVisitorOnMouseMoveEvent($.Xcds.FrameMap);
                        });
                    },
                    //绑定鼠标右键按下事件
                    BindVisitorOnRClickDownEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnRClickDown", "BindVisitorOnRClickDownEvent", function () {
                            $.Xcds.FrameMap.undoEvent("OnMouseMove");
                        });
                    },
                    //绑定鼠标右键弹起事件
                    BindVisitorOnRClickUpEvent: function () {
                        $.Xcds.FrameMap.doEvent("OnRClickUp", "BindVisitorOnRClickUpEvent", function () {
                            DeviceInstance.BindVisitorOnMouseMoveEvent($.Xcds.FrameMap);
                        });
                    },
                    //删除所有已存在的访客
                    ClearAllExistVisitor: function () {
                        for (var i = 0; i < DeviceInstance.VisitorCache.size(); i++) {
                            var CurrentVisitorModel = DeviceInstance.VisitorCache.get(DeviceInstance.VisitorCache.keys[i])[1];
                            $.Xcds.FrameMap.DeleteModelInstance($.Xcds.DeviceLayerName, CurrentVisitorModel.GetStrID());
                        }
                        $.Xcds.FrameMap.DeleteModelInstance("default", "VisitorLine");
                        DeviceInstance.VisitorCache.empty();
                        if ($.Xcds.VisitorLineModel) {
                            $.Xcds.VisitorLineModel.clear();
                        }
                    },
                    //删除所有已存在的巡更信息
                    ClearAllExistPatrol: function () {
                        for (var i = 0; i < DeviceInstance.PatrolCache.size(); i++) {
                            var CurrentVisitorModel = DeviceInstance.PatrolCache.get(DeviceInstance.PatrolCache.keys[i])[1];
                            $.Xcds.FrameMap.DeleteModelInstance($.Xcds.DeviceLayerName, CurrentVisitorModel.GetStrID());
                        }
                        $.Xcds.FrameMap.DeleteModelInstance("default", "PatrolLine");
                        DeviceInstance.PatrolCache.empty();
                    }
                    /*****************************************以上是访客管理相关方法**********************************************************/
                });
            }
        }
    });
})(jQuery);

/*****************************************以下是缓存的定义**********************************************************/
//定义一个存放cache的缓存对象
(function () { 
    $.extend($.Xcds,{
        MapCache: function() {
            this.keys = new Array();
            this.data = new Array();
            //添加键值对
            this.set = function (key, value) {
                if (this.data[key] == null) {//如键不存在则身【键】数组添加键名
                    this.keys.push(key);
                }
                this.data[key] = value; //给键赋值
            };
            //获取键对应的值
            this.get = function (key) {
                return this.data[key];
            };
            //去除键值，(去除键数据中的键名及对应的值)
            this.remove = function (key) {
                this.keys.remove(key);
                this.data[key] = null;
            };
            //判断键值元素是否为空
            this.isEmpty = function () {
                return this.keys.length == 0;
            };
            //获取键值元素大小
            this.size = function () {
                return this.keys.length;
            };
            //删除数组所有元素
            this.empty = function(){
                this.keys.length = 0;
                this.data.length = 0;
            };
        }
    });
})(jQuery);
/*****************************************以上是缓存的定义**********************************************************/

/*****************************************以下是共通的接口方法，最终移至total.js**********************************************************/
(function(){
    $.extend($.Xcds.url, {
        /* 设备list */
        FindDeviceList: 'device/list.json',

        /* 设备point */
        FindDevicePointList: 'position/visitor.json',

        /* 路线轨迹的坐标点 */
        FindRoute: 'position/route.json',

        /* 巡更人员的坐标点 */
        FindPatrolList: 'position/patrol.json'
    });
    $.extend($.Xcds.bc, {
        //根据楼层、房间、设备类型查询设备信息
        GetDeviceList: function(FloorId, RoomId, DeviceType, CallBackSuccess, CallBackFail){
            API.call({
                url: $.Xcds.url.FindDeviceList,
                data: $.param({
                    fid: FloorId,
                    type: DeviceType,
                    rid: RoomId
                }, true),
                success: function (response) {
                    CallBackSuccess(response);
                },
                error: function (info) {
                    CallBackFail(info);
                }
            });
        },
        //根据一组设备ID获取一组坐标点
        GetVisitorRoute: function(DeviceIds, CallBackSuccess, CallBackFail){
            API.call({
                url: $.Xcds.url.FindRoute,
                data: {bl_dids:DeviceIds},
                success: function (response) {
                    CallBackSuccess(response);
                },
                error: function (info) {
                    CallBackFail(info);
                }
            });
        },
        //根据一组PathId获取一组坐标点
        GetPatrolRoute: function(PathId, CallBackSuccess, CallBackFail){
            API.call({
                url: $.Xcds.url.FindPatrolList,
                data: {pid:PathId},
                success: function (response) {
                    CallBackSuccess(response);
                },
                error: function (info) {
                    CallBackFail(info);
                }
            });
        },
		 //根据一组设备ID获取一组坐标点
        GetDevicePoints: function(DeviceIds, CallBackSuccess, CallBackFail){
            API.call({
                url: $.Xcds.url.FindDevicePointList,
                data: {bl_dids:DeviceIds},
                success: function (response) {
                    CallBackSuccess(response);
                },
                error: function (info) {
                    CallBackFail(info);
                }
            });
        }
    });
})(jQuery);