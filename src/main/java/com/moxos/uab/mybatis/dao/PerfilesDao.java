package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Perfiles;
import com.moxos.uab.mybatis.entity.Personas;
import com.moxos.uab.mybatis.entity.Planes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PerfilesDao {

    Perfiles getPrfBuscarPerfil(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getPstListarPerfiles(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getPstListarPerfilesEntidad(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getPrfListarConceptos(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getPstListarConceptos(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getEstListarConceptos(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getDctListarConceptos(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getUsrListarConceptos(Perfiles perfil) throws DataAccessException;

    int setPstRegistrarTransaccion(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getTrnListarTiposPerfiles() throws DataAccessException;

    List<Perfiles> getTrnMiListarPerfilesProceso(Perfiles perfil) throws DataAccessException;

    Perfiles getTrnBuscarPerfilProceso(Perfiles perfil) throws DataAccessException;

    Perfiles getPrcBuscarPerfil(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getTrnPrcListarPerfiles(Perfiles perfil) throws DataAccessException;

    int setRegistrarTrnDetalle(Perfiles perfil) throws DataAccessException;

    int getTrnPerfilTieneDescuento(Perfiles perfil) throws DataAccessException;

    List<Planes> getTrnListarPerfilesMaterias(Planes plan) throws DataAccessException;

    Perfiles getTrnBuscarPerfilMateria(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getTrnListarPerfiles() throws DataAccessException;

    Perfiles getTrnBuscarPerfil(Perfiles perfil) throws DataAccessException;

    int setTrnRegistrarPerfilMateria(Planes plan) throws DataAccessException;

    Perfiles getTrnBuscarTransaccion(Perfiles perfil) throws DataAccessException;

    Perfiles getTrnBuscarTransaccionRecibo(Perfiles perfil) throws DataAccessException;

    Perfiles getTrnBuscarTransaccionReciboSede(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getTrnListarTrnDetalles(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getPrsListarConceptos(Perfiles perfil) throws DataAccessException;

    int setPrsRegistrarTransaccion(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getTrnListarTiposDescuentos() throws DataAccessException;

    Perfiles getTrnBuscarTipoDescuento(Perfiles perfil) throws DataAccessException;

    Perfiles getBuscarPerfilConcepto(Perfiles perfil) throws DataAccessException;

    List<Personas> getTrnListarCajeros(Perfiles perfil) throws DataAccessException;

    List<Personas> getTrnListarCajerosProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getTrnListarTransacciones(Perfiles perfil) throws DataAccessException;

    /* aqui */
    List<Perfiles> getRepCajasTransaccionesDiarias(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasTransaccionesDiariasGlobal(Perfiles perfil) throws DataAccessException;

    /* inicio provincia */
    List<Perfiles> getRepCajasTransaccionesDiariasGlobalProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasTransaccionesDiariasGlobalxcajero(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasTransaccionesDiariasGlobalxcajeroProv(Perfiles perfil) throws DataAccessException;

    /* fin provincia */
    List<Perfiles> getRepCajasTransaccionesDiariasEntidades(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenTesoroEntidades(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenInstitucionalEntidades(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenInstitucionalEntidadesConcepto(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenEstudiantilEntidades(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenEstudiantilEntidadesConcepto(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenProfactulativoEntidades(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenProfactulativoCarrera(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasDetalladoEntidades(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasDetalladoCarrera(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasTransaccionesDetalleGlobal(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasTransaccionesDetalleGlobalProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasTransaccionesDetalleGlobalAnuladas(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasTransaccionesDetalleGlobalAnuladasProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasTransaccionesDetalleEntidad(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasTransaccionesDetalle(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasTransaccionesDetalleAnuladas(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenMatriculas(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenTesoroCarrera(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenMatriculasGlobal(Perfiles perfil) throws DataAccessException;

    // Nro_2
    List<Perfiles> getRepCajasResumenMatriculasGlobalProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenInstitucional(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenInstitucionalGlobal(Perfiles perfil) throws DataAccessException;

    // Nro_3
    List<Perfiles> getRepCajasResumenInstitucionalGlobalProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenEstudiantil(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenEstudiantilGlobal(Perfiles perfil) throws DataAccessException;

    // Nro_4
    List<Perfiles> getRepCajasResumenEstudiantilGlobalProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenProfacultativo(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenProfacultativoGlobal(Perfiles perfil) throws DataAccessException;

    // Nro_5
    List<Perfiles> getRepCajasResumenProfacultativoGlobalProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoMatriculas(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoMatriculasGlobal(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoMatriculasGlobalProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoEstudiantil(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoEstudiantilGlobal(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoEstudiantilGlobalProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoInstitucional(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoInstitucionalGlobal(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoInstitucionalGlobalProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetallado(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoGlobal(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoGlobalMatricula(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoGlobalProv(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasTransaccionesPorPrograma(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getRepCajasResumenDetalladoEntidades(Perfiles perfil) throws DataAccessException;
    // List getRepCajasGlobalGeneral(Perfiles perfil) throws DataAccessException;

    /* hast aqui */

    List<Perfiles> getTrnListarTransaccionesRecibo(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getTrnListarTransaccionesReciboSede(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getTrnListarTrnDetalles2(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getTrnBuscarPorNroRecibo(Perfiles perfil) throws DataAccessException;

    List<Perfiles> getTrnBuscarPorNroReciboSede(Perfiles perfil) throws DataAccessException;

    void setTrnBorrarDetalle(Perfiles perfil) throws DataAccessException;

    void setTrnBorrarTransaccion(Perfiles perfil) throws DataAccessException;

    int getTrnBuscarSiguienteNroRecibo(Perfiles perfil) throws DataAccessException;

    void setTrnActualizarNroRecibo(Perfiles perfil) throws DataAccessException;

}