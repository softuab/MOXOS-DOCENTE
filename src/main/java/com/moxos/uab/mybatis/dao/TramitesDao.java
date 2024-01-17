package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Tramites;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TramitesDao {

    List<Tramites> getListarFormularioNuevo(Tramites tramite) throws DataAccessException;

    int setInsertarTramite(Tramites tramite) throws DataAccessException;

    int getBuscarActividadMinima(Tramites tramite) throws DataAccessException;

    int setInsertarFrLog(Tramites tramite) throws DataAccessException;

    int setRegistrarValor(Tramites tramite) throws DataAccessException;

    Tramites getBuscarTramite(Tramites tramite) throws DataAccessException;

    int setRecibirTramite(Tramites tramite) throws DataAccessException;

    Tramites getBuscarFrLog(Tramites tramite) throws DataAccessException;

    int setAvanzarTramite(Tramites tramite) throws DataAccessException;

    int setConcluirTramite(Tramites tramite) throws DataAccessException;

    int setEliminarFrLog(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMios(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMiosFiltrado(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMiosDespachados(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMiosDespachadosFiltrado(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarUsuariosActividadSiguiente(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarCamposReferencia(Tramites tramite) throws DataAccessException;

    int setRetrocederTramite(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarFormulario(Tramites tramite) throws DataAccessException;

    // Administrar mis pendientes agrupados
    List<Tramites> getListarTramitesMiosAgrupados(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMiosAgrupados2(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMiosAgrupadosDespachados(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMiosAgrupadosDespachados2(Tramites tramite) throws DataAccessException;

    Tramites getContarTramitesPorFechaEstado(Tramites tramite) throws DataAccessException;

    Tramites getContarTramitesPorFechaEstado2(Tramites tramite) throws DataAccessException;

    Tramites getContarTramitesPorFecha(Tramites tramite) throws DataAccessException;

    Tramites getContarTramitesPorFecha2(Tramites tramite) throws DataAccessException;

    Tramites getContarTramitesPorFecha3(Tramites tramite) throws DataAccessException;
    // Fin Administrar mis pendientes agrupados

    // Busqueda de tramites
    List<Tramites> getListarTramitesPorCampos(Tramites tramite) throws DataAccessException;

    int getBuscarTramiteExisteUbicacionOrganica(Tramites tramite) throws DataAccessException;

    Tramites getBuscarTramiteUbicacionOrganica(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramiteLog(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesFechaUbicacionOrganica(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesIniciados(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMovidos(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesConcluidos(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesIniciadosDetalle(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMovidosDetalle(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesConcluidosDetalle(Tramites tramite) throws DataAccessException;
    // Fin Busqueda de tramites

    // Busqueda ejecutiva
    List<Tramites> getListarDatosTramite(Tramites tramite) throws DataAccessException;
    // Fin Busqueda ejecutiva

    // Bloquear Tramites
    int setBloquearTramite(Tramites tramite) throws DataAccessException;
    // Fin Bloquear Tramites

    // Desbloquear Tramites
    int setDesbloquearTramite(Tramites tramite) throws DataAccessException;
    // Fin Desbloquear Tramites

    // Anular tramites
    List<Tramites> getListarTramitesAnulados() throws DataAccessException;

    int setAnularTramite(Tramites tramite) throws DataAccessException;
    // Fin Anular tramites

    // Imprimir tramites
    List<Tramites> getListarTramitesImpresion(Tramites tramite) throws DataAccessException;
    // Imprimir tramites

    // Redireccionar tramites
    List<Tramites> getListarTramites(Tramites tramite) throws DataAccessException;

    int setRedireccionarTramite(Tramites tramite) throws DataAccessException;
    // Fin Redireccionar tramites

    // Reingresar tramites
    Tramites getBuscarTramite2(Tramites tramite) throws DataAccessException;

    int setReingresarTramite(Tramites tramite) throws DataAccessException;
    // Fin Reingresar tramites

    // Administrar correspondencias
    Tramites getBuscarTipoProceso2(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMiosCorrespondenciaDe(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMiosCorrespondenciaPara(Tramites tramite) throws DataAccessException;

    int setAvanzarCorrespondencia(Tramites tramite) throws DataAccessException;

    int setInsertarTramiteCopia(Tramites tramite) throws DataAccessException;
    // Fin Administrar correspondencias

    // Busqueda por Fecha y Estados
    List<Tramites> getListarEstadosTramites() throws DataAccessException;

    List<Tramites> getListarTramitesEstadoFechaUbicacionOrganica(Tramites tramite) throws DataAccessException;
    // Fin Busqueda por Fecha y Estados

    // Habilitar tramite
    int setHabilitarTramite(Tramites tramite) throws DataAccessException;
    // FIN Habilitar tramite

    List<Tramites> getListarTramitesPorEstadoFecha(Tramites tramite) throws DataAccessException;

    // Administrar Kardex
    List<Tramites> getListarTramitesMiosKardex(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesMiosKardexPorProceso(Tramites tramite) throws DataAccessException;

    // Para ver siguiente
    List<Tramites> getListarTramitesMiosKardexPorProcesoAtendidos(Tramites tramite) throws DataAccessException;

    Tramites getBuscarMinMaxTramitesMiosKardexPorProceso(Tramites tramite) throws DataAccessException;

    // Reporte de tramites por funcionarios
    List<Tramites> getListarTramitesFuncionarios(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesFuncionarioProceso(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesAtendidos(Tramites tramite) throws DataAccessException;

    Tramites getContarTramitesAtendidos(Tramites tramite) throws DataAccessException;
    // Fin - Reporte de tramites por funcionarios

    String getContarPaginas(Tramites tramite) throws DataAccessException;

    String getContarPaginasDespachados(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesCorrelativo(Tramites tramite) throws DataAccessException;

    // Administrar tramites concluidos
    List<Tramites> getListarTramitesConcluidosPorProceso(Tramites tramite) throws DataAccessException;

    List<Tramites> getListarTramitesConcluidosPorProcesoFiltrado(Tramites tramite) throws DataAccessException;

    String getContarPaginasConcluidos(Tramites tramite) throws DataAccessException;

    String getContarPaginasTramitesGestionProceso(Tramites tramite) throws DataAccessException;
    // Fin - Administrar tramites concluidos

    // SILVIA
    // BUSQUEDA DE CORRESPONDENCIA DESPACHADAS
    List<Tramites> getListarCorrespondenciaDes(Tramites tramite) throws DataAccessException;

    // Busqueda de Correspodencia por referencia
    List<Tramites> getListarCorrespReference(Tramites tramite) throws DataAccessException;

    // Busqueda de Correspodencia por Remitente
    List<Tramites> getListarCorrespRemitente(Tramites tramite) throws DataAccessException;

    // Correspodencia Despachadas por fecha
    List<Tramites> getListarCorrespDesFecha(Tramites tramite) throws DataAccessException;
    // fin SILVIA

    // Cambiar Estado Tramites
    int setCambiarEstadoTramite(Tramites tramite) throws DataAccessException;
    // Fin /Cambiar Estado Tramites

    // INICIO - METODOS ADICIONADOS POR LA UAP
    // TRAMITES ATENDIDOS
    List<Tramites> getTrListarProcesos() throws DataAccessException;
    // FIN - METODOS ADICIONADOS POR LA UAP

}