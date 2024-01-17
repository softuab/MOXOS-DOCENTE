package com.moxos.uab.controller.docentes;

import com.moxos.uab.model.jsonmodels.MessageResult;
import com.moxos.uab.model.jsonmodels.MessageResultRest;
import com.moxos.uab.model.jsonmodels.Moodle.MoodleUserJsonRest;
import com.moxos.uab.model.models.Moodle.ParametrosMoodleRequest;
import com.moxos.uab.model.models.Moodle.UserCourseEnrollMoodleModel;
import com.moxos.uab.model.models.Moodle.UserCourseMoodleModel;
import com.moxos.uab.model.models.Moodle.UserMoodleRequest;
import com.moxos.uab.model.models.utility.ParametroEntradaRequest;
import com.moxos.uab.model.service.moodle.*;
import com.moxos.uab.model.service.moodle.models.AddUserMoodleModel;
import com.moxos.uab.mybatis.entity.*;
import com.moxos.uab.mybatis.logic.MiFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CursosMoodleController {
    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/EntradaAdministrarCursos")
    public String entrada(Model modelo) {
        Clientes cliente = this.getUsuario();
        ParametroEntradaRequest model = new ParametroEntradaRequest();
        modelo.addAttribute("simagen", cliente.getImagen());
        modelo.addAttribute("usuario", cliente.getNombres());
        modelo.addAttribute("gestion", Integer.toString(cliente.getGestion()));
        modelo.addAttribute("periodo", Integer.toString(cliente.getPeriodo()));
        modelo.addAttribute("model", model);
        return "Moodle/EntradaAdministrarCursos";
    }

    @RequestMapping(value = "/regresarListarAsignacionesAdministrarCursos", method = RequestMethod.POST)
    public String regresarListarAsignacionesAdministrarCursos(@ModelAttribute("model") ParametroEntradaRequest model, Model modelo) throws MoodleRestUserException, IOException {
        Clientes cliente = this.getUsuario();
        Personas persona = mi.getPrsBuscarPersonaDocente(cliente.getId_usuario());
        modelo.addAttribute("aux", "");
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("avanzado", "");

        //Sacamos la asignacion del docente
        Asignaciones asignacion = new Asignaciones();
        asignacion.setId_docente(cliente.getId_usuario());
        asignacion.setGestion(model.getGestion());
        asignacion.setPeriodo(model.getPeriodo());
        List<Asignaciones> datosAsignacion = this.mi.getDctListarAsignacionDocente(asignacion);
        MoodleConfiguracion config = mi.getConfiguracionCursosMoodle();
        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());
        MoodleUsers user = new MoodleUsers();
        user.setIdnumber(String.valueOf(persona.getId_persona()));
        user.setUsername(String.valueOf(persona.getCorreo().toLowerCase()));
        List<MoodleUsers> userresult = MoodleUserService.findUserByid(user);
        CursosMoodle obj = mi.getUsuarioMoodle(persona.getId_persona());
        obj.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(obj.getMoodle_password().getBytes()));
        List datosAsignaciones = datosAsignacion;
        modelo.addAttribute("datosAsignacion", datosAsignaciones);
        modelo.addAttribute("id_rol", Integer.toString(cliente.getId_rol()));
        modelo.addAttribute("usermoodle", obj);
        return "Moodle/ListarAsignaciones";

    }

    @RequestMapping(value = "/ListarAsignacionesAdministrarCursos", method = RequestMethod.POST)
    public String ListarAsignacionesAdministrarCursos(@ModelAttribute("model") @Validated ParametroEntradaRequest model, BindingResult result, Model modelo) throws MoodleRestUserException, IOException {
        Clientes cliente = this.getUsuario();
        if (result.hasErrors()) {
            modelo.addAttribute("simagen", cliente.getImagen());
            modelo.addAttribute("usuario", cliente.getNombres());
            modelo.addAttribute("gestion", Integer.toString(cliente.getGestion()));
            modelo.addAttribute("periodo", Integer.toString(cliente.getPeriodo()));
            modelo.addAttribute("model", model);
            return "Moodle/EntradaAdministrarCursos";
        }
        Personas persona = mi.getPrsBuscarPersonaDocente(cliente.getId_usuario());

        //obtiene la imgen de perfil
        modelo.addAttribute("aux", "");
        modelo.addAttribute("gestion", model.getGestion());
        modelo.addAttribute("periodo", model.getPeriodo());
        modelo.addAttribute("nombres", cliente.getNombres());
        modelo.addAttribute("avanzado", "");

        //Sacamos la asignacion del docente
        Asignaciones asignacion = new Asignaciones();
        asignacion.setId_docente(cliente.getId_usuario());
        asignacion.setGestion(model.getGestion());
        asignacion.setPeriodo(model.getPeriodo());
        List<Asignaciones> datosAsignacion = this.mi.getDctListarAsignacionDocente(asignacion);
        MoodleConfiguracion config = mi.getConfiguracionCursosMoodle();
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
            CursosMoodle obj = mi.getUsuarioMoodle(persona.getId_persona());
            if (obj == null) {
                CursosMoodle moodle = new CursosMoodle();
                moodle.setId_persona_moxos(Integer.parseInt(userresult.get(0).getIdnumber()));
                moodle.setMoodle_username(userresult.get(0).getUsername());
                moodle.setMoodle_password("null");
                moodle.setMoodle_correo(userresult.get(0).getEmail());
                moodle.setMoodle_nombres_usuario(cliente.getNombres());
                moodle.setUlt_usuario(cliente.getId_usuario());
                mi.registrarUsuariosMoodle(moodle);
                obj = mi.getUsuarioMoodle(persona.getId_persona());
            }
            String originalUrl = obj.getMoodle_password();
            obj.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrl.getBytes()));
            List datosAsignaciones = datosAsignacion;
            modelo.addAttribute("datosAsignacion", datosAsignaciones);
            modelo.addAttribute("id_rol", Integer.toString(cliente.getId_rol()));
            modelo.addAttribute("usermoodle", obj);
            return "Moodle/ListarAsignaciones";
        } else {
            modelo.addAttribute("id_rol", Integer.toString(cliente.getId_rol()));
            AddUserMoodleModel crearusuario = new AddUserMoodleModel();
            crearusuario.setApellidos(persona.getPaterno() + " " + persona.getMaterno());
            crearusuario.setAux("");
            crearusuario.setAvanzado("");
            crearusuario.setClave("");
            crearusuario.setCorreo(getUsuario().getCorreo().trim().toLowerCase());
            crearusuario.setNombres(cliente.getNombres());
            crearusuario.setGestion(model.getGestion());
            crearusuario.setIdnumber(persona.getId_persona());
            crearusuario.setPeriodo(model.getPeriodo());
            crearusuario.setNombre(persona.getNombres());
            crearusuario.setUsuario(getUsuario().getCorreo().trim().toLowerCase());
            modelo.addAttribute("model", crearusuario);
            return "Moodle/RegistrarMoodle";
        }
    }

    @RequestMapping(value = "/modificarUsuario", method = RequestMethod.GET)
    public String modificarUsuario(Model modelo) throws MoodleRestUserException, IOException {
        MoodleConfiguracion config = mi.getConfiguracionCursosMoodle();
        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());
        String idnumber = request.getParameter("id_persona");
        String indice = request.getParameter("indice");
        UserMoodleRequest model = new UserMoodleRequest();
        MoodleUsers user = new MoodleUsers();
        user = MoodleUserService.Get(idnumber);
        model.setUsername(user.getUsername());
        model.setIdnumber(user.getIdnumber());
        model.setFirstname(user.getFirstname());
        model.setLastname(user.getLastname());
        model.setEmail(user.getEmail());
        model.setId(user.getId());
        model.setIndice(Integer.valueOf(indice));
        modelo.addAttribute("model", model);
        return "Moodle/ModificarUsuario";
    }

    @RequestMapping(value = "/guardarCambios", method = RequestMethod.POST)
    public String guardarCambios(@ModelAttribute("model") @Validated UserMoodleRequest model, BindingResult result, Model modelo) throws MoodleRestUserException, IOException {
        if (result.hasErrors()) {
            modelo.addAttribute("model", model);
            return "Moodle/ModificarUsuario";
        }
        MoodleConfiguracion config = mi.getConfiguracionCursosMoodle();
        MoodleUsers user = new MoodleUsers();
        user.setEmail(model.getEmail());
        user.setPassword(model.getPassword());
        user.setFirstname(model.getFirstname());
        user.setLastname(model.getLastname());
        user.setIdnumber(model.getIdnumber());
        user.setUsername(model.getUsername());
        user.setId(model.getId());

        MoodleUserService.UpdatePassword(user);
        CursosMoodle cursosupdate = new CursosMoodle();
        cursosupdate.setId_persona_moxos(Integer.parseInt(user.getIdnumber()));
        cursosupdate.setMoodle_username(user.getUsername());
        cursosupdate.setMoodle_correo(user.getEmail());
        cursosupdate.setMoodle_nombres_usuario(user.getFirstname());
        cursosupdate.setMoodle_apellidos_usuario(user.getLastname());
        cursosupdate.setMoodle_password(user.getPassword());
        cursosupdate.setId_rol_cursos_moodle(config.getMoodle_rol_student());

        mi.updateUserMoodle(cursosupdate);
        mi.actualizarUsuariosMoodle(cursosupdate);
        cursosupdate.setId_moodle((int) user.getId());
        mi.matricularMoodle(cursosupdate);
        String originalUrl = user.getPassword();
        modelo.addAttribute("mensaje", "se actualizo correctamente la constraseña");
        modelo.addAttribute("tipomensaje", "info");
        modelo.addAttribute("pass", Base64.getUrlEncoder().encodeToString(originalUrl.getBytes()));
        return "Moodle/Aviso";
    }

    @RequestMapping(value = "/ListarCursosMoodle", method = RequestMethod.POST)
    public String ListarCursosMoodle(@ModelAttribute("model") ParametrosMoodleRequest model, Model modelo) throws MoodleRestUserException, IOException {
        List<CursosMoodle> listacursosmoodle = new ArrayList<>();
        Personas persona = mi.getPrsBuscarPersonaDocente(model.getId_docente());
        List<CursosMoodle> cursosmoodle = buscarCursos(model);

        modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("parametro", model);
        modelo.addAttribute("persona", persona);

        CursosMoodle obj = mi.getUsuarioMoodle(persona.getId_persona());
        String originalUrl = obj.getMoodle_password();
        obj.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrl.getBytes()));
        modelo.addAttribute("usermoodle", obj);
        if (cursosmoodle.isEmpty()) {
            modelo.addAttribute("mensajeerror", "No existe cursos creados para mostrar");
            return "Moodle/ListarCursosMoodle";
        } else {
            MoodleUsers userresult = null;
            MoodleUsers user = new MoodleUsers();
            user.setIdnumber(String.valueOf(persona.getId_persona()));
            userresult = MoodleUserService.find(user).get(0);
            listacursosmoodle = getListarCursos(cursosmoodle, userresult.getId());
            modelo.addAttribute("cursosmoodle", listacursosmoodle);
            return "Moodle/ListarCursosMoodle";
        }
    }

    @RequestMapping(value = "/crearCurso", method = RequestMethod.POST)
    public String crearCurso(@ModelAttribute("model") ParametrosMoodleRequest model, Model modelo) throws MoodleRestUserException, IOException {
        MoodleConfiguracion config = mi.getConfiguracionCursosMoodle();

        List<CursosMoodle> listacursosmoodle = new ArrayList<CursosMoodle>();
        Personas persona = mi.getPrsBuscarPersonaDocente(model.getId_docente());
        MoodleGroup group = MoodleGroupService.GetGroup(String.valueOf(model.getId_programa()));
        String nombreCompletoMateria = model.getSigla() + " - " + model.getMateria() + "  " + String.valueOf(model.getPeriodo()) + "/" + String.valueOf(model.getGestion()) + " PARALELO " + model.getGrupo();
        String nombreCortoMateria = String.valueOf(model.getId_materia()) + "-" + model.getSigla() + String.valueOf(model.getPeriodo()) + "/" + String.valueOf(model.getGestion()) + " PARALELO " + model.getGrupo();
        String idNumber = String.valueOf(model.getId_materia()) + "-" + String.valueOf(model.getPeriodo()) + "/" + String.valueOf(model.getGestion()) + "-" + model.getGrupo();

        //Instanciar objeto MoodleCourse para crear el curso
        MoodleCourse curso = new MoodleCourse();
        curso.setCategoryid(group.getId());
        curso.setFullname(nombreCompletoMateria);
        curso.setShortname(nombreCortoMateria);
        curso.setIdnumber(idNumber);
        curso.setVisible(1);
        MoodleCourse cursocreado = MoodleCourseService.createCourse(curso);

        //Instanciar objeto MoodleCourse para crear las secciones del curso
        MoodleSectionService section = new MoodleSectionService("http://192.168.100.150/moodle");
        MessageResultRest result = section.setSection((int) cursocreado.getId());

        //Instanciar objeto MoodleCourse para registrar en la base de datos
        CursosMoodle cursomoodle = new CursosMoodle();
        cursomoodle.setId_moodle(-1);
        cursomoodle.setMoodle_username("");
        cursomoodle.setMoodle_password("");
        cursomoodle.setMoodle_correo("");
        cursomoodle.setMoodle_nombres_usuario("");
        cursomoodle.setMoodle_apellidos_usuario("");
        cursomoodle.setMoodle_detallecurso(nombreCompletoMateria);
        cursomoodle.setId_moodle_cursos(String.valueOf(cursocreado.getId()));
        cursomoodle.setId_rol_cursos_moodle(2);
        cursomoodle.setId_persona_moxos(persona.getId_persona());
        cursomoodle.setId_programa(model.getId_programa());
        cursomoodle.setGestion(model.getGestion());
        cursomoodle.setPeriodo(model.getPeriodo());
        cursomoodle.setId_usuario_moxos_ru_doc(model.getId_docente());
        cursomoodle.setTipo_usuario(2);
        cursomoodle.setId_grupo(model.getId_grupo());
        cursomoodle.setId_materia(model.getId_materia());
        cursomoodle.setId_estado("A");
        cursomoodle.setId_rol(this.getUsuario().getId_rol());
        cursomoodle.setUlt_usuario(this.getUsuario().getId_usuario());
        mi.registrarCursosMoodleDocente(cursomoodle);

        List<CursosMoodle> cursosmoodle = buscarCursos(model);
        CursosMoodle usuarioMoodle = mi.getUsuarioMoodle(persona.getId_persona());
        String originalUrlusuarioMoodle = usuarioMoodle.getMoodle_password();
        usuarioMoodle.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrlusuarioMoodle.getBytes()));
        modelo.addAttribute("usermoodle", usuarioMoodle);

        modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("parametro", model);
        modelo.addAttribute("persona", persona);

        MoodleUsers userresult = null;
        MoodleUsers user = new MoodleUsers();
        user.setIdnumber(String.valueOf(persona.getId_persona()));
        userresult = MoodleUserService.find(user).get(0);
        if (cursosmoodle.isEmpty()) {
            modelo.addAttribute("mensajeerror", "No existe cursos creados para mostrar");
            return "Moodle/ListarCursosMoodle";
        } else {
            listacursosmoodle = getListarCursos(cursosmoodle, userresult.getId());
            modelo.addAttribute("cursosmoodle", listacursosmoodle);
            return "Moodle/ListarCursosMoodle";
        }
    }

    private List<CursosMoodle> buscarCursos(ParametrosMoodleRequest model) {
        ParametrosBusqueda busqueda = new ParametrosBusqueda();
        busqueda.setGestion(model.getGestion());
        busqueda.setId_docente(model.getId_docente());
        busqueda.setId_grupo(model.getId_grupo());
        busqueda.setId_materia(model.getId_materia());
        busqueda.setId_programa(model.getId_programa());
        busqueda.setPeriodo(model.getPeriodo());
        return mi.getListarCursosMoodleDocente(busqueda);
    }

    private List<CursosMoodle> getListarCursos(List<CursosMoodle> cursosmoodle, Long idUser) throws MoodleRestUserException, IOException {
        List<CursosMoodle> listacursosmoodle = new ArrayList<CursosMoodle>();
        for (CursosMoodle c : cursosmoodle) {
            CursosMoodle obj = c;
            String originalUrl = c.getMoodle_password();
            obj.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrl.getBytes()));
            MoodleEnroll enroll = new MoodleEnroll();
            enroll.setCourseid(Integer.parseInt(c.getId_moodle_cursos()));
            enroll.setUserid(idUser);
            MoodleCourse course = MoodleEnrollUsertheCourse.GetEnroll(enroll);
            if (course == null) {
                obj.setMatricular(Boolean.TRUE);
            } else {
                obj.setMatricular(Boolean.FALSE);
            }
            listacursosmoodle.add(obj);
        }
        return listacursosmoodle;
    }

    @RequestMapping(value = "/matricularCursoDocente", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public MessageResult matricularCursoDocente(@RequestBody UserCourseMoodleModel model) throws MoodleRestUserException, IOException {
        MessageResult Respuesta = new MessageResult();
        MoodleConfiguracion config = mi.getConfiguracionCursosMoodle();

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
            return Respuesta;
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
                mi.updateUserMoodle(cursosupdate);
                cursosupdate.setId_moodle((int) userresult.getId());
                mi.matricularMoodle(cursosupdate);
            }
        }
        Respuesta.setMessage("Se matriculo los estudiantes con exito..");
        Respuesta.setStatus("OK");
        return Respuesta;
    }

    @RequestMapping(value = "/administrarCursosListarAlumnosProgramados", method = RequestMethod.POST)
    public String administrarCursosListarAlumnosProgramados(@ModelAttribute("model") ParametrosMoodleRequest model, Model modelo) throws MoodleRestUserException, IOException {
        MoodleConfiguracion config = mi.getConfiguracionCursosMoodle();
        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());
        List<EstudianteProgramado> estudiantes = new ArrayList<EstudianteProgramado>();
        CursosMoodle cursos;

        //Busqueda de la materia en el moodle del curso creado
        try {
            cursos = buscarCursos(model).stream().findFirst().get();
        } catch (Exception ex) {
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("parametros", model);
            modelo.addAttribute("mensaje", "No creo ningun curso para matricular estudiantes");
            return "Moodle/Error";
        }
        List<CursosMoodle> estudiantesmatriculadosacademico = getListaMatriculadosEstudiantes(cursos.getId_moodle_cursos(), this.getUsuario().getId_usuario(), model);
        //Buscamos la asignacion docente
        Asignaciones buscarAsignacion = new Asignaciones();
        buscarAsignacion.setId_asignacion(model.getId_asignacion());
        Asignaciones datosAsignacion = this.mi.getDctBuscarAsignacionDocente(buscarAsignacion);
        modelo.addAttribute("datosAsignacion", datosAsignacion);
        if (datosAsignacion == null) {
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("parametros", model);
            modelo.addAttribute("mensaje", "No se encontr&oacute; la asignaci&oacute;n docente para la materia");
            return "Moodle/Error";
        }
        String sId_tipo_evaluacion = Integer.toString(datosAsignacion.getId_tipo_evaluacion());


        //Sacamos datos de la materia
        Materias datosMateria = new Materias();
        datosMateria.setId_materia(datosAsignacion.getId_materia());
        Materias buscarMateria = this.mi.getMtrBuscarMateria(datosMateria);

        modelo.addAttribute("idcursomoodle", cursos.getId_cursos_moodle());
        modelo.addAttribute("idcurso", cursos.getId_moodle_cursos());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("id_docente", this.getUsuario().getId_usuario());
        modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
        model.setPrograma(cursos.getPrograma());
        model.setSigla(buscarMateria.getSigla());
        model.setMateria(cursos.getMoodle_detallecurso());
        modelo.addAttribute("parametros", model);

        //Verificamos si el departamento de la materia coincide con la asignacion
        if (datosAsignacion.getId_departamento() != buscarMateria.getId_departamento()) {
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("parametros", model);
            modelo.addAttribute("mensaje", "Alerta!!. El departamento de la materia no coincide con la asignaci&oacute;n del docente. Consulte al administrador del sistema.");
            return "Moodle/Error";
        }

        //Verificamos si es una materia con modelo ahorro
        //Buscamos la materia modelo_ahorro
        Asignaciones datos = new Asignaciones();
        if (datosAsignacion.getId_modelo_ahorro() > 0) {
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
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("parametros", model);
            modelo.addAttribute("mensaje", "No existen mas fases.");
            return "Moodle/Error";
        }
        modelo.addAttribute("fase", datosFase.getFase());
        //Sacando la lista de estudiantes programados a la materia, evaluacion regular
        if (("1".equals(model.getId_tipo_grado())) && (!"2".equals(sId_tipo_evaluacion))) {
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
                if (!usuarioinscritos.stream().anyMatch(p -> p.getIdnumber().equals(String.valueOf(est.getId_persona())))) {
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
        return "Moodle/ListarEstudiantesProgramados";
    }

    private List<CursosMoodle> getListaMatriculadosEstudiantes(String id, int idDocente, ParametrosMoodleRequest model) {
        ParametrosBusqueda parametros = new ParametrosBusqueda();
        parametros.setId_docente(idDocente);
        parametros.setGestion(model.getGestion());
        parametros.setPeriodo(model.getPeriodo());
        parametros.setId_grupo(model.getId_grupo());
        parametros.setId_materia(model.getId_materia());
        parametros.setId_programa(model.getId_programa());
        parametros.setId_moodle_cursos(id);
        return mi.getListarCursosMoodleEstudiantePorCurso(parametros);
    }

    @RequestMapping(value = "/matricularEstudianteMateria", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public MessageResult matricularEstudianteMateria(@RequestBody UserCourseEnrollMoodleModel model) throws MoodleRestUserException, IOException {
        MessageResult Respuesta = new MessageResult();
        MoodleConfiguracion config = mi.getConfiguracionCursosMoodle();

        MoodleWebServiceAuth.init(config.getMoodle_host(), config.getMoodle_key());
        List<MoodleUsers> registrados = new ArrayList<>();
        List<MoodleUsers> users = Parser(model.getSeleccionados());
        for (MoodleUsers user : users) {
            MoodleUsers result = MoodleUserService.createUserByUserNameID(user);
            registrados.add(result);
        }
        for (MoodleUsers user : registrados) {
            registrarSistemaAcademico(user, config, model);
        }
        Respuesta.setMessage("Se matriculo los estudiantes con exito..");
        Respuesta.setStatus("OK");
        return Respuesta;
    }

    private void registrarSistemaAcademico(MoodleUsers user, MoodleConfiguracion config, UserCourseEnrollMoodleModel model) throws IOException, MoodleRestUserException {
        MoodleEnroll enroll = new MoodleEnroll();
        MoodleCourse course = null;
        if (!user.isRegistrado_moodle()) {
            enroll.setCourseid(model.getIdcurso());
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
            cursomoodle.setMoodle_detallecurso(model.getMateria());
            cursomoodle.setId_moodle_cursos(String.valueOf(model.getIdcurso()));
            cursomoodle.setId_rol_cursos_moodle(2);
            cursomoodle.setId_persona_moxos(Integer.parseInt(user.getIdnumber()));
            cursomoodle.setId_programa(model.getId_programa());
            cursomoodle.setGestion(model.getGestion());
            cursomoodle.setPeriodo(model.getPeriodo());
            cursomoodle.setId_usuario_moxos_ru_doc(Integer.parseInt(user.getUsername()));
            cursomoodle.setTipo_usuario(1);
            cursomoodle.setId_grupo(model.getId_grupo());
            cursomoodle.setId_materia(model.getId_materia());
            cursomoodle.setId_estado("A");
            cursomoodle.setId_rol(0);
            cursomoodle.setUlt_usuario(this.getUsuario().getId_usuario());
            mi.registrarCursosMoodleEstudiante(cursomoodle);
        } else {
            ParametrosBusqueda parametros = new ParametrosBusqueda();
            parametros.setId_persona(Integer.parseInt(user.getIdnumber()));
            parametros.setId_estudiante(Integer.parseInt(user.getUsername()));
            parametros.setGestion(model.getGestion());
            parametros.setPeriodo(model.getPeriodo());
            parametros.setId_moodle_cursos(String.valueOf(model.getIdcurso()));
            CursosMoodle cursosupdate = mi.getCursoMoodleEstudiante(parametros);
            int idnumber = Integer.parseInt(user.getIdnumber());
            cursosupdate.setId_persona_moxos(idnumber);
            cursosupdate.setMoodle_username(user.getUsername());
            cursosupdate.setMoodle_correo(user.getEmail());
            cursosupdate.setMoodle_nombres_usuario(user.getFirstname());
            cursosupdate.setMoodle_apellidos_usuario(user.getLastname());
            cursosupdate.setMoodle_password(user.getPassword());
            cursosupdate.setId_rol_cursos_moodle(config.getMoodle_rol_student());
            mi.updateUserMoodle(cursosupdate);
            cursosupdate.setId_moodle((int) user.getId());
            mi.matricularMoodle(cursosupdate);
        }
    }

    private List<MoodleUsers> Parser(MoodleUserJsonRest[] seleccionados) {
        List<MoodleUsers> users = new ArrayList<>();
        for (MoodleUserJsonRest item : seleccionados) {
            MoodleUsers user = new MoodleUsers();
            user.setEmail(item.getEmail() + "@uabjb.edu.bo");
            user.setIdnumber(item.getIdnumber());
            user.setId(0);
            user.setUsername(item.getUsername());
            user.setPassword(item.getEmail());
            user.setFirstname(item.getFirstname());
            user.setLastname(item.getLastname());
            user.setRegistrado_academico(item.isRegistrado_academico());
            user.setRegistrado_moodle(item.isRegistrado_moodle());
            users.add(user);
        }
        return users;
    }

    @RequestMapping(value = "/listarEstudiantesMatriculados", method = RequestMethod.POST)
    public String ListarEstudiantesMatriculados(@ModelAttribute("model") ParametrosMoodleRequest model, Model modelo) {
        CursosMoodle cursos;
        model.setId_docente(this.getUsuario().getId_usuario());
        try {
            cursos = buscarCursos(model).stream().findFirst().get();
        } catch (Exception ex) {
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("parametros", model);
            modelo.addAttribute("mensaje", "No creo ningun curso para matricular estudiantes");
            return "Moodle/Error";
        }
        List<CursosMoodle> estudiantesmatriculadosacademico = getListaMatriculadosEstudiantes(cursos.getId_moodle_cursos(), this.getUsuario().getId_usuario(), model);
        model.setMateria(cursos.getMoodle_detallecurso());
        modelo.addAttribute("nombres", this.getUsuario().getNombres());
        modelo.addAttribute("parametros", model);
        modelo.addAttribute("cursosmoodle", estudiantesmatriculadosacademico);
        return "Moodle/ListarEstudiantesMatriculados";

    }

    @RequestMapping(value = "/UsuariosCursosMoodle", method = RequestMethod.POST)
    public String UsuariosCursosMoodle(@ModelAttribute("model") @Validated AddUserMoodleModel model, BindingResult result, Model modelo) throws MoodleRestUserException, IOException {
        if (result.hasErrors()) {
            modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
            modelo.addAttribute("nombres", this.getUsuario().getNombres());
            modelo.addAttribute("model", model);
            return "Moodle/RegistrarMoodle";
        }
        Personas persona = mi.getPrsBuscarPersonaDocente(this.getUsuario().getId_usuario());
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
        asignacion.setId_docente(this.getUsuario().getId_usuario());
        asignacion.setGestion(model.getGestion());
        asignacion.setPeriodo(model.getPeriodo());
        List<Asignaciones> datosAsignacion = this.mi.getDctListarAsignacionDocente(asignacion);
        MoodleConfiguracion config = mi.getConfiguracionCursosMoodle();
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
        moodle.setMoodle_nombres_usuario(this.getUsuario().getNombres());
        moodle.setUlt_usuario(this.getUsuario().getId_usuario());
        mi.registrarUsuariosMoodle(moodle);

        CursosMoodle obj = mi.getUsuarioMoodle(persona.getId_persona());
        String originalUrl = obj.getMoodle_password();
        obj.setMoodle_passwordbase64(Base64.getUrlEncoder().encodeToString(originalUrl.getBytes()));
        modelo.addAttribute("datosAsignacion", datosAsignacion);
        modelo.addAttribute("id_rol", this.getUsuario().getId_rol());
        modelo.addAttribute("usermoodle", obj);
        return "Moodle/ListarAsignaciones";
    }
}
