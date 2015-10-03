package io.jari.materialup.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.jari.materialup.R;
import io.jari.materialup.adapters.CategoryAdapter;
import io.jari.materialup.api.ApiClient;
import io.jari.materialup.exeptions.ItemException;
import io.jari.materialup.exeptions.ItemImageException;
import io.jari.materialup.interfaces.ItemCallback;
import io.jari.materialup.interfaces.ItemImageCallBack;
import io.jari.materialup.models.Post;
import io.jari.materialup.responses.ItemResponse;
import io.jari.materialup.ui.activities.MainActivity;
import io.jari.materialup.ui.listeners.EndlessRecyclerOnScrollListener;
import io.jari.materialup.utils.StringUtils;
import retrofit.Retrofit;

import java.util.List;
import java.util.Random;

/**
 * Created by jari on 01/06/15.
 */
public class CategoryFragment extends BaseFragment
    implements ItemCallback, ItemImageCallBack, ApiClient.Callback<List<Post>> {

  private static final String TAG = CategoryFragment.class.getName();

  @Bind(R.id.recycler)
  RecyclerView recyclerView;

  @Bind(R.id.swiperefresh)
  SwipeRefreshLayout swipeRefreshLayout;

  @Bind(R.id.progressBar)
  ProgressBar progressBar;

  private String path;
  private String mQueryParameter;
  private CategoryAdapter categoryAdapter;

  public static CategoryFragment newInstance(String path) {
    CategoryFragment categoryFragment = new CategoryFragment();
    Bundle bundle = new Bundle();
    bundle.putCharSequence("path", path);
    categoryFragment.setArguments(bundle);
    return categoryFragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    mRootView = inflater.inflate(R.layout.fragment_category, container, false);
    ButterKnife.bind(this, mRootView);
    return mRootView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    init();
  }

  private void init() {
    if (getArguments() != null) {
      path = getArguments().getString("path");
      if (getArguments().getBoolean("popular")) {
        mQueryParameter = "popular";
      }
    }

    ApiClient.postList(this);

    if (!StringUtils.isEmpty(path)) {
      // TODO
    }

    //initialize the adapter with empty data set
    categoryAdapter = new CategoryAdapter(getActivity());
    RecyclerViewMaterialAdapter recyclerViewMaterialAdapter = new RecyclerViewMaterialAdapter
        (categoryAdapter);
    recyclerView.setAdapter(recyclerViewMaterialAdapter);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setHasFixedSize(true);

    //set on scroll listener for infinite scrolling
    EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener
        (linearLayoutManager) {
      @Override
      public void onLoadMore(int current_page) {
        if (!StringUtils.isEmpty(path)) {
          // TODO
        }
      }
    };
    recyclerView.addOnScrollListener(mOnScrollListener);

    //register the on scroll listener as per the documentation
    MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, mOnScrollListener);

    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        if (!StringUtils.isEmpty(path)) {
          if (categoryAdapter != null) {
            int size = categoryAdapter.getItemCount();
            categoryAdapter.removeAll();
            categoryAdapter.notifyItemRangeRemoved(0, size);
            ((RecyclerViewMaterialAdapter) recyclerView.getAdapter()).mvp_notifyDataSetChanged();
          }
        }
      }
    });

    swipeRefreshLayout.setColorSchemeResources(R.color.accent_color,
        R.color.accent,
        android.R.color.white);

  }

  @Override
  public void onItemSuccess(ItemResponse response) {
//    List<Item> items = response.getItemList();
//    if (categoryAdapter != null) {
//      int currentSize = categoryAdapter.getItemCount();
//      categoryAdapter.addItems(items);
//      categoryAdapter.notifyItemRangeInserted(currentSize, items.size());
//      ((RecyclerViewMaterialAdapter) recyclerView.getAdapter()).mvp_notifyDataSetChanged();
//      swipeRefreshLayout
//          .setRefreshing(false);
//      Log.i(TAG, "Total items : " + categoryAdapter.getItemCount());
//    }
//    getRandomCover(items);
//    dismissLoader();
  }

  @Override
  public void onItemError(ItemException error) {
    Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
    swipeRefreshLayout
        .setRefreshing(false);
  }

  @Override
  public void onItemImageSuccess(String imageUrl) {
    ((MainActivity) mActivity).updatePagerDrawable(imageUrl);
  }

  @Override
  public void onItemImageError(ItemImageException error) {
    Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
  }

  @Override public void onSuccess(List<Post> items, Retrofit retrofit) {
    if (categoryAdapter != null) {
      int currentSize = categoryAdapter.getItemCount();
      categoryAdapter.addItems(items);
      categoryAdapter.notifyItemRangeInserted(currentSize, items.size());
      ((RecyclerViewMaterialAdapter) recyclerView.getAdapter()).mvp_notifyDataSetChanged();
      swipeRefreshLayout
          .setRefreshing(false);
      Log.i(TAG, "Total items : " + categoryAdapter.getItemCount());
    }
    getRandomCover(items);
    dismissLoader();
  }

  private void getRandomCover(List<Post> items) {
    Post item = items.get(new Random().nextInt(items.size()));
  }

  public void dismissLoader() {
    progressBar.animate().alpha(0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        progressBar.setVisibility(View.GONE);
      }
    });
  }

  @Override public void onFailure(Throwable t) {

  }
}
