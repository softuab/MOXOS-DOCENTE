package com.moxos.uab.model.models.Moodle;

import com.moxos.uab.model.jsonmodels.Moodle.MoodleUserJsonRest;
import lombok.Data;

@Data
public class UserCourseEnrollMoodleModel {

    private Integer id_materia;
    private Integer id_grupo;
    private Integer id_programa;
    private Integer gestion;
    private Integer periodo;
    private Integer idcurso;
    private String materia;
    private MoodleUserJsonRest[] seleccionados;
}