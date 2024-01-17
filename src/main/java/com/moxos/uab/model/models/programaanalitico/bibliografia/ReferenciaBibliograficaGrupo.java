package com.moxos.uab.model.models.programaanalitico.bibliografia;

import com.moxos.uab.mybatis.entity.ListViewItem;
import lombok.Data;

import java.util.List;

@Data
public class ReferenciaBibliograficaGrupo {
    private Integer tipo_referncia;
    private List<ListViewItem> items;
}

