package dq.lelaohui.com.lelaohuipad.fragement.shop.dataprovider;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import dq.lelaohui.com.lelaohuipad.bean.FoodInfoCate;
import dq.lelaohui.com.lelaohuipad.controler.FootterControler;
import dq.lelaohui.com.nettylibrary.socket.LlhResponseHandler;
import dq.lelaohui.com.nettylibrary.socket.NetManager;
import dq.lelaohui.com.nettylibrary.util.ServiceNetContant;
import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.bean.FoodInfoData;

/**
 * Created by ThinkPad on 2016/12/1.
 */

public class FootDataManager extends DataManager {
    private String TAG=getClass().getSimpleName();
    private FootterControler fc;
    private NetManager.ProgressBarListener progressBarListener;
    private ArrayBlockingQueue<Future<String>> queue=new ArrayBlockingQueue<Future<String>>(3);
    private volatile  boolean isStart=true;
    private ReentrantLock reentrantLock=null;
    private FootDataListener dataListener;

    public FootDataListener getDataListener() {
        return dataListener;
    }

    public void setDataListener(FootDataListener dataListener) {
        this.dataListener = dataListener;
    }

    private Condition wati=null;
    private  Thread mThread=new Thread(){
        @Override
        public void run() {

           while(isStart){
               if(queue.isEmpty()){
                   if(progressBarListener!=null){
                       progressBarListener.hideProgress();
                   }
               }
               Future<String> task= null;
               try {
                   task = queue.take();
                   if(task!=null){
                       if(task.isDone()){
                           if( dataListener!=null){
                               try {
                                   dataListener.dataChanager(task.get());
                               } catch (InterruptedException e) {
                                   e.printStackTrace();
                               } catch (ExecutionException e) {
                                   e.printStackTrace();
                               }
                           }

                       }else if(!task.isCancelled()){
                           queue.offer(task);
                       }
                   }
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }


           }

        }
    };
    /**
     * 今日，明日，后日
     */
    String[] mealTime = {"0", "1", "2"};

    public FootDataManager(FootterControler fc, NetManager.ProgressBarListener progressBarListener) {
        this.fc = fc;
        this.progressBarListener = progressBarListener;
        reentrantLock=new ReentrantLock();
        wati=reentrantLock.newCondition();
        mThread.start();
    }

    public void requestFoodInfo(final String mealTime) throws InterruptedException {
        if (dataListener == null) {
            throw new RuntimeException("没有设置回调监听。。。。");
        }
        Cursor cursor= fc.getFoodTypeCursor(mealTime);
        Log.i(TAG, "requestFoodInfo: "+mealTime+"="+cursor.getCount());
        if(cursor!=null&&cursor.getCount()!=0){
            dataListener.dataChanager(mealTime);
            cursor.close();
            return;
        }
        if(progressBarListener!=null){
            progressBarListener.showProgress();
        }


        Future<String> cursorFuture= (Future<String>)  addTask(new Callable() {
            @Override
            public String call() throws Exception {
                reentrantLock.lock();
                try{
                    String scrole=mealTime;
                    fc.doQueryFoodInfo(scrole);
                    wati.await(30*1000, TimeUnit.MILLISECONDS);
                    return  mealTime;
                }catch (Exception e ){
                    e.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }
                return null;
            }
        });
        queue.put(cursorFuture);
    }

    @Override
    public boolean intercept(Bundle responseData) {
        if (responseData == null) {
            return false;
        }
        String action = fc.getResponseAction(responseData);
        if (TextUtils.isEmpty(action)) {
            return false;
        }
        if (ServiceNetContant.ServiceResponseAction.QUERY_FOOD_INFO_RESPONSE.equals(action)) {
            String body = fc.getResponseBody(responseData);
            Log.i(TAG, "intercept.info===" + responseData);
            FoodInfoCate foodInfoCate = (FoodInfoCate) fc.getJsonToObject(body, FoodInfoCate.class);

            String userData=responseData.getString(LlhResponseHandler.Respon_Key.USERDATA);
            if(TextUtils.isEmpty(userData)){
                userData="0";
            }
            if (foodInfoCate.getCode() == 0) {
                List<FoodInfoData> data = foodInfoCate.getData();
                for (FoodInfoData infoCate:data
                     ) {

                    infoCate.setIsScope(userData);
                }
                fc.instertData(data);
            }
            reentrantLock.lock();
            try {
                wati.signal();
            }finally {
                reentrantLock.unlock();
            }

        }
        return true;
    }
    public void onDestory(){
        while(!queue.isEmpty()){
            Future<String> task=queue.poll();
            if(!task.isDone()){
                if(!task.isCancelled()){
                    task.cancel(true);
                }
            }
        }
        isStart=false;
    }
    public interface  FootDataListener{
        void dataChanager(String id);
    }
}
