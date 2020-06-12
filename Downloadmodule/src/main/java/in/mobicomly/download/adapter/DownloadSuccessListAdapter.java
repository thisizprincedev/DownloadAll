package in.mobicomly.download.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coorchice.library.SuperTextView;

import org.xutils.x;

import java.io.File;
import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.common.Const;
import in.mobicomly.download.mvp.e.DownloadTaskEntity;
import in.mobicomly.download.mvp.v.DownLoadSuccessView;
import in.mobicomly.download.util.FileTools;
import in.mobicomly.download.util.Util;

public class DownloadSuccessListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<DownloadTaskEntity> list;
    private Context context;
    private DownLoadSuccessView downLoadSuccessView;
    public DownloadSuccessListAdapter(Context context, DownLoadSuccessView downLoadSuccessView, List<DownloadTaskEntity> list){
        this.context=context;
        this.downLoadSuccessView=downLoadSuccessView;
        this.list=list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_download_success, viewGroup, false);
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
        private TextView fileNameText,downSize;
        private ImageView fileIcon,deleTask;
        private SuperTextView btnOpen,fileIsDele;

        public TaskHolder(View itemView) {
            super(itemView);
            fileNameText = (TextView) itemView.findViewById(R.id.file_name);
            downSize = (TextView) itemView.findViewById(R.id.down_size);
            fileIcon = (ImageView) itemView.findViewById(R.id.file_icon);
            deleTask = (ImageView) itemView.findViewById(R.id.dele_task);
            btnOpen = (SuperTextView)itemView.findViewById(R.id.btn_open);
            fileIsDele = (SuperTextView)itemView.findViewById(R.id.file_is_dele);
        }
        public void bind(DownloadTaskEntity task){
            this.task=task;
            String filePath=task.getLocalPath()+ File.separator+task.getmFileName();
            fileNameText.setText(task.getmFileName());

            if(task.getThumbnailPath()!=null && FileTools.isVideoFile(filePath)){
                x.image().bind(fileIcon,task.getThumbnailPath());
            }else{
                String filename=task.getFile()?task.getmFileName():"";
                fileIcon.setImageDrawable(itemView.getResources().getDrawable(FileTools.getFileIcon(filename)));
            }
            downSize.setText(FileTools.convertFileSize(task.getmDownloadSize()));
            if(FileTools.exists(filePath)){
                fileIsDele.setVisibility(View.GONE);
                btnOpen.setVisibility(View.VISIBLE);
                fileNameText.setTextColor(itemView.getResources().getColor(R.color.dimgray));
                downSize.setTextColor(itemView.getResources().getColor(R.color.gray_8f));
                String suffix = Util.getFileSuffix(task.getmFileName());
                if(FileTools.isVideoFile(task.getmFileName())){
                    btnOpen.setText(itemView.getResources().getString(R.string.play));
                }else if("TORRENT".equals(suffix) || "APK".equals(suffix) || (!task.getFile() && task.getTaskType()== Const.BT_DOWNLOAD)){
                    btnOpen.setText(itemView.getResources().getString(R.string.open));
                }else{
                    btnOpen.setVisibility(View.INVISIBLE);
                }
            }else if(task.getFile() && !FileTools.exists(filePath)){
                fileIsDele.setVisibility(View.VISIBLE);
                fileNameText.setTextColor(itemView.getResources().getColor(R.color.gray_cc));
                downSize.setTextColor(itemView.getResources().getColor(R.color.gray_cc));
                btnOpen.setText("重新下载");
                btnOpen.setVisibility(View.VISIBLE);
            }else if(!task.getFile()){
                btnOpen.setVisibility(View.VISIBLE);
            }

        }

        public void onClick(){
            btnOpen.setOnClickListener(listener);
            deleTask.setOnClickListener(listener);
        }

        private View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.btn_open) {
                    downLoadSuccessView.openFile(task);
                } else if (id == R.id.dele_task) {
                    downLoadSuccessView.deleTask(task);
                }
            }
        };
    }
}
