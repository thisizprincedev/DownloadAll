package in.mobicomly.download.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coorchice.library.SuperTextView;
import com.daimajia.numberprogressbar.NumberProgressBar;

import java.math.BigDecimal;
import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.e.DownloadTaskEntity;
import in.mobicomly.download.mvp.v.DownLoadIngView;
import in.mobicomly.download.util.FileTools;
import in.mobicomly.download.util.TimeUtil;

public class DownloadingListAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<DownloadTaskEntity> list;
    private Context context;
    private DownLoadIngView downLoadIngView;
    private  Bitmap bitmap=null;
    public DownloadingListAdapter(Context context,DownLoadIngView downLoadIngView, List<DownloadTaskEntity> list){
        this.context=context;
        this.downLoadIngView=downLoadIngView;
        this.list=list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_downloading, viewGroup, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final DownloadTaskEntity task=list.get(i);
        TaskHolder holder=(TaskHolder)viewHolder;
        holder.bind(task);
        holder.onClick();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TaskHolder extends RecyclerView.ViewHolder{
        private DownloadTaskEntity task;
        private TextView fileNameText,downSize,downSpeed,downCDNSpeed,RemainingTime;
        private SuperTextView downStatus;
        private ImageView startTask,deleTask,fileIcon,fileIcon2;
        private NumberProgressBar progressBar;

        public TaskHolder(View itemView) {
            super(itemView);
            fileNameText = (TextView) itemView.findViewById(R.id.file_name);
            downSize = (TextView) itemView.findViewById(R.id.down_size);
            downSpeed = (TextView) itemView.findViewById(R.id.down_speed);
            downCDNSpeed = (TextView) itemView.findViewById(R.id.down_cdnspeed);
            RemainingTime = (TextView) itemView.findViewById(R.id.remaining_time);
            startTask = (ImageView) itemView.findViewById(R.id.start_task);
            deleTask = (ImageView) itemView.findViewById(R.id.dele_task);
            fileIcon = (ImageView) itemView.findViewById(R.id.file_icon);
            fileIcon2 = (ImageView) itemView.findViewById(R.id.file_icon2);
            progressBar = (NumberProgressBar)itemView.findViewById(R.id.number_progress_bar);
            downStatus = (SuperTextView)itemView.findViewById(R.id.down_status);
        }
        public void bind(DownloadTaskEntity task){
            this.task=task;
            fileNameText.setText(task.getmFileName());
            if(task.getFile()) {
                fileIcon.setImageDrawable(itemView.getResources().getDrawable(FileTools.getFileIcon(task.getmFileName())));
            }else{
                fileIcon.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_floder));
            }
//            if(task.getThumbnail()!=null){
//                fileIcon.setImageBitmap(task.getThumbnail());
//            }
            downSize.setText(String.format(itemView.getResources().getString(R.string.down_count),
                    FileTools.convertFileSize(task.getmFileSize()), FileTools.convertFileSize(task.getmDownloadSize())));
            downSpeed.setText(String.format(itemView.getResources().getString(R.string.down_speed),
                    FileTools.convertFileSize(task.getmDownloadSpeed())));
            downCDNSpeed.setText(String.format(itemView.getResources().getString(R.string.down_speed_up),
                    FileTools.convertFileSize(task.getmDCDNSpeed())));
            if(task.getmFileSize()!=0 && task.getmDownloadSize()!=0) {
                long speed=task.getmDownloadSpeed()==0?1:task.getmDownloadSpeed();
                long time = (task.getmFileSize() - task.getmDownloadSize()) / speed;
                RemainingTime.setText(String.format(itemView.getResources().getString(R.string.remaining_time), TimeUtil.formatFromSecond((int) time)));
            }
            if(task.getThumbnailPath()!=null){
                fileIcon2.setVisibility(View.VISIBLE);
            }else{
                fileIcon2.setVisibility(View.GONE);
            }

//            String videoPath=task.getLocalPath()+ File.separator+task.getmFileName();
//            bitmap = FileTools.getVideoThumbnail(videoPath, 200, 200, MediaStore.Video.Thumbnails.MICRO_KIND);
//           if (bitmap != null) {
//                task.setThumbnail(bitmap);
//               fileIcon2.setVisibility(View.VISIBLE);
//            }

            if(task.getmDownloadSize()!=0 && task.getmFileSize()!=0) {
                double f1 = new BigDecimal((float) task.getmDownloadSize() / task.getmFileSize()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                progressBar.setProgress((int) (f1 * 100));
                if(FileTools.isVideoFile(task.getmFileName()) && (f1 * 100)>1){
                    //fileIcon2.setVisibility(View.VISIBLE);
                }

            }else{
                progressBar.setProgress(0);
            }
            //if(SystemConfig.getNetType())
            if((task.getmTaskStatus()== Const.DOWNLOAD_STOP) || (task.getmTaskStatus()==Const.DOWNLOAD_CONNECTION && task.getTaskId()==0)){
                startTask.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_download));
                downStatus.setText(R.string.is_stop);
            }else if( task.getmTaskStatus()==Const.DOWNLOAD_CONNECTION ){
                startTask.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_connent));
                downStatus.setText("连接中");
            }else if( task.getmTaskStatus()==Const.DOWNLOAD_FAIL ){
                startTask.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_fail));
                downStatus.setText("下载失败");
            }else if(task.getmTaskStatus()== Const.DOWNLOAD_WAIT){
                startTask.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_wait));
                downStatus.setText(R.string.wait_down);
            }else {
                startTask.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_stop));
                downStatus.setText(R.string.downloading);
            }
        }

        public void onClick(){
            startTask.setOnClickListener(listener);
            deleTask.setOnClickListener(listener);
            fileIcon2.setOnClickListener(listener);
            fileIcon.setOnClickListener(listener);
        }

        private View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.start_task) {
                    if (task.getmTaskStatus() == Const.DOWNLOAD_FAIL) {
                        downLoadIngView.sopTask(task);
                        downLoadIngView.startTask(task);
                    } else if ((task.getmTaskStatus() == Const.DOWNLOAD_STOP) ||
                            (task.getmTaskStatus() == Const.DOWNLOAD_CONNECTION && task.getTaskId() == 0) ||
                            task.getmTaskStatus() == Const.DOWNLOAD_WAIT) {
                        downLoadIngView.startTask(task);
                    } else {
                        downLoadIngView.sopTask(task);
                    }
                } else if (id == R.id.dele_task) {
                    downLoadIngView.deleTask(task);
                } else if (id == R.id.file_icon2) {
                    downLoadIngView.openFile(task);
                } else if (id == R.id.file_icon) {
                    if (!task.getFile())
                        downLoadIngView.openFile(task);
                }
            }
        };
    }
}
