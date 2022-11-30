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
public class ListViewItem {

    private String id;
    private String value;

    public ListViewItem(String id, String value) {
        this.id = id;
        this.value = value;
    }

}
