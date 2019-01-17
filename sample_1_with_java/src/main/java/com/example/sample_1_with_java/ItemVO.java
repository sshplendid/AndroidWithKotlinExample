package com.example.sample_1_with_java;

abstract class ItemVO {
    public static final int TYPE_HEADER=0;
    public static final int TYPE_DATA=1;

    abstract int getType();
}

class HeaderItem extends ItemVO {
    String date;

    public HeaderItem(String date){
        this.date = date;
    }

    @Override
    int getType() {
        return ItemVO.TYPE_HEADER;
    }
}

class DataItem extends ItemVO {
    int id;
    String title;
    String content;
    boolean completed;

    public DataItem(int id, String title, String content, boolean completed){
        this.id = id;
        this.title = title;
        this.content = content;
        this.completed = completed;
    }

    @Override
    int getType() {
        return ItemVO.TYPE_DATA;
    }
}
