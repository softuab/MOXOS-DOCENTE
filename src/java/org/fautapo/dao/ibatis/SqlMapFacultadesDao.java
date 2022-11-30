package org.fautapo.dao.ibatis;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.dao.DataAccessException;
import org.fautapo.dao.FacultadesDao;
import org.fautapo.domain.Facultades;
import org.fautapo.domain.Universidades;
import org.fautapo.util.ListViewItem;

/**
 * @autor FAUTAPO
 * @fec_registro 2005-11-01
 * @ult_usuario FAUTAPO
 * @fec_modificacion 2005-11-01
 */
public class SqlMapFacultadesDao extends SqlSessionDaoSupport implements FacultadesDao {

    @Override
    public Facultades getFclBuscarFacultad(Facultades facultad) throws DataAccessException {
        return (Facultades) getSqlSession().selectOne("getFclBuscarFacultad", facultad);
    }

    @Override
    public List getUnvListarFacultades(Universidades universidad) throws DataAccessException {
        return getSqlSession().selectList("getUnvListarFacultades", universidad);
    }

    @Override
    public List getUnvListarFacultadesPost(Universidades universidad) throws DataAccessException {
        return getSqlSession().selectList("getUnvListarFacultadesPost", universidad);
    }

    @Override
    public List<ListViewItem> getListarFacultades() throws DataAccessException {
        return getSqlSession().selectList("getListarFacultades", null); //To change body of generated methods, choose Tools | Templates.
    }

}
