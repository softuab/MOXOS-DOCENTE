package com.moxos.uab.controller.programaanaliticos;


import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.programaanalitico.*;
import com.moxos.uab.model.models.utility.ParametroEntradaRequest;
import com.moxos.uab.mybatis.entity.*;
import com.moxos.uab.mybatis.logic.MiFacade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class ProgramaAnaliticoController {
    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ModelMapper modelMapper;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/EntradaProgramaAnalitico")
    public String entradaProgramaAnalitico(Model modelo) {
        ParametroEntradaRequest model = new ParametroEntradaRequest();
        modelo.addAttribute("simagen", getUsuario().getImagen());
        modelo.addAttribute("usuario", getUsuario().getNombres());
        modelo.addAttribute("gestion", Integer.toString(getUsuario().getGestion()));
        modelo.addAttribute("periodo", Integer.toString(getUsuario().getPeriodo()));
        modelo.addAttribute("model", model);
        return "ProgramasAnaliticos/Entrada";
    }

    @RequestMapping(value = "/ListarAsignacionesProgramaAnalitico", method = RequestMethod.GET)
    public String listarAsignacionesProgramaAnalitico(@ModelAttribute("model") @Validated ParametroEntradaRequest model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", getUsuario().getImagen());
            modelo.addAttribute("usuario", getUsuario().getNombres());
            modelo.addAttribute("gestion", Integer.toString(getUsuario().getGestion()));
            modelo.addAttribute("periodo", Integer.toString(getUsuario().getPeriodo()));
            modelo.addAttribute("model", model);
            return "ProgramasAnaliticos/Entrada";
        }
        //Sacamos la asignacion del docente
        List<Asignaciones> datosAsignaciones = getListarAsignaciones(model.getGestion(), model.getPeriodo());
        modelo.addAttribute("datosAsignacion", datosAsignaciones);
        modelo.addAttribute("id_rol", getUsuario().getId_rol());
        modelo.addAttribute("nombres", getUsuario().getNombres());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("gestion", model.getGestion());
        return "ProgramasAnaliticos/ListarAsignaciones";
    }

    @RequestMapping(value = "/retornarListaAsignacionesProgramaAnalitico", method = RequestMethod.GET)
    public String listarAsignacionesProgramaAnalitico(@ModelAttribute("model") ParametroEntradaRequest model, Model modelo) {
        //Sacamos la asignacion del docente
        List<Asignaciones> datosAsignaciones = getListarAsignaciones(model.getGestion(), model.getPeriodo());
        modelo.addAttribute("datosAsignacion", datosAsignaciones);
        modelo.addAttribute("id_rol", getUsuario().getId_rol());
        modelo.addAttribute("nombres", getUsuario().getNombres());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("gestion", model.getGestion());
        return "ProgramasAnaliticos/ListarAsignaciones";
    }

    private List<Asignaciones> getListarAsignaciones(Integer gestion, Integer periodo) {
        Asignaciones asignacion = new Asignaciones();
        asignacion.setId_docente(getUsuario().getId_usuario());
        asignacion.setGestion(gestion);
        asignacion.setPeriodo(periodo);
        List<Asignaciones> datosAsignacion = this.mi.getDctListarAsignacionDocenteporProgramaAnalitico(asignacion);
        Asignaciones aux = null;
        for (int i = 0; i < datosAsignacion.size(); i++) {
            aux = datosAsignacion.get(i);
            ProgramaAnalitico programaanalitico = new ProgramaAnalitico();
            programaanalitico.setId_materia(aux.getId_materia());
            programaanalitico.setId_plan(aux.getId_plan());
            programaanalitico.setId_grupo(aux.getId_grupo());
            int PermitirRegistro = mi.permitirRegistroPrograma(programaanalitico);
            Integer id_dct_programa_analitico = mi.getIdProgramaAnalitico(aux);
            aux.setId_dct_programa_analitico(id_dct_programa_analitico);
            aux.setPermitir(PermitirRegistro);
            datosAsignacion.set(i, aux);
        }
        return datosAsignacion;
    }


    @RequestMapping(value = "/detalleProgramaAnalitico", method = RequestMethod.GET)
    private String detalleProgramaAnalitico(@ModelAttribute("model") ProgramaAnaliticoParametroRequest model, Model modelo) {
        ProgramaAnalitico programa = mi.getDetalleProgramaAnalitico(model.getId_dct_programa_analitico());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("materia", model.getMateria());
        modelo.addAttribute("grupo", model.getGrupo());
        modelo.addAttribute("evaluarprograma", programa);
        modelo.addAttribute("tab", model.getTab());
        return "ProgramasAnaliticos/DetalleProgramaAnalitico";
    }

    @RequestMapping(value = "/ElaborarProgramaAnalitico", method = RequestMethod.POST)
    public String elaborarProgramaAnalitico(@ModelAttribute("model") ProgramaAnaliticoCreacionRequest model, Model modelo) throws UnsupportedEncodingException {
        ProgramaAnalitico auxprograma = new ProgramaAnalitico();
        auxprograma.setId_docente(model.getId_docente());
        auxprograma.setId_materia(model.getId_materia());
        auxprograma.setId_plan(model.getId_plan());
        auxprograma.setId_grupo(model.getId_grupo());
        auxprograma.setGestion(model.getGestion());
        auxprograma.setPeriodo(model.getPeriodo());
        List<ProgramaAnalitico> EvaluarProgramaAnalitico = null;
        EvaluarProgramaAnalitico = mi.getListaProgramaanalitico(auxprograma);

        if (!EvaluarProgramaAnalitico.isEmpty()) {
            ProgramaAnalitico evaluarprograma = null;
            evaluarprograma = EvaluarProgramaAnalitico.stream().findFirst().get();
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("gestion", model.getGestion());
            modelo.addAttribute("periodo", model.getPeriodo());
            modelo.addAttribute("materia", model.getMateria());
            modelo.addAttribute("grupo", model.getGrupo());
            if (evaluarprograma.getId_estado().equals("A")) {
                String params = "?id_dct_programa_analitico=" + evaluarprograma.getId_dct_programa_analitico() +
                        "&gestion=" + model.getGestion() +
                        "&periodo=" + model.getPeriodo() +
                        "&materia=" + URLEncoder.encode(model.getMateria(), "UTF-8") +
                        "&grupo=" + URLEncoder.encode(model.getGrupo(), "UTF-8") +
                        "&programa=" + URLEncoder.encode("", "UTF-8") +
                        "&tab=" + URLEncoder.encode("1", "UTF-8");
                return "redirect:/detalleProgramaAnalitico" + params;
            } else {
                if (evaluarprograma.getId_estado().equals("C")) {
                    modelo.addAttribute("mensaje", "Para modificar el programa analitico debe tener autorizacion del HCC.");
                    return "ProgramasAnaliticos/Aviso";
                } else {
                    modelo.addAttribute("mensaje", "No existe");
                    return "ProgramasAnaliticos/Aviso";
                }
            }
        } else {
            ParametrosBusqueda busqueda = new ParametrosBusqueda();
            busqueda.setId_grupo(model.getId_grupo());
            busqueda.setId_materia(model.getId_materia());
            busqueda.setId_plan(model.getId_plan());
            busqueda.setPeriodo(model.getPeriodo());
            busqueda.setGestion(model.getGestion());
            List<ListViewItem> listarpgranalitico = mi.getListarProgramasAnaliticosPorMateria(busqueda);
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            modelo.addAttribute("listarpgranalitico", listarpgranalitico);
            return "ProgramasAnaliticos/ElaborarProgramaAnalitico";
        }
    }

    @RequestMapping(value = "/InicioElaboracionProgramaAnalitico", method = RequestMethod.POST)
    public String inicioElaboracionProgramaAnalitico(@ModelAttribute("model") ProgramaAnaliticoCreacionRequest model, Model modelo) throws UnsupportedEncodingException {
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        ProgramaAnalitico auxprograma = new ProgramaAnalitico();
        auxprograma.setId_docente(model.getId_docente());
        auxprograma.setId_materia(model.getId_materia());
        auxprograma.setId_plan(model.getId_plan());
        auxprograma.setId_grupo(model.getId_grupo());
        auxprograma.setGestion(model.getGestion());
        auxprograma.setPeriodo(model.getPeriodo());
        List<ProgramaAnalitico> EvaluarProgramaAnalitico = null;
        EvaluarProgramaAnalitico = mi.getListaProgramaanalitico(auxprograma);

        if (EvaluarProgramaAnalitico.isEmpty()) {
            ProgramaAnalitico programaAnalitico = null;
            try {
                programaAnalitico = mi.getListarDatosCaratula(model.getId_asignacion()).stream().findFirst().get();
            } catch (Exception ex) {
                modelo.addAttribute("mensaje", "No se registro el area de conocimiento la asignatura comunicar con su jefe de estudio");
                return "ProgramasAnaliticos/Aviso";
            }

            if (programaAnalitico == null) {
                modelo.addAttribute("mensaje", "No existe la designacion del docente");
                return "ProgramasAnaliticos/Aviso";
            }

            ProgramaAnalitico programaAnaliticoDatosMateria = null;
            programaAnaliticoDatosMateria = mi.getListarMateriaProgramaAnalitico(model.getId_asignacion()).stream().findFirst().get();

            if (programaAnaliticoDatosMateria == null) {
                modelo.addAttribute("mensaje", "No existen datos de la materia");
                return "ProgramasAnaliticos/Aviso";
            }
            Docentes docente = null;
            Docentes auxdoc = new Docentes();
            auxdoc.setId_docente(model.getId_docente());
            docente = mi.getBuscarDocente(auxdoc);
            int id_persona = docente.getId_persona();

            Planes plan = new Planes();
            plan.setId_plan(model.getId_plan());
            plan.setId_programa(model.getId_programa());
            plan.setId_tipo_grado(model.getId_tipo_grado());
            plan.setId_mencion(model.getId_mencion());
            plan.setId_materia(model.getId_materia());

            List<ProgramaAnalitico> programaAnaliticoPrerequisitoMateria = null;
            programaAnaliticoPrerequisitoMateria = mi.getListarPrerequisitoMateria(plan);

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
            return "ProgramasAnaliticos/CrearProgramaAnalitico";
        } else {
            ProgramaAnalitico evaluarprograma = null;
            evaluarprograma = EvaluarProgramaAnalitico.stream().findFirst().get();
            if (evaluarprograma.getId_estado().equals("A")) {
                String params = "?id_dct_programa_analitico=" + evaluarprograma.getId_dct_programa_analitico() +
                        "&gestion=" + model.getGestion() +
                        "&periodo=" + model.getPeriodo() +
                        "&materia=" + URLEncoder.encode(model.getMateria(), "UTF-8") +
                        "&grupo=" + URLEncoder.encode(model.getGrupo(), "UTF-8") +
                        "&programa=" + URLEncoder.encode("", "UTF-8") +
                        "&tab=" + URLEncoder.encode("1", "UTF-8");
                return "redirect:/detalleProgramaAnalitico" + params;
            } else {
                if (evaluarprograma.getId_estado().equals("C")) {
                    modelo.addAttribute("mensaje", "Para modificar el programa analitico debe tener autorizacion del HCC.");
                    return "ProgramasAnaliticos/Aviso";
                } else {
                    modelo.addAttribute("mensaje", "No existe");
                    return "ProgramasAnaliticos/Aviso";
                }
            }
        }
    }

    @RequestMapping(value = "/RegistrarProgramaAnalitico", method = RequestMethod.POST)
    public String registrarProgramaAnalitico(@ModelAttribute("model") ProgramaAnaliticoRequest model, Model modelo) throws UnsupportedEncodingException {
        ProgramaAnalitico programaanalico = modelMapper.map(model, ProgramaAnalitico.class);
        programaanalico.setSistema_evaluacion_criterios("");
        int IDProgrmaAnalitico = mi.registrarProgromaAnalitico(programaanalico);
        String params = "?id_dct_programa_analitico=" + IDProgrmaAnalitico +
                "&gestion=" + model.getGestion() +
                "&periodo=" + model.getPeriodo() +
                "&materia=" + URLEncoder.encode(model.getMateria(), "UTF-8") +
                "&grupo=" + URLEncoder.encode(model.getGrupo(), "UTF-8") +
                "&programa=" + URLEncoder.encode("", "UTF-8") +
                "&tab=" + URLEncoder.encode("1", "UTF-8");
        return "redirect:/detalleProgramaAnalitico" + params;
    }

    @RequestMapping(value = "/editarProgramaAnalitico", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public MessageResult editarProgramaAnalitico(@RequestBody ProgramaAnaliticoGuardarRequest model) {
        MessageResult Respuesta = new MessageResult();
        ProgramaAnalitico programaanalico = modelMapper.map(model, ProgramaAnalitico.class);
        try {
            mi.actualizarProgramaAnalitico(programaanalico);
            Respuesta.setMessage("Se actualizo el registro con exito..");
            Respuesta.setStatus("OK");
        } catch (Exception ex) {
            Respuesta.setMessage("Problemas al eliminar:" + ex.getMessage());
            Respuesta.setStatus("Error");
        }
        return Respuesta;
    }

    @PostMapping("/ImportarProgramaAnalitico")
    private String ImportarProgramaAnalitico(@ModelAttribute("model") ImportarProgramaAnaliticoRequest model) throws UnsupportedEncodingException {
        ProgramaAnalitico programa = mi.getProgramaanalitico(model.getId_dct_programa_analitico());
        ProgramaAnalitico programaAnaliticoCopia = programa;
        programaAnaliticoCopia.setGestion(model.getGestion());
        programaAnaliticoCopia.setPeriodo(model.getPeriodo());
        programaAnaliticoCopia.setId_docente(getUsuario().getId_usuario());
        programaAnaliticoCopia.setId_asignacion(model.getId_asignacion());
        programaAnaliticoCopia.setId_grupo(model.getId_grupo());
        List<ProgramaAnalitico> EvaluarProgramaAnalitico = mi.getListaProgramaanalitico(programaAnaliticoCopia);
        if (EvaluarProgramaAnalitico.isEmpty()) {
            int IDProgrmaAnalitico = mi.copy(programaAnaliticoCopia, model.getId_dct_programa_analitico());
            String params = "?id_dct_programa_analitico=" + IDProgrmaAnalitico +
                    "&gestion=" + model.getGestion() +
                    "&periodo=" + model.getPeriodo() +
                    "&materia=" + URLEncoder.encode(model.getMateria(), "UTF-8") +
                    "&grupo=" + URLEncoder.encode(model.getGrupo(), "UTF-8") +
                    "&programa=" + URLEncoder.encode("", "UTF-8") +
                    "&tab=" + URLEncoder.encode("1", "UTF-8");
            return "redirect:/detalleProgramaAnalitico" + params;
        } else {
            ProgramaAnalitico evaluarprograma = null;
            evaluarprograma = EvaluarProgramaAnalitico.stream().findFirst().get();
            String params = "?id_dct_programa_analitico=" + evaluarprograma.getId_dct_programa_analitico() +
                    "&gestion=" + model.getGestion() +
                    "&periodo=" + model.getPeriodo() +
                    "&materia=" + URLEncoder.encode(model.getMateria(), "UTF-8") +
                    "&grupo=" + URLEncoder.encode(model.getGrupo(), "UTF-8") +
                    "&programa=" + URLEncoder.encode("", "UTF-8") +
                    "&tab=" + URLEncoder.encode("1", "UTF-8");
            return "redirect:/detalleProgramaAnalitico" + params;
        }
    }
}
