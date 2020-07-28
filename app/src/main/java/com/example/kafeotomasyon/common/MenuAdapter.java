package com.example.kafeotomasyon.common;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.kafeotomasyon.R;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemState;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.utils.RecyclerViewAdapterUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.kafeotomasyon.Utils.Constants.sogukicecekler;
import static com.example.kafeotomasyon.common.MenuDataProvider.adetler;

class MenuAdapter
        extends AbstractExpandableItemAdapter<MenuAdapter.MyGroupViewHolder, MenuAdapter.MyChildViewHolder> {
    private static final String TAG = "MyExpandableItemAdapter";

    private RecyclerViewExpandableItemManager mExpandableItemManager;
    private AbstractMenuDataProvider mProvider;
    private View.OnClickListener mItemOnClickListener = v -> onClickItemView(v);

    public static abstract class MyBaseViewHolder extends AbstractExpandableItemViewHolder {
        public FrameLayout mContainer;
        public TextView mTextView;

        public MyBaseViewHolder(View v, View.OnClickListener clickListener) {
            super(v);
            mContainer = v.findViewById(R.id.container);
            mTextView = v.findViewById(R.id.textviewmenu);

            mContainer.setOnClickListener(clickListener);
        }
    }

    public static class MyGroupViewHolder extends MyBaseViewHolder {

        public MyGroupViewHolder(View v, View.OnClickListener clickListener) {
            super(v, clickListener);

        }
    }

    public static class MyChildViewHolder extends MyBaseViewHolder {
        public Button mButtonAddChildAbove;
        public Button mButtonAddChildBelow;
        public Button mButtonRemoveChild;

        public MyChildViewHolder(View v, View.OnClickListener clickListener) {
            super(v, clickListener);
            mButtonAddChildAbove = v.findViewById(R.id.button_arttir);
            mButtonAddChildBelow = v.findViewById(R.id.button_azalt);
            mButtonRemoveChild = v.findViewById(R.id.button_ekle);

            mButtonAddChildAbove.setOnClickListener(clickListener);
            mButtonAddChildBelow.setOnClickListener(clickListener);
            mButtonRemoveChild.setOnClickListener(clickListener);
        }
    }

    public MenuAdapter(
            RecyclerViewExpandableItemManager expandableItemManager,
            AbstractMenuDataProvider dataProvider) {

        mExpandableItemManager = expandableItemManager;
        mProvider = dataProvider;

        setHasStableIds(true);
    }

    @Override
    public int getGroupCount() {
        return mProvider.getGroupCount();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return mProvider.getChildCount(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mProvider.getGroupItem(groupPosition).getGroupId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mProvider.getChildItem(groupPosition, childPosition).getChildId();
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    @NonNull
    public MyGroupViewHolder onCreateGroupViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.menu_list_group_item, parent, false);
        return new MyGroupViewHolder(v, mItemOnClickListener);
    }

    @Override
    @NonNull
    public MyChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.menu_list_item, parent, false);
        return new MyChildViewHolder(v, mItemOnClickListener);
    }

    @Override
    public void onBindGroupViewHolder(@NonNull MyGroupViewHolder holder, int groupPosition, int viewType) {
        // child item
        final AbstractMenuDataProvider.BaseData item = mProvider.getGroupItem(groupPosition);

        // set text
        holder.mTextView.setText(item.getText());

        // mark as clickable
        holder.itemView.setClickable(true);

        // set background resource (target view ID: container)
        final ExpandableItemState expandState = holder.getExpandState();

        if (expandState.isUpdated()) {
            int bgResId;

            if (expandState.isExpanded()) {
                bgResId = R.drawable.bg_group_item_expanded_state;
            } else {
                bgResId = R.drawable.bg_group_item_normal_state;
            }

            holder.mContainer.setBackgroundResource(bgResId);
        }
    }

    @Override
    public void onBindChildViewHolder(@NonNull MyChildViewHolder holder, int groupPosition, int childPosition, int viewType) {
        // group item
        final AbstractMenuDataProvider.ChildData item = mProvider.getChildItem(groupPosition, childPosition);

        // set text
        holder.mTextView.setText(item.getText());

        // set background resource (target view ID: container)
        int bgResId;
        bgResId = R.drawable.bg_item_normal_state;
        holder.mContainer.setBackgroundResource(bgResId);
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(@NonNull MyGroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        // NOTE: Handles all click events manually
        return false;
    }

    void onClickItemView(View v) {
        RecyclerView.ViewHolder vh = RecyclerViewAdapterUtils.getViewHolder(v);
        int flatPosition = vh.getAdapterPosition();

        if (flatPosition == RecyclerView.NO_POSITION) {
            return;
        }

        long expandablePosition = mExpandableItemManager.getExpandablePosition(flatPosition);
        int groupPosition = RecyclerViewExpandableItemManager.getPackedPositionGroup(expandablePosition);
        int childPosition = RecyclerViewExpandableItemManager.getPackedPositionChild(expandablePosition);

        switch (v.getId()) {
            // common events
            case R.id.container:
                if (childPosition == RecyclerView.NO_POSITION) {
                    handleOnClickGroupItemContainerView(groupPosition);
                } else {
                    handleOnClickChildItemContainerView(groupPosition, childPosition);
                }
                break;
            // child item events
            case R.id.button_arttir:
                handleOnClickChildItemAddAboveButton(groupPosition, childPosition);
                break;
            case R.id.button_azalt:
                handleOnClickChildItemAddBelowButton(groupPosition, childPosition);
                break;
            case R.id.button_ekle:
                handleOnClickChildItemRemoveButton(groupPosition, childPosition);
                break;
            default:
                throw new IllegalStateException("Unexpected click event");
        }
    }

    private void handleOnClickGroupItemContainerView(int groupPosition) {
        // toggle expanded/collapsed
        if (isGroupExpanded(groupPosition)) {
            mExpandableItemManager.collapseGroup(groupPosition);
        } else {
            mExpandableItemManager.expandGroup(groupPosition);
        }
    }

    private void handleOnClickChildItemContainerView(int groupPosition, int childPosition) {
    }

    private void handleOnClickChildItemAddAboveButton(int groupPosition, int childPosition) {
        adetler[groupPosition][childPosition]++;
        Log.e(TAG,""+adetler[groupPosition][childPosition]+" "+sogukicecekler[groupPosition][childPosition]);
    }

    private void handleOnClickChildItemAddBelowButton(int groupPosition, int childPosition) {
        if (adetler[groupPosition][childPosition]!=0)
            adetler[groupPosition][childPosition]--;

        Log.e(TAG,""+adetler[groupPosition][childPosition]+" "+sogukicecekler[groupPosition][childPosition]);
    }

    private void handleOnClickChildItemRemoveButton(int groupPosition, int childPosition) {
        mProvider.removeChildItem(groupPosition, childPosition);
        mExpandableItemManager.notifyChildItemRemoved(groupPosition, childPosition);
    }

    private boolean isGroupExpanded(int groupPosition) {
        return mExpandableItemManager.isGroupExpanded(groupPosition);
    }
}
