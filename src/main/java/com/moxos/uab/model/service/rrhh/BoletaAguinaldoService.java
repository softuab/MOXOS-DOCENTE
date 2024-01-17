package com.moxos.uab.model.service.rrhh;

import com.moxos.uab.model.service.rrhh.models.PlanillaAdministrativoAguinaldoModel;
import com.moxos.uab.model.service.rrhh.models.PlanillaBoletaAguinaldoModel;
import com.moxos.uab.model.service.rrhh.models.PlanillaDocenteAguinaldoModel;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoletaAguinaldoService {

    private RestTemplate restTemplate;
    private String servidor = "";

    public BoletaAguinaldoService(String servidor) {
        this.restTemplate = restTemplate;
        this.servidor = servidor;
    }

    public PlanillaAdministrativoAguinaldoModel getInfoFuncionario(String tipo, String username) {
        restTemplate = new RestTemplate();
        String uri = servidor + WsRecursosHumanos.INFORMACION_AGUINALDO.getApi() + "?tipo=" + tipo + "&username=" + username;
        PlanillaAdministrativoAguinaldoModel model = null;
        try {
            model = restTemplate.getForObject(uri, PlanillaAdministrativoAguinaldoModel.class);
        } catch (Exception ex) {
            Logger.getLogger(BoletaService.class.getName()).log(Level.SEVERE, null, ex);
            model = null;
        }
        return model;
    }

    public List<PlanillaBoletaAguinaldoModel> getListaFuncionesMes(PlanillaDocenteAguinaldoModel model) {
        restTemplate = new RestTemplate();
        String uri = servidor + WsRecursosHumanos.LISTAR_FUNCIONES_AGUINALDO.getApi() + "?Tipo=" + model.getTipo() + "&IDGestion=" + model.getiDGestion() + "&IDAguinaldo=" + model.getiDAguinaldo() + "&Carnet=" + model.getCarnet();
        List<PlanillaBoletaAguinaldoModel> detalle = restTemplate.getForObject(uri, List.class);
        return detalle;
    }

    public void getBoleta(HttpServletResponse response, Long id) {
        restTemplate = new RestTemplate();
        String uri = servidor + WsRecursosHumanos.BOLETAS_AGUINALDO.getApi() + "?id=" + id;

        restTemplate.execute(uri, HttpMethod.GET, null, responseExtractor -> {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=BoletaPagoAguinaldo.pdf");

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
