package dq.lelaohui.com.lelaohuipad.controler;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dq.lelaohui.com.lelaohuipad.LeLaohuiApp;
import dq.lelaohui.com.lelaohuipad.LeLaohuiMainActivity;
import dq.lelaohui.com.lelaohuipad.base.LaoHuiBaseControler;
import dq.lelaohui.com.lelaohuipad.bean.FilterSubscribeCate;
import dq.lelaohui.com.lelaohuipad.bean.FilterSubscribeData;
import dq.lelaohui.com.lelaohuipad.bean.LogonBena;
import dq.lelaohui.com.lelaohuipad.bean.SerSubescribeCate;
import dq.lelaohui.com.lelaohuipad.bean.SerSubescribeData;
import dq.lelaohui.com.lelaohuipad.util.ServerRequestParam;
import dq.lelaohui.com.lelaohuipad.util.ServerSubscribeRequestParam;
import dq.lelaohui.com.nettylibrary.socket.LlhResponseHandler;
import dq.lelaohui.com.nettylibrary.socket.RequestParam;
import dq.lelaohui.com.nettylibrary.util.NetContant;
import dq.lelaohui.com.nettylibrary.util.ServiceNetContant;
import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.manager.BaseDaoOperator;

/**
 * Created by ZTF on 2016/11/25.
 * 服务预约控制
 */

public class ServerSubscribeControler  extends LaoHuiBaseControler {
    public static final String SUCCESS_CODE="200";
    private static ServerSubscribeControler serverSubscribeControler=null;
    private ServerSubscribeRequestParam requestParam;
    private ServerSubscribeControler(){
        requestParam=new ServerSubscribeRequestParam();
    }
    public static ServerSubscribeControler getControler(){
        if(serverSubscribeControler==null){
            synchronized (ServerMenuControler.class){
                if(serverSubscribeControler==null){
                    serverSubscribeControler=new ServerSubscribeControler();
                }
            }
        }
        return serverSubscribeControler;
    }

    @Override
    public void doBusses(Bundle responseData) {
        {
            if(responseData==null){
                log(getClass().getName()+" doBusses 数据异常");
                return ;
            }
            String action=getResponseAction(responseData);
            if(TextUtils.isEmpty(action)){
                log("解析数据异常，异常原因：action is null");
            }
            if(action.equals(ServiceNetContant.ServiceResponseAction.GET_STOCK_DETAIL_BY_USER_RESPONSE)){
                String body=getResponseBody(responseData);
                log("doBusses: "+responseData);
                SerSubescribeCate serSubescribeCate=(SerSubescribeCate)getJsonToObject(body, SerSubescribeCate.class);
                if (serSubescribeCate.getCode().equals(SUCCESS_CODE)){
                    if (getIControlerCallBack() != null) {//解析数据成功，通知UI界面
                        List<SerSubescribeData> serSubescribeDataList=serSubescribeCate.getData();
                        log("serSubescribeDataList=="+serSubescribeDataList.size());
                        if (serSubescribeDataList!=null&&serSubescribeDataList.size()>0){
                            log("serSubescribeData.get(0).getOrderId()==="+serSubescribeDataList.get(0).getOrderId());
                            Bundle bundle = new Bundle();
                            bundle.putString("action", ServiceNetContant.ServiceResponseAction.GET_STOCK_DETAIL_BY_USER_RESPONSE);
                            bundle.putParcelableArrayList("serSubescribeDataList", (ArrayList<? extends Parcelable>) serSubescribeDataList);
                            getIControlerCallBack().result(bundle);
                        }
                    }
                }else{
                    log("data is null");
                }
            }else if (action.equals(ServiceNetContant.ServiceResponseAction.GET_SERVER_DETAIL_BY_INFO_RESPONSE)){
                String body=getResponseBody(responseData);
                log("doBusses: "+responseData);
                FilterSubscribeCate filterSubscribeCate=(FilterSubscribeCate)getJsonToObject(body, FilterSubscribeCate.class);
                if (filterSubscribeCate.getCode().equals(SUCCESS_CODE)){
                    if (getIControlerCallBack() != null) {//解析数据成功，通知UI界面
                        List<FilterSubscribeData> filterSubscribeDataList=filterSubscribeCate.getData();
                        if (filterSubscribeDataList!=null&&filterSubscribeDataList.size()>0){
                            Bundle bundle = new Bundle();
                            bundle.putString("action", ServiceNetContant.ServiceResponseAction.GET_SERVER_DETAIL_BY_INFO_RESPONSE);
                            bundle.putParcelableArrayList("filterSubscribeDataList", (ArrayList<? extends Parcelable>) filterSubscribeDataList);
                            getIControlerCallBack().result(bundle);
                        }
                    }
                }else{
                    log("data is null");
                }
            }else{
                log("data is null");
            }
        }
    }

    /**
     * 查询用户库存信息
     * @param customerId
     */
    public void doQueryServerSetatlInfo(String customerId){
        LeLaohuiApp app= (LeLaohuiApp) getContext();
        if(app==null){
            throw  new RuntimeException(" app is null exception");
        }
        RequestParam requestParam1=requestParam.doQueryServerSetatlInfo(customerId);
        app.reqData(requestParam1);
    }

    public void   doQueryServerSetatlData(String customerId,String endTime,long serStockDetailId){
        LeLaohuiApp app= (LeLaohuiApp) getContext();
        if(app==null){
            throw  new RuntimeException(" app is null exception");
        }
        RequestParam requestParam1=requestParam.doQueryServerSetatlData(customerId,endTime,serStockDetailId);
        app.reqData(requestParam1);
    }
    @Override
    public BaseDaoOperator getBaseDaoOperator(String version) {
        return null;
    }

    @Override
    public BaseDaoOperator getBaseDaoOperator() {
        return null;
    }

}