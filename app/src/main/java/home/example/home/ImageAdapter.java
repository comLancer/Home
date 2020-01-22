package home.example.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private final FirebaseListView mContext;
    private ArrayList<Model> mItems;

    public ImageAdapter(FirebaseListView context) {
        mContext = context;
        mItems = new ArrayList<>();
    }

    public void updateImageArrayList(ArrayList<Model> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup viewGroup) {
        if (itemView == null) {
            itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item, viewGroup, false);
        }
        ImageView ivProfile = itemView.findViewById(R.id.imgbtn_url_profile);

        EditText edTag = itemView.findViewById(R.id.et_edit_tag);
        EditText edName = itemView.findViewById(R.id.et_edit_name);
        EditText edInfo = itemView.findViewById(R.id.et_edit_info);
       /* TextView tvEmail = itemView.findViewById(R.id.tv_email);
        TextView tvPhone = itemView.findViewById(R.id.tvPhone);*/
        Model editProfile = (Model) getItem(position);


        edTag.setText(editProfile.getTag());

        // tvPhone.setText(user.getPhone()+"");


        Glide
                .with(mContext)
                .load(editProfile.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(ivProfile);

        return itemView;
    }
}

