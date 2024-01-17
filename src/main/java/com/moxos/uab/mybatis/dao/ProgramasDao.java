package com.moxos.uab.mybatis.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.moxos.uab.mybatis.entity.Programas;
import com.moxos.uab.mybatis.entity.Facultades;
import com.moxos.uab.mybatis.entity.ListViewItem;
import com.moxos.uab.mybatis.entity.Planes;
import com.moxos.uab.mybatis.entity.Universidades;
import com.moxos.uab.mybatis.entity.Usuarios;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProgramasDao {

    Programas getPrgBuscarPrograma(Programas programa) throws DataAccessException;

    List<Programas> getFclListarProgramas(Facultades facultad) throws DataAccessException;

    List<Programas> getUnvListarProgramas(Universidades universidad) throws DataAccessException;

    List<Programas> getUnvListarProgramasPost(Universidades universidad) throws DataAccessException;

    // PROGRAMACIONES
    Programas getPrdBuscarPrgPeriodo(Programas programa) throws DataAccessException;

    List<Programas> getPrgBuscarDetalles(Programas programa) throws DataAccessException;

    Programas getMdlBuscarMateriaAhorro(Programas programa) throws DataAccessException;

    Programas setEstProgramacionMateria(Programas programa) throws DataAccessException;

    int setPstProgramacionMateria(Programas programa) throws DataAccessException;

    List<Programas> getTpsListarProgramaciones() throws DataAccessException;

    Programas getTpsBuscarProgramacion(Programas programa) throws DataAccessException;
    // FIN PROGRAMACIONES

    // ADMINISTRAR HORARIOS
    List<Programas> getListarProgramasAcceso(Usuarios usuario) throws DataAccessException;

    List<Planes> getMtrListarPlanesPrograma(Programas programa) throws DataAccessException;
    // FIN ADMINISTRAR HORARIOS

    // PROGRAMACIONES COMO ESTUDIANTE
    int getBuscarNivelMaximoPlanesEst(Programas programa) throws DataAccessException;

    List<Programas> getZchListarChoqueMaterias(Programas programa) throws DataAccessException;

    List<Programas> getZchListarChoquePeriodos(Programas programa) throws DataAccessException;

    List<Programas> setEstListarProgramarMaterias(Programas programa) throws DataAccessException;
    // RETIRO ADICION DE MATERIAS COMO AUTORIDAD

    List<Programas> getEstListarProgramacionesEstudiante(Programas programa) throws DataAccessException;

    List<Programas> setEstPrgRetirarProgramacionesMaterias(Programas programa) throws DataAccessException;

    List<Programas> setEstPrgRegistrarListarCambiarGrupos(Programas programa) throws DataAccessException;

    // MI SEGUNDA PARTE
    List<Programas> getListarTiposAdmisiones() throws DataAccessException;

    List<Programas> getListarTiposAdmisionesPost() throws DataAccessException;

    List<Programas> getListarTiposAdmisionesPrograma(Programas programa) throws DataAccessException;

    List<Programas> getListarTiposAdmisionesProgramaLiberacion(Programas programa) throws DataAccessException;

    // Buscar programa de estudiante
    Programas getPrgBuscarProgramaEstudiante(Programas programa) throws DataAccessException;
    // fin Buscar programa de estudiante

    List<Programas> getListarGradosProgramas(Programas programa) throws DataAccessException;

    // FIN MI SEGUNDA PARTE
    List<Programas> getEstListarNotaMinimaporPrograma(Programas programa) throws DataAccessException;

    List<ListViewItem> getUnvListarCarreraFacultad(int id_facultad) throws DataAccessException;

}
