package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Postulantes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostulantesDao {

    Postulantes getPstBuscarPostulante(Postulantes postulante) throws DataAccessException;

    Postulantes getPstBuscarPostulantePrograma(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getPstListarPostulantesNombres(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getPstListarPostulantesDip(Postulantes postulante) throws DataAccessException;

    // Registrar Postulantes
    // JOJO
    List<Postulantes> getMiListarPostulantesDip(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getMiListarPostulantesNombre(Postulantes postulante) throws DataAccessException;

    // JOJO
    // CRCB
    int setMiRegistrarPstPersona(Postulantes postulante) throws DataAccessException;

    int setMiRegistrarPostulante(Postulantes postulante) throws DataAccessException;

    //
    int setMiRegistrarPostulanteC(Postulantes postulante) throws DataAccessException;

    int setPstRegistrarDocumentos(Postulantes postulante) throws DataAccessException;

    Postulantes getPstBuscarPersona(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getListarTiposDocumentos() throws DataAccessException;

    List<Postulantes> getListarTiposDocumentosClasificacionVigente(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getListarTiposClasificaciones() throws DataAccessException;

    List<Postulantes> getListarTiposClasificacionesPost() throws DataAccessException;

    int setPstRegistrarMatricula(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getListarPstMateriasProgramadas(Postulantes postulante) throws DataAccessException;

    Postulantes getPstBuscarPostulanteNombres(Postulantes postulante) throws DataAccessException;

    Postulantes getPstBuscarPostulanteNombresSede(Postulantes postulante) throws DataAccessException;

    Postulantes getPstBuscarMatriculaPostulante(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getMiListarPstNombreGestionPeriodo(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getMiListarPstDipGestionPeriodo(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getMiListarPstProgramaGestionPeriodo(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getMiListarPstProgramaGestionPeriodoSede(Postulantes postulante) throws DataAccessException;

    // FIN Registrar Postulantes
    List<Postulantes> getMiListarPstPsaGestionPeriodo(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getRepAsistenciapostulantepsa(Postulantes postulante) throws DataAccessException;

    int setRegistrarasignacion(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getRepAsistenciapostulantepsaci(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getDctListarPostulantespsasoloid(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getNroPostulantesPsa(Postulantes postulante) throws DataAccessException;

    int setPstModificarAsistenciaPostulante(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getMiListarPstAprobadoDipGestionPeriodo(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getMiListarPstAprobadoNombreGestionPeriodo(Postulantes postulante) throws DataAccessException;

    int setPstModificarEstadoPostulante(Postulantes postulante) throws DataAccessException;

    // Pst Personas
    List<Postulantes> getPstListarPersonasNombre(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getPstListarPersonasDip(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getMiListarPostulantesPorPersona(Postulantes postulante) throws DataAccessException;

    int setRegistrarPstPrsColegio(Postulantes postulante) throws DataAccessException;

    Postulantes getBuscarPstPersonaColegio(Postulantes postulante) throws DataAccessException;

    // inicio JOJO
    Postulantes getMiPrsBuscarPostulante(Postulantes postulante) throws DataAccessException;

    void setPstRegistrarPrograma(Postulantes postulante) throws DataAccessException;

    // fin JOJO
    // Estadistica
    List<Postulantes> getNroPstInscritosHabilitados(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getNroPstInscritosHabilitadosTipoAdmision(Postulantes postulante) throws DataAccessException;

    // Registrar programa anterior
    int setPstRegistrarProgramaAnterior(Postulantes postulante) throws DataAccessException;

    // Listado de Postulantes Inscritos
    List<Postulantes> getPstListarInscritosPorProgramaTipoAdmision(Postulantes postulante) throws DataAccessException;

    // Listado de Postulantes Aprobados
    List<Postulantes> getPstListarAprobadosPorProgramaTipoAdmision(Postulantes postulante) throws DataAccessException;

    // Listado de Postulantes Reprobados
    List<Postulantes> getPstListarReprobadosPorProgramaTipoAdmision(Postulantes postulante) throws DataAccessException;

    // Buscar Postulantes
    List<Postulantes> getMiListarPostulantesDipTipoAdm(Postulantes postulante) throws DataAccessException;

    List<Postulantes> getMiListarPostulantesNombreTipoAdm(Postulantes postulante) throws DataAccessException;

    // INICIO - METODOS ADICIONADOS POR LA UAP
    // Insertar Pst_personas para tramites
    int setMiRegistrarPstPersonaTrn(Postulantes postulante) throws DataAccessException;
    // FIN - METODOS ADICIONADOS POR LA UAP

}