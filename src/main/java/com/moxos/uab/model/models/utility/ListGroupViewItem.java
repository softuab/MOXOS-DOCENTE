package com.moxos.uab.model.models.utility;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListGroupViewItem {
    private List<ListViewItemSelected> items;
    private String group;

    public ListGroupViewItem() {
        items = new ArrayList<>();
    }
}
