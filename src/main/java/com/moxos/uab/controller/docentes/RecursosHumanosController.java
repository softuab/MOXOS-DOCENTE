package com.moxos.uab.controller.docentes;

import com.moxos.uab.model.models.rrhh.BuscarPlanillasRequest;
import com.moxos.uab.model.service.rrhh.BoletaAguinaldoService;
import com.moxos.uab.model.service.rrhh.BoletaService;
import com.moxos.uab.model.service.rrhh.models.*;
import com.moxos.uab.mybatis.entity.Clientes;
import com.moxos.uab.mybatis.logic.MiFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class RecursosHumanosController {
    @Autowired
    private MiFacade mi;
    @Autowired
    private HttpServletRequest request;
    private BoletaService service;
    private BoletaAguinaldoService serviceaguinaldo;

    private Clientes getUsuario() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Clientes cliente = (Clientes) attr.getRequest().getSession().getAttribute("__sess_cliente");
        return cliente;
    }

    @RequestMapping("/rrhh/EntradaBoleta")
    public String Entrada(Model modelo) {
        String correo = this.getUsuario().getCorreo();
        service = new BoletaService("http://192.168.100.150/ws");
        PlanillaAdministrativoModel model = service.getInfoFuncionario("D", correo);
        if (model == null) {
            modelo.addAttribute("mensaje", "No se encuentra habilitado en planilla comunicarse con Recursos Humanos");
            return "RecursosHumanos/Boletas/Error";
        }
        modelo.addAttribute("model", model);
        modelo.addAttribute("usuario", this.getUsuario().getNombres());
        modelo.addAttribute("simagen", this.getUsuario().getImagen());
        return "RecursosHumanos/Boletas/Entrada";
    }

    @RequestMapping("/rrhh/ListarPlanillas")
    @ResponseBody
    public List<ItemViewModel> ListarPlanillas(@ModelAttribute("model") BuscarPlanillasRequest model) {
        service = new BoletaService("http://192.168.100.150/ws");
        List<ItemViewModel> planillas = service.getPlanillas(model.getIdMes(), model.getIDGestion());
        return planillas;
    }

    @RequestMapping("/rrhh/ListarFuncionesBoleta")
    public String getListaFuncionesMes(@ModelAttribute("model") PlanillaDocenteModel model, Model modelo) {
        service = new BoletaService("http://192.168.100.150/ws");
        List<PlanillaBoletaModel> detalle = service.getListaFuncionesMes(model);
        modelo.addAttribute("model", detalle);
        return "RecursosHumanos/Boletas/ListaFunciones";
    }

    @RequestMapping("/rrhh/Descargar")
    public void Descargar(@ModelAttribute("id") Long id, HttpServletResponse response) {
        service = new BoletaService("http://192.168.100.150/ws");
        service.getBoleta(response, id);
    }

    @RequestMapping("/rrhh/aguinaldo/EntradaBoleta")
    public String entradaAguinaldo(Model modelo) {
        String correo = this.getUsuario().getCorreo();
        serviceaguinaldo = new BoletaAguinaldoService("http://192.168.100.150/ws");
        PlanillaAdministrativoAguinaldoModel model = serviceaguinaldo.getInfoFuncionario("D", correo);
        if (model == null) {
            modelo.addAttribute("mensaje", "No se encuentra habilitado en planilla comunicarse con Recursos Humanos");
            return "RecursosHumanos/BoletaAguinaldo/Error";
        }
        modelo.addAttribute("model", model);
        modelo.addAttribute("usuario", this.getUsuario().getNombres());
        modelo.addAttribute("simagen", this.getUsuario().getImagen());
        return "RecursosHumanos/BoletaAguinaldo/Entrada";
    }

    @RequestMapping("/rrhh/aguinaldo/ListarFunciones")
    public String getListaFuncionesMes(@ModelAttribute("model") PlanillaDocenteAguinaldoModel model, Model modelo) {
        serviceaguinaldo = new BoletaAguinaldoService("http://192.168.100.150/ws");
        List<PlanillaBoletaAguinaldoModel> detalle = serviceaguinaldo.getListaFuncionesMes(model);
        modelo.addAttribute("model", detalle);
        return "RecursosHumanos/BoletaAguinaldo/ListaFunciones";
    }

    @RequestMapping("/rrhh/aguinaldo/Descargar")
    public void descargarAguinaldo(@ModelAttribute("id") Long id, HttpServletResponse response) {
        serviceaguinaldo = new BoletaAguinaldoService("http://192.168.100.150/ws");
        serviceaguinaldo.getBoleta(response, id);
    }

    @ExceptionHandler(value = Exception.class)
    public String handleException(Model modelo, Exception e) {
        modelo.addAttribute("mensaje", e.getMessage());
        return "Error";
    }
}
