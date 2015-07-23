package cuongvo.zeemitv.adapter;

import android.app.Activity;
import android.text.TextUtils;
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
import cuongvo.zeemitv.model.LivePOJO;
import cuongvo.zeemitv.util.graphic.FontsUtil;
import cuongvo.zeemitv.util.graphic.PicassoCircleTransformation;

/**
 * Created by cuongmv162 on 7/20/2015.
 */
public class LiveAdapter extends BaseAdapter {
    private static final int LAYOUT_ID = R.layout.adapter_channel_item;

    private Activity mActivity;
    private List<LivePOJO> mList;

    public LiveAdapter(Activity activity, List<LivePOJO> list){
        this.mActivity = activity;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(mList.get(position).getId());
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
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

        LivePOJO livePOJO = (LivePOJO) getItem(position);
        viewHolder.mTextViewChannelName.setText(livePOJO.getName());
        viewHolder.mTextViewFollower.setText(String.valueOf(livePOJO.getFollowersCount()).concat(" ").concat(mActivity.getString(R.string.followers)));
        viewHolder.mTextViewUserName.setText(livePOJO.getUserPOJO().getUserName());

        if(!TextUtils.isEmpty(livePOJO.getUserPOJO().getProfilePicture())) {
            Picasso.with(mActivity).load(livePOJO.getUserPOJO().getProfilePicture()).transform(new PicassoCircleTransformation()).into(viewHolder.mImageViewProfilePicture);
        }else{
            Picasso.with(mActivity).load(R.drawable.default_avatar).transform(new PicassoCircleTransformation()).into(viewHolder.mImageViewProfilePicture);
        }

        if(!TextUtils.isEmpty(livePOJO.getCoverImage())){
            Picasso.with(mActivity).load(livePOJO.getCoverImage()).into(viewHolder.mImageViewCover);
        }

        viewHolder.mRatingBar.setRating((float)livePOJO.getRating());

        return row;

    }

    public List<LivePOJO> getmList() {
        return mList;
    }

    public void setmList(List<LivePOJO> mList) {
        this.mList = mList;
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
