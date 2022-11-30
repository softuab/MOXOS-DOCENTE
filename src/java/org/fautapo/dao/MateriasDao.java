package org.fautapo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Planes; 

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
*/

public interface MateriasDao {
  //PLANES
  List getPlnListarMaterias(Planes plan) throws DataAccessException;
  //FIN PLANES
  
  //LIBRETAS
  Materias getMtrBuscarMateria(Materias materia) throws DataAccessException;
  // FIN LIBRETAS
  
  //EST_ PROGRAMACIONES
  List getEstPrgListarProgramacionMateriasAut(Materias materia) throws DataAccessException; 
  List getDptoListarMateriaGrupo(Materias materia) throws DataAccessException;  
  List getPstPrgListarProgramacionMateriasAut(Materias materia) throws DataAccessException; 
  //FIN EST_ PROGRAMACIONES
  
  //PROGRAMACIONES COMO ESTUDIANTE
  List getEstListarProgramacionMateriasReq(Materias materia) throws DataAccessException; 
  //FIN PROGRAMACIONES COMO ESTUDIANTE
  
  //Programacion automatica
  Materias getDptoListarMateriaGrupoMinimo(Materias materia) throws DataAccessException;  
  //Fin Programacion automatica
  
  List getListarMateriasGradoPlanPrograma(Materias materia) throws DataAccessException;

  //INICIO - Admin Materias
  List getListarMateriasPorDepartamento(Materias materia) throws DataAccessException;
  List getListarMateriasPorSigla(Materias materia) throws DataAccessException;
  List getListarMateriasPorMateria(Materias materia) throws DataAccessException;
  Materias getMtrBuscarTipoMateria(Materias materia) throws DataAccessException;
  List getMtrListarTiposMaterias() throws DataAccessException;
  int setRegistrarMateria(Materias materia) throws DataAccessException;
  int setEliminarMateria(Materias materia) throws DataAccessException;
  int getBuscar_nro_excepcion_calendario(Materias materia) throws DataAccessException;
  //FIN - Admin Materias  
}