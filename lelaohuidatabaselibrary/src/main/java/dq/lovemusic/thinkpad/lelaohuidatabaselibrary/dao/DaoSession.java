package dq.lovemusic.thinkpad.lelaohuidatabaselibrary.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.bean.ProCateMenuService;
import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.bean.ProCateService;
import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.bean.SerInitProPack;
import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.bean.SerInitProPackDetailListBean;
import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.bean.FoodInfoData;

import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.dao.ProCateMenuServiceDao;
import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.dao.ProCateServiceDao;
import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.dao.SerInitProPackDao;
import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.dao.SerInitProPackDetailListBeanDao;
import dq.lovemusic.thinkpad.lelaohuidatabaselibrary.dao.FoodInfoDataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig proCateMenuServiceDaoConfig;
    private final DaoConfig proCateServiceDaoConfig;
    private final DaoConfig serInitProPackDaoConfig;
    private final DaoConfig serInitProPackDetailListBeanDaoConfig;
    private final DaoConfig foodInfoDataDaoConfig;

    private final ProCateMenuServiceDao proCateMenuServiceDao;
    private final ProCateServiceDao proCateServiceDao;
    private final SerInitProPackDao serInitProPackDao;
    private final SerInitProPackDetailListBeanDao serInitProPackDetailListBeanDao;
    private final FoodInfoDataDao foodInfoDataDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        proCateMenuServiceDaoConfig = daoConfigMap.get(ProCateMenuServiceDao.class).clone();
        proCateMenuServiceDaoConfig.initIdentityScope(type);

        proCateServiceDaoConfig = daoConfigMap.get(ProCateServiceDao.class).clone();
        proCateServiceDaoConfig.initIdentityScope(type);

        serInitProPackDaoConfig = daoConfigMap.get(SerInitProPackDao.class).clone();
        serInitProPackDaoConfig.initIdentityScope(type);

        serInitProPackDetailListBeanDaoConfig = daoConfigMap.get(SerInitProPackDetailListBeanDao.class).clone();
        serInitProPackDetailListBeanDaoConfig.initIdentityScope(type);

        foodInfoDataDaoConfig = daoConfigMap.get(FoodInfoDataDao.class).clone();
        foodInfoDataDaoConfig.initIdentityScope(type);

        proCateMenuServiceDao = new ProCateMenuServiceDao(proCateMenuServiceDaoConfig, this);
        proCateServiceDao = new ProCateServiceDao(proCateServiceDaoConfig, this);
        serInitProPackDao = new SerInitProPackDao(serInitProPackDaoConfig, this);
        serInitProPackDetailListBeanDao = new SerInitProPackDetailListBeanDao(serInitProPackDetailListBeanDaoConfig, this);
        foodInfoDataDao = new FoodInfoDataDao(foodInfoDataDaoConfig, this);

        registerDao(ProCateMenuService.class, proCateMenuServiceDao);
        registerDao(ProCateService.class, proCateServiceDao);
        registerDao(SerInitProPack.class, serInitProPackDao);
        registerDao(SerInitProPackDetailListBean.class, serInitProPackDetailListBeanDao);
        registerDao(FoodInfoData.class, foodInfoDataDao);
    }
    
    public void clear() {
        proCateMenuServiceDaoConfig.clearIdentityScope();
        proCateServiceDaoConfig.clearIdentityScope();
        serInitProPackDaoConfig.clearIdentityScope();
        serInitProPackDetailListBeanDaoConfig.clearIdentityScope();
        foodInfoDataDaoConfig.clearIdentityScope();
    }

    public ProCateMenuServiceDao getProCateMenuServiceDao() {
        return proCateMenuServiceDao;
    }

    public ProCateServiceDao getProCateServiceDao() {
        return proCateServiceDao;
    }

    public SerInitProPackDao getSerInitProPackDao() {
        return serInitProPackDao;
    }

    public SerInitProPackDetailListBeanDao getSerInitProPackDetailListBeanDao() {
        return serInitProPackDetailListBeanDao;
    }

    public FoodInfoDataDao getFoodInfoDataDao() {
        return foodInfoDataDao;
    }

}
