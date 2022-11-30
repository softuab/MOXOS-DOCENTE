/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.dao.ibatis;

import java.util.List;
import org.fautapo.dao.KardexDao;
import org.fautapo.domain.CategoriaDocente;
import org.fautapo.domain.KardexPersonal;
import org.fautapo.domain.ParametrosBusqueda;
import org.fautapo.domain.PersonaActividadesAcademicas;
import org.fautapo.domain.PersonaCursosRealizados;
import org.fautapo.domain.PersonaEvaluacionDocente;
import org.fautapo.domain.PersonaExperienciaLaboral;
import org.fautapo.domain.PersonaFormacionAcademica;
import org.fautapo.domain.PersonaIdioma;
import org.fautapo.domain.PersonaKardex;
import org.fautapo.domain.PersonaModalidadIngreso;
import org.fautapo.domain.PersonaProduccionCientifica;
import org.fautapo.domain.PersonaProyectoDocente;
import org.fautapo.model.Kardex.DetalleFormacionAcademicaPersonaModel;
import org.fautapo.model.Kardex.DetalleIdiomaPersonaModel;
import org.fautapo.model.Kardex.DetallePersonaExperienciaLaboral;
import org.fautapo.model.Kardex.DetallePersonaModalidadIngresoModel;
import org.fautapo.util.ListViewItem;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author hp
 */
public class SqlMapKardexDao extends SqlSessionDaoSupport implements KardexDao {

    @Override
    public void RegistrarNuevoKardexDocente(PersonaKardex kardex) throws DataAccessException {
        getSqlSession().insert("RegistrarNuevoKardexDocente", kardex);//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonaKardex GetKardexPersonalNuevo(int id_persona) throws DataAccessException {
        return (PersonaKardex) getSqlSession().selectOne("GetKardexPersonalNuevo", id_persona);
    }

