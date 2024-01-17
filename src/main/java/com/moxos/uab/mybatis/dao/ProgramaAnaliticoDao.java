/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moxos.uab.mybatis.dao;

import java.util.List;

import com.moxos.uab.mybatis.entity.*;
import org.springframework.dao.DataAccessException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProgramaAnaliticoDao {

    List<ProgramaAnalitico> GetListarDatosCaratula(int id_asignacion) throws DataAccessException;

    List<ProgramaAnalitico> GetListarMateriaProgramaAnalitico(int id_asignacion) throws DataAccessException;

    ProgramaAnalitico getListarInformeProgramaAnalitico(int id_asignacion) throws DataAccessException;

    int PermitirRegistroPrograma(ProgramaAnalitico programaAnalitico) throws DataAccessException;

    List<ProgramaAnalitico> GetListarPrerequisitoMateria(Planes plan) throws DataAccessException;

    int RegistrarProgromaAnalitico(ProgramaAnalitico programaAnalitico) throws DataAccessException;

    List<ProgramaAnalitico> GetListaProgramaanalitico(ProgramaAnalitico programaAnalitico) throws DataAccessException;

    int ActualizarProgramaAnalitico(ProgramaAnalitico programaAnalitico) throws DataAccessException;

    ProgramaAnalitico getProgramaanalitico(int id_dct_programa_analitico) throws DataAccessException;

    ProgramaAnalitico getDetalleProgramaAnalitico(int id_dct_programa_analitico) throws DataAccessException;

    //bibliografia
    Integer actualizarBibliografia(BiBliografia bibliografia) throws DataAccessException;

    Integer registrarBibliografia(BiBliografia bibliografia) throws DataAccessException;

    Integer eliminarBibliografia(Integer id_prg_a_bibliografia) throws DataAccessException;

    List<BiBliografia> GetListarBibliografia(BiBliografia bibliografia) throws DataAccessException;

    BiBliografia getBibliografia(Integer id_prg_a_bibliografia) throws DataAccessException;

    //cronograma
    Integer actualizarCronograma(Cronograma cronograma) throws DataAccessException;

    Integer registrarCronograma(Cronograma cronograma) throws DataAccessException;

    Integer eliminarCronograma(Integer id_prg_a_cronograma) throws DataAccessException;

    List<Cronograma> getListarCronograma(Integer id_dct_programa_analitico) throws DataAccessException;

    Cronograma getCronograma(Integer id_prg_a_cronograma) throws DataAccessException;

    //CONTENIDO
    int ActualizarObjetivo_Instructivo(Contenidos contenidos) throws DataAccessException;

    int RegistrarObjetivo_Instructivo(Contenidos contenidos) throws DataAccessException;

    int copyDistribucion(Contenidos contenidos) throws DataAccessException;

    int EliminarObjetivo_Instructivo(Contenidos contenidos) throws DataAccessException;

    List<Contenidos> GetListarContenido(Contenidos contenidos) throws DataAccessException;

    Contenidos getContenido(Integer id_prg_a_contenido) throws DataAccessException;

    /* distribucion de tiempos*/


    void registrarFormasDistribucion(FormasDistribucion formasdistribucion) throws DataAccessException;


    ProgramaAnalitico GetDatosCaratula(int id_dct_programa_analitico) throws DataAccessException;

    int GetNivelAcademico(ProgramaAnalitico programa) throws DataAccessException;

    /* fin distribucion de tiempos*/
    List<ListViewItem> GetListarProgramasAnaliticosPorMateria(ParametrosBusqueda busqueda) throws DataAccessException;

    void ActualizarMapaContenido(Contenidos contenido) throws DataAccessException;

    int copyBibliografia(ProgramaAnalitico programa) throws DataAccessException;

    List<Contenidos> GetListaCopiarContenidos(int id_dct_programa_analitico) throws DataAccessException;

    List<Cronograma> GetListaCopiarCronograma(int id_dct_programa_analitico) throws DataAccessException;


    int RegistrarObjetivo_Instructivo_mapa(Contenidos contenido) throws DataAccessException;

    List<FormasDistribucion> GetListaCopiarFormasDistirbucion(int id_prg_a_distribucion) throws DataAccessException;

    ProgramaAnalitico GetProgramaanaliticoDetalle(ProgramaAnalitico model) throws DataAccessException;

    List<FormasOrganizacionDistribucion> getListarFormasOrganizacionDistribucion(Integer id_dct_programa_analitico) throws DataAccessException;

    List<FormasOrganizacion> getListaFormasPorTipo(String tipo_forma) throws DataAccessException;

    List<FormasOrganizacionDistribucion> getListarFormasPorDistribucion(Integer id_dct_programa_analitico) throws DataAccessException;

    Integer getidDistribucion(FormasDistribucion distribucion) throws DataAccessException;

    Integer getTotalHorasdistribucion(Integer id_dct_programa_analitico) throws DataAccessException;

    void insertarDistribucion(FormasDistribucion distribucion) throws DataAccessException;

    void actualizarHorasDistribucion(FormasOrganizacionDistribucion distribucion) throws DataAccessException;

    void eliminarDistribucion(Integer id_prg_a_distribucion) throws DataAccessException;

    List<FormasDistribucion> GetListaCopiarDistribucion(FormasDistribucion distribucion) throws DataAccessException;

    HorasMaterias getCantidadHorasAsignatura(Integer id_dct_programa_analitico) throws DataAccessException;

    FormasDistribucion getFormasDistribucion(Integer id_prg_a_distribucion) throws DataAccessException;

    List<ListViewItem> getListaFormasClases() throws DataAccessException;

    Integer getIdProgramaAnalitico(Asignaciones asignaciones) throws DataAccessException;

    List<FormasOrganizacion> getListaFormarPorProgramaYTipoPresencial(Integer id_dct_programa_analitico) throws DataAccessException;

    List<FormasOrganizacion> getListaFormarPorProgramaYTipoVirtual(Integer id_dct_programa_analitico) throws DataAccessException;

    Integer insertProgramaAnalitico(ProgramaAnalitico programaAnalitico) throws DataAccessException;

    List<FormasDistribucion> getListarFormasOrganizacionDistribucionCopiar(Integer id_prg_a_contenido);

}
