package dq.lelaohui.com.lelaohuipad.fragement.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dq.lelaohui.com.lelaohuipad.base.BaseOrderInfoActivity;
import dq.lelaohui.com.lelaohuipad.bean.BaseOrderCate;
import dq.lelaohui.com.lelaohuipad.bean.FoodOrederData;
import dq.lelaohui.com.lelaohuipad.bean.FoodTradeNoData;
import dq.lelaohui.com.lelaohuipad.bean.SubShopFoodBean;
import dq.lelaohui.com.lelaohuipad.controler.FootOrderInfoControler;
import dq.lelaohui.com.lelaohuipad.controler.FootterControler;
import dq.lelaohui.com.lelaohuipad.port.IControler;
import dq.lelaohui.com.lelaohuipad.util.SysVar;
import dq.lelaohui.com.nettylibrary.util.ServiceNetContant;

/**
 * Created by ZTF on 2016/12/9.
 * 提交购买餐品信息
 */

public class SubShopFoodInfoActivity  extends BaseOrderInfoActivity{
    private  SysVar var=null;
    private static final String TAG="SubShopFoodInfoActivity";
    private String mealTime,isScope;
    private   List<FoodOrederData>  foodOrederDataList=new ArrayList<>();
    private int addressType=1;
    private int totalMoney;
    private int payType=1;
    private SysVar sysVar=null;
    private String customerId;
    List<FoodOrederData.SupplierInfoBean.ProductBean> productList;
    @Override
    protected void initPageData() {
        var = SysVar.getInstance();
        customerId=var.getUserId();
        if (getIntent()!=null) {
            foodOrederDataList = getIntent().getParcelableArrayListExtra("orderFoodInfo");
            if (foodOrederDataList != null) {
                mealTime = getIntent().getStringExtra("mealTime");
                isScope = getIntent().getStringExtra("isScope");
                totalMoney = foodOrederDataList.get(0).getTotal();
                getShopping_product_price().setText("总价：￥" + foodOrederDataList.get(0).getTotal() + "元");
                getOrder_number().setText("订单" + foodOrederDataList.get(0).getSupplierInfo().size() + "：" + foodOrederDataList.get(0).getSupplierInfo().get(0).getSupplierName());
                getOrder_price_count().setText("总价：￥" + foodOrederDataList.get(0).getSupplierInfo().get(0).getSupplierAmt() + "元");
                getPay_status().setText("未支付");
                productList = foodOrederDataList.get(0).getSupplierInfo().get(0).getProduct();
            }
        }
    }

    @Override
    protected List<OrderInfoBean> getOrderInfoList() {
        List<OrderInfoBean> infoBeen=new ArrayList<>();
        if (productList!=null&&productList.size()>0){
          for (int i=0;i<productList.size();i++){
             OrderInfoBean orderInfoBean=new OrderInfoBean();
             orderInfoBean.setCateName(productList.get(i).getCateName());
             orderInfoBean.setPrice(String.valueOf(productList.get(i).getProPrice()));
             orderInfoBean.setOrderCode("订单");
              int num=TextUtils.isEmpty(productList.get(i).getProNum())?0:Integer.parseInt(productList.get(i).getProNum());
              orderInfoBean.setNum(num);
              infoBeen.add(orderInfoBean);
          }
        }
        return infoBeen;
    }

    @Override
    public IControler getControler() {
        return FootOrderInfoControler.getControler();
    }

    /**
     * 乐老卡支付相关接口
     * @param action
     * @return
     */
    @Override
    protected boolean upLoadFinshOrder(String action) {
        return ServiceNetContant.ServiceResponseAction.UPLOAD_SERVER_ORDER_PAYMENY.equals(action);
    }
    /**
     *提交订餐相关数据
     * (addressType,payType,totalMoney,userAddressStr,userPhoneStr,isScope,Integer.parseInt(mealTime),userId,buyUserId,data);
     */
    @Override
    protected BaseOrderCate getBaseOrderCate() {
        SubShopFoodBean subShopFoodBean=new  SubShopFoodBean();
        if(foodOrederDataList!=null&&foodOrederDataList.size()>0){
            ArrayList<Bundle> data =  UploadFoodOrderInfo.foodOrderInfo(foodOrederDataList);
            subShopFoodBean.setBuyUserId(customerId);
            subShopFoodBean.setIsDistr(addressType);
            subShopFoodBean.setIsScope(isScope);
            subShopFoodBean.setMealtime(Integer.parseInt(mealTime));
            subShopFoodBean.setPhone("123456789");
            subShopFoodBean.setPayType(payType);
            subShopFoodBean.setTotalMoney(totalMoney);
            subShopFoodBean.setCofirmOrderData(data);
            subShopFoodBean.setAddressStr( getAddressType());
        }
        return subShopFoodBean;
    }

}
