/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.web.Docentes;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.ParametrosBusqueda;
import org.fautapo.domain.ProgramaAnalitico;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.ParametrosEntrada;
import org.fautapo.model.programaanalitico.ProgramaAnaliticoCreacionModel;
import org.fautapo.model.programaanalitico.ProgramaAnaliticoModel;
import org.fautapo.util.ListViewItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class ProgramaAnaliticoController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ModelMapper modelMapper;

    private Clientes GetUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/EntradaProgramaAnalitico.fautapo")
    public String EntradaProgramaAnalitico(Model modelo) {
        ParametrosEntrada model = new ParametrosEntrada();
        modelo.addAttribute("simagen", GetUsuario().getImagen());
        modelo.addAttribute("usuario", GetUsuario().getNombres());
        modelo.addAttribute("gestion", Integer.toString(GetUsuario().getGestion()));
        modelo.addAttribute("periodo", Integer.toString(GetUsuario().getPeriodo()));
        modelo.addAttribute("model", model);
        return "AdministrarProgramasAnaliticos/Entrada";
    }

    @RequestMapping(value = "/ListarAsignacionesProgramaAnalitico.fautapo", method = RequestMethod.POST)
    public String ListarAsignacionesProgramaAnalitico(@ModelAttribute("model") @Validated ParametrosEntrada model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", GetUsuario().getImagen());
            modelo.addAttribute("usuario", GetUsuario().getNombres());
            modelo.addAttribute("gestion", Integer.toString(GetUsuario().getGestion()));
            modelo.addAttribute("periodo", Integer.toString(GetUsuario().getPeriodo()));
            modelo.addAttribute("model", model);
            return "AdministrarProgramasAnaliticos/Entrada";
        }
        //Sacamos la asignacion del docente
        Asignaciones asignacion = new Asignaciones();
        asignacion.setId_docente(GetUsuario().getId_usuario());
        asignacion.setGestion(model.getGestion());
        asignacion.setPeriodo(model.getPeriodo());
        List datosAsignacion = this.mi.getDctListarAsignacionDocenteporProgramaAnalitico(asignacion);
        Asignaciones aux = new Asignaciones();
        for (int i = 0; i < datosAsignacion.size(); i++) {
            aux = (Asignaciones) datosAsignacion.get(i);
            int iId_modelo_ahorro = aux.getId_modelo_ahorro();
            int iId_programa = aux.getId_programa();
            int iId_materia = aux.getId_materia();
            String iId_plan = aux.getId_plan();
            int iId_grupo = aux.getId_grupo();
            if (iId_modelo_ahorro > 0) {
                Asignaciones datos = new Asignaciones();
                datos.setId_modelo_ahorro(iId_modelo_ahorro);
                datos.setId_programa(iId_programa);
                datos.setGestion(model.getGestion());
                datos.setPeriodo(model.getPeriodo());
                datos.setId_materia(iId_materia);
                List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
                aux.setMateria_ahorro(materiaAhorro);
                ProgramaAnalitico programaanalitico = new ProgramaAnalitico();
                programaanalitico.setId_materia(iId_materia);
                programaanalitico.setId_plan(iId_plan);
                programaanalitico.setId_grupo(iId_grupo);
                int PermitirRegistro = mi.PermitirRegistroPrograma(programaanalitico);
                aux.setPermitir(PermitirRegistro);
                datosAsignacion.set(i, aux);
            } else {
                ProgramaAnalitico programaanalitico = new ProgramaAnalitico();
                programaanalitico.setId_materia(iId_materia);
                programaanalitico.setId_plan(iId_plan);
                programaanalitico.setId_grupo(iId_grupo);
                int PermitirRegistro = mi.PermitirRegistroPrograma(programaanalitico);
                aux.setPermitir(PermitirRegistro);
                datosAsignacion.set(i, aux);

            }
        }

        List datosAsignaciones = datosAsignacion;
        modelo.addAttribute("datosAsignacion", datosAsignaciones);
        modelo.addAttribute("id_rol", GetUsuario().getId_rol());
        modelo.addAttribute("nombres", GetUsuario().getNombres());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("gestion", model.getGestion());
        return "AdministrarProgramasAnaliticos/ListarAsignaciones";
    }

    @RequestMapping(value = "/ElaborarProgramaAnalitico.fautapo", method = RequestMethod.POST)
    public String ElaborarProgramaAnalitico(@ModelAttribute("model") ProgramaAnaliticoCreacionModel model, Model modelo) {
        ParametrosBusqueda busqueda = new ParametrosBusqueda();
        ProgramaAnalitico auxprograma = new ProgramaAnalitico();
        auxprograma.setId_docente(model.getId_docente());
        auxprograma.setId_materia(model.getId_materia());
        auxprograma.setId_plan(model.getId_plan());
        auxprograma.setId_grupo(model.getId_grupo());
        auxprograma.setGestion(model.getGestion());
        auxprograma.setPeriodo(model.getPeriodo());
        List<ProgramaAnalitico> EvaluarProgramaAnalitico = null;
        EvaluarProgramaAnalitico = mi.GetListaProgramaanalitico(auxprograma);

        if (!EvaluarProgramaAnalitico.isEmpty()) {
            ProgramaAnalitico evaluarprograma = null;
            evaluarprograma = EvaluarProgramaAnalitico.stream().findFirst().get();
            modelo.addAttribute("nombres", GetUsuario().getNombres());
            modelo.addAttribute("gestion", model.getGestion());
            modelo.addAttribute("periodo", model.getPeriodo());
            modelo.addAttribute("materia", model.getMateria());
            modelo.addAttribute("grupo", model.getGrupo());
            if (evaluarprograma.getId_estado().equals("A")) {
                modelo.addAttribute("evaluarprograma", evaluarprograma);
                return "AdministrarProgramasAnaliticos/DetalleProgramaAnalitico";
            } else {
                if (evaluarprograma.getId_estado().equals("C")) {
                    modelo.addAttribute("mensaje", "Para modificar el programa analitico debe tener autorizacion del HCC.");
                    return "AdministrarProgramasAnaliticos/Aviso";
                } else {
                    modelo.addAttribute("mensaje", "No existe");
                    return "AdministrarProgramasAnaliticos/Aviso";
                }
            }
        } else {
            busqueda.setId_grupo(model.getId_grupo());
            busqueda.setId_materia(model.getId_materia());
            busqueda.setId_plan(model.getId_plan());
            busqueda.setPeriodo(model.getPeriodo());
            busqueda.setGestion(model.getGestion());
            List<ListViewItem> listarpgranalitico = mi.GetListarProgramasAnaliticosPorMateria(busqueda);
            modelo.addAttribute("nombres", GetUsuario().getNombres());
            modelo.addAttribute("model", model);
            modelo.addAttribute("listarpgranalitico", listarpgranalitico);
            return "AdministrarProgramasAnaliticos/ElaborarProgramaAnalitico";
        }
    }

    @RequestMapping(value = "/InicioElaboracionProgramaAnalitico.fautapo", method = RequestMethod.POST)
    public String InicioElaboracionProgramaAnalitico(@ModelAttribute("model") ProgramaAnaliticoCreacionModel model, Model modelo) {
        modelo.addAttribute("nombres", GetUsuario().getNombres());
        ProgramaAnalitico auxprograma = new ProgramaAnalitico();
        auxprograma.setId_docente(model.getId_docente());
        auxprograma.setId_materia(model.getId_materia());
        auxprograma.setId_plan(model.getId_plan());
        auxprograma.setId_grupo(model.getId_grupo());
        auxprograma.setGestion(model.getGestion());
        auxprograma.setPeriodo(model.getPeriodo());
        List<ProgramaAnalitico> EvaluarProgramaAnalitico = null;
        EvaluarProgramaAnalitico = mi.GetListaProgramaanalitico(auxprograma);

        if (EvaluarProgramaAnalitico.isEmpty()) {
            ProgramaAnalitico programaAnalitico = null;
            try {
                programaAnalitico = mi.GetListarDatosCaratula(model.getId_asignacion()).stream().findFirst().get();
            } catch (Exception ex) {
                modelo.addAttribute("mensaje", "No se registro el area de conocimiento la asignatura comunicar con su jefe de estudio");
                return "AdministrarProgramasAnaliticos/Error";
            }

            if (programaAnalitico == null) {
                modelo.addAttribute("mensaje", "No existe la designacion del docente");
                return "AdministrarProgramasAnaliticos/Error";
            }

            ProgramaAnalitico programaAnaliticoDatosMateria = null;
            programaAnaliticoDatosMateria = mi.GetListarMateriaProgramaAnalitico(model.getId_asignacion()).stream().findFirst().get();

            if (programaAnaliticoDatosMateria == null) {
                modelo.addAttribute("mensaje", "No existen datos de la materia");
                return "AdministrarProgramasAnaliticos/Error";
            }
            Docentes docente = null;
            Docentes auxdoc = new Docentes();
            auxdoc.setId_docente(model.getId_docente());
            docente = mi.getBuscarDocente(auxdoc);
            int id_persona = docente.getId_persona();
            ProgramaAnalitico datosauxiliar = new ProgramaAnalitico();
            datosauxiliar.setId_plan(request.getParameter("id_plan"));
            datosauxiliar.setId_materia(model.getId_materia());
            datosauxiliar.setId_mencion(model.getId_mencion());

            List<ProgramaAnalitico> programaAnaliticoPrerequisitoMateria = null;
            programaAnaliticoPrerequisitoMateria = mi.GetListarPrerequisitoMateria(datosauxiliar);

            String htmlrequisito = "";

            for (ProgramaAnalitico obj : programaAnaliticoPrerequisitoMateria) {
                htmlrequisito += obj.getSigla() + "-" + obj.getMateria() + "<br>";
            }
            int nivel_academico = programaAnaliticoPrerequisitoMateria.isEmpty() ? programaAnaliticoDatosMateria.getNivel_academico() : programaAnaliticoPrerequisitoMateria.stream().findFirst().get().getNivel_academico();
            modelo.addAttribute("nivel_academico", nivel_academico);
            modelo.addAttribute("htmlrequisito", htmlrequisito);
            modelo.addAttribute("programaAnalitico", programaAnalitico);
            modelo.addAttribute("model", model);
            modelo.addAttribute("id_persona", id_persona);
            modelo.addAttribute("programaAnaliticoDatosMateria", programaAnaliticoDatosMateria);
            /* modelo.addAttribute("programaAnaliticoPrerequisitoMateria", programaAnaliticoPrerequisitoMateria);*/
            return "AdministrarProgramasAnaliticos/CrearProgramaAnalitico";
        } else {
            ProgramaAnalitico evaluarprograma = null;
            evaluarprograma = EvaluarProgramaAnalitico.stream().findFirst().get();
            if (evaluarprograma.getId_estado().equals("A")) {
                modelo.addAttribute("evaluarprograma", evaluarprograma);
                return "AdministrarProgramasAnaliticos/DetalleProgramaAnalitico";
            } else {
                if (evaluarprograma.getId_estado().equals("C")) {
                    modelo.addAttribute("mensaje", "Para modificar el programa analitico debe tener autorizacion del HCC.");
                    return "AdministrarProgramasAnaliticos/Aviso";
                } else {
                    modelo.addAttribute("mensaje", "No existe");
                    return "AdministrarProgramasAnaliticos/Aviso";
                }
            }
        }
    }

    @RequestMapping(value = "/RegistrarProgramaAnalitico.fautapo", method = RequestMethod.POST)
    public String RegistrarProgramaAnalitico(@ModelAttribute("model") ProgramaAnaliticoModel model, Model modelo) {
        ProgramaAnalitico programaanalico = modelMapper.map(model, ProgramaAnalitico.class);
        programaanalico.setSistema_evaluacion_criterios("");
        int IDProgrmaAnalitico = mi.RegistrarProgromaAnalitico(programaanalico);
        ProgramaAnalitico evaluarprograma = new ProgramaAnalitico();
        evaluarprograma.setId_dct_programa_analitico(IDProgrmaAnalitico);
        List<ProgramaAnalitico> EvaluarProgramaAnalitico = null;
        EvaluarProgramaAnalitico = mi.GetProgramaanalitico(evaluarprograma);
        evaluarprograma = EvaluarProgramaAnalitico.stream().findFirst().get();
        modelo.addAttribute("evaluarprograma", evaluarprograma);
        modelo.addAttribute("usuario", GetUsuario().getNombres());
        modelo.addAttribute("periodo", request.getParameter("periodo"));
        modelo.addAttribute("gestion", request.getParameter("gestion"));
        modelo.addAttribute("nombres", request.getParameter("nombres"));
        modelo.addAttribute("materia", request.getParameter("materia"));
        modelo.addAttribute("grupo", request.getParameter("grupo"));
        return "AdministrarProgramasAnaliticos/DetalleProgramaAnalitico";
    }
}
