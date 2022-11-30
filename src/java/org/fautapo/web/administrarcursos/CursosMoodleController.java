/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fautapo.web.administrarcursos;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.fautapo.Moodle.Entidades.MoodleCourse;
import org.fautapo.Moodle.Entidades.MoodleEnroll;
import org.fautapo.Moodle.Entidades.MoodleGroup;
import org.fautapo.Moodle.Entidades.MoodleUsers;
import org.fautapo.Moodle.Exception.MoodleRestUserException;
import org.fautapo.Moodle.Servicios.MoodleCourseService;
import org.fautapo.Moodle.Servicios.MoodleEnrollUsertheCourse;
import org.fautapo.Moodle.Servicios.MoodleGroupService;
import org.fautapo.Moodle.Servicios.MoodleUserService;
import org.fautapo.domain.Asignaciones;
import org.fautapo.domain.Clientes;
import org.fautapo.domain.CursosMoodle;
import org.fautapo.domain.MoodleConfiguracion;
import org.fautapo.domain.Personas;
import org.fautapo.domain.logic.MiFacade;
import org.fautapo.model.ParametrosEntrada;
import org.fautapo.Moodle.Servicios.MoodleWebServiceAuth;
import org.fautapo.domain.Docentes;
import org.fautapo.domain.EstudianteProgramado;
import org.fautapo.domain.Libretas;
import org.fautapo.domain.Materias;
import org.fautapo.domain.ParametrosBusqueda;
import org.fautapo.model.AddUserMoodleModel;
import org.fautapo.model.MessageResult;
import org.fautapo.model.MessageUserResult;
import org.fautapo.model.UserCourseEnrollMoodleModel;
import org.fautapo.model.UserCourseMoodleModel;
import org.fautapo.model.UserMoodleModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class CursosMoodleController {

    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;

    private Clientes GetUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/EntradaAdministrarCursos.fautapo")
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
        return "administrarCursos/EntradaAdministrarCursos";
    }

    @RequestMapping(value = "/ListarAsignacionesAdministrarCursos.fautapo", method = RequestMethod.POST)
    public String ListarAsignacionesAdministrarCursos(@ModelAttribute("model") @Validated ParametrosEntrada model, BindingResult result, Model modelo) throws IOException, UnsupportedEncodingException, MalformedURLException, JSONException, MoodleRestUserException {
        Clientes cliente = (Clientes) request.getSession().getAttribute("__sess_cliente");
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
            return "administrarCursos/EntradaAdministrarCursos";
        }
        String sNombres = cliente.getNombres();
        int iId_docente = cliente.getId_usuario();
        int iId_rol = cliente.getId_rol();

        int id_docente = cliente.getId_usuario();
        Personas persona = mi.getPrsBuscarPersonaDocente(id_docente);

        //obtiene la imgen de perfil
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
                datos.setId_persona(aux.getId_persona());
                List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
                aux.setMateria_ahorro(materiaAhorro);
                datosAsignacion.set(i, aux);
            } else {
                datosAsignacion.set(i, aux);
            }
        }
        MoodleConfiguracion config = mi.GetConfiguracionCursosMoodle();
        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());
        MoodleUsers user = new MoodleUsers();
        user.setIdnumber(String.valueOf(persona.getId_persona()));
        user.setUsername(String.valueOf(persona.getCorreo().toLowerCase()));
        List<MoodleUsers> userresult = MoodleUserService.findUserByid(user);

        if (!userresult.isEmpty()) {
            if (!userresult.get(0).getIdnumber().equals(String.valueOf(persona.getId_persona()))) {
                MoodleUsers useraux = userresult.get(0);
                useraux.setIdnumber(String.valueOf(persona.getId_persona()));
                MoodleUserService.UpdateIDNumber(useraux);
            }
            CursosMoodle obj = mi.GetUsuarioMoodle(persona.getId_persona());
            if (obj == null) {
                CursosMoodle moodle = new CursosMoodle();
                moodle.setId_persona_moxos(Integer.parseInt(userresult.get(0).getIdnumber()));
                moodle.setMoodle_username(userresult.get(0).getUsername());
                moodle.setMoodle_password("null");
                moodle.setMoodle_correo(userresult.get(0).getEmail());
                moodle.setMoodle_nombres_usuario(sNombres);
                moodle.setUlt_usuario(cliente.getId_usuario());
                mi.RegistrarUsuariosMoodle(moodle);
                obj = mi.GetUsuarioMoodle(persona.getId_persona());
            }
            String originalUrl = obj.getMoodle_password();
            obj.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrl.getBytes()));
            List datosAsignaciones = datosAsignacion;
            modelo.addAttribute("datosAsignacion", datosAsignaciones);
            modelo.addAttribute("id_rol", Integer.toString(iId_rol));
            modelo.addAttribute("usermoodle", obj);
            return "administrarCursos/ListarAsignaciones";
        } else {
            modelo.addAttribute("id_rol", Integer.toString(iId_rol));
            AddUserMoodleModel crearusuario = new AddUserMoodleModel();
            crearusuario.setApellidos(persona.getPaterno() + " " + persona.getMaterno());
            crearusuario.setAux("");
            crearusuario.setAvanzado("");
            crearusuario.setClave("");
            crearusuario.setCorreo(persona.getCorreo());
            crearusuario.setNombres(sNombres);
            crearusuario.setGestion(model.getGestion());
            crearusuario.setIdnumber(persona.getId_persona());
            crearusuario.setPeriodo(model.getPeriodo());
            crearusuario.setNombre(persona.getNombres());
            crearusuario.setUsuario(persona.getCorreo());
            modelo.addAttribute("model", crearusuario);
            return "administrarCursos/RegistrarMoodle";
        }
    }

    @RequestMapping(value = "/ListarCursosMoodle.fautapo", method = RequestMethod.POST)
    public String ListarCursosMoodle(Model modelo) throws IOException, UnsupportedEncodingException, MalformedURLException, JSONException, MoodleRestUserException {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        List<CursosMoodle> listacursosmoodle = new ArrayList<CursosMoodle>();
        int id_materia = cliente.getInt(request, "id_materia");
        int id_grupo = cliente.getInt(request, "id_grupo");
        int id_programa = cliente.getInt(request, "id_programa");
        int gestion = cliente.getInt(request, "gestion");
        int periodo = cliente.getInt(request, "periodo");
        int id_docente = cliente.getInt(request, "id_docente");
        String nombres = cliente.getNombres();
        String sigla = cliente.getString(request, "sigla");
        String grupo = cliente.getString(request, "grupo");
        String materia = cliente.getString(request, "materia");
        String programa = cliente.getString(request, "programa");
        String fase = cliente.getString(request, "fase");
        Personas persona = mi.getPrsBuscarPersonaDocente(id_docente);

        int id_rol = cliente.getId_rol();

        ParametrosBusqueda busqueda = new ParametrosBusqueda();
        busqueda.setGestion(gestion);
        busqueda.setId_docente(id_docente);
        busqueda.setId_grupo(id_grupo);
        busqueda.setId_materia(id_materia);
        busqueda.setId_programa(id_programa);
        busqueda.setPeriodo(periodo);
        List<CursosMoodle> cursosmoodle = mi.GetListarCursosMoodleDocente(busqueda);

        modelo.addAttribute("id_rol", id_rol);
        modelo.addAttribute("nombres", nombres);
        modelo.addAttribute("sigla", sigla);
        modelo.addAttribute("grupo", grupo);
        modelo.addAttribute("materia", materia);
        modelo.addAttribute("programa", programa);
        modelo.addAttribute("fase", fase);
        modelo.addAttribute("id_materia", id_materia);
        modelo.addAttribute("id_grupo", id_grupo);
        modelo.addAttribute("id_programa", id_programa);
        modelo.addAttribute("gestion", gestion);
        modelo.addAttribute("periodo", periodo);
        modelo.addAttribute("id_docente", id_docente);
        modelo.addAttribute("persona", persona);

        CursosMoodle obj = mi.GetUsuarioMoodle(persona.getId_persona());
        String originalUrl = obj.getMoodle_password();
        obj.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrl.getBytes()));
        modelo.addAttribute("usermoodle", obj);
        if (cursosmoodle.isEmpty()) {
            modelo.addAttribute("mensajeerror", "No existe cursos creados para mostrar");
            return "administrarCursos/ListarCursosMoodle";
        } else {
            MoodleUsers userresult = null;
            MoodleUsers user = new MoodleUsers();
            user.setIdnumber(String.valueOf(persona.getId_persona()));
            userresult = MoodleUserService.find(user).get(0);

            for (CursosMoodle c : cursosmoodle) {
                CursosMoodle obj1 = c;
                String originalUrl1 = c.getMoodle_password();
                obj.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrl1.getBytes()));
                MoodleEnroll enroll = new MoodleEnroll();
                enroll.setCourseid(Integer.parseInt(c.getId_moodle_cursos()));
                enroll.setUserid(userresult.getId());
                MoodleCourse course = MoodleEnrollUsertheCourse.GetEnroll(enroll);
                if (course == null) {
                    obj1.setMatricular(Boolean.TRUE);
                } else {
                    obj1.setMatricular(Boolean.FALSE);
                }
                listacursosmoodle.add(obj1);
            }
            modelo.addAttribute("cursosmoodle", listacursosmoodle);
            return "administrarCursos/ListarCursosMoodle";
        }
    }

    @RequestMapping(value = "/CrearCurso.fautapo", method = RequestMethod.POST)
    public String CrearCurso(Model modelo) throws IOException, UnsupportedEncodingException, MalformedURLException, JSONException, MoodleRestUserException {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        MoodleConfiguracion config = mi.GetConfiguracionCursosMoodle();

        List<CursosMoodle> listacursosmoodle = new ArrayList<CursosMoodle>();
        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());
        int id_materia = cliente.getInt(request, "id_materia");
        int id_grupo = cliente.getInt(request, "id_grupo");
        int id_programa = cliente.getInt(request, "id_programa");
        int gestion = cliente.getInt(request, "gestion");
        int periodo = cliente.getInt(request, "periodo");
        int id_docente = cliente.getInt(request, "id_docente");
        String nombres = cliente.getNombres();
        String sigla = cliente.getString(request, "sigla");
        String grupo = cliente.getString(request, "grupo");
        String materia = cliente.getString(request, "materia");
        String programa = cliente.getString(request, "programa");
        String fase = cliente.getString(request, "fase");

        String encodedWithUTF8 = programa;

        int id_rol = cliente.getId_rol();
        Personas persona = mi.getPrsBuscarPersonaDocente(id_docente);
        MoodleGroup group = MoodleGroupService.GetGroup(String.valueOf(id_programa));
        System.out.println(group.getName());

        MoodleCourse curso = new MoodleCourse();
        curso.setCategoryid(group.getId());
        curso.setFullname(sigla + " - " + materia + "  " + String.valueOf(periodo) + "/" + String.valueOf(gestion) + " PARALELO " + grupo);
        curso.setShortname(String.valueOf(id_materia) + "-" + sigla + String.valueOf(periodo) + "/" + String.valueOf(gestion) + " PARALELO " + grupo);
        curso.setIdnumber(String.valueOf(id_materia) + "-" + String.valueOf(periodo) + "/" + String.valueOf(gestion) + "-" + grupo);
        curso.setVisible(1);
        MoodleCourse cursocreado = MoodleCourseService.createCourse(curso);

        CursosMoodle cursomoodle = new CursosMoodle();
        cursomoodle.setId_moodle(-1);
        cursomoodle.setMoodle_username("");
        cursomoodle.setMoodle_password("");
        cursomoodle.setMoodle_correo("");
        cursomoodle.setMoodle_nombres_usuario("");
        cursomoodle.setMoodle_apellidos_usuario("");
        cursomoodle.setMoodle_detallecurso(sigla + " - " + materia + "  " + String.valueOf(periodo) + "/" + String.valueOf(gestion) + " PARALELO " + grupo);
        cursomoodle.setId_moodle_cursos(String.valueOf(cursocreado.getId()));
        cursomoodle.setId_rol_cursos_moodle(2);
        cursomoodle.setId_persona_moxos(persona.getId_persona());
        cursomoodle.setId_programa(id_programa);
        cursomoodle.setGestion(gestion);
        cursomoodle.setPeriodo(periodo);
        cursomoodle.setId_usuario_moxos_ru_doc(id_docente);
        cursomoodle.setTipo_usuario(2);
        cursomoodle.setId_grupo(id_grupo);
        cursomoodle.setId_materia(id_materia);
        cursomoodle.setId_estado("A");
        cursomoodle.setId_rol(id_rol);
        cursomoodle.setUlt_usuario(cliente.getId_usuario());
        mi.RegistrarCursosMoodleDocente(cursomoodle);

        ParametrosBusqueda busqueda = new ParametrosBusqueda();
        busqueda.setGestion(gestion);
        busqueda.setId_docente(id_docente);
        busqueda.setId_grupo(id_grupo);
        busqueda.setId_materia(id_materia);
        busqueda.setId_programa(id_programa);
        busqueda.setPeriodo(periodo);
        List<CursosMoodle> cursosmoodle = mi.GetListarCursosMoodleDocente(busqueda);
        CursosMoodle usuarioMoodle = mi.GetUsuarioMoodle(persona.getId_persona());
        String originalUrlusuarioMoodle = usuarioMoodle.getMoodle_password();
        usuarioMoodle.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrlusuarioMoodle.getBytes()));
        modelo.addAttribute("usermoodle", usuarioMoodle);
        modelo.addAttribute("id_rol", id_rol);
        modelo.addAttribute("nombres", nombres);
        modelo.addAttribute("sigla", sigla);
        modelo.addAttribute("grupo", grupo);
        modelo.addAttribute("materia", materia);
        modelo.addAttribute("programa", programa);
        modelo.addAttribute("fase", fase);
        modelo.addAttribute("id_materia", id_materia);
        modelo.addAttribute("id_grupo", id_grupo);
        modelo.addAttribute("id_programa", id_programa);
        modelo.addAttribute("gestion", gestion);
        modelo.addAttribute("periodo", periodo);
        modelo.addAttribute("id_docente", id_docente);
        modelo.addAttribute("persona", persona);
        MoodleUsers userresult = null;
        MoodleUsers user = new MoodleUsers();
        user.setIdnumber(String.valueOf(persona.getId_persona()));
        userresult = MoodleUserService.find(user).get(0);
        if (cursosmoodle.isEmpty()) {
            modelo.addAttribute("mensajeerror", "No existe cursos creados para mostrar");
            return "administrarCursos/ListarCursosMoodle";
        } else {
            for (CursosMoodle c : cursosmoodle) {
                CursosMoodle obj = c;
                String originalUrl = c.getMoodle_password();
                obj.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrl.getBytes()));
                MoodleEnroll enroll = new MoodleEnroll();
                enroll.setCourseid(Integer.parseInt(c.getId_moodle_cursos()));
                enroll.setUserid(userresult.getId());
                MoodleCourse course = MoodleEnrollUsertheCourse.GetEnroll(enroll);
                if (course == null) {
                    obj.setMatricular(Boolean.TRUE);
                } else {
                    obj.setMatricular(Boolean.FALSE);
                }
                listacursosmoodle.add(obj);
            }
            modelo.addAttribute("cursosmoodle", listacursosmoodle);
            return "administrarCursos/ListarCursosMoodle";
        }
    }

    @RequestMapping(value = "/AdministrarCursosListarAlumnosProgramados.fautapo", method = RequestMethod.POST)
    public String AdministrarCursosListarAlumnosProgramados(Model modelo) throws IOException, UnsupportedEncodingException, MalformedURLException, JSONException, MoodleRestUserException {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        MoodleConfiguracion config = mi.GetConfiguracionCursosMoodle();
        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());
        List<EstudianteProgramado> estudiantes = new ArrayList<EstudianteProgramado>();
        CursosMoodle cursos;
        //parametros de entrada
        String sId_tipo_grado = request.getParameter("id_tipo_grado");
        int gestion = cliente.getInt(request, "gestion");
        int periodo = cliente.getInt(request, "periodo");
        int id_grupo = cliente.getInt(request, "id_grupo");
        int id_materia = cliente.getInt(request, "id_materia");
        int id_programa = cliente.getInt(request, "id_programa");

        //Busqueda de la materia en el moodle del curso creado
        int iId_asignacion = cliente.getInt(request, "id_asignacion");
        ParametrosBusqueda parametros = new ParametrosBusqueda();
        parametros.setId_docente(cliente.getId_usuario());
        parametros.setGestion(gestion);
        parametros.setPeriodo(periodo);
        parametros.setId_grupo(id_grupo);
        parametros.setId_materia(id_materia);
        parametros.setId_programa(id_programa);
        try {
            cursos = mi.GetListarCursosMoodleDocente(parametros).stream().findFirst().get();
        } catch (Exception ex) {
            modelo.addAttribute("mensaje", "No creo ningun curso para matricular estudiantes");
            return "administrarCursos/Aviso";
        }
        parametros.setId_moodle_cursos(cursos.getId_moodle_cursos());
        List<CursosMoodle> estudiantesmatriculadosacademico = mi.GetListarCursosMoodleEstudiantePorCurso(parametros);
        //Buscamos la asignacion docente
        Asignaciones buscarAsignacion = new Asignaciones();
        buscarAsignacion.setId_asignacion(iId_asignacion);
        Asignaciones datosAsignacion = this.mi.getDctBuscarAsignacionDocente(buscarAsignacion);
        modelo.addAttribute("datosAsignacion", datosAsignacion);
        if (datosAsignacion == null) {
            modelo.addAttribute("mensaje", "No se encontr&oacute; la asignaci&oacute;n docente para la materia");
            return "Error";
        }
        String sId_tipo_evaluacion = Integer.toString(datosAsignacion.getId_tipo_evaluacion());

        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("id_docente", Integer.toString(cliente.getId_usuario()));
        modelo.addAttribute("id_rol", Integer.toString(cliente.getId_rol()));
        modelo.addAttribute("idcursomoodle", cursos.getId_cursos_moodle());
        modelo.addAttribute("idcurso", cursos.getId_moodle_cursos());
        modelo.addAttribute("gestion", gestion);
        modelo.addAttribute("periodo", periodo);
        modelo.addAttribute("id_grupo", id_grupo);
        modelo.addAttribute("id_materia", id_materia);

        //Sacamos el programa  
        modelo.addAttribute("id_programa", id_programa);
        modelo.addAttribute("programa", cursos.getPrograma());

        //Sacamos datos de la materia
        Materias datosMateria = new Materias();
        datosMateria.setId_materia(datosAsignacion.getId_materia());
        Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);
        modelo.addAttribute("sigla", buscarMateria.getSigla());
        modelo.addAttribute("id_materia", Integer.toString(buscarMateria.getId_materia()));
        modelo.addAttribute("materia", cursos.getMoodle_detallecurso());
        //Verificamos si el departamento de la materia coincide con la asignacion
        if (datosAsignacion.getId_departamento() != buscarMateria.getId_departamento()) {
            modelo.addAttribute("mensaje", "Alerta!!. El departamento de la materia no coincide con la asignaci&oacute;n del docente. Consulte al administrador del sistema.");
            return "Error";
        }

        //Verificamos si es una materia con modelo ahorro
        //Buscamos la materia modelo_ahorro
        Asignaciones datos = new Asignaciones();
        if (datosAsignacion.getId_modelo_ahorro() > 0) {
            datos.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
            datos.setId_materia(datosAsignacion.getId_materia());
            datos.setId_programa(id_programa);
            datos.setGestion(datosAsignacion.getGestion());
            datos.setPeriodo(datosAsignacion.getPeriodo());
            List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
            Asignaciones aux = new Asignaciones();
            for (int i = 0; i < materiaAhorro.size(); i++) {
                aux = (Asignaciones) materiaAhorro.get(i);
            }
        }
        List<MoodleUsers> usuarioinscritos = MoodleEnrollUsertheCourse.GetUserEnrollCourse(Integer.parseInt(cursos.getId_moodle_cursos()));
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
            return "administrarCursos/Aviso";
        }
        modelo.addAttribute("fase", datosFase.getFase());
        //Sacando la lista de estudiantes programados a la materia, evaluacion regular
        if (("1".equals(sId_tipo_grado)) && (!"2".equals(sId_tipo_evaluacion))) {
            Libretas datosEstProg = new Libretas();
            datosEstProg.setId_materia(datosAsignacion.getId_materia());
            datosEstProg.setId_grupo(datosAsignacion.getId_grupo());
            datosEstProg.setId_modelo_ahorro(datosAsignacion.getId_modelo_ahorro());
            datosEstProg.setGestion(datosAsignacion.getGestion());
            datosEstProg.setPeriodo(datosAsignacion.getPeriodo());
            datosEstProg.setId_fase(datosAsignacion.getId_fase());
            datosEstProg.setId_tipo_evaluacion(datosAsignacion.getId_tipo_evaluacion());
            List<Libretas> lEstudiantes = this.mi.getEstBuscarEstudiantesProgramadospersona(datosEstProg);
            for (Libretas lib : lEstudiantes) {
                EstudianteProgramado est = new EstudianteProgramado();
                est.setId_curso(0);
                est.setId_persona(lib.getId_persona());
                est.setNombres_completos(lib.getNombres());
                String paterno = lib.getPaterno().length() <= 0 ? lib.getMaterno() : lib.getPaterno();
                String materno = lib.getMaterno().length() <= 0 ? "" : lib.getMaterno();
                String usuario = lib.getNombre() + "!" + paterno + " " + materno;
                est.setUsuario(lib.getDip().replace("-", ""));
                est.setNombres(usuario);
                est.setRu(lib.getId_estudiante());
                est.setRegistrado(false);
                est.setRegistradomoodle(false);
                estudiantes.add(est);
            }

            List<EstudianteProgramado> revisar = new ArrayList<>();
            for (EstudianteProgramado est : estudiantes) {
                if (!estudiantesmatriculadosacademico.stream().anyMatch(p -> p.getId_persona_moxos() == est.getId_persona())) {
                    est.setRegistrado(false);
                } else {
                    est.setRegistrado(true);
                }
                revisar.add(est);
            }
            List<EstudianteProgramado> result = new ArrayList<>();
            for (EstudianteProgramado est : revisar) {
                if (!usuarioinscritos.stream().anyMatch(p -> Integer.parseInt(p.getIdnumber().isEmpty() ? "0" : p.getIdnumber()) == est.getId_persona())) {
                    est.setRegistradomoodle(false);
                    result.add(est);
                } else {
                    est.setRegistradomoodle(true);
                    result.add(est);
                }
            }
            PagedListHolder lNotas = new PagedListHolder(result.stream().filter(p -> !p.isRegistrado() || !p.isRegistradomoodle()).collect(Collectors.toList()));
            lNotas.setPageSize(lNotas.getNrOfElements());
            modelo.addAttribute("listaNotas", lNotas);
        }
        return "administrarCursos/ListarEstudiantesProgramados";
    }

    @RequestMapping(value = "/ListarEstudiantesMatriculados.fautapo", method = RequestMethod.POST)
    public String ListarEstudiantesMatriculados(Model modelo) {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        CursosMoodle cursos;

        String Id_moodle_cursos = request.getParameter("Id_moodle_cursos");
        int gestion = cliente.getInt(request, "gestion");
        int periodo = cliente.getInt(request, "periodo");
        int id_grupo = cliente.getInt(request, "id_grupo");
        int id_materia = cliente.getInt(request, "id_materia");
        int id_programa = cliente.getInt(request, "id_programa");
        String fase = request.getParameter("fase");

        ParametrosBusqueda parametros = new ParametrosBusqueda();
        parametros.setId_docente(cliente.getId_usuario());
        parametros.setGestion(gestion);
        parametros.setPeriodo(periodo);
        parametros.setId_grupo(id_grupo);
        parametros.setId_materia(id_materia);
        parametros.setId_programa(id_programa);
        try {
            cursos = mi.GetListarCursosMoodleDocente(parametros).stream().findFirst().get();
        } catch (Exception ex) {
            modelo.addAttribute("mensaje", "No creo ningun curso para matricular estudiantes");
            return "administrarCursos/Aviso";
        }
        parametros.setId_moodle_cursos(Id_moodle_cursos);
        List<CursosMoodle> estudiantesmatriculadosacademico = mi.GetListarCursosMoodleEstudiantePorCurso(parametros);
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("materia", cursos.getMoodle_detallecurso());
        modelo.addAttribute("programa", cursos.getPrograma());
        modelo.addAttribute("fase", fase);
        modelo.addAttribute("cursosmoodle", estudiantesmatriculadosacademico);
        return "administrarCursos/ListarEstudiantesMatriculados";

    }

    @RequestMapping(value = "/CrearCursosMoodle.fautapo", method = RequestMethod.POST)
    public String CrearCursosMoodle(Model modelo) throws IOException, UnsupportedEncodingException, MalformedURLException, JSONException, MoodleRestUserException {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        List<CursosMoodle> listacursosmoodle = new ArrayList<CursosMoodle>();
        int id_materia = cliente.getInt(request, "id_materia");
        int id_grupo = cliente.getInt(request, "id_grupo");
        int id_programa = cliente.getInt(request, "id_programa");
        int gestion = cliente.getInt(request, "gestion");
        int periodo = cliente.getInt(request, "periodo");
        int id_docente = cliente.getInt(request, "id_docente");
        String nombres = cliente.getNombres();
        String sigla = cliente.getString(request, "sigla");
        String grupo = cliente.getString(request, "grupo");
        String materia = cliente.getString(request, "materia");
        String programa = cliente.getString(request, "programa");
        String fase = cliente.getString(request, "fase");
        Personas persona = mi.getPrsBuscarPersonaDocente(id_docente);

        int id_rol = cliente.getId_rol();

        ParametrosBusqueda busqueda = new ParametrosBusqueda();
        busqueda.setGestion(gestion);
        busqueda.setId_docente(id_docente);
        busqueda.setId_grupo(id_grupo);
        busqueda.setId_materia(id_materia);
        busqueda.setId_programa(id_programa);
        busqueda.setPeriodo(periodo);
        List<CursosMoodle> cursosmoodle = mi.GetListarCursosMoodleDocente(busqueda);
        CursosMoodle usuarioMoodle = mi.GetUsuarioMoodle(persona.getId_persona());
        String originalUrlusuarioMoodle = usuarioMoodle.getMoodle_password();
        usuarioMoodle.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrlusuarioMoodle.getBytes()));
        modelo.addAttribute("usermoodle", usuarioMoodle);
        modelo.addAttribute("id_rol", id_rol);
        modelo.addAttribute("nombres", nombres);
        modelo.addAttribute("sigla", sigla);
        modelo.addAttribute("grupo", grupo);
        modelo.addAttribute("materia", materia);
        modelo.addAttribute("programa", programa);
        modelo.addAttribute("fase", fase);
        modelo.addAttribute("id_materia", id_materia);
        modelo.addAttribute("id_grupo", id_grupo);
        modelo.addAttribute("id_programa", id_programa);
        modelo.addAttribute("gestion", gestion);
        modelo.addAttribute("periodo", periodo);
        modelo.addAttribute("id_docente", id_docente);
        modelo.addAttribute("persona", persona);
        MoodleUsers userresult = null;
        MoodleUsers user = new MoodleUsers();
        user.setIdnumber(String.valueOf(persona.getId_persona()));
        userresult = MoodleUserService.find(user).get(0);
        if (cursosmoodle.isEmpty()) {
            modelo.addAttribute("mensajeerror", "No existe cursos creados para mostrar");
            return "administrarCursos/ListarCursosMoodle";
        } else {
            for (CursosMoodle c : cursosmoodle) {
                CursosMoodle obj = c;
                String originalUrl = c.getMoodle_password();
                obj.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrl.getBytes()));
                MoodleEnroll enroll = new MoodleEnroll();
                enroll.setCourseid(Integer.parseInt(c.getId_moodle_cursos()));
                enroll.setUserid(userresult.getId());
                MoodleCourse course = MoodleEnrollUsertheCourse.GetEnroll(enroll);
                if (course == null) {
                    obj.setMatricular(Boolean.TRUE);
                } else {
                    obj.setMatricular(Boolean.FALSE);
                }
                listacursosmoodle.add(obj);
            }
            modelo.addAttribute("cursosmoodle", listacursosmoodle);
            return "administrarCursos/ListarCursosMoodle";
        }
    }

    @RequestMapping(value = "/DetalleUsuario.fautapo", method = RequestMethod.POST)
    public String DetalleUsuario(Model modelo) throws MoodleRestUserException, JSONException, IOException {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        MoodleConfiguracion config = mi.GetConfiguracionCursosMoodle();
        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());
        String id_persona = cliente.getString(request, "id_persona");
        MoodleUsers userresult = MoodleUserService.Get(id_persona);
        return "administrarCursos/modificarusuario";
    }

    @RequestMapping(value = "/ModificarUsuario.fautapo", method = RequestMethod.GET)
    public String ModificarUsuario(Model modelo) throws MoodleRestUserException, JSONException, IOException {
        Clientes cliente = this.GetUsuario();
        MoodleConfiguracion config = mi.GetConfiguracionCursosMoodle();
        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());
        String idnumber = cliente.getString(request, "id_persona");
        String indice = cliente.getString(request, "indice");
        MoodleUsers user = new MoodleUsers();
        user = MoodleUserService.Get(idnumber);
        modelo.addAttribute("user", user);
        modelo.addAttribute("indice", indice);
        return "administrarCursos/modificarusuario";
    }

    @RequestMapping(value = "/RegistraroActualizarUsuarioMoodle.fautapo", method = RequestMethod.POST)
    public String RegistraroActualizarUsuarioMoodle(Model modelo) throws MoodleRestUserException, JSONException, IOException {
        Clientes cliente = this.GetUsuario();
        if (cliente == null) {
            modelo.addAttribute("mensaje", "Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            return "Aviso";
        }
        String sigla = cliente.getString(request, "sigla");
        String materia = cliente.getString(request, "materia");
        String nombres = cliente.getNombres();
        String programa = cliente.getString(request, "programa");
        String fase = cliente.getString(request, "fase");

        int idcursomoodle = cliente.getInt(request, "idcursomoodle");
        int id_materia = cliente.getInt(request, "id_materia");
        int id_grupo = cliente.getInt(request, "id_grupo");
        int id_programa = cliente.getInt(request, "id_programa");
        int gestion = cliente.getInt(request, "gestion");
        int periodo = cliente.getInt(request, "periodo");
        int idcurso = cliente.getInt(request, "idcurso");

        int id_rol = cliente.getId_rol();
        int id_docente = cliente.getId_usuario();
        Personas persona = mi.getPrsBuscarPersonaDocente(id_docente);

        modelo.addAttribute("sigla", sigla);
        modelo.addAttribute("materia", materia);
        modelo.addAttribute("nombres", nombres);
        modelo.addAttribute("programa", programa);
        modelo.addAttribute("fase", fase);
        modelo.addAttribute("id_rol", id_rol);
        modelo.addAttribute("nombre", persona.getNombres());
        modelo.addAttribute("apellidos", persona.getPaterno() + " " + persona.getMaterno());
        modelo.addAttribute("correo", persona.getCorreo());
        modelo.addAttribute("idnumber", persona.getId_persona());
        modelo.addAttribute("id_materia", id_materia);
        modelo.addAttribute("id_grupo", id_grupo);
        modelo.addAttribute("id_programa", id_programa);
        modelo.addAttribute("gestion", gestion);
        modelo.addAttribute("periodo", periodo);
        modelo.addAttribute("idcurso", idcurso);
        modelo.addAttribute("idcursomoodle", idcursomoodle);
        return "administrarCursos/RegistrarMoodle";
    }

    @RequestMapping(value = "/UsuariosCursosMoodle.fautapo", method = RequestMethod.POST)
    public String UsuariosCursosMoodle(@ModelAttribute("model") @Validated AddUserMoodleModel model, BindingResult result, Model modelo) throws MoodleRestUserException, JSONException, IOException {
        Clientes cliente = this.GetUsuario();
        if (result.hasErrors()) {
            modelo.addAttribute("id_rol", cliente.getId_rol());
            modelo.addAttribute("nombres", cliente.getNombres());
            modelo.addAttribute("model", model);
            return "administrarCursos/RegistrarMoodle";
        }
        String sNombres = cliente.getNombres();
        int iId_docente = cliente.getId_usuario();
        int iId_rol = cliente.getId_rol();

        int id_docente = cliente.getId_usuario();
        Personas persona = mi.getPrsBuscarPersonaDocente(id_docente);
        String sMensaje = "";

        //obtiene la imgen de perfil
        modelo.addAttribute("aux", model.getAux());
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("nombres", model.getNombres());
        modelo.addAttribute("avanzado", model.getAvanzado());
        modelo.addAttribute("clave", model.getClave());

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
                datos.setId_persona(aux.getId_persona());
                List materiaAhorro = this.mi.getMtrListarMateriaAhorro(datos);
                aux.setMateria_ahorro(materiaAhorro);
                datosAsignacion.set(i, aux);
            } else {
                datosAsignacion.set(i, aux);

            }
        }
        MoodleConfiguracion config = mi.GetConfiguracionCursosMoodle();
        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());
        MoodleUsers user = new MoodleUsers();
        user.setEmail(model.getCorreo());
        user.setFirstname(model.getNombre());
        user.setIdnumber(model.getIdnumber().toString());
        user.setLastname(model.getApellidos());
        user.setPassword(model.getConfirmar_password());
        user.setUsername(model.getUsuario());
        MoodleUsers userresult = null;

        userresult = MoodleUserService.createUser(user);
        CursosMoodle moodle = new CursosMoodle();
        moodle.setId_persona_moxos(Integer.parseInt(userresult.getIdnumber()));
        moodle.setMoodle_username(userresult.getUsername());
        moodle.setMoodle_password(model.getConfirmar_password());
        moodle.setMoodle_correo(model.getCorreo());
        moodle.setMoodle_nombres_usuario(sNombres);
        moodle.setUlt_usuario(cliente.getId_usuario());
        mi.RegistrarUsuariosMoodle(moodle);

        CursosMoodle obj = mi.GetUsuarioMoodle(persona.getId_persona());
        String originalUrl = obj.getMoodle_password();
        obj.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrl.getBytes()));
        List datosAsignaciones = datosAsignacion;
        modelo.addAttribute("datosAsignacion", datosAsignaciones);
        modelo.addAttribute("id_rol", Integer.toString(iId_rol));
        modelo.addAttribute("usermoodle", obj);
        return "administrarCursos/ListarAsignaciones";
    }

    @RequestMapping(value = "/MatricularCursoDocente.fautapo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageResult> MatricularCursoDocente(@ModelAttribute("model") UserCourseMoodleModel model) throws MoodleRestUserException, JSONException, UnsupportedEncodingException, IOException {
        Clientes cliente = this.GetUsuario();
        MessageResult Respuesta = new MessageResult();
        MoodleConfiguracion config = mi.GetConfiguracionCursosMoodle();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }

        CursosMoodle cursosupdate = new CursosMoodle();

        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());

        MoodleUsers user = new MoodleUsers();
        user.setIdnumber(model.getIdnumber());
        user.setUsername(model.getUsername());
        MoodleUsers userresult = null;
        try {
            userresult = MoodleUserService.findUserByid(user).get(0);
        } catch (Exception ex) {
            Respuesta.setMessage("No se encuentra creado el usuario moodle.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }

        cursosupdate.setId_cursos_moodle(model.getIdcursomoodle());
        cursosupdate.setId_persona_moxos(Integer.parseInt(model.getIdnumber()));
        cursosupdate.setMoodle_username(model.getUsername());
        cursosupdate.setMoodle_correo(userresult.getEmail());
        cursosupdate.setMoodle_nombres_usuario(userresult.getUsername());
        cursosupdate.setMoodle_apellidos_usuario(userresult.getLastname());
        cursosupdate.setMoodle_password(userresult.getFirstname());
        cursosupdate.setId_rol_cursos_moodle(config.getMoodle_rol_teacher());

        MoodleEnroll enroll = new MoodleEnroll();
        enroll.setCourseid(model.getIdcurso());
        enroll.setRoleid(config.getMoodle_rol_teacher());
        enroll.setSuspend(0);
        enroll.setUserid(userresult.getId());

        MoodleCourse course = MoodleEnrollUsertheCourse.GetEnroll(enroll);
        if (course == null) {
            String error = MoodleEnrollUsertheCourse.create(enroll);
            if (error == null) {
                mi.UpdateUserMoodle(cursosupdate);
                cursosupdate.setId_moodle((int) userresult.getId());
                mi.MatricularMoodle(cursosupdate);
            }
        }
        Respuesta.setMessage("Se matriculo los estudiantes con exito..");
        Respuesta.setStatus("OK");
        return new ResponseEntity<>(Respuesta, HttpStatus.OK);
    }

    @RequestMapping(value = "/MatricularEstudianteMateria.fautapo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageResult> MatricularEstudianteMateria(@ModelAttribute("model") UserCourseEnrollMoodleModel model) throws MoodleRestUserException, JSONException, UnsupportedEncodingException, IOException {
        Clientes cliente = this.GetUsuario();
        MessageResult Respuesta = new MessageResult();
        MoodleConfiguracion config = mi.GetConfiguracionCursosMoodle();
        if (cliente == null) {
            Respuesta.setMessage("Su sesion ha terminado. Vuelva a la pagina inicial e ingrese de nuevo.");
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }

        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());
        List<MoodleUsers> registrados = new ArrayList<>();
        List<MoodleUsers> users = Parser(model.getSeleccionados());
        for (MoodleUsers user : users) {
            MoodleUsers result = MoodleUserService.createUserByUserNameID(user);
            registrados.add(result);
        }
        for (MoodleUsers user : registrados) {
            RegistrarSistemaAcademico(user, config, model.getIdcurso(), model.getMateria(), model.getGestion(), model.getPeriodo(), model.getId_programa(), model.getId_grupo(), model.getId_materia(), cliente);
        }
        Respuesta.setMessage("Se matriculo los estudiantes con exito..");
        Respuesta.setStatus("OK");
        return new ResponseEntity<>(Respuesta, HttpStatus.OK);
    }

    private void RegistrarSistemaAcademico(MoodleUsers user, MoodleConfiguracion config, int idcurso, String materia, int gestion, int periodo, int id_programa, int id_grupo, int id_materia, Clientes cliente) throws IOException, UnsupportedEncodingException, MalformedURLException, JSONException, MoodleRestUserException {
        MoodleEnroll enroll = new MoodleEnroll();
        MoodleCourse course = null;
        if (!user.isRegistrado_moodle()) {
            enroll.setCourseid(idcurso);
            enroll.setRoleid(config.getMoodle_rol_student());
            enroll.setSuspend(0);
            enroll.setUserid(user.getId());
            MoodleEnrollUsertheCourse.create(enroll);
        }
        if (!user.isRegistrado_academico()) {
            CursosMoodle cursomoodle = new CursosMoodle();
            cursomoodle.setId_moodle((int) user.getId());
            cursomoodle.setMoodle_username(user.getUsername());
            cursomoodle.setMoodle_password(user.getPassword());
            cursomoodle.setMoodle_correo(user.getEmail());
            cursomoodle.setMoodle_nombres_usuario(user.getFirstname());
            cursomoodle.setMoodle_apellidos_usuario(user.getLastname());
            cursomoodle.setMoodle_detallecurso(materia);
            cursomoodle.setId_moodle_cursos(String.valueOf(idcurso));
            cursomoodle.setId_rol_cursos_moodle(2);
            cursomoodle.setId_persona_moxos(Integer.parseInt(user.getIdnumber()));
            cursomoodle.setId_programa(id_programa);
            cursomoodle.setGestion(gestion);
            cursomoodle.setPeriodo(periodo);
            cursomoodle.setId_usuario_moxos_ru_doc(Integer.parseInt(user.getUsername()));
            cursomoodle.setTipo_usuario(1);
            cursomoodle.setId_grupo(id_grupo);
            cursomoodle.setId_materia(id_materia);
            cursomoodle.setId_estado("A");
            cursomoodle.setId_rol(0);
            cursomoodle.setUlt_usuario(cliente.getId_usuario());
            mi.RegistrarCursosMoodleEstudiante(cursomoodle);
        } else {
            ParametrosBusqueda parametros = new ParametrosBusqueda();
            parametros.setId_persona(Integer.parseInt(user.getIdnumber()));
            parametros.setId_estudiante(Integer.parseInt(user.getUsername()));
            parametros.setGestion(gestion);
            parametros.setPeriodo(periodo);
            parametros.setId_moodle_cursos(String.valueOf(idcurso));
            CursosMoodle cursosupdate = mi.GetCursoMoodleEstudiante(parametros);
            int idnumber = Integer.parseInt(user.getIdnumber());
            cursosupdate.setId_persona_moxos(idnumber);
            cursosupdate.setMoodle_username(user.getUsername());
            cursosupdate.setMoodle_correo(user.getEmail());
            cursosupdate.setMoodle_nombres_usuario(user.getFirstname());
            cursosupdate.setMoodle_apellidos_usuario(user.getLastname());
            cursosupdate.setMoodle_password(user.getPassword());
            cursosupdate.setId_rol_cursos_moodle(config.getMoodle_rol_student());
            mi.UpdateUserMoodle(cursosupdate);
            cursosupdate.setId_moodle((int) user.getId());
            mi.MatricularMoodle(cursosupdate);
        }
    }

    private List<MoodleUsers> Parser(String usuarios) throws JSONException {
        List<MoodleUsers> users = new ArrayList<>();
        JSONArray arr = new JSONArray(usuarios);
        if (arr.length() != 0) {
            for (int i = 0; i < arr.length(); i++) {
                JSONObject json = arr.getJSONObject(i);
                MoodleUsers user = new MoodleUsers();
                user.setEmail(json.getString("usuario") + "@uabjb.edu.bo");
                user.setIdnumber(json.getString("id_persona"));
                user.setId(0);
                user.setUsername(json.getString("ru"));
                user.setPassword(json.getString("usuario"));
                String[] nombres = json.getString("nombres").split("!");
                user.setFirstname(nombres[0]);
                user.setLastname(nombres[1]);
                user.setRegistrado_academico(json.getInt("registrado") == 1 ? true : false);
                user.setRegistrado_moodle(json.getInt("registradomoodle") == 1 ? true : false);
                users.add(user);
            }
        }
        return users;
    }

    @RequestMapping(value = "/GuardarCambios.fautapo", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageUserResult> GuardarCambios(@ModelAttribute("model") @Validated UserMoodleModel model, BindingResult result) throws MoodleRestUserException, JSONException, UnsupportedEncodingException, IOException {
        MessageUserResult Respuesta = new MessageUserResult();
        MoodleConfiguracion config = mi.GetConfiguracionCursosMoodle();
        MoodleUsers user = new MoodleUsers();
        user.setEmail(model.getEmail());
        user.setPassword(model.getPassword());
        user.setFirstname(model.getFirstname());
        user.setLastname(model.getLastname());
        user.setIdnumber(model.getIdnumber());
        user.setUsername(model.getUsuario());
        user.setId(model.getId());

        if (result.hasErrors()) {
            String errores = "";
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                errores += error.getDefaultMessage() + " ";
            }
            Respuesta.setMessage(errores);
            Respuesta.setStatus("Error");
            return new ResponseEntity<>(Respuesta, HttpStatus.OK);
        }

        MoodleUserService.UpdatePassword(user);
        CursosMoodle cursosupdate = new CursosMoodle();
        cursosupdate.setId_persona_moxos(Integer.parseInt(user.getIdnumber()));
        cursosupdate.setMoodle_username(user.getUsername());
        cursosupdate.setMoodle_correo(user.getEmail());
        cursosupdate.setMoodle_nombres_usuario(user.getFirstname());
        cursosupdate.setMoodle_apellidos_usuario(user.getLastname());
        cursosupdate.setMoodle_password(user.getPassword());
        cursosupdate.setId_rol_cursos_moodle(config.getMoodle_rol_student());
        mi.UpdateUserMoodle(cursosupdate);
        mi.ActualizarUsuariosMoodle(cursosupdate);
        cursosupdate.setId_moodle((int) user.getId());
        mi.MatricularMoodle(cursosupdate);
        Respuesta.setMessage("");
        Respuesta.setStatus("OK");
        Respuesta.setIndice(model.getIndice().toString());
        Respuesta.setPassword(model.getPassword());
        return new ResponseEntity<>(Respuesta, HttpStatus.OK);
    }
}
