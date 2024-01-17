package com.moxos.uab.model.models.utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListViewItemSelected {

    private String id;
    private String value;
    private Boolean selected;

    public ListViewItemSelected(String id, String value, Boolean selected) {
        this.id = id;
        this.value = value;
        this.selected = selected;
    }

}