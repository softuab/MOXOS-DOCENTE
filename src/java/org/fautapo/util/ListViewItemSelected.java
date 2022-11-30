/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.util;

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
