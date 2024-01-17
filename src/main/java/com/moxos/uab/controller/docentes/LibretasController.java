package com.moxos.uab.controller.docentes;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.models.Libretas.*;
import com.moxos.uab.model.models.Moodle.UserCourseMoodleModel;
import com.moxos.uab.model.models.utility.ParametroEntradaRequest;
import com.moxos.uab.mybatis.entity.*;
import com.moxos.uab.mybatis.logic.MiFacade;
import com.moxos.uab.util.Matematicas;
import com.moxos.uab.util.RemoteIpHostHelper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class LibretasController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/definirEvaluacion/entrada")
    public String Entrada(Model modelo) {
        ParametroEntradaRequest model = new ParametroEntradaRequest();
        modelo.addAttribute("simagen", this.getUsuario().getImagen());
        modelo.addAttribute("usuario", this.getUsuario().getNombres());
        modelo.addAttribute("gestion", Integer.toString(this.getUsuario().getGestion()));
        modelo.addAttribute("periodo", Integer.toString(this.getUsuario().getPeriodo()));
        modelo.addAttribute("model", model);
        return "Libretas/Entrada";
    }

    @RequestMapping(value = "/libretas/retornorListarAsignaciones", method = RequestMethod.POST)
    public String retornorListarAsignaciones(@ModelAttribute("model") ParametroEntradaRequest model, Model modelo) {
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());

        //Sacamos la asignacion del docente
        List<Asignaciones> datosAsignacion = retornarAsignacionDetalle(model);
        modelo.addAttribute("datosAsignacion", datosAsignacion);
        modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
        return "Libretas/ListarAsignaciones";
    }

    private List<Asignaciones> retornarAsignacionDetalle(ParametroEntradaRequest model) {
        Asignaciones asignacion = new Asignaciones();
        asignacion.setId_docente(this.getUsuario().getId_usuario());
        asignacion.setGestion(model.getGestion());
        asignacion.setPeriodo(model.getPeriodo());
        List<Asignaciones> datosAsignacion = this.mi.getDctListarAsignacionDocente(asignacion);
        return datosAsignacion;
    }

    @RequestMapping(value = "/libretas/ListarAsignaciones", method = RequestMethod.POST)
    public String ListarAsignaciones(@ModelAttribute("model") @Validated ParametroEntradaRequest model, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", this.getUsuario().getImagen());
            modelo.addAttribute("usuario", this.getUsuario().getNombres());
            modelo.addAttribute("gestion", Integer.toString(this.getUsuario().getGestion()));
            modelo.addAttribute("periodo", Integer.toString(this.getUsuario().getPeriodo()));
            modelo.addAttribute("model", model);
            return "Libretas/Entrada";
        }
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());

        //Sacamos la asignacion del docente
        List<Asignaciones> datosAsignacion = retornarAsignacionDetalle(model);
        modelo.addAttribute("datosAsignacion", datosAsignacion);
        modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
        return "Libretas/ListarAsignaciones";
    }

    @RequestMapping(value = "/libretas/DefinirEvaluacion", method = RequestMethod.POST)
    public String definirEvaluacion(@ModelAttribute("model") ParametrosLibretasRequest model, Model modelo) {
        //Verificando
        if (model.getId_asignacion() == 0) {
            modelo.addAttribute("mensaje", "No ingreso la Asignaci&oacute;n del Docente. Verifique los datos.");
            return "Libretas/Error";
        }

        //Buscamos la asignacion del Docente
        Asignaciones datosAsignacion = new Asignaciones();
        datosAsignacion.setId_asignacion(model.getId_asignacion());
        datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
        if (datosAsignacion == null) {
            modelo.addAttribute("mensaje", "No se existen datos para la asignaci&oacute;n del docente seleccionado.");
            return "Libretas/Error";
        }
        modelo.addAttribute("datosAsignacion", datosAsignacion);

        //Buscar el programa
        Programas buscarPrograma = new Programas();
        buscarPrograma.setId_programa(model.getId_programa());
        Programas datosPrograma = this.mi.getPrgBuscarPrograma(buscarPrograma);
        model.setPrograma(datosPrograma.getPrograma());
        //modelo.addAttribute(datosPrograma.);

        Materias materias = new Materias();
        materias.setId_programa(model.getId_programa());
        materias.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
        List<GrupoDefinicion> gruposdefinicion = mi.getDefinicionNotasPorPrograma(materias);


        //Buscar Datos de la materia
        Materias datosMateria = new Materias();
        datosMateria.setId_materia(datosAsignacion.getId_materia());
        Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
        model.setSigla(buscarMateria.getSigla());
        model.setMateria(buscarMateria.getMateria());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("parametros", model);
        //Verificamos si el departamento de la materia coincide con la asignacion
        if (datosAsignacion.getId_departamento() != buscarMateria.getId_departamento()) {
            modelo.addAttribute("mensaje", "Alerta!!. El departamento de la materia no coincide con la asignaci&oacute;n del docente. Consulte al administrador del sistema.");
            return "Libretas/Error";
        }

        //Sacamos la fase actual segun la asignacion del docente
        Libretas datosFase = getBuscarFase(datosAsignacion.getId_fase(), datosAsignacion.getId_tipo_evaluacion(), datosAsignacion.getId_departamento(), datosAsignacion.getGestion(), datosAsignacion.getPeriodo());
        if (datosFase.getFase() == null) {
            modelo.addAttribute("mensaje", "No existen mas fases. Por lo que no puede realizar m&aacute;s definiciones de Evaluaci&oacute;n.");
            return "Libretas/Error";
        }
        modelo.addAttribute("fase", datosFase.getFase());

        //En caso de existir fases listar Tipos de Notas
        Libretas datosTiposNotas = new Libretas();
        datosTiposNotas.setGestion(datosAsignacion.getGestion());
        datosTiposNotas.setPeriodo(datosAsignacion.getPeriodo());
        datosTiposNotas.setId_materia(datosAsignacion.getId_materia());
        datosTiposNotas.setId_fase(datosAsignacion.getId_fase());
        datosTiposNotas.setId_grupo(datosAsignacion.getId_grupo());
        datosTiposNotas.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        datosTiposNotas.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
        datosTiposNotas.setId_departamento(datosAsignacion.getId_departamento());
        List<Libretas> lTiposNotas = this.mi.getLbrListarTiposNotas(datosTiposNotas);
        List<Libretas> lTiposNotasLibretas = new ArrayList<>();
        Integer porcentaje;
        Boolean esModalidad = this.mi.esMateriaModalidad(datosAsignacion.getId_materia());
        for (Libretas obj : lTiposNotas) {
            if (!esModalidad) {
                try {
                    porcentaje = gruposdefinicion.stream().filter(p -> p.getId_tipo_nota() == obj.getId_tipo_nota()).collect(Collectors.toList()).stream().findFirst().get().getPonderacion();
                } catch (Exception ex) {
                    porcentaje = 0;
                }
                obj.setPorcentaje(porcentaje);
                lTiposNotasLibretas.add(obj);
            } else {
                if (obj.getId_tipo_nota() == 5 || obj.getId_tipo_nota() == 6) {
                    porcentaje = 100;
                } else {
                    porcentaje = 0;
                }
                obj.setPorcentaje(porcentaje);
                lTiposNotasLibretas.add(obj);
            }
        }
        modelo.addAttribute("lTiposNotas", lTiposNotasLibretas);
        return "Libretas/DefinirEvaluacion";
    }

    @RequestMapping(value = "/libretas/confirmarDefinicion", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public MessageResult confirmarDefinicion(@RequestBody DefinicionLibretasRequest model) {
        MessageResult respuesta = new MessageResult();
        Integer iResultado = 0;

        //Verificando asignacion
        if (model.getId_asignacion() == null || model.getId_asignacion() == 0) {
            respuesta.setMessage("No ingreso la Asignaci&oacute;n del Docente. Verifique los datos.");
            respuesta.setStatus("Error");
            return respuesta;
        }
        //Buscamos Asignacion Docente
        Asignaciones datosAsignacion = new Asignaciones();
        datosAsignacion.setId_asignacion(model.getId_asignacion());
        datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
        if (datosAsignacion == null) {
            respuesta.setMessage("No  se existen datos para la asignaci&oacute;n del docente seleccionado.");
            respuesta.setStatus("Error");
            return respuesta;
        }
        for (DetalleTipoNotasRequest detalle : model.getDetalle()) {
            //Primero anulamos
            if ((detalle.getCantidad() == 0) && (detalle.getPonderacion() == 0)) {
                //Eliminamos la definicion de evaluacion
                Libretas datosEvaluacion = getLibretas(datosAsignacion, detalle);
                iResultado = this.mi.setGrpEliminarEvaluacion(datosEvaluacion);
                if (iResultado != 1) {
                    respuesta.setMessage("No se podra; anular la definicion donde modifica CANTIDAD y PONDERACION = 0  Por que la definicion cuenta con registros de  notas en libretas.");
                    respuesta.setStatus("Error");
                    return respuesta;
                }
            }
        }
        //Luego Registramos
        //Registramos la definicion de evaluacion
        for (DetalleTipoNotasRequest detalle : model.getDetalle()) {
            if ((detalle.getCantidad() > 0) && (detalle.getPonderacion() > 0)) {
                //En caso de que existe actualizamos la definicion de evaluacion
                Libretas datosEvaluacion = getLibretas(datosAsignacion, detalle);
                iResultado = this.mi.setGrpRegistrarEvaluacion(datosEvaluacion);
                if (iResultado != 1) {
                    respuesta.setMessage("Ocurrio; un error. No se pudo registrar la definicion de evaluacion.");
                    respuesta.setStatus("Error");
                    return respuesta;
                }
            }
        }
        respuesta.setMessage("");
        respuesta.setStatus("OK");
        return respuesta;
    }

    private Libretas getLibretas(Asignaciones datosAsignacion, DetalleTipoNotasRequest detalle) {
        Libretas datosEvaluacion = new Libretas();
        datosEvaluacion.setId_materia(datosAsignacion.getId_materia());
        datosEvaluacion.setId_grupo(datosAsignacion.getId_grupo());
        datosEvaluacion.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
        datosEvaluacion.setId_tipo_nota(detalle.getTipoNota());
        datosEvaluacion.setId_fase(datosAsignacion.getId_fase());
        datosEvaluacion.setId_departamento(datosAsignacion.getId_departamento());
        datosEvaluacion.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        datosEvaluacion.setId_tipo_docente(datosAsignacion.getId_tipo_docente());
        datosEvaluacion.setGestion(datosAsignacion.getGestion());
        datosEvaluacion.setPeriodo(datosAsignacion.getPeriodo());
        datosEvaluacion.setCantidad(detalle.getCantidad());
        datosEvaluacion.setPonderacion(detalle.getPonderacion());
        datosEvaluacion.setId_rol(this.getUsuario().getId_rol());       //CRCB
        datosEvaluacion.setUlt_usuario(this.getUsuario().getId_usuario());
        return datosEvaluacion;
    }

    @RequestMapping(value = "/libretas/ListarEstudiantesProgramados", method = RequestMethod.POST)
    public String listarEstudiantesProgramados(@ModelAttribute("model") ParametrosLibretasEvaluacionRequest model, Model modelo) {

        List<Libretas> listaNotasPromedios = new ArrayList<>();
        double notaMinimaSegundaInstacia = 0;

        //Sacamos el programa
        Programas datosPrograma = new Programas();
        datosPrograma.setId_programa(model.getId_programa());
        Programas buscarPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);

        //Sacamos datos de la materia
        Materias datosMateria = new Materias();
        datosMateria.setId_materia(model.getId_materia());
        Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);

        //Verificamos si el departamento de la materia coincide con la asignacion
        if (model.getId_departamento() != buscarMateria.getId_departamento()) {
            modelo.addAttribute("mensaje", "Alerta!!. El departamento de la materia no coincide con la asignaci&oacute;n del docente. Consulte al administrador del sistema.");
            return "Libretas/Error";
        }

        //Sacamos la fase actual segun la asignacion del docente
        Libretas datosFase = getBuscarFase(model.getId_fase(), model.getId_tipo_evaluacion(), model.getId_departamento(), model.getGestion(), model.getPeriodo());
        ;
        if (datosFase.getFase() == null) {
            modelo.addAttribute("mensaje", "No existen mas fases.");
            return "Libretas/Error";
        }
        model.setFase(datosFase.getFase());

        //Sacamos la definicion de items de la materia
        Libretas datosEvaluacion = new Libretas();
        datosEvaluacion.setId_materia(model.getId_materia());
        datosEvaluacion.setId_grupo(model.getId_grupo());
        datosEvaluacion.setGestion(model.getGestion());
        datosEvaluacion.setPeriodo(model.getPeriodo());
        datosEvaluacion.setId_departamento(buscarMateria.getId_departamento());
        datosEvaluacion.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
        datosEvaluacion.setId_fase(model.getId_fase());
        datosEvaluacion.setId_modelo_ahorro(model.getId_modelo_ahorro());
        datosEvaluacion.setId_programa(model.getId_programa());
        datosEvaluacion.setId_docente(this.getUsuario().getId_usuario());
        List<Libretas> lEvaluacion = this.mi.getGrpListarEvaluacionDefinida(datosEvaluacion);
        modelo.addAttribute("listaItems", lEvaluacion);

        //Sacamos las notas de los estudiantes
        if (model.getId_tipo_nota_s() != null) {
            String elementos[];
            elementos = new String[2];
            elementos = model.getId_tipo_nota_s().split(":");
            String sIdLbrTipoNota = elementos[0];
            String sCantidad = elementos[1];
            modelo.addAttribute("cantidad", sCantidad);
            int iCantidad = Integer.parseInt(sCantidad);

            //Sacamos el tipo de nota que estamos calificando
            Libretas datosTipoNota = new Libretas();
            datosTipoNota.setId_lbr_tipo_nota(Integer.parseInt(sIdLbrTipoNota));
            datosTipoNota = this.mi.getLbrBuscarTipoNota(datosTipoNota);
            int iIdTipoNota = datosTipoNota.getId_tipo_nota();
            datosEvaluacion.setId_tipo_nota(iIdTipoNota);
            modelo.addAttribute("id_tipo_nota", iIdTipoNota);
            modelo.addAttribute("tipo_nota", datosTipoNota.getTipo_nota().toUpperCase());

            List<Libretas> listaCalendarioExcepcion = mi.getCalendarioAcademicoExcepciones(datosEvaluacion);
            List<Libretas> listaCalendarioExcepcionEstudiante = mi.getCalendarioAcademicoExcepcionesEstudiante(datosEvaluacion);
            List<Libretas> nominaNotasBaseDatos = mi.getDetalleNotaLibretaMateria(datosEvaluacion);

            //Sacando la lista de estudiantes programados a la materia, evaluacion regular
            if ((model.getId_tipo_grado() == 1) && (model.getId_tipo_evaluacion() != 2)) {
                Libretas datosEstProg = new Libretas();
                datosEstProg.setId_materia(model.getId_materia());
                datosEstProg.setId_grupo(model.getId_grupo());
                datosEstProg.setId_modelo_ahorro(model.getId_modelo_ahorro());
                datosEstProg.setGestion(model.getGestion());
                datosEstProg.setPeriodo(model.getPeriodo());
                datosEstProg.setId_fase(model.getId_fase());
                datosEstProg.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
                datosEstProg.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
                List<Libretas> lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);
                if (model.getId_fase() == 1) {
                    listaNotasPromedios = mi.getEstListarNotasEstudianteLibretaSegunda(datosEstProg);
                    Programas programa = new Programas();
                    programa.setId_programa(model.getId_programa());
                    programa.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
                    programa.setPeriodo(model.getPeriodo());
                    programa.setGestion(model.getGestion());
                    List<Programas> listaNotasminimas = this.mi.getEstListarNotaMinimaporPrograma(programa);
                    notaMinimaSegundaInstacia = listaNotasminimas.isEmpty() ? -1 : listaNotasminimas.stream().findFirst().get().getNota_minima();
                    if (notaMinimaSegundaInstacia == -1) {
                        modelo.addAttribute("mensaje", "No definio la nota minima de segunda instancia.");
                        return "Libretas/Error";
                    }
                }
                Libretas libreta = null;
                for (int i = 0; i < lEstudiantes.size(); i++) {
                    libreta = lEstudiantes.get(i);
                    int iIdEstudiante = libreta.getId_estudiante();
                    List<Libretas> notasEstudiante = new ArrayList<>();
                    if (permiteEvaluar(iIdEstudiante, buscarPrograma.getNota_aprobacion(), notaMinimaSegundaInstacia, listaNotasPromedios)) //Evaluamos si permite para evaluar
                    {
                        for (int j = 1; j <= iCantidad; j++) {
                            Libretas nota = null;
                            Libretas notaResultado = null;
                            int nroNota = j;
                            List<Libretas> nominafiltrar = nominaNotasBaseDatos.stream().filter(p -> p.getNro_nota() == nroNota && p.getId_estudiante() == iIdEstudiante).collect(Collectors.toList());
                            nota = nominafiltrar.isEmpty() ? null : nominafiltrar.stream().findFirst().get();
                            int permitirModificar = 0;
                            if (nota == null) {
                                notaResultado = new Libretas();
                                notaResultado.setNota(0);
                                notaResultado.setNro_nota(nroNota);
                                if (iIdTipoNota == 3 || iIdTipoNota == 5) {
                                    List<Libretas> notasPermitidas = listaCalendarioExcepcion.stream().filter(p -> p.getId_tipo_nota() == iIdTipoNota && p.getNro_tipo_nota() == nroNota).collect(Collectors.toList());
                                    permitirModificar = notasPermitidas.isEmpty() ? 0 : 1;
                                    if (!listaCalendarioExcepcionEstudiante.isEmpty()) {
                                        List<Libretas> notasPermitidasporestudiante = listaCalendarioExcepcionEstudiante.stream().filter(p -> p.getId_tipo_nota() == iIdTipoNota && p.getNro_tipo_nota() == nroNota && p.getId_estudiante() == iIdEstudiante).collect(Collectors.toList());
                                        permitirModificar = notasPermitidasporestudiante.isEmpty() ? 0 : 1;
                                    }
                                } else {
                                    permitirModificar = 1;
                                }
                                notaResultado.setPermitidomodificar(permitirModificar);
                                notasEstudiante.add(notaResultado);
                            } else {
                                notaResultado = new Libretas();
                                notaResultado.setNota(nota.getNota());
                                notaResultado.setNro_nota(nroNota);
                                if (iIdTipoNota == 3 || iIdTipoNota == 5) {
                                    List<Libretas> notasPermitidas = listaCalendarioExcepcion.stream().filter(p -> p.getId_tipo_nota() == iIdTipoNota && p.getNro_tipo_nota() == nroNota).collect(Collectors.toList());
                                    permitirModificar = notasPermitidas.isEmpty() ? 0 : 1;
                                    if (!listaCalendarioExcepcionEstudiante.isEmpty()) {
                                        List<Libretas> notasPermitidasporestudiante = listaCalendarioExcepcionEstudiante.stream().filter(p -> p.getId_tipo_nota() == iIdTipoNota && p.getNro_tipo_nota() == nroNota && p.getId_estudiante() == iIdEstudiante).collect(Collectors.toList());
                                        permitirModificar = notasPermitidasporestudiante.isEmpty() ? 0 : 1;
                                    }
                                } else {
                                    permitirModificar = 1;
                                }
                                notaResultado.setPermitidomodificar(permitirModificar);
                                notasEstudiante.add(notaResultado);
                            }
                        }

                        libreta.setNotas(notasEstudiante);
                        lEstudiantes.set(i, libreta);
                        modelo.addAttribute("numItems", notasEstudiante);
                    }
                }
                PagedListHolder lNotas = new PagedListHolder(lEstudiantes);
                lNotas.setPageSize(lNotas.getNrOfElements());
                modelo.addAttribute("listaNotas", lNotas);
            }
        }
        modelo.addAttribute("parametro", model);
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("id_docente", this.getUsuario().getId_usuario());
        modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
        return "Libretas/ListarEstudiantesProgramados";
    }

    private boolean permiteEvaluar(int idEstudiante, double notaMinima, double minimaSegundaInstancia, List<Libretas> listaPromedios) {
        if (!listaPromedios.isEmpty()) {
            List<Libretas> notasPromedio = listaPromedios.stream().filter(p -> p.getId_estudiante() == idEstudiante).collect(Collectors.toList());
            if (!notasPromedio.isEmpty()) {
                double nota = Matematicas.Redondear(notasPromedio.stream().mapToDouble(p -> p.getNota()).sum(), 0);
                if (nota < notaMinima) {
                    nota = Matematicas.Redondear(notasPromedio.stream().filter(p -> p.getId_tipo_nota() != 5).mapToDouble(p -> p.getNota()).sum(), 0);
                    return nota >= minimaSegundaInstancia;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    private Libretas getBuscarFase(Integer idFase, Integer idTipoEvaluacion, Integer idDepartamento, Integer gestion, Integer periodo) {
        Libretas buscarFase = new Libretas();
        buscarFase.setId_fase(idFase);
        buscarFase.setId_tipo_evaluacion(idTipoEvaluacion);
        buscarFase.setId_departamento(idDepartamento);
        buscarFase.setGestion(gestion);
        buscarFase.setPeriodo(periodo);
        return this.mi.getLbrBuscarFase(buscarFase);
    }

    @RequestMapping(value = "/libretas/registrarNotasEstudiantes", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public MessageResult RegistrarNotasEstudiantes(@RequestBody DatosNotaRequest model) {
        MessageResult Respuesta = new MessageResult();
        //Sacamos la fase actual segun la asignacion del docente
        Libretas datosFase = getBuscarFase(model.getId_fase(), model.getId_tipo_evaluacion(), model.getId_departamento(), model.getGestion(), model.getPeriodo());
        if (datosFase.getFase() == null) {
            Respuesta.setMessage("No se puede encontrar la fase debe actualizar la pagina.");
            Respuesta.setStatus("Error");
            return Respuesta;
        }
        //Verificando si la fase es mayor a 100
        if (datosFase.getId_fase() >= 100) {
            Respuesta.setMessage("No esta permitido administrar notas para la fase ::&nbsp;" + datosFase.getFase());
            Respuesta.setStatus("Error");
            return Respuesta;
        }

        //Sacamos el tipo de nota que se esta calificando
        Libretas datosTipoNota = new Libretas();
        datosTipoNota.setId_lbr_tipo_nota(model.getId_tipo_nota());
        datosTipoNota = this.mi.getLbrBuscarTipoNota(datosTipoNota);

        //GUARDANDO LAS NOTAS DE LOS ESTUDIANTES, EVALUACION REGULAR
        //Recuperamos las notas de los estudiantes
        if (model.getId_tipo_grado() == 1 && model.getId_tipo_evaluacion() != 2) {
            int iId_estudiante = model.getId_estudiante();
            int iNro_nota = model.getNro_nota_s();
            //Registramos las notas de los estudiantes
            Libretas insertar = new Libretas();
            insertar.setId_estudiante(iId_estudiante);
            insertar.setId_grupo(model.getId_grupo());
            insertar.setId_materia(model.getId_materia());
            insertar.setId_departamento(model.getId_departamento());
            insertar.setId_modelo_ahorro(model.getId_modelo_ahorro());
            insertar.setId_fase(model.getId_fase());
            insertar.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
            insertar.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
            insertar.setGestion(model.getGestion());
            insertar.setPeriodo(model.getPeriodo());
            insertar.setNro_nota(iNro_nota);
            insertar.setNota(model.getNota());
            insertar.setId_rol(this.getUsuario().getId_rol());     //CRCB
            insertar.setUlt_usuario(this.getUsuario().getId_usuario());
            int iValor = this.mi.setEstInsertarNotaEstudianteFase(insertar); //ESTA HACE LOS CUATRO PASOS
            if (iValor != 1) {
                Respuesta.setMessage("Nota no registrada por problemas de registro en la base de datos. ");
                Respuesta.setStatus("Error");
                return Respuesta;
            } else {
                String ip = RemoteIpHostHelper.getRemoteIpFrom(request);
                String UserAgent = RemoteIpHostHelper.getUserAgent(request);
                String Ubicacion = model.getUbicacion();
                Libretas lib = this.mi.getNotasEstudiante(insertar);
                lib.setIp(ip);
                lib.setUbicacion(Ubicacion);
                lib.setDetalle_dispositivo(UserAgent);
                this.mi.registrarBitacoraCambiosDocente(lib);
            }
        }
        Respuesta.setMessage("");
        Respuesta.setStatus("OK");
        return Respuesta;
    }

    @GetMapping("/libretas/exportar-excel")
    public void exportarExcel(@ModelAttribute("model") PlanillaEstudiantesExcelRequest model, HttpServletResponse response) throws IOException {

        String elementos[];
        elementos = model.getId_tipo_nota_s().split(":");
        Integer sIdLbrTipoNota = Integer.parseInt(elementos[0]);
        Integer iCantidad = Integer.parseInt(elementos[1]);

        //Sacamos el tipo de nota que estamos calificando
        Libretas datosTipoNota = new Libretas();
        datosTipoNota.setId_lbr_tipo_nota(sIdLbrTipoNota);
        datosTipoNota = this.mi.getLbrBuscarTipoNota(datosTipoNota);
        Integer iIdTipoNota = datosTipoNota.getId_tipo_nota();

        Libretas buscarlistadeprogramados = new Libretas();
        buscarlistadeprogramados.setId_programa(model.getId_programa());
        buscarlistadeprogramados.setId_grupo(model.getId_grupo());
        buscarlistadeprogramados.setId_tipo_nota(iIdTipoNota);
        buscarlistadeprogramados.setId_materia(model.getId_materia());
        buscarlistadeprogramados.setGestion(model.getGestion());
        buscarlistadeprogramados.setPeriodo(model.getPeriodo());
        buscarlistadeprogramados.setId_tipo_evaluacion(model.getId_tipo_evaluacion());

        List<Libretas> programados = mi.getPlanillaProgramadosMateria(buscarlistadeprogramados);
        List<Libretas> notas = mi.getNotasdeMatriculados(buscarlistadeprogramados);


        // código para crear el archivo Excel
        Workbook libro = new XSSFWorkbook();
        Sheet hoja = libro.createSheet("PLANILLA ESTUDIANTES");

        // stylo celda
        CellStyle style = libro.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        Row titulo = hoja.createRow(0);
        Cell rutitulo = titulo.createCell(0);
        Cell nombreCompletotitulo = titulo.createCell(1);
        Cell auladesconcentradatitulo = titulo.createCell(2);
        rutitulo.setCellValue("R.U.");
        rutitulo.setCellStyle(style);
        nombreCompletotitulo.setCellValue("NOMBRE COMPLETO");
        nombreCompletotitulo.setCellStyle(style);
        auladesconcentradatitulo.setCellValue("AULA DESCONCENTRADA");
        auladesconcentradatitulo.setCellStyle(style);

        int incrementar = 3;
        for (int j = 1; j <= iCantidad; j++) {
            Cell cell = titulo.createCell(incrementar);
            cell.setCellValue("NOTA  " + (j + 1));
            cell.setCellStyle(style);
            incrementar++;
        }
        int i = 1;
        for (Libretas libreta : programados) {
            Row detalle = hoja.createRow(i);
            Cell ru = detalle.createCell(0);
            Cell nombreCompleto = detalle.createCell(1);
            Cell auladesconcentrada = detalle.createCell(2);

            nombreCompleto.setCellValue(libreta.getNombres());
            nombreCompleto.setCellStyle(style);
            ru.setCellValue(libreta.getId_estudiante());
            ru.setCellStyle(style);
            auladesconcentrada.setCellValue(libreta.getSede_desconcentrada());
            auladesconcentrada.setCellStyle(style);

            incrementar = 3;
            for (int j = 1; j <= iCantidad; j++) {
                Integer nro_nota = j;
                Optional<Libretas> resultado = notas.stream().filter(p -> p.getNro_nota() == nro_nota && p.getId_estudiante() == libreta.getId_estudiante()).findFirst();
                Cell cell = detalle.createCell(incrementar);
                if (resultado.isPresent()) {
                    cell.setCellValue(resultado.get().getNota());
                } else {
                    cell.setCellValue(0);
                }
                cell.setCellStyle(style);
                incrementar++;
            }
            i++;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        libro.write(baos);
        // Establecer los encabezados para descargar el archivo
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=planillestudiantesproogramados.xlsx");
        // Escribir los datos del archivo en la respuesta HTTP
        response.getOutputStream().write(baos.toByteArray());
    }

    @RequestMapping(value = "/libretas/avanzarFase", method = RequestMethod.POST)
    public String avanzarFase(@ModelAttribute("model") ParametrosLibretasFaseRequest model, Model modelo) {
        Libretas buscarFaseActual = getBuscarFase(model.getId_fase(), model.getId_tipo_evaluacion(), model.getId_departamento(), model.getGestion(), model.getPeriodo());
        model.setFase(buscarFaseActual.getFase());
        modelo.addAttribute("buscarFaseActual", buscarFaseActual);
        modelo.addAttribute("fase_actual", buscarFaseActual.getFase());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("parametros", model);
        //Sacando la fase maxima
        Libretas datosFaseMax = new Libretas();
        datosFaseMax.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
        datosFaseMax.setId_programa(model.getId_programa());
        datosFaseMax.setGestion(model.getGestion());
        datosFaseMax.setPeriodo(model.getPeriodo());
        int iId_fase_maxima = this.mi.getLbrBuscarFaseMaxima(datosFaseMax);

        if (model.getId_tipo_grado() == 1) {  //Universitario
            //Sacando la fase siguiente de acuerdo a Asignacion docente
            Libretas datosFaseA = new Libretas();
            datosFaseA.setId_fase(model.getId_fase() + 1);
            datosFaseA.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
            datosFaseA.setId_departamento(model.getId_departamento());
            datosFaseA.setGestion(model.getGestion());
            datosFaseA.setPeriodo(model.getPeriodo());
            Libretas buscarFaseSiguiente = this.mi.getLbrBuscarFase(datosFaseA);
            if (buscarFaseSiguiente == null) {
                modelo.addAttribute("fase_siguiente", "Pre-Cierre de Libreta (No existen mas fases definidas)");
            } else {
                modelo.addAttribute("fase_siguiente", buscarFaseSiguiente.getFase());
            }
        }
        if (model.getId_tipo_grado() != 1) { //Vestibular / Postgrado/etc..
            //Sacando la fase siguiente
            Libretas buscarFaseSiguiente = getBuscarFase(model.getId_fase(), model.getId_tipo_evaluacion(), model.getId_departamento(), model.getGestion(), model.getPeriodo());
            if (buscarFaseSiguiente == null) {
                modelo.addAttribute("fase_siguiente", "Pre-Cierre de Libreta (No existen mas fases definidas)");
            } else {
                modelo.addAttribute("fase_siguiente", buscarFaseSiguiente.getFase());
            }
        }
        return "Libretas/AvanzarFase";
    }

    @RequestMapping(value = "/libretas/registrarAvanzarFase", method = RequestMethod.POST)
    public String RegistrarAvanzarFase(@ModelAttribute("model") ParametrosLibretasFaseRequest model, Model modelo) {

        String sMensaje = "";
        //Buscamos la asignacion docente
        Asignaciones datosAsignacion = new Asignaciones();
        datosAsignacion.setId_asignacion(model.getId_asignacion());
        datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
        model.setFase(datosAsignacion.getFase());
        model.setGestion(datosAsignacion.getGestion());
        model.setPeriodo(datosAsignacion.getPeriodo());
        model.setId_fase(datosAsignacion.getId_fase());
        model.setId_materia(datosAsignacion.getId_materia());
        model.setGrupo(datosAsignacion.getGrupo());
        model.setMateria(datosAsignacion.getMateria());
        model.setSigla(datosAsignacion.getSigla());
        model.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        model.setId_departamento(datosAsignacion.getId_departamento());
        model.setId_grupo(datosAsignacion.getId_grupo());
        model.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());


        //obtener imagen del perfil
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("parametros", model);


        //Sacamos la fase actual de acuerdo a la asignacion del docente
        Libretas datosFase = new Libretas();
        datosFase.setId_fase(model.getId_fase());
        datosFase.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
        datosFase.setId_departamento(model.getId_departamento());
        datosFase.setGestion(model.getGestion());
        datosFase.setPeriodo(model.getPeriodo());
        Libretas buscarFaseActual = this.mi.getLbrBuscarFase(datosFase);
        modelo.addAttribute("buscarFaseActual", buscarFaseActual);
        modelo.addAttribute("fase_actual", buscarFaseActual.getFase());

        //Sacando la fase maxima
        Libretas datosFaseMax = new Libretas();
        datosFaseMax.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
        datosFaseMax.setId_programa(model.getId_programa());
        datosFaseMax.setGestion(model.getGestion());
        datosFaseMax.setPeriodo(model.getPeriodo());
        int iId_fase_maxima = this.mi.getLbrBuscarFaseMaxima(datosFaseMax);

        if ("Avanzar >>".equals(model.getAvanzar())) {
            Libretas avanzar = new Libretas();
            avanzar.setId_docente(this.getUsuario().getId_usuario());
            avanzar.setId_departamento(model.getId_departamento());
            avanzar.setId_materia(model.getId_materia());
            avanzar.setId_grupo(model.getId_grupo());
            avanzar.setId_modelo_ahorro(model.getId_modelo_ahorro());
            avanzar.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
            avanzar.setGestion(model.getGestion());
            avanzar.setPeriodo(model.getPeriodo());
            avanzar.setId_fase(model.getId_fase());
            avanzar.setId_tipo_grado(model.getId_tipo_grado());
            avanzar.setId_rol(this.getUsuario().getId_rol());         //CRCB
            avanzar.setUlt_usuario(this.getUsuario().getId_usuario());

            int iValor = this.mi.setDctAvanzarFase(avanzar);
            if (iValor == 1) {
                sMensaje = "** FASE AVANZADA **";
            } else {
                sMensaje = "** FASE AVANZADA **";
            }
        } else {
            sMensaje = "No selecciono la Accion de Avanzar >>";
        }

        modelo.addAttribute("mensaje", sMensaje);
        return "Libretas/SalidaRegistrarAvanzarFase";
    }

    @GetMapping("/libretas/exportarEstudiantesProgramados")
    public void exportarEstudiantesProgramados(@ModelAttribute("model") PlanillaEstudiantesExcelRequest model, HttpServletResponse response) throws IOException {

        Libretas buscarlistadeprogramados = new Libretas();
        buscarlistadeprogramados.setId_programa(model.getId_programa());
        buscarlistadeprogramados.setId_grupo(model.getId_grupo());
        buscarlistadeprogramados.setId_materia(model.getId_materia());
        buscarlistadeprogramados.setGestion(model.getGestion());
        buscarlistadeprogramados.setPeriodo(model.getPeriodo());
        buscarlistadeprogramados.setId_tipo_evaluacion(model.getId_tipo_evaluacion());
        List<Libretas> programados = mi.getPlanillaProgramadosMateria(buscarlistadeprogramados);

        // código para crear el archivo Excel
        Workbook libro = new XSSFWorkbook();
        Sheet hoja = libro.createSheet("PLANILLA ESTUDIANTES");

        // stylo celda
        CellStyle style = libro.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        Row titulo = hoja.createRow(0);
        Cell rutitulo = titulo.createCell(0);
        Cell nombreCompletotitulo = titulo.createCell(1);
        Cell auladesconcentradatitulo = titulo.createCell(2);
        rutitulo.setCellValue("R.U.");
        rutitulo.setCellStyle(style);
        nombreCompletotitulo.setCellValue("NOMBRE COMPLETO");
        nombreCompletotitulo.setCellStyle(style);
        auladesconcentradatitulo.setCellValue("AULA DESCONCENTRADA");
        auladesconcentradatitulo.setCellStyle(style);

        int i = 1;
        for (Libretas libreta : programados) {
            Row detalle = hoja.createRow(i);
            Cell ru = detalle.createCell(0);
            Cell nombreCompleto = detalle.createCell(1);
            Cell auladesconcentrada = detalle.createCell(2);
            nombreCompleto.setCellValue(libreta.getNombres());
            nombreCompleto.setCellStyle(style);
            ru.setCellValue(libreta.getId_estudiante());
            ru.setCellStyle(style);
            auladesconcentrada.setCellValue(libreta.getSede_desconcentrada());
            auladesconcentrada.setCellStyle(style);
            i++;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        libro.write(baos);
        // Establecer los encabezados para descargar el archivo
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=planillestudiantesproogramados.xlsx");
        // Escribir los datos del archivo en la respuesta HTTP
        response.getOutputStream().write(baos.toByteArray());
    }

}
