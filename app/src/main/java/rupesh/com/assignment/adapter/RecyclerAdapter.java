package  rupesh.com.assignment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import rupesh.com.assignment.R;
import rupesh.com.assignment.models.Dictionary;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Dictionary> resultdata;

    public RecyclerAdapter(List<Dictionary> listdata) {
        this.resultdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.card_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textview.setText(resultdata.get(position).getTitle());
            holder.textViewValue.setText(resultdata.get(position).getDescription());
            String url = resultdata.get(position).getImageHref();
            // validate url
            if (url != null && URLUtil.isValidUrl(url)) {
                Picasso.get().cancelRequest(holder.imageView);
                Picasso.get().load(url).into(holder.imageView);
            } else {
                Picasso.get().load(R.color.colorPrimary).into(holder.imageView);
            }
    }

    @Override
    public int getItemCount() {
        return resultdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textview;
        public TextView textViewValue;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textview = (TextView) itemView.findViewById(R.id.tv_tittle);
            this.textViewValue = (TextView) itemView.findViewById(R.id.description);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_pic);
        }
    }
}