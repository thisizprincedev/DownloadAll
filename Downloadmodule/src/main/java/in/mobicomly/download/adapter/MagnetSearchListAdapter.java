package in.mobicomly.download.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.mobicomly.download.R;
import in.mobicomly.download.mvp.e.MagnetInfo;
import in.mobicomly.download.mvp.v.MagnetSearchView;

public class MagnetSearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<MagnetInfo> list;
    private Context context;
    private MagnetSearchView magnetSearchView;
    public MagnetSearchListAdapter(Context context, MagnetSearchView magnetSearchView, List<MagnetInfo> list){
        this.context=context;
        this.magnetSearchView=magnetSearchView;
        this.list=list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_search_magnet_result, viewGroup, false);
        return new MagnetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MagnetInfo task=list.get(i);
        MagnetHolder holder=(MagnetHolder)viewHolder;
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MagnetHolder extends RecyclerView.ViewHolder{
        private TextView magnetNameText,magnetSize,magnetDate,magnetHot;

        public MagnetHolder(View itemView) {
            super(itemView);
            magnetNameText = (TextView) itemView.findViewById(R.id.magnet_name);
            magnetSize = (TextView) itemView.findViewById(R.id.magnet_size);
            magnetDate = (TextView) itemView.findViewById(R.id.magnet_date);
            magnetHot = (TextView) itemView.findViewById(R.id.magnet_hot);
        }
        public void bind(final MagnetInfo magnet){
            magnetNameText.setText(magnet.getName());
            magnetDate.setText(String.format(itemView.getResources().getString(R.string.magnet_date),magnet.getCount()));
            magnetSize.setText(String.format(itemView.getResources().getString(R.string.magnet_size),magnet.getFormatSize()));
            magnetHot.setText(String.format(itemView.getResources().getString(R.string.magnet_hot),magnet.getHot()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    magnetSearchView.moreOption(magnet);
                }
            });
        }

    }
}
