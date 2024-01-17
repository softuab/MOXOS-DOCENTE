package com.moxos.uab.model.service.rrhh;


import com.moxos.uab.model.service.rrhh.models.ItemViewModel;
import com.moxos.uab.model.service.rrhh.models.PlanillaAdministrativoModel;
import com.moxos.uab.model.service.rrhh.models.PlanillaBoletaModel;
import com.moxos.uab.model.service.rrhh.models.PlanillaDocenteModel;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoletaService {

    private RestTemplate restTemplate;
    private String servidor = "";

    public BoletaService(String servidor) {
        this.restTemplate = restTemplate;
        this.servidor = servidor;
    }

    public PlanillaAdministrativoModel getInfoFuncionario(String tipo, String username) {
        restTemplate = new RestTemplate();
        String uri = servidor + WsRecursosHumanos.INFORMACION.getApi() + "?tipo=" + tipo + "&username=" + username;
        PlanillaAdministrativoModel model = null;
        try {
            model = restTemplate.getForObject(uri, PlanillaAdministrativoModel.class);
        } catch (Exception ex) {
            Logger.getLogger(BoletaService.class.getName()).log(Level.SEVERE, null, ex);
            model = null;
        }
        return model;
    }

    public List<PlanillaBoletaModel> getListaFuncionesMes(PlanillaDocenteModel model) {
        restTemplate = new RestTemplate();
        String uri = servidor + WsRecursosHumanos.LISTAR_FUNCIONES.getApi() + "?Tipo=" + model.getTipo() + "&IDPlanilla=" + model.getiDPlanilla() + "&Carnet=" + model.getCarnet();
        List<PlanillaBoletaModel> detalle = restTemplate.getForObject(uri, List.class);
        return detalle;
    }

    public List<ItemViewModel> getPlanillas(String idMes, Integer idgestion) {
        restTemplate = new RestTemplate();
        String uri = servidor + WsRecursosHumanos.LISTAR_PLANILLAS.getApi() + "?idMes=" + idMes + "&idgestion=" + idgestion;
        List<ItemViewModel> detalle = restTemplate.getForObject(uri, List.class);
        return detalle;
    }

    public void getBoleta(HttpServletResponse response, Long id) {
        restTemplate = new RestTemplate();
        String uri = servidor + WsRecursosHumanos.BOLETAS.getApi() + "?id=" + id;

        restTemplate.execute(uri, HttpMethod.GET, null, responseExtractor -> {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=BoletaPago.pdf");

            try (OutputStream outputStream = response.getOutputStream();
                 InputStream inputStream = responseExtractor.getBody()) {
                int read;
                byte[] buffer = new byte[4096];
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
            } catch (IOException ex) {
                Logger.getLogger(BoletaService.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        });
    }
}

