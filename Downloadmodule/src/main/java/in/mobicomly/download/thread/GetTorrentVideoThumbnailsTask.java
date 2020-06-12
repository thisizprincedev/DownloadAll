package in.mobicomly.download.thread;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.List;

import in.mobicomly.download.listener.GetThumbnailsListener;
import in.mobicomly.download.mvp.e.TorrentInfoEntity;
import in.mobicomly.download.util.FileTools;

public class GetTorrentVideoThumbnailsTask extends AsyncTask<Object,Void,List<TorrentInfoEntity>> {
    private GetThumbnailsListener listener;
    public GetTorrentVideoThumbnailsTask(GetThumbnailsListener listener){
        this.listener=listener;
    }
    @Override
    protected List<TorrentInfoEntity> doInBackground(Object... objects) {
        List<TorrentInfoEntity> list=(List<TorrentInfoEntity>)objects[0];
        for(TorrentInfoEntity te:list){
            if(FileTools.isVideoFile(te.getmFileName())){
                Bitmap bitmap= FileTools.getVideoThumbnail(te.getPath(),250,150, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                if(bitmap!=null) {
                    te.setThumbnail(bitmap);
                }
            }
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<TorrentInfoEntity> torrentInfoEntities) {
        super.onPostExecute(torrentInfoEntities);
        listener.success(null);
    }
}
