$.extend($.Xcds, {
    MonitorDataArray: new Array(19),
    MapFloorData: new Array(19),
    FloorMonitorLayer: "xcds_sk",
    CurrentFloorId: 0,
    MonitorData: function (options) {
        var defaultOption = {
            Floor: 0,
            color: "255,0,0,20",
            monitorValue: 0,
            monitorUnit: ""
        };
        $.extend(defaultOption, options);
        var mdata = new Object();
        $.extend(mdata, options);
        return mdata;
    },
    InitialMonitorMap: function () {
        $.Xcds.map.InitActiveLayer($.Xcds.FloorMonitorLayer);
        //$.Xcds.ClearFloor();
        //$.Xcds.map.InitAreaCode("0420");


        $.Xcds.map.Redraw();
    },
    AddFloorData: function (monitorData) {
        $.Xcds.MonitorDataArray[monitorData.Floor] = monitorData;
    },
    RefreshMap: function () {
        for (var k = 1; k <= 19; k++) {
            if ($.Xcds.MonitorDataArray[k] != null) {
                $.Xcds.SetFloorColor(k, $.Xcds.MonitorDataArray[k].color);
                //$.Xcds.map.AppendVectorModel($.Xcds.FloorMonitorLayer, $.Xcds.MapFloorData[k]);
                //$.Xcds.map.SetModelInstanceTransparent($.Xcds.FloorMonitorLayer, $.Xcds.MapFloorData[k].GetStrID(), 1, 0.8);
            }
        }
        $.Xcds.map.SetLayerVisible("xcds_sk", 1);
        $.Xcds.map.doEvent("OnMouseMove", "InitialMonitorMap", function (options) {
            $.Xcds.map.InitActiveLayer($.Xcds.FloorMonitorLayer);
            //$.Xcds.FixToolBar(options);
            var CurrentFloorBeenOver = $.Xcds.map.SelectModel(options.logX, options.logY, options.logX, options.logY);
            if (CurrentFloorBeenOver != "" && CurrentFloorBeenOver.indexOf("xcds_sk_") != -1 && CurrentFloorBeenOver.indexOf("xcds_sk_sk_") == -1 && CurrentFloorBeenOver.split(",")[0].split("_")[2].split("-")[0] != "") {
                var OveredFloorId = parseInt(CurrentFloorBeenOver.split(",")[0].split("_")[2].split("-")[0]);
                if (OveredFloorId != $.Xcds.CurrentFloorId) {
                    var FloorMonitor = $.Xcds.MonitorDataArray[OveredFloorId];
                    if (FloorMonitor != null) {
                        var TextInToolTip = new Array();
                        TextInToolTip.push("楼层信息:," + FloorMonitor.Floor + "楼");
                        TextInToolTip.push("监控类别:," + FloorMonitor.monitorTypeName);
                        TextInToolTip.push("监控数值:," + FloorMonitor.monitorValue + FloorMonitor.monitorUnit);
                        $.Xcds.ShowToolTip(options, TextInToolTip);
                        $.Xcds.CurrentFloorId = OveredFloorId;
                    }
                }

            }
            else {
                $.Xcds.map.UnSelectAll();
                $.Xcds.HideToolTip(options);
            }
        })
        $.Xcds.map.Refresh();
    },
    ClearFloor: function () {
        for (var k = 1; k <= 19; k++) {
            $.Xcds.map.DeleteModelInstance($.Xcds.FloorMonitorLayer, "xcds_sk_" + k + "-000");
            $.Xcds.map.DeleteModelInstance($.Xcds.FloorMonitorLayer, "xcds_sk_" + k + "-001");
            $.Xcds.map.DeleteModelInstance($.Xcds.FloorMonitorLayer, "xcds_sk_" + k + "-002");
            $.Xcds.map.DeleteModelInstance($.Xcds.FloorMonitorLayer, "xcds_sk_" + k + "-003");
            $.Xcds.map.DeleteModelInstance($.Xcds.FloorMonitorLayer, "xcds_sk_" + k + "-004");
            $.Xcds.map.DeleteModelInstance($.Xcds.FloorMonitorLayer, "xcds_sk_" + k + "-005");
        }
    },
    SetFloorColor: function (FloorId, ColorString) {
        var FloorSKString = "xcds_sk_" + FloorId;
        var ColorModelIndex;
        switch (ColorString) {
            case "51,159,250,255":
                ColorModelIndex = "-001";
                break;
            case "105,216,14,255":
                ColorModelIndex = "-002";
                break;
            case "228,224,1,255":
                ColorModelIndex = "-003";
                break;
            case "249,100,0,255":
                ColorModelIndex = "-004";
                break;
            case "249,0,0,255":
                ColorModelIndex = "-005";
                break;
            default:
                ColorModelIndex = "-000";
                break;

        }
        for (var i = 0; i <= 5; i++) {
            if (FloorSKString + "-00" + i == FloorSKString + ColorModelIndex) {
                $.Xcds.map.SetModelInstanceTransparent($.Xcds.FloorMonitorLayer, FloorSKString + "-00" + i, 1, 1);
            }
            else {
                $.Xcds.map.DeleteModelInstance($.Xcds.FloorMonitorLayer, FloorSKString + "-00" + i);
            }
        }

    }
});

