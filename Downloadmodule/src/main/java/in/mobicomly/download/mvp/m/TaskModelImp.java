package in.mobicomly.download.mvp.m;

import org.xutils.ex.DbException;

import java.util.List;

import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.e.DownloadTaskEntity;
import in.mobicomly.download.util.DBTools;

public class TaskModelImp implements TaskModel {
    @Override
    public List<DownloadTaskEntity> findAllTask(){
        try {
            return DBTools.getInstance().db().selector(DownloadTaskEntity.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public DownloadTaskEntity findTaskById(int id) {
        try {
            return DBTools.getInstance().db().findById(DownloadTaskEntity.class,id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<DownloadTaskEntity> findTaskByUrl(String url) {
        try {
            return DBTools.getInstance().db().selector(DownloadTaskEntity.class).where("url", "=", url).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DownloadTaskEntity> findTaskByHash(String hash) {
        try {
            return DBTools.getInstance().db().selector(DownloadTaskEntity.class).where("hash", "=", hash).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DownloadTaskEntity> findLoadingTask() {
        try {
            return DBTools.getInstance().db().selector(DownloadTaskEntity.class)
                    .where("mTaskStatus", "<>", Const.DOWNLOAD_SUCCESS)
                    .orderBy("createDate",true)
                    .findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DownloadTaskEntity> findDowningTask() {
        try {
            return DBTools.getInstance().db().selector(DownloadTaskEntity.class)
                    .where("mTaskStatus", "in",new int[]{ Const.DOWNLOAD_LOADING,Const.DOWNLOAD_CONNECTION})
                    .and("taskId", "<>", 0)
                   // .and("mTaskStatus", "=", Const.DOWNLOAD_FAIL)
                    .findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DownloadTaskEntity> findSuccessTask() {
        try {
            return DBTools.getInstance().db().selector(DownloadTaskEntity.class)
                    .where("mTaskStatus", "=", Const.DOWNLOAD_SUCCESS)
                    .orderBy("createDate",true)
                    .findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DownloadTaskEntity upDataTask(DownloadTaskEntity task) {
        try {
            DBTools.getInstance().db().saveOrUpdate(task);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public Boolean deleTask(DownloadTaskEntity task) {
        try {
            DBTools.getInstance().db().delete(task);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }
}
