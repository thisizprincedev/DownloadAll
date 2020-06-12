package in.mobicomly.download.mvp.e;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "TorrentTask")
public class TorrentTaskEntity {
    @Column(name = "id", isId = true,autoGen = true)
    private int id;
    @Column(name = "taskId")
    private int downTaskId;
    @Column(name = "taskId")
    private String baseFolder;
    @Column(name = "taskId")
    private String subPath;
    @Column(name = "taskId")
    private long fileSize;
    @Column(name = "taskId")
    private String fileName;
    @Column(name = "taskId")
    private int fileIndex;
}