    @Override
    public void ActualizarDatosKardexDocente(PersonaKardex kardex) throws DataAccessException {
        getSqlSession().update("ActualizarDatosKardexDocente", kardex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ListViewItem> GetLocalidadPersona() throws DataAccessException {
        return getSqlSession().selectList("GetLocalidadPersona", null);
    }

    @Override
    public List<ListViewItem> GetNivelEstudio() throws DataAccessException {
        return getSqlSession().selectList("GetNivelEstudio", null); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ListViewItem> GetProfesiones() throws DataAccessException {
        return getSqlSession().selectList("GetProfesiones", null); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ListViewItem> GetColegiosProfesionales() throws DataAccessException {
        return getSqlSession().selectList("GetColegiosProfesionales", null); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ListViewItem> GetBancos() throws DataAccessException {
        return getSqlSession().selectList("GetBancos", null); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonaKardex GetImagenesPersonaKardex(int id_persona) throws DataAccessException {
        return (PersonaKardex) getSqlSession().selectOne("GetImagenesPersonaKardex", id_persona); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int RegistrarNuevoIdiomaKardex(PersonaIdioma idioma) throws DataAccessException {
        Integer insertedId = (Integer) getSqlSession().selectOne("RegistrarNuevoIdiomaKardex", idioma);
        return insertedId;
    }

    @Override
    public void ActualizarDatosiIdiomaKardexDocente(PersonaIdioma idioma) throws DataAccessException {
        getSqlSession().update("ActualizarDatosiIdiomaKardexDocente", idioma);
    }

    @Override
    public void AprobarIdiomaKardexDocente(PersonaIdioma idioma) throws DataAccessException {
        getSqlSession().update("AprobarIdiomaKardexDocente", idioma);
    }

    @Override
    public void EliminarIdiomaKardexDocente(int id_idioma) throws DataAccessException {
        getSqlSession().update("EliminarIdiomaKardexDocente", id_idioma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetalleIdiomaPersonaModel> GetPersonaTotalIdiomaKardex(int id_persona) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaTotalIdiomaKardex", id_persona); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetalleIdiomaPersonaModel> GetPersonaSubTotalIdiomaKardex(PersonaKardex kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaSubTotalIdiomaKardex", kardex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonaIdioma GetPersonaIdiomaKardex(int id_idioma) throws DataAccessException {
        return (PersonaIdioma) getSqlSession().selectOne("GetPersonaIdiomaKardex", id_idioma); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ActualizarImagenIdiomaKardexDocente(PersonaIdioma idioma) throws DataAccessException {
        getSqlSession().update("ActualizarImagenIdiomaKardexDocente", idioma);
    }
//formacion academica

    @Override
    public int RegistrarNuevoFormacionAcademicaKardex(PersonaFormacionAcademica formacion) throws DataAccessException {
        Integer insertedId = (Integer) getSqlSession().selectOne("RegistrarNuevoFormacionAcademicaKardex", formacion);
        return insertedId;
    }

    @Override
    public void ActualizarDatosFormacionAcademicaKardexDocente(PersonaFormacionAcademica formacion) throws DataAccessException {
        getSqlSession().update("ActualizarDatosFormacionAcademicaKardexDocente", formacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void AprobarFormacionAcademicaKardexDocente(PersonaFormacionAcademica formacion) throws DataAccessException {
        getSqlSession().update("AprobarFormacionAcademicaKardexDocente", formacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void EliminarFormacionAcademicaKardexDocente(int id_formacion) throws DataAccessException {
        getSqlSession().update("EliminarFormacionAcademicaKardexDocente", id_formacion);
    }

    @Override
    public void ActualizarImagenFormacionAcademicaKardexDocente(PersonaFormacionAcademica formacion) throws DataAccessException {
        getSqlSession().update("ActualizarImagenFormacionAcademicaKardexDocente", formacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetalleFormacionAcademicaPersonaModel> GetPersonaTotalFormacionAcademicaKardex(int id_persona_kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaTotalFormacionAcademicaKardex", id_persona_kardex);//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonaFormacionAcademica GetPersonaFormacionAcademicaKardex(int id_formacion) throws DataAccessException {
        return (PersonaFormacionAcademica) getSqlSession().selectOne("GetPersonaFormacionAcademicaKardex", id_formacion);
    }

    @Override
    public List<DetalleFormacionAcademicaPersonaModel> GetPersonaSubTotalFormacionAcademicaKardex(PersonaKardex kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaSubTotalFormacionAcademicaKardex", kardex); //To change body of generated methods, choose Tools | Templates.
    }
//experiencia laboral

    @Override
    public int RegistrarExperienciaLaboralKardex(PersonaExperienciaLaboral experiencia) throws DataAccessException {
        Integer insertedId = (Integer) getSqlSession().selectOne("RegistrarExperienciaLaboralKardex", experiencia);
        return insertedId;
    }

    @Override
    public int RegistrarCursosRealizadosKardex(PersonaCursosRealizados cursos) throws DataAccessException {
        Integer insertedId = (Integer) getSqlSession().selectOne("RegistrarCursosRealizadosKardex", cursos);
        return insertedId;
    }

    @Override
    public int RegistrarProduccionCientificaKardex(PersonaProduccionCientifica produccion) throws DataAccessException {
        Integer insertedId = (Integer) getSqlSession().selectOne("RegistrarProduccionCientificaKardex", produccion);
        return insertedId;
    }

    @Override
    public int RegistrarEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion) throws DataAccessException {
        Integer insertedId = (Integer) getSqlSession().selectOne("RegistrarEvaluacionDocenteKardex", evaluacion);
        return insertedId;
    }

    @Override
    public void ActualizarDatosExperienciaLaboralKardexDocente(PersonaExperienciaLaboral experiencia) throws DataAccessException {
        getSqlSession().update("ActualizarDatosExperienciaLaboralKardexDocente", experiencia);
    }

    @Override
    public void AprobarExperienciaLaboralKardexDocente(PersonaExperienciaLaboral experiencia) throws DataAccessException {
        getSqlSession().update("AprobarExperienciaLaboralKardexDocente", experiencia);
    }

    @Override
    public void EliminarExperienciaLaboralKardexDocente(int id_experiencia_laboral) throws DataAccessException {
        getSqlSession().update("EliminarExperienciaLaboralKardexDocente", id_experiencia_laboral);
    }

    @Override
    public void ActualizarImagenExperienciaLaboralKardexDocente(PersonaExperienciaLaboral experiencia) throws DataAccessException {
        getSqlSession().update("ActualizarImagenExperienciaLaboralKardexDocente", experiencia);
    }

    @Override
    public List<DetallePersonaExperienciaLaboral> GetPersonaTotalExperienciaLaboralKardex(int id_persona_kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaTotalExperienciaLaboralKardex", id_persona_kardex);
    }

    @Override
    public PersonaExperienciaLaboral GetPersonaExperienciaLaboralKardex(int id_experiencia_laboral) throws DataAccessException {
        return (PersonaExperienciaLaboral) getSqlSession().selectOne("GetPersonaExperienciaLaboralKardex", id_experiencia_laboral);
    }

    @Override
    public List<DetallePersonaExperienciaLaboral> GetPersonaSubTotalExperienciaLaboralKardex(PersonaKardex kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaSubTotalExperienciaLaboralKardex", kardex);
    }

    @Override
    public void ActualizarDatosCursosRealizadosKardexDocente(PersonaCursosRealizados cursos) throws DataAccessException {
        getSqlSession().update("ActualizarDatosCursosRealizadosKardexDocente", cursos);
    }

    @Override
    public void AprobarCursosRealizadosKardexDocente(PersonaCursosRealizados cursos) throws DataAccessException {
        getSqlSession().update("AprobarCursosRealizadosKardexDocente", cursos);
    }

    @Override
    public void EliminarCursosRealizadosKardexDocente(int id_cursos_realizados) throws DataAccessException {
        getSqlSession().update("EliminarCursosRealizadosKardexDocente", id_cursos_realizados);
    }

    @Override
    public void ActualizarImagenCursosRealizadosKardexDocente(PersonaCursosRealizados cursos) throws DataAccessException {
        getSqlSession().update("ActualizarImagenCursosRealizadosKardexDocente", cursos);
    }

    @Override
    public List<PersonaCursosRealizados> GetPersonaTotalCursosRealizadosKardex(int id_persona_kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaTotalCursosRealizadosKardex", id_persona_kardex);
    }

    @Override
    public PersonaCursosRealizados GetPersonaCursosRealizadosKardex(int id_cursos_realizados) throws DataAccessException {
        return (PersonaCursosRealizados) getSqlSession().selectOne("GetPersonaCursosRealizadosKardex", id_cursos_realizados);
    }

    @Override
    public List<PersonaCursosRealizados> GetPersonaSubTotalCursosRealizadosKardex(PersonaKardex kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaSubTotalCursosRealizadosKardex", kardex);
    }

    @Override
    public void ActualizarProduccionCientificaKardexDocente(PersonaProduccionCientifica produccion) throws DataAccessException {
        getSqlSession().update("ActualizarProduccionCientificaKardexDocente", produccion);
    }

    @Override
    public void AprobarProduccionCientificaKardexDocente(PersonaProduccionCientifica produccion) throws DataAccessException {
        getSqlSession().update("AprobarProduccionCientificaKardexDocente", produccion);
    }

    @Override
    public void EliminarProduccionCientificaKardexDocente(int id_produccion_cientifica) throws DataAccessException {
        getSqlSession().update("EliminarProduccionCientificaKardexDocente", id_produccion_cientifica);
    }

    @Override
    public void ActualizarImagenProduccionCientificaKardexDocente(PersonaProduccionCientifica produccion) throws DataAccessException {
        getSqlSession().update("ActualizarImagenProduccionCientificaKardexDocente", produccion);
    }

    @Override
    public List<PersonaProduccionCientifica> GetPersonaTotalProduccionCientificaKardex(int id_persona_kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaTotalProduccionCientificaKardex", id_persona_kardex);
    }

    @Override
    public PersonaProduccionCientifica GetPersonaProduccionCientificaKardex(int id_produccion_cientifica) throws DataAccessException {
        return (PersonaProduccionCientifica) getSqlSession().selectOne("GetPersonaProduccionCientificaKardex", id_produccion_cientifica);
    }

    @Override
    public List<PersonaProduccionCientifica> GetPersonaSubTotalProduccionCientificaKardex(PersonaKardex kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaSubTotalProduccionCientificaKardex", kardex);
    }

    @Override
    public void ActualizarEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion) throws DataAccessException {
        getSqlSession().update("ActualizarEvaluacionDocenteKardex", evaluacion);
    }

    @Override
    public void AprobarEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion) throws DataAccessException {
        getSqlSession().update("AprobarEvaluacionDocenteKardex", evaluacion);
    }

    @Override
    public void EliminarEvaluacionDocenteKardex(int id_evaluacion_docente) throws DataAccessException {
        getSqlSession().update("EliminarEvaluacionDocenteKardex", id_evaluacion_docente);
    }

    @Override
    public void ActualizarImagenEvaluacionDocenteKardex(PersonaEvaluacionDocente evaluacion) throws DataAccessException {
        getSqlSession().update("ActualizarImagenEvaluacionDocenteKardex", evaluacion);
    }

    @Override
    public List<PersonaEvaluacionDocente> GetPersonaTotalEvaluacionDocenteKardex(int id_persona_kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaTotalEvaluacionDocenteKardex", id_persona_kardex);
    }

    @Override
    public PersonaEvaluacionDocente GetPersonaEvaluacionDocenteKardex(int id_evaluacion_docente) throws DataAccessException {
        return (PersonaEvaluacionDocente) getSqlSession().selectOne("GetPersonaEvaluacionDocenteKardex", id_evaluacion_docente);
    }

    @Override
    public List<PersonaEvaluacionDocente> GetPersonaSubTotalEvaluacionDocenteKardex(PersonaKardex kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaSubTotalEvaluacionDocenteKardex", kardex);
    }

    @Override
    public List<ListViewItem> GetNivelEstudioPorNivel(String grado) throws DataAccessException {
        return getSqlSession().selectList("GetNivelEstudioPorNivel", grado); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int RegistrarNuevoModalidadKardex(PersonaModalidadIngreso modalidad) throws DataAccessException {
        Integer insertedId = (Integer) getSqlSession().selectOne("RegistrarNuevoModalidadKardex", modalidad);
        return insertedId;
    }

    @Override
    public void ActualizarDatosiModalidadKardexDocente(PersonaModalidadIngreso modalidad) throws DataAccessException {
        getSqlSession().update("ActualizarDatosiModalidadKardexDocente", modalidad);//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void AprobarModalidadKardexDocente(PersonaModalidadIngreso modalidad) throws DataAccessException {
        getSqlSession().update("AprobarModalidadKardexDocente", modalidad); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void EliminarModalidadKardexDocente(int id_modalidadingreso) throws DataAccessException {
        getSqlSession().update("EliminarModalidadKardexDocente", id_modalidadingreso); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ActualizarImagenModalidadKardexDocente(PersonaModalidadIngreso modalidad) throws DataAccessException {
        getSqlSession().update("ActualizarImagenModalidadKardexDocente", modalidad); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetallePersonaModalidadIngresoModel> GetPersonaTotalModalidadKardex(int id_persona_kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaTotalModalidadKardex", id_persona_kardex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonaModalidadIngreso GetPersonaModalidadKardex(int id_modalidadingreso) throws DataAccessException {
        return (PersonaModalidadIngreso) getSqlSession().selectOne("GetPersonaModalidadKardex", id_modalidadingreso);//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetallePersonaModalidadIngresoModel> GetPersonaSubTotalModalidadKardex(PersonaKardex kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaSubTotalModalidadKardex", kardex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String GetImageidiomas(int id_idioma) throws DataAccessException {
        String image = (String) getSqlSession().selectOne("GetImageidiomas", id_idioma);
        return image;
    }

    @Override
    public String GetImagemodalidad(int id_modalidadingreso) throws DataAccessException {
        String image = (String) getSqlSession().selectOne("GetImagemodalidad", id_modalidadingreso);
        return image;
    }

    @Override
    public String GetImageformacionacademica(int id_formacion) throws DataAccessException {
        String image = (String) getSqlSession().selectOne("GetImageformacionacademica", id_formacion);
        return image;
    }

    @Override
    public String GetImageexperiencia(int id_experiencia_laboral) throws DataAccessException {
        String image = (String) getSqlSession().selectOne("GetImageexperiencia", id_experiencia_laboral);
        return image;
    }

    @Override
    public String GetImagecursosrealizados(int id_cursos_realizados) throws DataAccessException {
        String image = (String) getSqlSession().selectOne("GetImagecursosrealizados", id_cursos_realizados);
        return image;
    }

    @Override
    public String GetImageproduccioncientifica(int id_produccion_cientifica) throws DataAccessException {
        String image = (String) getSqlSession().selectOne("GetImageproduccioncientifica", id_produccion_cientifica);
        return image;
    }

    @Override
    public KardexPersonal getKardexPersonalDocente(int id_persona) throws DataAccessException {
        return (KardexPersonal) getSqlSession().selectOne("getKardexPersonalDocente", id_persona);
    }

    @Override
    public List<CategoriaDocente> GetPersonaCategoriaKardex(ParametrosBusqueda busqueda) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaCategoriaKardex", busqueda);
    }

    @Override
    public void ActualizarInformacionPersonalKardexDocente(PersonaKardex kardex) throws DataAccessException {
        getSqlSession().update("ActualizarInformacionPersonalKardexDocente", kardex);
    }

    @Override
    public void ActualizarIdentificacionPersonalKardexDocente(PersonaKardex kardex) throws DataAccessException {
        getSqlSession().update("ActualizarIdentificacionPersonalKardexDocente", kardex);
    }

    @Override
    public void ActualizarServicioMilitarPersonalKardexDocente(PersonaKardex kardex) throws DataAccessException {
        getSqlSession().update("ActualizarServicioMilitarPersonalKardexDocente", kardex);
    }

    @Override
    public void ActualizarEducacionPregradoPersonalKardexDocente(PersonaKardex kardex) throws DataAccessException {
        getSqlSession().update("ActualizarEducacionPregradoPersonalKardexDocente", kardex);
    }

    @Override
    public void ActualizarEducacionPosgradoKardexDocente(PersonaKardex kardex) throws DataAccessException {
        getSqlSession().update("ActualizarEducacionPosgradoKardexDocente", kardex);
    }

    @Override
    public void ActualizarInformacionLaboralPersonalKardexDocente(PersonaKardex kardex) throws DataAccessException {
        getSqlSession().update("ActualizarInformacionLaboralPersonalKardexDocente", kardex);
    }

    @Override
    public void ActualizarContactoPersonalKardexDocente(PersonaKardex kardex) throws DataAccessException {
        getSqlSession().update("ActualizarContactoPersonalKardexDocente", kardex);
    }

    @Override
    public List<ListViewItem> GetProgramasPregrado() throws DataAccessException {
        return getSqlSession().selectList("GetProgramasPregrado");
    }

    @Override
    public int RegistrarNuevoActividadesAcademicasKardex(PersonaActividadesAcademicas actividades) throws DataAccessException {
        Integer insertedId = (Integer) getSqlSession().selectOne("RegistrarNuevoActividadesAcademicasKardex", actividades);
        return insertedId;
    }

    @Override
    public void ActualizarDatosiActividadesAcademicasKardexDocente(PersonaActividadesAcademicas actividades) throws DataAccessException {
        getSqlSession().update("ActualizarDatosiActividadesAcademicasKardexDocente", actividades);
    }

    @Override
    public void AprobarActividadesAcademicasKardexDocente(PersonaActividadesAcademicas actividades) throws DataAccessException {
        getSqlSession().update("AprobarActividadesAcademicasKardexDocente", actividades);
    }

    @Override
    public void EliminarActividadesAcademicasKardexDocente(int id_activades_academicas) throws DataAccessException {
        getSqlSession().update("EliminarActividadesAcademicasKardexDocente", id_activades_academicas);
    }

    @Override
    public void ActualizarImagenActividadesAcademicasKardexDocente(PersonaActividadesAcademicas actividades) throws DataAccessException {
        getSqlSession().update("ActualizarImagenActividadesAcademicasKardexDocente", actividades);
    }

    @Override
    public List<PersonaActividadesAcademicas> GetPersonaTotalActividadesAcademicasKardex(int id_persona_kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaTotalActividadesAcademicasKardex", id_persona_kardex);
    }

    @Override
    public PersonaActividadesAcademicas GetPersonaActividadesAcademicasKardex(int id_activades_academicas) throws DataAccessException {
        return getSqlSession().selectOne("GetPersonaActividadesAcademicasKardex", id_activades_academicas);
    }

    @Override
    public List<PersonaActividadesAcademicas> GetPersonaSubTotalActividadesAcademicasKardex(PersonaKardex kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaSubTotalActividadesAcademicasKardex", kardex);
    }

    @Override
    public String GetImageactividadesacademicas(int id_activades_academicas) throws DataAccessException {
        String image = (String) getSqlSession().selectOne("GetImageactividadesacademicas", id_activades_academicas);
        return image;
    }

    @Override
    public int RegistrarNuevoProyectoKardexPersona(PersonaProyectoDocente proyecto) throws DataAccessException {
        Integer insertedId = (Integer) getSqlSession().selectOne("RegistrarNuevoProyectoKardex", proyecto);
        return insertedId;
    }

    @Override
    public void ActualizarDatosProyectoKardexDocente(PersonaProyectoDocente proyecto) throws DataAccessException {
        getSqlSession().update("ActualizarDatosProyectoKardexDocente", proyecto);
    }

    @Override
    public void AprobarProyectoKardexDocente(PersonaProyectoDocente proyecto) throws DataAccessException {
        getSqlSession().update("AprobarProyectoKardexDocente", proyecto);
    }

    @Override
    public void EliminarProyectoKardexDocente(int id_personas_proyecto) throws DataAccessException {
        getSqlSession().update("EliminarProyectoKardexDocente", id_personas_proyecto);
    }

    @Override
    public void ActualizarImagenProyectoKardexDocente(PersonaProyectoDocente proyecto) throws DataAccessException {
        getSqlSession().update("ActualizarImagenProyectoKardexDocente", proyecto);
    }

    @Override
    public List<PersonaProyectoDocente> GetPersonaTotalProyectoKardex(int id_persona_kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaTotalProyectoKardex", id_persona_kardex);
    }

    @Override
    public PersonaProyectoDocente GetPersonaProyectoKardex(int id_personas_proyecto) throws DataAccessException {
        return getSqlSession().selectOne("GetPersonaProyectoKardex", id_personas_proyecto);
    }

    @Override
    public List<PersonaProyectoDocente> GetPersonaSubTotalProyectoKardex(PersonaKardex kardex) throws DataAccessException {
        return getSqlSession().selectList("GetPersonaSubTotalProyectoKardex", kardex);
    }

    @Override
    public String GetImageproyecto(int id_personas_proyecto) throws DataAccessException {
        String image = (String) getSqlSession().selectOne("GetImageproyecto", id_personas_proyecto);
        return image;
    }

}
