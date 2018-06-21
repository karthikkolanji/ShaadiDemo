package com.startedup.base.ui.shaadi;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.startedup.base.R;
import com.startedup.base.model.shaadi.ResultsItem;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<ResultsItem> list;
  private int lastPosition = -1;
  private UserRemoveListener userRemoveListener;

  UserAdapter(List<ResultsItem> list, UserRemoveListener userRemoveListener) {
    this.list = list;
    this.userRemoveListener = userRemoveListener;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemLayoutView;

    itemLayoutView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_user, parent, false);
    return new UserViewHolder(itemLayoutView);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    UserViewHolder viewHolder = (UserViewHolder) holder;
    viewHolder.bindView(viewHolder, position);
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class UserViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_user) ImageView ivUser;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_age_height) TextView tvAgeHeight;
    @BindView(R.id.tv_city_state) TextView tvCityState;
    @BindView(R.id.tv_remove) TextView tvRemove;
    @BindView(R.id.cv_root) CardView cvRoot;

    UserViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bindView(UserViewHolder viewHolder, int position) {
      ResultsItem item = list.get(position);
      viewHolder.tvName.setText(item.getName().getFirst() + " " + item.getName().getLast());
      viewHolder.tvAgeHeight.setText(item.getDob().getAge() + " yrs");
      viewHolder.tvCityState.setText(
          item.getLocation().getCity() + ", " + item.getLocation().getState());
      Glide.with(viewHolder.ivUser.getContext())
          .load(item.getPicture().getLarge())
          .into(viewHolder.ivUser);
      enterAnimation(viewHolder.cvRoot, position);
    }

    @OnClick(R.id.tv_remove)
    public void onRemoveClicked() {
      userRemoveListener.onUserRemoved(getLayoutPosition());
    }
  }

  public void enterAnimation(CardView cardView, int position) {
    if (position > lastPosition) {
      LayoutAnimationController animation =
          AnimationUtils.loadLayoutAnimation(cardView.getContext(),
              R.anim.layout_animation_fall_down);
      cardView.setLayoutAnimation(animation);
      lastPosition = position;
    }
  }

  public interface UserRemoveListener {
    void onUserRemoved(int position);
  }
}