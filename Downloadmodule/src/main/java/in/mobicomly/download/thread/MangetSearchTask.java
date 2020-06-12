package in.mobicomly.download.thread;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import in.mobicomly.download.listener.MagnetSearchListener;
import in.mobicomly.download.mvp.e.MagnetInfo;
import in.mobicomly.download.mvp.e.MagnetSearchBean;
import in.mobicomly.download.mvp.m.MagnetWServiceModel;
import in.mobicomly.download.mvp.m.MagnetWServiceModelImp;

public class MangetSearchTask extends AsyncTask<MagnetSearchBean, Void, List<MagnetInfo>> {
    private MagnetWServiceModel magnetWService;
    private MagnetSearchListener listener;

    public MangetSearchTask(MagnetSearchListener listener){
        this.listener=listener;
        magnetWService=new MagnetWServiceModelImp();

    }

    @Override
    protected List<MagnetInfo> doInBackground(MagnetSearchBean... parm) {
        List<MagnetInfo> infos = new ArrayList<MagnetInfo>();
        try {
            infos = magnetWService.parser(parm[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return infos;
    }
    @Override
    protected void onPostExecute(List<MagnetInfo> info) {
        listener.success(info);
       // super.onPostExecute(info);
    }
}