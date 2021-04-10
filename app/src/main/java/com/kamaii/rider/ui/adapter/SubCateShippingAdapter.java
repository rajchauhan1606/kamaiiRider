package com.kamaii.rider.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kamaii.rider.DTO.ShippingDTO;
import com.kamaii.rider.R;
import com.kamaii.rider.interfacess.OnDataErrorListener;
import com.kamaii.rider.interfacess.OnSelectedItemListener;
import com.kamaii.rider.ui.models.SubCateModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubCateShippingAdapter extends RecyclerView.Adapter<SubCateShippingAdapter.ViewHolder>  {

    private List<SubCateModel> newsPaperModelList;
    private Context context;
    private ArrayList<Integer> mSectionPositions;
    private OnSelectedItemListener onSelectedItemListener;
    private OnDataErrorListener dataErrorListener;
    private HashMap<String, String> parmsshipping = new HashMap<>();
    ShippingDTO shippingDTO;
    private ArrayList<ShippingDTO> shippingDTOArrayList=new ArrayList<>();
    String userid="",shipping="",my_location="";


    //int[] rainbow;
    public SubCateShippingAdapter(Context context, List<SubCateModel> newsPaperModelList, OnSelectedItemListener onSelectedItemListener, String userid) {
        this.context = context;
        this.newsPaperModelList = newsPaperModelList;
        this.onSelectedItemListener = onSelectedItemListener;
        this.userid=userid;


      //  rainbow = context.getResources().getIntArray(R.array.gradientcolors);
    }

    @Override
    public int getItemCount() {
        return newsPaperModelList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_cate_shipping, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position)
    {
        final int i=position;
        holder.txt_contain.setText(newsPaperModelList.get(position).getCat_name());
        Glide.with(this.context).load(newsPaperModelList.get(position).getImage()).into(holder.img_sub_cat);


/*
        if(shippingDTOArrayList.size()>0){
            if(shippingDTOArrayList.get(position).getSub_cat_id().equalsIgnoreCase(newsPaperModelList.get(position).getId())){
                if(shippingDTOArrayList.get(position).getShipping().equalsIgnoreCase("1")){
                    holder.imgshipping.setImageResource(R.drawable.right);
                    shipping="1";

                }

                if(shippingDTOArrayList.get(position).getMy_location().equalsIgnoreCase("1")){
                    holder.imgmylocation.setImageResource(R.drawable.right);
                    my_location="1";

                }
            }

        }*/

       /* parmsshipping.put(Consts.USER_ID, userid);
        parmsshipping.put(Consts.SUB_CAT_ID,  newsPaperModelList.get(position).getId());
        progressDialog=new ProgressDialog(context);
       // progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Please Wait");
        //progressDialog.show();
        progressDialog.setCancelable(false);
     //   ProjectUtils.showProgressDialog(context, true, context.getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_SERVICE_SHIPPING, parmsshipping, context).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
             //   progressDialog.cancel();
             //   progressDialog.dismiss();
                if (flag) {
                    try {

                        // shippingDTO = new Gson().fromJson(response.getJSONObject("data").toString(), ShippingDTO.class);




                        JSONArray jsonarray = response.getJSONArray("data");

                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject c = jsonarray.getJSONObject(i);


                            String id=c.getString("id");
                            String sub_cat_id=c.getString("sub_cat_id");
                            shipping=c.getString("shipping");
                            my_location=c.getString("my_location");


                            if(newsPaperModelList.get(i).getId().equalsIgnoreCase(sub_cat_id)){



                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // ProjectUtils.showToast( Booking.this, msg);

                } else {
                    // ProjectUtils.showToast( Booking.this, msg);
                }


            }
        });*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectedItemListener.setOnClick(newsPaperModelList.get(i).getId(),i,newsPaperModelList.get(i).getCat_name(),shipping,my_location);
               // shipping="1";
                //my_location="1";

            }
        });


    }


    public void notifyDataChanged(ArrayList<SubCateModel> list)
    {
        newsPaperModelList=list;
      //  newsPaperModelList.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_contain;
        ImageView img_sub_cat,imgshipping,imgmylocation;


        ViewHolder(View itemView) {
            super(itemView);
            txt_contain = itemView.findViewById(R.id.txt_contain);
            img_sub_cat = itemView.findViewById(R.id.img_sub_cat);
            imgshipping = itemView.findViewById(R.id.imgshipping);
            imgmylocation = itemView.findViewById(R.id.imgmylocation);

            //txt_contain.setTypeface(AppController.getInstance().getTypeface("Poppins-SemiBold.otf"));
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }





}

