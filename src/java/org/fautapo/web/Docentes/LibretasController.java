package org.fautapo.web.Docentes;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.FormatosNum;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.Programas;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.ConfirmarModel;
import org.fautapo.model.DatosNotaModel;
import org.fautapo.model.MessageResult;
import org.fautapo.model.ParametrosEntrada;
import org.fautapo.util.Convert;
import org.fautapo.util.RemoteIpHostHelper;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class LibretasController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;

    private Clientes GetUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/definirEvaluacion/entrada.fautapo")
    public String Entrada(Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        ParametrosEntrada model = new ParametrosEntrada();
        modelo.addAttribute("simagen", cliente.getImagen());
        modelo.addAttribute("usuario", cliente.getNombres());
        modelo.addAttribute("gestion", Integer.toString(cliente.getGestion()));
        modelo.addAttribute("periodo", Integer.toString(cliente.getPeriodo()));
        modelo.addAttribute("model", model);
        return "definirEvaluacion/Entrada";
    }

    @RequestMapping(value = "/definirEvaluacion/ListarAsignaciones.fautapo", method = RequestMethod.POST)
    public String ListarAsignaciones(@ModelAttribute("model") @Validated ParametrosEntrada model, BindingResult result, Model modelo) throws Exception {

        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", cliente.getImagen());
            modelo.addAttribute("usuario", cliente.getNombres());
            modelo.addAttribute("gestion", Integer.toString(cliente.getGestion()));
            modelo.addAttribute("periodo", Integer.toString(cliente.getPeriodo()));
            modelo.addAttribute("model", model);
            return "definirEvaluacion/Entrada";
        }

        String sNombres = cliente.getNombres();
        int iId_docente = cliente.getId_usuario();
        int iId_rol = cliente.getId_rol();

        modelo.addAttribute("aux", "");
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("nombres", sNombres);
        modelo.addAttribute("avanzado", "");

        //Sacamos la asignacion del docente
        Asignaciones asignacion = new Asignaciones();
        asignacion.setId_docente(iId_docente);
        asignacion.setGestion(model.getGestion());
        asignacion.setPeriodo(model.getPeriodo());
        List datosAsignacion = this.mi.getDctListarAsignacionDocente(asignacion);
        Asignaciones aux = new Asignaciones();
        for (int i = 0; i < datosAsignacion.size(); i++) {
            aux = (Asignaciones) datosAsignacion.get(i);
            int iId_modelo_ahorro = aux.getId_modelo_ahorro();
            int iId_programa = aux.getId_programa();
            int iId_materia = aux.getId_materia();
            if (iId_modelo_ahorro > 0) {
                Asignaciones datos = new Asignaciones();
                datos.setId_modelo_ahorro(iId_modelo_ahorro);
                datos.setId_programa(iId_programa);
                datos.setGestion(model.getGestion());
                datos.setPeriodo(model.getPeriodo());
                datos.setId_materia(iId_materia);
                List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
                aux.setMateria_ahorro(materiaAhorro);
                datosAsignacion.set(i, aux);
            } else {
                datosAsignacion.set(i, aux);
            }
        }

        List datosAsignaciones = datosAsignacion;
        modelo.addAttribute("datosAsignacion", datosAsignaciones);
        modelo.addAttribute("id_rol", Integer.toString(iId_rol));
        return "definirEvaluacion/ListarAsignaciones";
    }

    @RequestMapping(value = "/DefinirEvaluacion.fautapo", method = RequestMethod.POST)
    public String DefinirEvaluacion(Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        String sNombres = cliente.getNombres();
        int iId_rol = cliente.getId_rol();
        int iId_docente = cliente.getId_usuario();

        int iId_asignacion = cliente.getInt(request, "id_asignacion");
        String sGestion = request.getParameter("gestion");
        String sPeriodo = request.getParameter("periodo");
        int iId_modelo_ahorro = cliente.getInt(request, "id_modelo_ahorro");
        String sId_materia = request.getParameter("id_materia");
        String sId_fase = request.getParameter("id_fase");
        String sId_programa = request.getParameter("id_programa");
        String sId_tipo_evaluacion = request.getParameter("id_tipo_evaluacion");
        String sId_grupo = request.getParameter("id_grupo");
        String sId_departamento = request.getParameter("id_departamento");

        modelo.addAttribute("gestion", sGestion);
        modelo.addAttribute("periodo", sPeriodo);
        modelo.addAttribute("nombres", sNombres);
        modelo.addAttribute("id_docente", Integer.toString(iId_docente));
        modelo.addAttribute("id_rol", Integer.toString(iId_rol));
        modelo.addAttribute("id_grupo", sId_grupo);
        modelo.addAttribute("id_fase", sId_fase);
        modelo.addAttribute("id_tipo_evaluacion", sId_tipo_evaluacion);
        modelo.addAttribute("id_departamento", sId_departamento);
        modelo.addAttribute("id_modelo_ahorro", Integer.toString(iId_modelo_ahorro));
        modelo.addAttribute("id_asignacion", Integer.toString(iId_asignacion));

        //Verificando
        if (iId_asignacion == 0) {
            modelo.addAttribute("mensaje", "No ingreso la Asignaci&oacute;n del Docente. Verifique los datos.");
            return "Error";
        }

        //Buscamos la asignacion del Docente
        Asignaciones datosAsignacion = new Asignaciones();
        datosAsignacion.setId_asignacion(iId_asignacion);
        datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
        if (datosAsignacion == null) {
            modelo.addAttribute("mensaje", "No se existen datos para la asignaci&oacute;n del docente seleccionado.");
            return "Error";
        }
        modelo.addAttribute("datosAsignacion", datosAsignacion);

        //Buscar el programa
        Programas buscarPrograma = new Programas();
        buscarPrograma.setId_programa(Integer.parseInt(sId_programa));
        Programas datosPrograma = this.mi.getPrgBuscarPrograma(buscarPrograma);
        modelo.addAttribute("id_programa", Integer.toString(datosPrograma.getId_programa()));
        modelo.addAttribute("programa", datosPrograma.getPrograma());
        //modelo.addAttribute(datosPrograma.);

        //Buscar Datos de la materia
        Materias datosMateria = new Materias();
        datosMateria.setId_materia(datosAsignacion.getId_materia());
        Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
        modelo.addAttribute("sigla", buscarMateria.getSigla());
        modelo.addAttribute("id_materia", Integer.toString(buscarMateria.getId_materia()));
        modelo.addAttribute("materia", buscarMateria.getMateria());
        //Verificamos si el departamento de la materia coincide con la asignacion
        if (datosAsignacion.getId_departamento() != buscarMateria.getId_departamento()) {
            modelo.addAttribute("mensaje", "Alerta!!. El departamento de la materia no coincide con la asignaci&oacute;n del docente. Consulte al administrador del sistema.");
            return "Error";
        }

        //Verificamos si es una materia con modelo ahorro
        //Buscamos la materia modelo_ahorro
        Asignaciones datos = new Asignaciones();
        if (iId_modelo_ahorro > 0) {
            datos.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
            datos.setId_materia(datosAsignacion.getId_materia());
            datos.setId_programa(datosPrograma.getId_programa());
            datos.setGestion(datosAsignacion.getGestion());
            datos.setPeriodo(datosAsignacion.getPeriodo());
            List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
            Asignaciones aux = new Asignaciones();
            for (int i = 0; i < materiaAhorro.size(); i++) {
                aux = (Asignaciones) materiaAhorro.get(i);
                modelo.addAttribute("materia_ahorro", aux.getModelo_ahorro());
            }
        }
        modelo.addAttribute("id_modelo_ahorro", Integer.toString(datosAsignacion.getId_modelo_ahorro()));

        //Sacamos la fase actual segun la asignacion del docente
        Libretas buscarFase = new Libretas();
        buscarFase.setId_fase(datosAsignacion.getId_fase());
        buscarFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        buscarFase.setId_departamento(datosAsignacion.getId_departamento());
        buscarFase.setGestion(datosAsignacion.getGestion());
        buscarFase.setPeriodo(datosAsignacion.getPeriodo());
        Libretas datosFase = this.mi.getLbrBuscarFase(buscarFase);
        if (datosFase.getFase() == null) {
            modelo.addAttribute("mensaje", "No existen mas fases. Por lo que no puede realizar m&aacute;s definiciones de Evaluaci&oacute;n.");
            return "Aviso";
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
        List lTiposNotas = this.mi.getLbrListarTiposNotas(datosTiposNotas);
        List<String> listaid = new ArrayList<String>();
        for (Object obj : lTiposNotas) {
            listaid.add(String.valueOf(((Libretas) obj).getId_tipo_nota()));
        }
        JSONArray jsonArray = new JSONArray(listaid);
        modelo.addAttribute("JsonTiposNotas", jsonArray);
        modelo.addAttribute("lTiposNotas", lTiposNotas);
        return "definirEvaluacion/DefinirEvaluacion";
    }

    @RequestMapping(value = "/ListarEstudiantesProgramados.fautapo", method = RequestMethod.POST)
    public String ListarEstudiantesProgramados(Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        String sIdPrograma = request.getParameter("id_programa");
        String sIdTipoNotaS = request.getParameter("id_tipo_nota_s");
        String sNroNotaS = request.getParameter("nro_nota_s");
        int sIdTipoGrado = cliente.getInt(request, "id_tipo_grado");
        List<Libretas> listaNotasPromedios = new ArrayList<>();
        double notaMinimaSegundaInstacia = 0;
        //Nuevo
        int iIdAsignacion = cliente.getInt(request, "id_asignacion");
        //Buscamos la asignacion docente
        Asignaciones buscarAsignacion = new Asignaciones();
        buscarAsignacion.setId_asignacion(iIdAsignacion);
        Asignaciones datosAsignacion = this.mi.getDctBuscarAsignacionDocente(buscarAsignacion);
        if (datosAsignacion == null) {
            modelo.addAttribute("mensaje", "No se encontraron la asignacicion del docente para la materia");
            return "Error";
        }

        //Sacamos el programa 
        Programas datosPrograma = new Programas();
        datosPrograma.setId_programa(Integer.parseInt(sIdPrograma));
        Programas buscarPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);

        //Sacamos datos de la materia
        Materias datosMateria = new Materias();
        datosMateria.setId_materia(datosAsignacion.getId_materia());
        Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);

        //Verificamos si el departamento de la materia coincide con la asignacion
        if (datosAsignacion.getId_departamento() != buscarMateria.getId_departamento()) {
            modelo.addAttribute("mensaje", "Alerta!!. El departamento de la materia no coincide con la asignaci&oacute;n del docente. Consulte al administrador del sistema.");
            return "Error";
        }

        //Sacamos la fase actual segun la asignacion del docente
        Libretas buscarFase = new Libretas();
        buscarFase.setId_fase(datosAsignacion.getId_fase());
        buscarFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        buscarFase.setId_departamento(datosAsignacion.getId_departamento());
        buscarFase.setGestion(datosAsignacion.getGestion());
        buscarFase.setPeriodo(datosAsignacion.getPeriodo());
        Libretas datosFase = this.mi.getLbrBuscarFase(buscarFase);
        if (datosFase.getFase() == null) {
            modelo.addAttribute("mensaje", "No existen mas fases.");
            return "Aviso";
        }

        //Sacamos la definicion de items de la materia
        Libretas datosEvaluacion = new Libretas();
        datosEvaluacion.setId_materia(datosAsignacion.getId_materia());
        datosEvaluacion.setId_grupo(datosAsignacion.getId_grupo());
        datosEvaluacion.setGestion(datosAsignacion.getGestion());
        datosEvaluacion.setPeriodo(datosAsignacion.getPeriodo());
        datosEvaluacion.setId_departamento(buscarMateria.getId_departamento());
        datosEvaluacion.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        datosEvaluacion.setId_fase(datosAsignacion.getId_fase());
        datosEvaluacion.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
        datosEvaluacion.setId_programa(buscarPrograma.getId_programa());
        List<Libretas> lEvaluacion = this.mi.getGrpListarEvaluacionDefinida(datosEvaluacion);
        modelo.addAttribute("listaItems", lEvaluacion);

        //Sacamos las notas de los estudiantes
        if (sIdTipoNotaS != null) {
            String elementos[];
            elementos = new String[2];
            elementos = sIdTipoNotaS.split(":");
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
            List<Libretas> nominaNotasBaseDatos = mi.getDetalleNotaLibretaMateria(datosEvaluacion);

            //Sacando la lista de estudiantes programados a la materia, evaluacion regular
            if ((sIdTipoGrado == 1) && (datosAsignacion.getId_tipo_evaluacion() != 2)) {
                Libretas datosEstProg = new Libretas();
                datosEstProg.setId_materia(datosAsignacion.getId_materia());
                datosEstProg.setId_grupo(datosAsignacion.getId_grupo());
                datosEstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                datosEstProg.setGestion(datosAsignacion.getGestion());
                datosEstProg.setPeriodo(datosAsignacion.getPeriodo());
                datosEstProg.setId_fase(datosAsignacion.getId_fase());
                datosEstProg.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                datosEstProg.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
                List<Libretas> lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);
                if (datosAsignacion.getId_fase() == 1) {
                    listaNotasPromedios = mi.getEstListarNotasEstudianteLibretaSegunda(datosEstProg);
                    Programas programa = new Programas();
                    programa.setId_programa(Integer.parseInt(sIdPrograma));
                    programa.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                    programa.setPeriodo(datosAsignacion.getPeriodo());
                    programa.setGestion(datosAsignacion.getGestion());
                    List<Programas> listaNotasminimas = this.mi.getEstListarNotaMinimaporPrograma(programa);
                    notaMinimaSegundaInstacia = listaNotasminimas.isEmpty() ? -1 : listaNotasminimas.stream().findFirst().get().getNota_minima();
                    if (notaMinimaSegundaInstacia == -1) {
                        modelo.addAttribute("mensaje", "No definio la nota minima de segunda instancia.");
                        return "Aviso";
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
        modelo.addAttribute("id_modelo_ahorro", datosAsignacion.getId_modelo_ahorro());
        modelo.addAttribute("id_programa", sIdPrograma);
        modelo.addAttribute("programa", buscarPrograma.getPrograma());
        modelo.addAttribute("sigla", buscarMateria.getSigla());
        modelo.addAttribute("id_materia", buscarMateria.getId_materia());
        modelo.addAttribute("materia", buscarMateria.getMateria());
        modelo.addAttribute("fase", datosFase.getFase());
        modelo.addAttribute("datosAsignacion", datosAsignacion);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("id_docente", Integer.toString(cliente.getId_usuario()));
        modelo.addAttribute("id_rol", Integer.toString(cliente.getId_rol()));
        modelo.addAttribute("id_tipo_grado", sIdTipoGrado);
        modelo.addAttribute("nro_nota", sNroNotaS);
        modelo.addAttribute("id_tipo_nota_s", sIdTipoNotaS);
        modelo.addAttribute("nro_nota_s", sNroNotaS);
        modelo.addAttribute("id_tipo_evaluacion", Integer.toString(datosAsignacion.getId_tipo_evaluacion()));

        return "administrarLibretas/ListarEstudiantesProgramados";
    }

    private boolean permiteEvaluar(int idEstudiante, double notaMinima, double minimaSegundaInstancia, List<Libretas> listaPromedios) {
        if (!listaPromedios.isEmpty()) {
            List<Libretas> notasPromedio = listaPromedios.stream().filter(p -> p.getId_estudiante() == idEstudiante).collect(Collectors.toList());
            if (!notasPromedio.isEmpty()) {
                double nota = notasPromedio.stream().mapToDouble(p -> p.getNota()).sum();
                if (nota < notaMinima) {
                    nota = notasPromedio.stream().filter(p -> p.getId_tipo_nota() != 5).mapToDouble(p -> p.getNota()).sum();
                    return nota >= minimaSegundaInstancia;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/RegistrarNotasEstudiantes.fautapo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageResult> RegistrarNotasEstudiantes(@ModelAttribute("model") DatosNotaModel model) {
        Clientes cliente = this.GetUsuario();
        MessageResult Respuesta = new MessageResult();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        } else {
            try {
                Asignaciones buscarAsignacion = new Asignaciones();
                buscarAsignacion.setId_asignacion(model.getId_asignacion());
                Asignaciones datosAsignacion = this.mi.getDctBuscarAsignacionDocente(buscarAsignacion);
                if (datosAsignacion == null) {
                    Respuesta.setMessage("No se encontr&oacute; la asignaci&oacute;n docente para la materia");
                    Respuesta.setStatus("Error");
                    return new ResponseEntity<>(Respuesta, HttpStatus.OK);
                }
                String sId_tipo_evaluacion = Integer.toString(datosAsignacion.getId_tipo_evaluacion());

                Programas datosPrograma = new Programas();
                datosPrograma.setId_programa(model.getId_programa());
                Programas buscarPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);

                //Sacamos datos de la materia
                Materias datosMateria = new Materias();
                datosMateria.setId_materia(datosAsignacion.getId_materia());
                Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
                //Verificamos si el departamento de la materia coincide con la asignacion
                if (datosAsignacion.getId_departamento() != buscarMateria.getId_departamento()) {
                    Respuesta.setMessage("No se encontr&oacute; la asignaci&oacute;n docente para la materia");
                    Respuesta.setStatus("Error");
                    return new ResponseEntity<>(Respuesta, HttpStatus.OK);
                }

                //Sacamos la fase actual segun la asignacion del docente
                Libretas buscarFase = new Libretas();
                buscarFase.setId_fase(datosAsignacion.getId_fase());
                buscarFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                buscarFase.setId_departamento(datosAsignacion.getId_departamento());
                buscarFase.setGestion(datosAsignacion.getGestion());
                buscarFase.setPeriodo(datosAsignacion.getPeriodo());
                Libretas datosFase = this.mi.getLbrBuscarFase(buscarFase);
                if (datosFase.getFase() == null) {
                    Respuesta.setMessage("No se puede encontrar la fase.");
                    Respuesta.setStatus("Error");
                    return new ResponseEntity<>(Respuesta, HttpStatus.OK);
                }
                //Verificando si la fase es mayor a 100
                if (datosFase.getId_fase() >= 100) {
                    Respuesta.setMessage("No esta permitido administrar notas para la fase ::&nbsp;" + datosFase.getFase());
                    Respuesta.setStatus("Error");
                    return new ResponseEntity<>(Respuesta, HttpStatus.OK);
                }
                //Verificamos si es una materia con modelo ahorro
                //Buscamos la materia modelo_ahorro
                Asignaciones datos = new Asignaciones();
                if (datosAsignacion.getId_modelo_ahorro() > 0) {
                    datos.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                    datos.setId_materia(datosAsignacion.getId_materia());
                    datos.setId_programa(datosPrograma.getId_programa());
                    datos.setGestion(datosAsignacion.getGestion());
                    datos.setPeriodo(datosAsignacion.getPeriodo());
                    List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
                    Asignaciones aux = new Asignaciones();
                    for (int i = 0; i < materiaAhorro.size(); i++) {
                        aux = (Asignaciones) materiaAhorro.get(i);
                    }
                }

                //Sacamos el tipo de nota que se esta calificando
                String sTipos[];
                sTipos = new String[2];
                sTipos = model.getId_tipo_nota_s().split(":");
                String sId_lbr_tipo_nota = sTipos[0];
                String sCantidad = sTipos[1];

                Libretas datosTipoNota = new Libretas();
                datosTipoNota.setId_lbr_tipo_nota(Integer.parseInt(sId_lbr_tipo_nota));
                datosTipoNota = this.mi.getLbrBuscarTipoNota(datosTipoNota);
                int iId_tipo_nota = datosTipoNota.getId_tipo_nota();
                int iCantidad = Integer.parseInt(sCantidad);

                //GUARDANDO LAS NOTAS DE LOS ESTUDIANTES, EVALUACION REGULAR
                //Recuperamos las notas de los estudiantes
                if (("1".equals(model.getId_tipo_grado())) && (!"2".equals(sId_tipo_evaluacion))) {
                    Enumeration e = request.getParameterNames();
                    while (e.hasMoreElements()) {
                        String sName = (String) e.nextElement();      //Recuperando el nombre del objeto (Ej. de la caja de texto)
                        String sNota = request.getParameter(sName);   //Recuperando el valor del objeto (Ej. de la caja de texto)
                        String sDato = sName.substring(0, 4);
                        if ("nota".equals(sDato)) {
                            String sElementos[];
                            sElementos = new String[2];
                            sElementos = sName.split(":");
                            String sNombre = sElementos[0];
                            String sId_estudiante = sElementos[1];
                            int iId_estudiante = Integer.parseInt(sId_estudiante);
                            int iNro_nota = Integer.parseInt(model.getNro_nota_s());

                            FormatosNum formatoNum = new FormatosNum();
                            String iNota;
                            if (!sNota.equals("")) {
                                iNota = formatoNum.parseDecimal(Double.parseDouble(sNota), 7);
                            } else {
                                Respuesta.setMessage("Debe presionar la tecla enter para registrar la nota");
                                Respuesta.setStatus("Error");
                                return new ResponseEntity<>(Respuesta, HttpStatus.OK);
                            }
                            //Registramos las notas de los estudiantes
                            Libretas insertar = new Libretas();
                            insertar.setId_estudiante(iId_estudiante);
                            insertar.setId_grupo(datosAsignacion.getId_grupo());
                            insertar.setId_materia(datosAsignacion.getId_materia());
                            insertar.setId_departamento(buscarMateria.getId_departamento());
                            insertar.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                            insertar.setId_fase(datosAsignacion.getId_fase());
                            insertar.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
                            insertar.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                            insertar.setGestion(datosAsignacion.getGestion());
                            insertar.setPeriodo(datosAsignacion.getPeriodo());
                            insertar.setNro_nota(iNro_nota);
                            insertar.setNota(Double.parseDouble(iNota));
                            insertar.setId_rol(cliente.getId_rol());     //CRCB
                            insertar.setUlt_usuario(cliente.getId_usuario());
                            int iValor = this.mi.setEstInsertarNotaEstudianteFase(insertar); //ESTA HACE LOS CUATRO PASOS
                            if (iValor != 1) {
                                Respuesta.setMessage("Nota no registrada por problemas de conexion. ");
                                Respuesta.setStatus("Error");
                                return new ResponseEntity<>(Respuesta, HttpStatus.OK);
                            } else {
                                String ip = RemoteIpHostHelper.getRemoteIpFrom(request);
                                String UserAgent = RemoteIpHostHelper.getUserAgent(request);
                                String Ubicacion = request.getParameter("ubicacion");
                                Libretas lib = this.mi.getNotasEstudiante(insertar);
                                lib.setIp(ip);
                                lib.setUbicacion(Ubicacion);
                                lib.setDetalle_dispositivo(UserAgent);
                                this.mi.RegistrarBitacoraCambiosDocente(lib);
                            }
                        }
                    }
                    //SACAMOS LAS NOTAS DE LOS ESTUDIANTES PARA LA CONFIRMACION
                    if (model.getId_tipo_nota_s() != null) {
                        //Sacando la lista de estudiantes programados a la materia
                        Libretas datosEstProg = new Libretas();
                        datosEstProg.setId_materia(datosAsignacion.getId_materia());
                        datosEstProg.setId_grupo(datosAsignacion.getId_grupo());
                        datosEstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                        datosEstProg.setGestion(datosAsignacion.getGestion());
                        datosEstProg.setPeriodo(datosAsignacion.getPeriodo());
                        datosEstProg.setId_fase(datosAsignacion.getId_fase());
                        datosEstProg.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                        List lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);

                        Libretas libreta = new Libretas();
                        for (int i = 0; i < lEstudiantes.size(); i++) {
                            libreta = (Libretas) lEstudiantes.get(i);
                            int iId_estudiante = libreta.getId_estudiante();
                            //Obtenemos las notas de cada uno de los estudiantes
                            Libretas datosNotas = new Libretas();
                            datosNotas.setId_estudiante(iId_estudiante);
                            datosNotas.setId_grupo(datosAsignacion.getId_grupo());
                            datosNotas.setId_materia(datosAsignacion.getId_materia());
                            datosNotas.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
                            datosNotas.setId_fase(datosAsignacion.getId_fase());
                            datosNotas.setGestion(datosAsignacion.getGestion());
                            datosNotas.setPeriodo(datosAsignacion.getPeriodo());
                            datosNotas.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                            datosNotas.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                            datosNotas.setId_programa(model.getId_programa());
                            datosNotas.setId_docente(cliente.getId_usuario());
                            int _iNota_final = this.mi.getEstSumarNotasEstudianteEvalRegular(datosNotas);

                            //Sacamos la nota de aprobacion de la fase 
                            Libretas datosFase2 = new Libretas();
                            datosFase2.setId_fase(datosAsignacion.getId_fase());
                            datosFase2.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                            datosFase2.setId_departamento(datosAsignacion.getId_departamento());
                            datosFase2.setGestion(datosAsignacion.getGestion());
                            datosFase2.setPeriodo(datosAsignacion.getPeriodo());
                            Libretas buscarFase2 = this.mi.getLbrBuscarFase(datosFase2);

                            if (_iNota_final == 0) {
                                List lNotasEst = this.mi.getEstListarNotasEstudiante(datosNotas);
                                libreta.setNotas(lNotasEst);
                                lEstudiantes.set(i, libreta);
                            }
                        }
                        PagedListHolder lNotas = new PagedListHolder(lEstudiantes);
                        lNotas.setPageSize(lNotas.getNrOfElements());
                    }
                }

                //GUARDANDO LAS NOTAS DE LOS ESTUDIANTES, EVALUACION CONTINUA
                //Recuperamos las notas de los estudiantes
                if (("1".equals(model.getId_tipo_grado())) && ("2".equals(sId_tipo_evaluacion))) {
                    Enumeration e = request.getParameterNames();
                    while (e.hasMoreElements()) {
                        String sName = (String) e.nextElement();      //Recuperando el nombre del objeto (Ej. de la caja de texto)
                        String sNota = request.getParameter(sName);   //Recuperando el valor del objeto (Ej. de la caja de texto)
                        String sDato = sName.substring(0, 4);
                        if ("nota".equals(sDato)) {
                            String sElementos[];
                            sElementos = new String[2];
                            sElementos = sName.split(":");
                            String sNombre = sElementos[0];
                            String sId_estudiante = sElementos[1];
                            int iId_estudiante = Integer.parseInt(sId_estudiante);
                            int iNro_nota = Integer.parseInt(model.getNro_nota_s());

                            FormatosNum formatoNum = new FormatosNum();
                            String iNota;
                            if (!sNota.equals("")) {
                                iNota = formatoNum.parseDecimal(Double.parseDouble(sNota), 7);
                            } else {
                                Respuesta.setMessage("Presionar enter para registrar la nota");
                                Respuesta.setStatus("Error");
                                return new ResponseEntity<>(Respuesta, HttpStatus.OK);
                            }
                            //int iNota = Integer.parseInt(sNota);

                            //PRIMERO: Verificamos si ya existe una nota de cada estudiante.
                            //SEGUNDO: Si existe la nota => modificar
                            //TERCERO: Si no existe la nota => insertar
                            //CUARTO : Insertar la nota calculada de la fase actual
                            Libretas insertar = new Libretas();
                            insertar.setId_estudiante(iId_estudiante);
                            insertar.setId_grupo(datosAsignacion.getId_grupo());
                            insertar.setId_materia(datosAsignacion.getId_materia());
                            insertar.setId_departamento(buscarMateria.getId_departamento());
                            insertar.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                            insertar.setId_fase(datosAsignacion.getId_fase());
                            insertar.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
                            insertar.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                            insertar.setGestion(datosAsignacion.getGestion());
                            insertar.setPeriodo(datosAsignacion.getPeriodo());
                            insertar.setNro_nota(iNro_nota);
                            insertar.setNota(Double.parseDouble(iNota));
                            insertar.setId_rol(cliente.getId_rol());     //CRCB
                            insertar.setUlt_usuario(cliente.getId_usuario());
                            int iValor = this.mi.setEstInsertarNotaEstudianteFase(insertar); //ESTA HACE LOS CUATRO PASOS
                            if (iValor != 1) {
                                Respuesta.setMessage("Nota no registrada por problemas de conexion. ");
                                Respuesta.setStatus("Error");
                                return new ResponseEntity<>(Respuesta, HttpStatus.OK);
                            } else {
                                String ip = RemoteIpHostHelper.getRemoteIpFrom(request);
                                String UserAgent = RemoteIpHostHelper.getUserAgent(request);
                                String Ubicacion = request.getParameter("ubicacion");
                                Libretas lib = this.mi.getNotasEstudiante(insertar);
                                lib.setIp(ip);
                                lib.setUbicacion(Ubicacion);
                                lib.setDetalle_dispositivo(UserAgent);
                                this.mi.RegistrarBitacoraCambiosDocente(lib);
                            }
                        }
                    }
                    //SACAMOS LAS NOTAS DE LOS ESTUDIANTES PARA LA CONFIRMACION
                    if (model.getId_tipo_nota_s() != null) {
                        //Sacando la lista de estudiantes programados a la materia
                        Libretas datosEstProg = new Libretas();
                        datosEstProg.setId_materia(datosAsignacion.getId_materia());
                        datosEstProg.setId_grupo(datosAsignacion.getId_grupo());
                        datosEstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                        datosEstProg.setGestion(datosAsignacion.getGestion());
                        datosEstProg.setPeriodo(datosAsignacion.getPeriodo());
                        datosEstProg.setId_fase(datosAsignacion.getId_fase());
                        datosEstProg.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                        List lEstudiantes = this.mi.getEstBuscarEstudiantesProgramados(datosEstProg);

                        Libretas libreta = new Libretas();
                        for (int i = 0; i < lEstudiantes.size(); i++) {
                            libreta = (Libretas) lEstudiantes.get(i);
                            int iId_estudiante = libreta.getId_estudiante();
                            //Obtenemos las notas de cada uno de los estudiantes
                            Libretas datosNotas = new Libretas();
                            datosNotas.setId_estudiante(iId_estudiante);
                            datosNotas.setId_grupo(datosAsignacion.getId_grupo());
                            datosNotas.setId_materia(datosAsignacion.getId_materia());
                            datosNotas.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
                            datosNotas.setId_fase(datosAsignacion.getId_fase());
                            datosNotas.setGestion(datosAsignacion.getGestion());
                            datosNotas.setPeriodo(datosAsignacion.getPeriodo());
                            datosNotas.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                            datosNotas.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                            int _iNota_final = this.mi.getEstSumarNotasEstudianteEvalContinua(datosNotas);

                            //Sacamos la fase minima de la materia
                            Libretas datosFase1 = new Libretas();
                            datosFase1.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                            datosFase1.setId_programa(model.getId_programa());
                            datosFase1.setGestion(datosAsignacion.getGestion());
                            datosFase1.setPeriodo(datosAsignacion.getPeriodo());
                            int iMin_fase = this.mi.getLbrBuscarFaseMinima(datosFase1);

                            //Sacamos la nota de aprobacion de la fase inicial
                            Libretas datosFase2 = new Libretas();
                            datosFase2.setId_fase(iMin_fase);
                            datosFase2.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                            datosFase2.setId_departamento(datosAsignacion.getId_departamento());
                            datosFase2.setGestion(datosAsignacion.getGestion());
                            datosFase2.setPeriodo(datosAsignacion.getPeriodo());
                            Libretas buscarFase2 = this.mi.getLbrBuscarFase(datosFase2);

                            if (_iNota_final == 0) {
                                List lNotasEst = this.mi.getEstListarNotasEstudiante(datosNotas);
                                for (int j = lNotasEst.size(); j < iCantidad; j++) {
                                    Libretas aux = new Libretas();
                                    //if (_iNota_final == 2){ 
                                    aux.setNota(0);
                                    /*}
	      if (_iNota_final != 2){ 
                aux.setNota(_iNota_final);
	      }	*/
                                    aux.setNro_nota(j + 1);
                                    lNotasEst.add(aux);
                                }
                                libreta.setNotas(lNotasEst);
                                lEstudiantes.set(i, libreta);
                            }
                        }
                        PagedListHolder lNotas = new PagedListHolder(lEstudiantes);
                        lNotas.setPageSize(lNotas.getNrOfElements());
                    }
                }

                //GUARDANDO LAS NOTAS DE LOS POSTULANTES
                //Recuperamos las notas de los postulantes
                if ("2".equals(model.getId_tipo_grado())) {
                    Enumeration e = request.getParameterNames();
                    while (e.hasMoreElements()) {
                        String sName = (String) e.nextElement();      //Recuperando el nombre del objeto (Ej. de la caja de texto)
                        String sNota = request.getParameter(sName);   //Recuperando el valor del objeto (Ej. de la caja de texto)
                        String sDato = sName.substring(0, 4);
                        if ("nota".equals(sDato)) {
                            String sElementos[];
                            sElementos = new String[2];
                            sElementos = sName.split(":");
                            String sNombre = sElementos[0];
                            String sId_postulante = sElementos[1];
                            int iId_postulante = Integer.parseInt(sId_postulante);
                            int iNro_nota = Integer.parseInt(model.getNro_nota_s());

                            FormatosNum formatoNum = new FormatosNum();
                            String iNota;
                            if (!sNota.equals("")) {
                                iNota = formatoNum.parseDecimal(Double.parseDouble(sNota), 7);
                            } else {
                                Respuesta.setMessage("Presionar enter para registrar la nota");
                                Respuesta.setStatus("Error");
                                return new ResponseEntity<>(Respuesta, HttpStatus.OK);
                            }
                            //int iNota = Integer.parseInt(sNota);

                            //PRIMERO: Verificamos si ya existe una nota de cada postulante.
                            //SEGUNDO: Si existe la nota => modificar
                            //TERCERO: Si no existe la nota => insertar
                            //CUARTO : Insertar la nota calculada de la fase actual
                            Libretas insertar = new Libretas();
                            insertar.setId_postulante(iId_postulante);
                            insertar.setId_grupo(datosAsignacion.getId_grupo());
                            insertar.setId_materia(datosAsignacion.getId_materia());
                            insertar.setId_departamento(buscarMateria.getId_departamento());
                            insertar.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                            insertar.setId_fase(datosAsignacion.getId_fase());
                            insertar.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
                            insertar.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                            insertar.setGestion(datosAsignacion.getGestion());
                            insertar.setPeriodo(datosAsignacion.getPeriodo());
                            insertar.setNro_nota(iNro_nota);
                            insertar.setNota(Double.parseDouble(iNota));
                            insertar.setId_rol(cliente.getId_rol());   //CRCB
                            insertar.setUlt_usuario(cliente.getId_usuario());
                            int iValor = this.mi.setPstInsertarNotaPostulanteFase(insertar); //ESTA HACE LOS CUATRO PASOS
                        }
                    }
                    //SACAMOS LAS NOTAS DE LOS POSTULANTES PARA LA CONFIRMACION
                    if (model.getId_tipo_nota_s() != null) {
                        //Sacando la lista de postulantes programados a la materia
                        Libretas datosPstProg = new Libretas();
                        datosPstProg.setId_materia(datosAsignacion.getId_materia());
                        datosPstProg.setId_grupo(datosAsignacion.getId_grupo());
                        datosPstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                        datosPstProg.setGestion(datosAsignacion.getGestion());
                        datosPstProg.setPeriodo(datosAsignacion.getPeriodo());
                        List lPostulantes = this.mi.getPstBuscarPostulantesProgramados(datosPstProg);

                        Libretas libreta = new Libretas();
                        for (int i = 0; i < lPostulantes.size(); i++) {
                            libreta = (Libretas) lPostulantes.get(i);
                            int iId_postulante = libreta.getId_postulante();
                            //Obtenemos las notas de cada uno de los postulantes
                            Libretas datosNotas = new Libretas();
                            datosNotas.setId_postulante(iId_postulante);
                            datosNotas.setId_grupo(datosAsignacion.getId_grupo());
                            datosNotas.setId_materia(datosAsignacion.getId_materia());
                            datosNotas.setId_tipo_nota(datosTipoNota.getId_tipo_nota());
                            datosNotas.setId_fase(datosAsignacion.getId_fase());
                            datosNotas.setGestion(datosAsignacion.getGestion());
                            datosNotas.setPeriodo(datosAsignacion.getPeriodo());

                            List lNotasPst = this.mi.getPstListarNotasPostulante(datosNotas);
                            for (int j = lNotasPst.size(); j < iCantidad; j++) {
                                Libretas aux = new Libretas();
                                aux.setNota(0);
                                aux.setNro_nota(j + 1);
                                lNotasPst.add(aux);
                            }
                            libreta.setNotas(lNotasPst);
                            lPostulantes.set(i, libreta);
                        }
                        PagedListHolder lNotas = new PagedListHolder(lPostulantes);
                        lNotas.setPageSize(lNotas.getNrOfElements());
                    }
                }
                Respuesta.setMessage("");
                Respuesta.setStatus("OK");
            } catch (Exception exception) {

                Respuesta.setMessage("Ocurrio un error al grabar los datos verifique su conexion.. " + exception.getMessage());
                Respuesta.setStatus("Error");
            }
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/State.fautapo", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<String> Status() {
        String mensaje = "";
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            mensaje = "expirado";
            return new ResponseEntity<>("{\"status\" : \"" + mensaje + "\"}", HttpStatus.OK);
        }
        mensaje = "on_line";
        return new ResponseEntity<>("{\"status\" : \"" + mensaje + "\"}", HttpStatus.OK);
    }

    @RequestMapping(value = "/avanzarFase.fautapo", method = RequestMethod.POST)
    public String AvanzarFase(Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        //String sUlt_usuario = cliente.getId_usuario() + "|" + cliente.getId_rol();
        String sId_tipo_nota_s = "";  //AUXILIAR 
        int iId_asignacion = cliente.getInt(request, "id_asignacion");
        String sId_tipo_grado = request.getParameter("id_tipo_grado");
        String sId_programa = request.getParameter("id_programa");
        //obtener imagen del perfil
        modelo.addAttribute("simagen", cliente.getImagen());

        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("id_tipo_grado", sId_tipo_grado);
        modelo.addAttribute("id_programa", sId_programa);

        //Buscamos la asignacion docente
        Asignaciones buscarAsignacion = new Asignaciones();
        buscarAsignacion.setId_asignacion(iId_asignacion);
        Asignaciones datosAsignacion = this.mi.getDctBuscarAsignacionDocente(buscarAsignacion);
        modelo.addAttribute("datosAsignacion", datosAsignacion);

        if (datosAsignacion == null) {
            modelo.addAttribute("mensaje", "No se encontr&oacute; la asignaci&oacute;n docente para la materia");
            return "Error";
        }

        //Sacamos el programa 
        Programas datosPrograma = new Programas();
        datosPrograma.setId_programa(Integer.parseInt(sId_programa));
        Programas buscarPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
        modelo.addAttribute("buscarPrograma", buscarPrograma);
        modelo.addAttribute("id_programa", sId_programa);
        modelo.addAttribute("programa", buscarPrograma.getPrograma());

        //Sacamos datos de la materia
        Materias datosMateria = new Materias();
        datosMateria.setId_materia(datosAsignacion.getId_materia());
        Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
        modelo.addAttribute("buscarMateria", buscarMateria);
        //modelo.addAttribute("id_tipo_nota_s", sId_tipo_nota_s);

        //Sacamos la fase actual de acuerdo a la asignacion del docente
        Libretas datosFase = new Libretas();
        datosFase.setId_fase(datosAsignacion.getId_fase());
        datosFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        datosFase.setId_departamento(datosAsignacion.getId_departamento());
        datosFase.setGestion(datosAsignacion.getGestion());
        datosFase.setPeriodo(datosAsignacion.getPeriodo());
        Libretas buscarFaseActual = this.mi.getLbrBuscarFase(datosFase);
        modelo.addAttribute("buscarFaseActual", buscarFaseActual);
        modelo.addAttribute("fase_actual", buscarFaseActual.getFase());

        //Sacando la fase maxima
        Libretas datosFaseMax = new Libretas();
        datosFaseMax.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        datosFaseMax.setId_programa(Integer.parseInt(sId_programa));
        datosFaseMax.setGestion(datosAsignacion.getGestion());
        datosFaseMax.setPeriodo(datosAsignacion.getPeriodo());
        int iId_fase_maxima = this.mi.getLbrBuscarFaseMaxima(datosFaseMax);

        if (Integer.parseInt(sId_tipo_grado) == 1) {  //Universitario
            //Sacando la fase siguiente de acuerdo a Asignacion docente
            Libretas datosFaseA = new Libretas();
            datosFaseA.setId_fase(datosAsignacion.getId_fase() + 1);
            datosFaseA.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
            datosFaseA.setId_departamento(datosAsignacion.getId_departamento());
            datosFaseA.setGestion(datosAsignacion.getGestion());
            datosFaseA.setPeriodo(datosAsignacion.getPeriodo());
            Libretas buscarFaseSiguiente = this.mi.getLbrBuscarFase(datosFaseA);
            if (buscarFaseSiguiente == null) {
                modelo.addAttribute("fase_siguiente", "Pre-Cierre de Libreta (No existen mas fases definidas)");
            } else {
                modelo.addAttribute("fase_siguiente", buscarFaseSiguiente.getFase());
            }
        }
        if (Integer.parseInt(sId_tipo_grado) != 1) { //Vestibular / Postgrado/etc..
            //Sacando la fase siguiente
            Libretas datosFaseA = new Libretas();
            datosFaseA.setId_fase(iId_fase_maxima);
            datosFaseA.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
            datosFaseA.setId_departamento(datosAsignacion.getId_departamento());
            datosFaseA.setGestion(datosAsignacion.getGestion());
            datosFaseA.setPeriodo(datosAsignacion.getPeriodo());
            Libretas buscarFaseSiguiente = this.mi.getLbrBuscarFase(datosFaseA);
            if (buscarFaseSiguiente == null) {
                modelo.addAttribute("fase_siguiente", "Pre-Cierre de Libreta (No existen mas fases definidas)");
            } else {
                modelo.addAttribute("fase_siguiente", buscarFaseSiguiente.getFase());
            }
        }

        return "administrarLibretas/AvanzarFase";
    }

    @RequestMapping(value = "/registrarAvanzarFase.fautapo", method = RequestMethod.POST)
    public String RegistrarAvanzarFase(Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        //String sUlt_usuario = cliente.getId_usuario() + "|" + cliente.getId_rol();

        int iId_asignacion = cliente.getInt(request, "id_asignacion");
        String sId_tipo_grado = request.getParameter("id_tipo_grado");
        String sAccion = cliente.getString(request, "avanzar");
        String sId_programa = request.getParameter("id_programa");
        String sMensaje = "";

        //obtener imagen del perfil
        int iId_docente = cliente.getId_usuario();
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("id_tipo_grado", sId_tipo_grado);
        modelo.addAttribute("id_programa", sId_programa);

        //Buscamos la asignacion docente
        Asignaciones datosAsignacion = new Asignaciones();
        datosAsignacion.setId_asignacion(iId_asignacion);
        datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
        modelo.addAttribute("datosAsignacion", datosAsignacion);
        if (datosAsignacion == null) {
            modelo.addAttribute("mensaje", "No se encontr&oacute; la asignaci&oacute;n docente para la materia");
            return "Error";
        }

        //Sacamos el programa 
        Programas datosPrograma = new Programas();
        datosPrograma.setId_programa(Integer.parseInt(sId_programa));
        Programas buscarPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);
        modelo.addAttribute("buscarPrograma", buscarPrograma);
        modelo.addAttribute("id_programa", sId_programa);
        modelo.addAttribute("programa", buscarPrograma.getPrograma());

        //Sacamos la fase actual de acuerdo a la asignacion del docente
        Libretas datosFase = new Libretas();
        datosFase.setId_fase(datosAsignacion.getId_fase());
        datosFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        datosFase.setId_departamento(datosAsignacion.getId_departamento());
        datosFase.setGestion(datosAsignacion.getGestion());
        datosFase.setPeriodo(datosAsignacion.getPeriodo());
        Libretas buscarFaseActual = this.mi.getLbrBuscarFase(datosFase);
        modelo.addAttribute("buscarFaseActual", buscarFaseActual);
        modelo.addAttribute("fase_actual", buscarFaseActual.getFase());

        //Sacando la fase maxima
        Libretas datosFaseMax = new Libretas();
        datosFaseMax.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        datosFaseMax.setId_programa(Integer.parseInt(sId_programa));
        datosFaseMax.setGestion(datosAsignacion.getGestion());
        datosFaseMax.setPeriodo(datosAsignacion.getPeriodo());
        int iId_fase_maxima = this.mi.getLbrBuscarFaseMaxima(datosFaseMax);

        if ("Avanzar >>".equals(sAccion)) {
            Libretas avanzar = new Libretas();
            avanzar.setId_docente(datosAsignacion.getId_docente());
            avanzar.setId_departamento(datosAsignacion.getId_departamento());
            avanzar.setId_materia(datosAsignacion.getId_materia());
            avanzar.setId_grupo(datosAsignacion.getId_grupo());
            avanzar.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
            avanzar.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
            avanzar.setGestion(datosAsignacion.getGestion());
            avanzar.setPeriodo(datosAsignacion.getPeriodo());
            avanzar.setId_fase(datosAsignacion.getId_fase());
            avanzar.setId_tipo_grado(Integer.parseInt(sId_tipo_grado));
            avanzar.setId_rol(cliente.getId_rol());         //CRCB
            avanzar.setUlt_usuario(cliente.getId_usuario());

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
        return "administrarLibretas/SalidaRegistrarAvanzarFase";
    }

    @RequestMapping(value = "/definirEvaluacion/RetornarListarAsignaciones.fautapo", method = RequestMethod.POST)
    public String RetornarListarAsignaciones(@ModelAttribute("model") ParametrosEntrada model, Model modelo) {

        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        String sNombres = cliente.getNombres();
        int iId_docente = cliente.getId_usuario();
        int iId_rol = cliente.getId_rol();

        modelo.addAttribute("aux", "");
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("nombres", sNombres);
        modelo.addAttribute("avanzado", "");

        //Sacamos la asignacion del docente
        Asignaciones asignacion = new Asignaciones();
        asignacion.setId_docente(iId_docente);
        asignacion.setGestion(model.getGestion());
        asignacion.setPeriodo(model.getPeriodo());
        List datosAsignacion = this.mi.getDctListarAsignacionDocente(asignacion);
        Asignaciones aux = new Asignaciones();
        for (int i = 0; i < datosAsignacion.size(); i++) {
            aux = (Asignaciones) datosAsignacion.get(i);
            int iId_modelo_ahorro = aux.getId_modelo_ahorro();
            int iId_programa = aux.getId_programa();
            int iId_materia = aux.getId_materia();
            if (iId_modelo_ahorro > 0) {
                Asignaciones datos = new Asignaciones();
                datos.setId_modelo_ahorro(iId_modelo_ahorro);
                datos.setId_programa(iId_programa);
                datos.setGestion(model.getGestion());
                datos.setPeriodo(model.getPeriodo());
                datos.setId_materia(iId_materia);
                List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
                aux.setMateria_ahorro(materiaAhorro);
                datosAsignacion.set(i, aux);
            } else {
                datosAsignacion.set(i, aux);
            }
        }

        List datosAsignaciones = datosAsignacion;
        modelo.addAttribute("datosAsignacion", datosAsignaciones);
        modelo.addAttribute("id_rol", Integer.toString(iId_rol));
        return "definirEvaluacion/ListarAsignaciones";
    }

    @RequestMapping(value = "/ContinuarDefinicionFase.fautapo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageResult> ContinuarDefinicionFase(@ModelAttribute("id_asignacion") Integer id_asignacion) {
        MessageResult Respuesta = new MessageResult();
        int iCantidad = 0;
        int iPonderacion = 0;
        int iResultado = 0;

        Asignaciones datosAsignacion = new Asignaciones();
        datosAsignacion.setId_asignacion(id_asignacion);
        datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);

        Libretas datosTiposNotas = new Libretas();
        datosTiposNotas.setGestion(datosAsignacion.getGestion());
        datosTiposNotas.setPeriodo(datosAsignacion.getPeriodo());
        datosTiposNotas.setId_materia(datosAsignacion.getId_materia());
        datosTiposNotas.setId_fase(datosAsignacion.getId_fase());
        datosTiposNotas.setId_grupo(datosAsignacion.getId_grupo());
        datosTiposNotas.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        datosTiposNotas.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
        datosTiposNotas.setId_departamento(datosAsignacion.getId_departamento());
        List lTiposNotas = this.mi.getLbrListarTiposNotas(datosTiposNotas);
        List lTiposNotasActual = new ArrayList<>();
        for (Object obj : lTiposNotas) {
            Libretas datosAux1 = (Libretas) obj;
            iCantidad = Convert.ToInteger(request, "cantidad:" + datosAux1.getId_tipo_nota());
            iPonderacion = Convert.ToInteger(request, "ponderacion:" + datosAux1.getId_tipo_nota());
            datosAux1.setCantidad(iCantidad);
            datosAux1.setPonderacion(iPonderacion);
            lTiposNotasActual.add(datosAux1);
        }

        Respuesta.setMessage(Tabla(lTiposNotasActual));
        Respuesta.setStatus("OK");
        return new ResponseEntity<>(Respuesta, HttpStatus.OK);
    }

    private String Tabla(List Libreta) {
        String cuerpo = "";
        String cabecera = "<table class='col-md-12 table-bordered table-striped table-condensed table-hover cf'> <thead class='cf'> <tr>  <th>Nro.</th><th>TIPO NOTA</th><th>CANTIDAD</th><th>PORCENTAJE</th> </tr> </thead> <tbody>";
        String Pie = "</tbody> </table>";
        Integer i = 1;
        for (Object obj : Libreta) {
            cuerpo += "<tr>";
            cuerpo += "<td>" + String.valueOf(i) + "</td>";
            cuerpo += "<td>" + ((Libretas) obj).getTipo_nota() + "</td>";
            cuerpo += "<td>" + String.valueOf(((Libretas) obj).getCantidad()) + "</td>";
            cuerpo += "<td>" + String.valueOf(((Libretas) obj).getPonderacion()) + "</td>";
            cuerpo += "</tr>";
            i++;
        }
        return cabecera + cuerpo + Pie;
    }

    @RequestMapping(value = "/ConfirmarDefinicion.fautapo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageResult> ConfirmarDefinicion(@ModelAttribute("model") ConfirmarModel model) {
        MessageResult Respuesta = new MessageResult();
        Clientes cliente = this.GetUsuario();
        int iCantidad = 0;
        int iPonderacion = 0;
        int iResultado = 0;

        //Verificando asignacion
        if (model.getId_asignacion() == null || model.getId_asignacion() == 0) {
            Respuesta.setMessage("No ingreso la Asignaci&oacute;n del Docente. Verifique los datos.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }

        //Buscamos Asignacion Docente
        Asignaciones datosAsignacion = new Asignaciones();
        datosAsignacion.setId_asignacion(model.getId_asignacion());
        datosAsignacion = this.mi.getDctBuscarAsignacionDocente(datosAsignacion);
        if (datosAsignacion == null) {
            Respuesta.setMessage("No  se existen datos para la asignaci&oacute;n del docente seleccionado.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }
        //Sacamos el programa 
        Programas datosPrograma = new Programas();
        datosPrograma.setId_programa(model.getId_programa());
        datosPrograma = this.mi.getPrgBuscarPrograma(datosPrograma);

        //Sacamos datos de la materia
        Materias datosMateria = new Materias();
        datosMateria.setId_materia(datosAsignacion.getId_materia());
        Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
        //Verificamos si el departamento de la materia coincide con la asignacion
        if (datosAsignacion.getId_departamento() != buscarMateria.getId_departamento()) {
            Respuesta.setMessage("Alerta!!. El departamento de la materia no coincide con la asignaci&oacute;n del docente. Consulte al administrador del sistema.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }

        //Verificamos si la materia tiene modelos_ahorros
        if (datosAsignacion.getId_modelo_ahorro() > 0) {
            Asignaciones datos = new Asignaciones();
            datos.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
            datos.setId_materia(datosAsignacion.getId_materia());
            datos.setId_programa(model.getId_programa());
            datos.setGestion(datosAsignacion.getGestion());
            datos.setPeriodo(datosAsignacion.getPeriodo());
            List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
            Asignaciones aux = new Asignaciones();
            for (int i = 0; i < materiaAhorro.size(); i++) {
                aux = (Asignaciones) materiaAhorro.get(i);
            }
        }

        //Sacamos la fase actual
        Libretas datosFase = new Libretas();
        datosFase.setId_fase(datosAsignacion.getId_fase());
        datosFase.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        datosFase.setId_departamento(datosAsignacion.getId_departamento());
        datosFase.setGestion(datosAsignacion.getGestion());
        datosFase.setPeriodo(datosAsignacion.getPeriodo());
        datosFase = this.mi.getLbrBuscarFase(datosFase);

        //Verificamos si la definicion de evaluacion suma el 100%
        Libretas datosTiposNotas = new Libretas();
        datosTiposNotas.setGestion(datosAsignacion.getGestion());
        datosTiposNotas.setPeriodo(datosAsignacion.getPeriodo());
        datosTiposNotas.setId_materia(datosAsignacion.getId_materia());
        datosTiposNotas.setId_fase(datosAsignacion.getId_fase());
        datosTiposNotas.setId_grupo(datosAsignacion.getId_grupo());
        datosTiposNotas.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        datosTiposNotas.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
        datosTiposNotas.setId_departamento(datosAsignacion.getId_departamento());
        List lTiposNotas = this.mi.getLbrListarTiposNotas(datosTiposNotas);

        for (int i = 0; i < lTiposNotas.size(); i++) {
            Libretas datosAux = (Libretas) lTiposNotas.get(i);
            iCantidad += cliente.getInt(request, "cantidad:" + datosAux.getId_tipo_nota());
            iPonderacion += cliente.getInt(request, "ponderacion:" + datosAux.getId_tipo_nota());

            if ((iCantidad == 0) && (iPonderacion > 0)) {
                Respuesta.setMessage("Debe ingresar la cantidad del item para la ponderaci&oacute;n asignada.");
                Respuesta.setStatus("Error");
                return new ResponseEntity<>(Respuesta, HttpStatus.OK);
            }
        }
        if (iCantidad == 0) {
            Respuesta.setMessage("Debe ingresar una cantidad a ser evaluada para el item mayor o igual a 1.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }

        if ((iPonderacion < 100) || (iPonderacion > 100) || (iPonderacion == 0)) {
            Respuesta.setMessage("La ponderacion debe sumar un valor de 100 %.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }

        //Si todo OK. Primero verificamos si podemos anular la definicion de evaluacion
        for (int i = 0; i < lTiposNotas.size(); i++) {
            Libretas datosAux = (Libretas) lTiposNotas.get(i);
            iCantidad = cliente.getInt(request, "cantidad:" + datosAux.getId_tipo_nota());
            iPonderacion = cliente.getInt(request, "ponderacion:" + datosAux.getId_tipo_nota());
            //Primero anulamos
            if ((iCantidad == 0) && (iPonderacion == 0)) {
                //Eliminamos la definicion de evaluacion
                Libretas datosEvaluacion = new Libretas();
                datosEvaluacion.setId_materia(datosAsignacion.getId_materia());
                datosEvaluacion.setId_grupo(datosAsignacion.getId_grupo());
                datosEvaluacion.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                datosEvaluacion.setId_tipo_nota(datosAux.getId_tipo_nota());
                datosEvaluacion.setId_fase(datosAsignacion.getId_fase());
                datosEvaluacion.setId_departamento(datosAsignacion.getId_departamento());
                datosEvaluacion.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                datosEvaluacion.setGestion(datosAsignacion.getGestion());
                datosEvaluacion.setPeriodo(datosAsignacion.getPeriodo());
                datosEvaluacion.setId_rol(cliente.getId_rol());	   //CRCB
                datosEvaluacion.setUlt_usuario(cliente.getId_usuario());

                iResultado = this.mi.setGrpEliminarEvaluacion(datosEvaluacion);
                if (iResultado != 1) {
                    Respuesta.setMessage("No se podra; anular la definicion donde modifica CANTIDAD y PONDERACION = 0  Por que la definicion cuenta con registros de  notas en libretas.");
                    Respuesta.setStatus("Error");
                    return new ResponseEntity<>(Respuesta, HttpStatus.OK);
                }
            }
        }

        //Luego Registramos
        //Registramos la definicion de evaluacion
        for (int i = 0; i < lTiposNotas.size(); i++) {
            Libretas datosAux1 = (Libretas) lTiposNotas.get(i);
            iCantidad = cliente.getInt(request, "cantidad:" + datosAux1.getId_tipo_nota());
            iPonderacion = cliente.getInt(request, "ponderacion:" + datosAux1.getId_tipo_nota());
            if ((iCantidad > 0) && (iPonderacion > 0)) {
                //En caso de que existe actualizamos la definicion de evaluacion
                Libretas datosEvaluacion = new Libretas();
                datosEvaluacion.setId_materia(datosAsignacion.getId_materia());
                datosEvaluacion.setId_grupo(datosAsignacion.getId_grupo());
                datosEvaluacion.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
                datosEvaluacion.setId_tipo_nota(datosAux1.getId_tipo_nota());
                datosEvaluacion.setId_fase(datosAsignacion.getId_fase());
                datosEvaluacion.setId_departamento(datosAsignacion.getId_departamento());
                datosEvaluacion.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
                datosEvaluacion.setId_tipo_docente(datosAsignacion.getId_tipo_docente());
                datosEvaluacion.setGestion(datosAsignacion.getGestion());
                datosEvaluacion.setPeriodo(datosAsignacion.getPeriodo());
                datosEvaluacion.setCantidad(iCantidad);
                datosEvaluacion.setPonderacion(iPonderacion);
                datosEvaluacion.setId_rol(cliente.getId_rol());	   //CRCB
                datosEvaluacion.setUlt_usuario(cliente.getId_usuario());
                iResultado = this.mi.setGrpRegistrarEvaluacion(datosEvaluacion);
                if (iResultado != 1) {
                    Respuesta.setMessage("Ocurrio; un error. No se pudo registrar la definicion de evaluacion.");
                    Respuesta.setStatus("Error");
                    return new ResponseEntity<>(Respuesta, HttpStatus.OK);
                }
            }
        }

        //Sacamos la definicion actual de la evaluacion
        Libretas datosDefinicion = new Libretas();
        datosDefinicion.setGestion(datosAsignacion.getGestion());
        datosDefinicion.setPeriodo(datosAsignacion.getPeriodo());
        datosDefinicion.setId_materia(datosAsignacion.getId_materia());
        datosDefinicion.setId_fase(datosAsignacion.getId_fase());
        datosDefinicion.setId_grupo(datosAsignacion.getId_grupo());
        datosDefinicion.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
        datosDefinicion.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
        datosDefinicion.setId_departamento(datosAsignacion.getId_departamento());
        List lTiposNotasActual = this.mi.getLbrListarTiposNotas(datosDefinicion);

        Respuesta.setMessage("");
        Respuesta.setStatus("OK");
        return new ResponseEntity<>(Respuesta, HttpStatus.OK);
    }
}
