package io.jari.materialup.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.jari.materialup.R;
import io.jari.materialup.models.Post;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jari on 07/06/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
  private List<Post> dataSet;
  private Activity mContext;

  public CategoryAdapter(List<Post> dataSet, Activity context) {
    this.dataSet = dataSet;
    this.mContext = context;
  }

  public CategoryAdapter(Activity context) {
    this.mContext = context;
    this.dataSet = new ArrayList<>();
  }

  public static Bitmap drawableToBitmap(Drawable drawable) {
    if (drawable instanceof BitmapDrawable) {
      return ((BitmapDrawable) drawable).getBitmap();
    }

    int width = drawable.getIntrinsicWidth();
    width = width > 0 ? width : 200;
    int height = drawable.getIntrinsicHeight();
    height = height > 0 ? height : 200;

    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);

    return bitmap;
  }

  public void removeAll() {
    for (int i = dataSet.size() - 1; i >= 0; i--) {
      remove(dataSet.get(i));
    }
  }

  public void remove(Post item) {
    int position = dataSet.indexOf(item);
    dataSet.remove(position);
    notifyItemRemoved(position);
  }

  public void addItems(List<Post> items) {
    dataSet.addAll(items);
    notifyItemInserted(dataSet.size() - 1);
  }

  // Create new views (invoked by the layout manager)
  @Override
  public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // create a new view
    CardView card = (CardView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_card, parent, false);

    return new ViewHolder(card, mContext);
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.update(dataSet.get(position));
  }

  @Override
  public int getItemCount() {
    return dataSet.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public CardView cardView;
    public Activity context;
    public Post item;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.details)
    TextView details;
    @Bind(R.id.label)
    TextView label;
    @Bind(R.id.score)
    TextView score;
    @Bind(R.id.comments)
    TextView comments;
    @Bind(R.id.views)
    TextView views;
    @Bind(R.id.avatar)
    CircleImageView avatar;
    public ViewHolder(CardView v, final Activity context) {
      super(v);
      cardView = v;
      this.context = context;

      ButterKnife.bind(this, v);

      v.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//          ItemActivity.launch(context, item);
        }
      });
    }

    public void update(Post item) {
      this.item = item;
      title.setText(item.getName());

      if (item.getMakers().size() > 0) {
        details.setText(item.getCategory().getName() +
            " by " + item.getMakers().get(0).getNickname());
      } else {
        details.setText(item.getCategory().getName());
      }

      if (item.getLabel().equals("")) {
        label.setVisibility(View.GONE);
      } else {
        label.setVisibility(View.VISIBLE);
        label.setBackgroundColor(ColorGenerator.MATERIAL.getColor(item.getLabel()));
        label.setText(item.getLabel());
      }

      score.setText(item.getUpvotesCount() + "");
      comments.setText(item.getCommentsCount() + "");
      views.setText(item.getViewCount() + "");

      if (item.getThumbnails() != null && !"".equals(item.getThumbnails().getTeaserUrl())) {
        final ImageView image = (ImageView) cardView.findViewById(R.id.image);
        image.setVisibility(View.VISIBLE);
        DrawableRequestBuilder<String> request = Glide.with(context)
            .load(item.getThumbnails().getTeaserUrl())
            .fitCenter();

        if (item.getThumbnails().getTeaserUrl().endsWith(".gif"))
          request.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image);
        else request.into(image);

      } else cardView.findViewById(R.id.image).setVisibility(View.GONE);

      boolean generateLetter = false;
      try {
//        if (item.getMakers().get(0).getUrl() != null && !item.getMakerAvatar().equals("")) {
//          Glide.with(context)
//              .load(item.getMakerAvatar())
//              .into(avatar);
//        } else {
//          generateLetter = true;
//        }
      } catch (NullPointerException e) {
        //so there's like this weird bug where twitter doesn't pass a content-type.
        //glide doesn't know what to do and crashes.
        generateLetter = true;
      }

      if (generateLetter) {
        Bitmap bitmap = drawableToBitmap(
            TextDrawable
                .builder()
                .buildRect(
                    item.getMakers().get(0).getFullName().substring(0, 1).toUpperCase(),
                    ColorGenerator.MATERIAL.getColor(item.getMakers().get(0).getFullName())
                )
        );

        avatar.setImageDrawable(
            new BitmapDrawable(
                context.getResources(),
                bitmap
            )
        );
      }

    }
  }
}
