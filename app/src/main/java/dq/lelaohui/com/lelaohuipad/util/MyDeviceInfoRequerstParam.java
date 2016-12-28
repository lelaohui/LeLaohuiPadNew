package dq.lelaohui.com.lelaohuipad.util;

import dq.lelaohui.com.nettylibrary.socket.RequestParam;
import dq.lelaohui.com.nettylibrary.util.NetContant;
import dq.lelaohui.com.nettylibrary.util.Protocol_KEY;

/**
 * Created by ZTF on 2016/12/28.
 * 获取我的设备信息相关接口
 */

public class MyDeviceInfoRequerstParam {
    /**
     * 获取数据请求的cateGory
     */
    private static final String CATEGORY = "lelaohui";
    public static final String USEDATA ="query.device.info";
    private SysVar var=null;
    public MyDeviceInfoRequerstParam(){
        var=SysVar.getInstance();
    }

    public RequestParam getRequestParamLLH(){
        RequestParam requestParam=new RequestParam();
        requestParam.addHeader(Protocol_KEY.CATEGORY,CATEGORY);
        requestParam.addHeader(Protocol_KEY.USERDATA, USEDATA);
        return requestParam;
    }
    /**
     * 查询用户设备信息
     * @param customerId
     * @param centerId
     * @return
     */
    public RequestParam queryUserDeviceInfo(String customerId,int centerId,int orgId,int orgType){
        RequestParam requestParam=getRequestParamLLH();
        requestParam.addHeader(Protocol_KEY.ACTION, NetContant.ServiceAction.GET_DEVICE_STATUS_INFOS);
        requestParam.addHeader(Protocol_KEY.USERDATA, "query.user.device");
        requestParam.addBody(Protocol_KEY.IS_SERVER_REQ,false);
        requestParam.addBody(Protocol_KEY.USERID,customerId);
        requestParam.addBody(Protocol_KEY.ORG_ID,orgId);
        requestParam.addBody(Protocol_KEY.ORG_TYPE,orgType);
        requestParam.addBody(Protocol_KEY.CENTERID,centerId);
        return requestParam;
    }



}
