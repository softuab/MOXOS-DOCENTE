package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.moxos.uab.mybatis.entity.Estudiantes;
import com.moxos.uab.mybatis.entity.Personas;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonasDao {

    Personas getBuscarPersonaUsuario(Personas persona) throws DataAccessException;

    Personas getBuscarPersona(Personas persona) throws DataAccessException;

    // INICIO - MI
    Personas getPrsBuscarPersona(Personas persona) throws DataAccessException;

    Personas getPrsBuscarPersonaDocente(int id_docente) throws DataAccessException;

    // FIN - MI
    // INICIO SEGUNDA PARTE
    // Listar Paises
    List<Personas> getListarPaises() throws DataAccessException;

    List<Personas> getListarDepartamentos(Personas persona) throws DataAccessException;

    List<Personas> getListarProvincias(Personas persona) throws DataAccessException;

    List<Personas> getListarLocalidades(Personas persona) throws DataAccessException;

    List<Personas> getListarLocalidadesTodas() throws DataAccessException;

    // Listar Tipos
    List<Personas> getListarTiposSexos() throws DataAccessException;

    List<Personas> getListarTiposEstadosCiviles() throws DataAccessException;

    List<Personas> getListarTiposEmpresasTelef() throws DataAccessException;

    List<Personas> getListarTiposEstudiantes() throws DataAccessException;

    Personas getBuscarTipoEstudiante(Personas persona) throws DataAccessException;

    List<Personas> getListarTiposGraduaciones() throws DataAccessException;

    List<Personas> getListarTiposInstituciones() throws DataAccessException;

    List<Personas> getListarColegiosTipoIns(Personas persona) throws DataAccessException;

    List<Personas> getListarTiposTurnos() throws DataAccessException;

    List<Personas> getListarTiposProblemasRol(Personas persona) throws DataAccessException;

    // Registrar
    int setRegistrarPersona(Personas persona) throws DataAccessException;

    int setRegistrarPrsColegio(Personas persona) throws DataAccessException;

    int setRegistrarPrsClasificacion(Personas persona) throws DataAccessException;

    int setRegistrarPrsDocumentos(Personas persona) throws DataAccessException;

    int setRegistrarPrsCompromisos(Personas persona) throws DataAccessException;

    List<Personas> getListarPrsDocumentosPersona(Personas persona) throws DataAccessException;

    Personas getBuscarTipoClasificacionPersona(Personas persona) throws DataAccessException;

    List<Personas> getListarTiposCompromisos() throws DataAccessException;

    Personas getBuscarPersonaColegio(Personas persona) throws DataAccessException;

    List<Personas> getPrsListarPersonasDip(Personas persona) throws DataAccessException;

    // Items Personas
    Personas getBuscarItemPersona(Personas persona) throws DataAccessException;

    List<Personas> getListarItemsPersonasDip(Personas persona) throws DataAccessException;

    Personas getBuscarItemsUsuario(Personas persona) throws DataAccessException;

    // Listar PrsCompromisos
    List<Personas> getListarPrsCompromisosPersona(Personas persona) throws DataAccessException;

    List<Personas> getListarPrsDocumentosClasificacion(Personas persona) throws DataAccessException;

    int getBuscarPrsDocumentacionCompleta(Personas persona) throws DataAccessException;

    // Fin Listar PrsCompromisos

    // FIN SEGUNDA PARTE

    Personas getMiBuscarCompromiso(Personas persona) throws DataAccessException;

    int getMiPrsNroCompromisos(Personas persona) throws DataAccessException;

    // Listar Personas
    List<Personas> getListarPersonas(Personas persona) throws DataAccessException;

    // INICIO - METODOS ADICIONADOS POR LA UAP
    Personas getEstBuscarEstudianteDocente(Personas persona) throws DataAccessException;

    // Listado de Curso de Preparatoria de Ingles Otros
    List<Estudiantes> getListarCursoPreInglesOtros(Personas persona) throws DataAccessException;

    List<Estudiantes> getListarCursoPsicoOtros(Personas persona) throws DataAccessException;
    // FIN - METODOS ADICIONADOS POR LA UAP

}