package in.mobicomly.download.mvp.v;


public interface DownloadManagementView {
    void addTaskSuccess();
    void addTaskFail(String msg);
    void updataApp(String version,String url,String content);
}
