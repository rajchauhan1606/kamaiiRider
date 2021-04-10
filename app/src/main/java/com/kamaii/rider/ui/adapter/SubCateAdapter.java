package com.kamaii.rider.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kamaii.rider.R;
import com.kamaii.rider.interfacess.OnDataErrorListener;
import com.kamaii.rider.interfacess.OnSelectedItemListener;
import com.kamaii.rider.ui.models.SubCateModel;

import java.util.ArrayList;
import java.util.List;

public class SubCateAdapter extends RecyclerView.Adapter<SubCateAdapter.ViewHolder>  {

    private List<SubCateModel> newsPaperModelList;
    private Context context;
    private ArrayList<Integer> mSectionPositions;
    private OnSelectedItemListener onSelectedItemListener;
    private OnDataErrorListener dataErrorListener;

    //int[] rainbow;
    public SubCateAdapter(Context context, List<SubCateModel> newsPaperModelList, OnSelectedItemListener onSelectedItemListener) {
        this.context = context;
        this.newsPaperModelList = newsPaperModelList;

        this.onSelectedItemListener = onSelectedItemListener;


      //  rainbow = context.getResources().getIntArray(R.array.gradientcolors);
    }

    @Override
    public int getItemCount() {
        return newsPaperModelList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_cate, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        final int i=position;
        holder.txt_contain.setText(newsPaperModelList.get(position).getCat_name());
        Glide.with(this.context).load(newsPaperModelList.get(position).getImage()).into(holder.img_sub_cat);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Intent intent=new Intent(context, DiscoverActivity.class);
                intent.putExtra(Consts.SUB_CATEGORY_ID,newsFilterdList.get(position).getId());
                intent.putExtra(Consts.Sub_CAT_NAME,newsFilterdList.get(position).getCat_name());
                context.startActivity(intent);*/
                onSelectedItemListener.setOnClick(newsPaperModelList.get(position).getId(),i,newsPaperModelList.get(position).getCat_name(),"","");
            }
        });


    }
    public void setDataErrorListener(OnDataErrorListener dataErrorListener) {
        this.dataErrorListener = dataErrorListener;
    }
    public void update(List<SubCateModel> newsPaperModelList)
    {
        this.newsPaperModelList.addAll(newsPaperModelList);
        notifyDataSetChanged();
    }

    public void notifyDataChanged(ArrayList<SubCateModel> list)
    {
        newsPaperModelList=list;
      //  newsPaperModelList.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_contain;
        ImageView img_sub_cat;
        LinearLayout item_layout;
        ViewHolder(View itemView) {
            super(itemView);
            txt_contain = itemView.findViewById(R.id.txt_contain);
            img_sub_cat = itemView.findViewById(R.id.img_sub_cat);

            //txt_contain.setTypeface(AppController.getInstance().getTypeface("Poppins-SemiBold.otf"));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}

