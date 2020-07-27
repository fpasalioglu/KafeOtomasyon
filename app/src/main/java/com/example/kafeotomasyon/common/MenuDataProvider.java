package com.example.kafeotomasyon.common;

import java.util.LinkedList;
import java.util.List;

public class MenuDataProvider extends AbstractMenuDataProvider {

    static String[][] sogukicecekler = {{"Fanta", "Soğuk Kahve","Hoşaf","Ayran"}, {"Kola", "Limonata", "Hoşaf"}};
    static String[] menuler = {"Soğuk İçecekler", "Sıcak İçecekler"};
    public static int[][] adetler = {};

    private List<GroupSet> mData;
    private IdGenerator mGroupIdGenerator;
    private int toplam=0;
    public MenuDataProvider() {
        mData = new LinkedList<>();
        mGroupIdGenerator = new IdGenerator();

        for (int i = 0; i < menuler.length; i++) {
            addGroupItem(i);
            for (int j = 0; j < sogukicecekler[i].length; j++) {
                addChildItem(i, j);
                toplam++;
            }
        }

        adetler = new int[menuler.length][toplam];
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
    public void removeGroupItem(int groupPosition) {
        mData.remove(groupPosition);
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

    @Override
    public void clear() {
        mData.clear();
    }

    @Override
    public void clearChildren(int groupPosition) {
        mData.get(groupPosition).mChildren.clear();
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
            String text = sogukicecekler[groupPosition][position];
            ConcreteChildData child = new ConcreteChildData(id, text);

            mChildren.add(position, child);
        }
    }
}
