package cuongvo.zeemitv.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cuongvo.zeemitv.R;
import cuongvo.zeemitv.model.ChannelPOJO;
import cuongvo.zeemitv.util.graphic.FontsUtil;
import cuongvo.zeemitv.util.graphic.PicassoCircleTransformation;

/**
 * Created by cuongmv162 on 7/20/2015.
 */
public class ChannelAdapter extends BaseAdapter {
    private static final int LAYOUT_ID = R.layout.adapter_channel_item;

    private Activity mActivity;
    private List<ChannelPOJO> mList;

    public ChannelAdapter(Activity activity, List<ChannelPOJO> list){
        this.mActivity = activity;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.valueOf(mList.get(i).getId());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        RelativeLayout row = null;

        if (view == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            row = (RelativeLayout) inflater.inflate(LAYOUT_ID, null);
            viewHolder = new ViewHolder();
            viewHolder.mImageViewProfilePicture = (ImageView)row.findViewById(R.id.adapter_profile_picture);
            viewHolder.mRatingBar = (RatingBar)row.findViewById(R.id.adapter_rating);
            viewHolder.mTextViewChannelName = (TextView)row.findViewById(R.id.adapter_channel_name);
            viewHolder.mTextViewFollower = (TextView)row.findViewById(R.id.adapter_followers);
            viewHolder.mTextViewUserName = (TextView)row.findViewById(R.id.adapter_user_name);
            viewHolder.mImageViewCover = (ImageView)row.findViewById(R.id.adapter_live_cover_image);
            FontsUtil.setViewGroupTypeface((ViewGroup) row, mActivity);

            row.setTag(viewHolder);
        } else {
            row = (RelativeLayout)view;
            viewHolder = (ViewHolder)row.getTag();
        }

        ChannelPOJO channelPOJO = (ChannelPOJO) getItem(i);
        viewHolder.mTextViewChannelName.setText(channelPOJO.getName());
        viewHolder.mTextViewFollower.setText(String.valueOf(channelPOJO.getFollowersCount()).concat(" ").concat(mActivity.getString(R.string.followers)));
        viewHolder.mTextViewUserName.setText(channelPOJO.getUserPOJO().getUserName());

        if(!TextUtils.isEmpty(channelPOJO.getUserPOJO().getProfilePicture())) {
            Picasso.with(mActivity).load(channelPOJO.getUserPOJO().getProfilePicture()).transform(new PicassoCircleTransformation()).into(viewHolder.mImageViewProfilePicture);
        }else{
            Picasso.with(mActivity).load(R.drawable.default_avatar).transform(new PicassoCircleTransformation()).into(viewHolder.mImageViewProfilePicture);
        }

        if(!TextUtils.isEmpty(channelPOJO.getCoverImage())){
            Picasso.with(mActivity).load(channelPOJO.getCoverImage()).into(viewHolder.mImageViewCover);
        }

        Log.d("CuongCuongCuong", "channelPOJO.getRating " + channelPOJO.getRating());
        viewHolder.mRatingBar.setRating((float) channelPOJO.getRating());

        return row;
    }

    public List<ChannelPOJO> getmList() {
        return mList;
    }


    class ViewHolder {
        TextView mTextViewChannelName;
        TextView mTextViewUserName;
        TextView mTextViewFollower;
        RatingBar mRatingBar;
        ImageView mImageViewProfilePicture;
        ImageView mImageViewCover;
    }
}
