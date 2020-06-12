package in.mobicomly.download.mvp.m;

import org.xutils.ex.DbException;

import java.util.List;
import in.mobicomly.download.mvp.e.PlayerVideoEntity;
import in.mobicomly.download.util.DBTools;

public class PlayerVideoModelImp implements PlayerVideoModel {
    @Override
    public List<PlayerVideoEntity> findAllVideo(){
        try {
            return DBTools.getInstance().db().selector(PlayerVideoEntity.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<PlayerVideoEntity> findVideoByPath(String path) {
        try {
            return DBTools.getInstance().db().selector(PlayerVideoEntity.class).where("localPath", "=", path).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PlayerVideoEntity findVideoById(int id) {
        try {
            return DBTools.getInstance().db().findById(PlayerVideoEntity.class,id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PlayerVideoEntity saveOrUpdata(PlayerVideoEntity video) {
        try {
            DBTools.getInstance().db().saveOrUpdate(video);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return video;
    }


    @Override
    public PlayerVideoEntity upDataVideo(PlayerVideoEntity video) {
        try {
            DBTools.getInstance().db().saveOrUpdate(video);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return video;
    }

    @Override
    public Boolean deleVideo(PlayerVideoEntity video) {
        try {
            DBTools.getInstance().db().delete(video);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }
}
