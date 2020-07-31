package com.example.kafeotomasyon.common;

import java.util.LinkedList;
import java.util.List;

import static com.example.kafeotomasyon.Utils.Constants.menuler;
import static com.example.kafeotomasyon.Utils.Constants.menuicerikleri;

public class MenuDataProvider extends AbstractMenuDataProvider {

    private List<GroupSet> mData;
    private IdGenerator mGroupIdGenerator;
    public MenuDataProvider() {
        mData = new LinkedList<>();
        mGroupIdGenerator = new IdGenerator();

        for (int i = 0; i < menuler.length; i++) {
            addGroupItem(i);
            for (int j = 0; j < menuicerikleri[i].length; j++) {
                addChildItem(i, j);
            }
        }
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return mData.get(groupPosition).mChildren.size();
    }

    @Override
    public GroupData getGroupItem(int groupPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        return mData.get(groupPosition).mGroup;
    }

    @Override
    public ChildData getChildItem(int groupPosition, int childPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        final List<ConcreteChildData> children = mData.get(groupPosition).mChildren;

        if (childPosition < 0 || childPosition >= children.size()) {
            throw new IndexOutOfBoundsException("childPosition = " + childPosition);
        }

        return children.get(childPosition);
    }

    @Override
    public void removeChildItem(int groupPosition, int childPosition) {
        mData.get(groupPosition).mChildren.remove(childPosition);
    }

    @Override
    public void addGroupItem(int groupPosition) {
            long id = mGroupIdGenerator.next();
            String text = menuler[(int) id];
            ConcreteGroupData newItem = new ConcreteGroupData(id, text);

            mData.add(groupPosition, new GroupSet(newItem));

    }

    @Override
    public void addChildItem(int groupPosition, int childPosition) {
        mData.get(groupPosition).addNewChildData(groupPosition, childPosition);
    }

    public static final class ConcreteGroupData extends GroupData {
        private final long mId;
        private final String mText;

        ConcreteGroupData(long id, String text) {
            mId = id;
            mText = text;
        }

        @Override
        public long getGroupId() {
            return mId;
        }

        @Override
        public String getText() {
            return mText;
        }
    }

    public static final class ConcreteChildData extends ChildData {
        private long mId;
        private final String mText;

        ConcreteChildData(long id, String text) {
            mId = id;
            mText = text;
        }

        @Override
        public long getChildId() {
            return mId;
        }

        @Override
        public String getText() {
            return mText;
        }
    }

    private static class IdGenerator {
        long mId;

        public long next() {
            final long id = mId;
            mId += 1;
            return id;
        }
    }

    private static class GroupSet {
        private ConcreteGroupData mGroup;
        private List<ConcreteChildData> mChildren;
        private IdGenerator mChildIdGenerator;

        public GroupSet(ConcreteGroupData group) {
            mGroup = group;
            mChildren = new LinkedList<>();
            mChildIdGenerator = new IdGenerator();
        }

        public void addNewChildData(int groupPosition, int position) {
            long id = mChildIdGenerator.next();
            String text = menuicerikleri[groupPosition][position];

            if (text!=null) {
                ConcreteChildData child = new ConcreteChildData(id, text);
                mChildren.add(position, child);
            }
        }
    }
}
