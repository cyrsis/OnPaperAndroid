package tech.onpaper.victor.onpaper.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tech.onpaper.victor.onpaper.R;

/**
 * Created by cyber on 2017-01-19.
 */

public class TileContentFragment extends Fragment {
  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    RecyclerView recyclerView =
        (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

    ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());

    return inflater.inflate(R.layout.item_title, null);
  }

  private static class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ContentAdapter(Context context) {


    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return null;
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override public int getItemCount() {
      return 0;
    }
  }
}
