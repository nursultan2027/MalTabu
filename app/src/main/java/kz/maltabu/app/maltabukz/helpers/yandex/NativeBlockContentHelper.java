package kz.maltabu.app.maltabukz.helpers.yandex;

import android.util.Pair;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import java.util.List;

import kz.maltabu.app.maltabukz.models.Post;

public class NativeBlockContentHelper {

    private SparseArray<Integer> mPositionViewType;

    public NativeBlockContentHelper() {
        mPositionViewType = new SparseArray<>();
    }

    public void setData(@NonNull List<Pair<Integer, Object>> dataList) {
        mPositionViewType.clear();

        for (int i = 0; i < dataList.size(); i++) {
            final Pair<Integer, Object> pair = dataList.get(i);
            mPositionViewType.put(i, pair.first);
        }
    }

    public int getItemType(final int position) {
        Integer type = mPositionViewType.get(position);
        return type != null ? type : Holder.BlockContentProvider.NONE_TYPE;
    }

    public int getItemCount() {
        return mPositionViewType.size();
    }
}