package com.moxos.uab.model.models.programaanalitico.bibliografia;

import lombok.Data;

@Data
public class BibliografiaTipoReferenciaRequest {

    private Integer tipo_referncia;
    private Integer id_dct_programa_analitico;
    private Integer id_prg_a_bibliografia;
}
