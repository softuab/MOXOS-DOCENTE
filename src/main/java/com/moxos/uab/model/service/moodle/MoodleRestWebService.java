package com.moxos.uab.model.service.moodle;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

public class MoodleRestWebService {

    public MoodleRestWebService() {
    }

    /**
     * This calls the external Moodle web service. params String containing the
     * parameters of the call. elements NodeList containing name/value pairs of
     * returned XML data.
     *
     * @param urlParameters
     * @return elements NodeList
     * @throws java.net.MalformedURLException
     * @throws org.json.JSONException
     */
    public static JsonResult call(String urlParameters, String function) throws MalformedURLException, IOException, JSONException, MoodleRestUserException {
        JsonResult elements = null;
        try {
            if (MoodleWebServiceAuth.getAuth() == null)
                throw new MoodleRestUserException();

            URL getUrl = new URL(MoodleWebServiceAuth.getURL() + "/webservice/rest/server.php?" + MoodleWebServiceAuth.getAuth() + "&wsfunction=" + function);

            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String myJsonString = rd.readLine();
            elements = new JsonResult(myJsonString);
            connection.disconnect();
        } catch (IOException | JSONException ex) {
            Logger.getLogger(MoodleRestWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return elements;
    }
}
