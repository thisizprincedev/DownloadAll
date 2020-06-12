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

import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.mvp.e.TorrentInfoEntity;
import in.mobicomly.download.mvp.v.TorrentInfoView;
import in.mobicomly.download.util.FileTools;

public class TorrentInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<TorrentInfoEntity> list;
    private Context context;
    private TorrentInfoView torrentInfoView;
    public TorrentInfoAdapter(Context context,TorrentInfoView torrentInfoView, List<TorrentInfoEntity> list){
        this.context=context;
        this.torrentInfoView=torrentInfoView;
        this.list=list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_torrent_info, viewGroup, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final TorrentInfoEntity task=list.get(i);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                torrentInfoView.itemClick(i);
            }
        });
        TaskHolder holder=(TaskHolder)viewHolder;
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TaskHolder extends RecyclerView.ViewHolder{
        private TorrentInfoEntity task;
        private TextView fileNameText;
        private ImageView fileIcon,fileCheckBox;
        private SuperTextView fileType,fileSize,filePlayer;

        public TaskHolder(View itemView) {
            super(itemView);
            fileNameText = (TextView) itemView.findViewById(R.id.file_name);
            fileIcon = (ImageView) itemView.findViewById(R.id.file_icon);
            fileCheckBox = (ImageView) itemView.findViewById(R.id.file_check_box);
            fileSize = (SuperTextView)itemView.findViewById(R.id.file_size);
            fileType = (SuperTextView)itemView.findViewById(R.id.file_type);
            filePlayer = (SuperTextView)itemView.findViewById(R.id.file_play);
        }
        public void bind(final TorrentInfoEntity task){
            this.task=task;
            String suffix = task.getmFileName().substring(task.getmFileName().lastIndexOf(".") + 1);
            fileNameText.setText(task.getmFileName());

            fileSize.setText(FileTools.convertFileSize(task.getmFileSize()));
            fileType.setText(suffix);
            if(torrentInfoView.getIsDown()){
                fileIcon.setImageDrawable(itemView.getResources().getDrawable(FileTools.getFileIcon(task.getmFileName())));
                if(task.getCheck()){
                    fileCheckBox.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_check));
                }else{
                    fileCheckBox.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_uncheck));
                }
            }else{
                fileCheckBox.setVisibility(View.GONE);
                fileIcon.setImageDrawable(itemView.getResources().getDrawable(FileTools.getFileIcon(task.getmFileName())));
                if(FileTools.isVideoFile(task.getmFileName())){
                    if(task.getThumbnail()!=null){
                        fileIcon.setImageBitmap(task.getThumbnail());
                        filePlayer.setVisibility(View.VISIBLE);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                torrentInfoView.playerViedo(task);
                            }
                        });
                    }else{
                        filePlayer.setVisibility(View.GONE);
                    }
                }
            }


        }

    }
}
