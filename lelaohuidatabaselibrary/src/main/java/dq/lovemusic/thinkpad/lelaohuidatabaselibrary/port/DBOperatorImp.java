package dq.lovemusic.thinkpad.lelaohuidatabaselibrary.port;

import org.greenrobot.greendao.AbstractDaoSession;

import java.util.List;

/**
 * Created by ThinkPad on 2016/10/14.
 */

public interface DBOperatorImp {
    Object queryData(Object t);
    List<Object> queryDataList(Object t);
     void updateData(Object t);
     void intsert(Object t);
     void delete(Object t);
}
