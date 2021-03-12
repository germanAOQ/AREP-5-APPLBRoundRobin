package edu.escuelaing.arep.docker.sparkwebserver;

import com.google.gson.Gson;
import edu.escuelaing.arep.docker.connection.HttpUtility;
import edu.escuelaing.arep.docker.model.Mensaje;
import edu.escuelaing.arep.docker.model.StandardResponse;
import edu.escuelaing.arep.docker.model.StatusResponse;
import org.json.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * @author Germán Ospina
 *
 */
public class SparkWebServer {

    private static int server = 1;

    /** Método main de la aplicación web
     * @param args
     */
    public static void main(String... args){
        port(getPort());
        staticFiles.location("/public");
        get("/hello", (req,res) -> {

            return "Hola Mundo!";
        });
        post("/logservice", (request, response) -> {
            Mensaje mensaje = new Gson().fromJson(request.body(), Mensaje.class);
            Map<String,String> params = new HashMap<String, String>();
            params.put("descripcion",mensaje.getDescripcion());
            params.put("date",mensaje.getDate());
            String requestURL = getServer();
            String[] responseList = null;
            try{
                HttpUtility.sendPostRequest(requestURL, params);
                responseList = HttpUtility.readMultipleLinesRespone();
            }catch (IOException ex){
                ex.printStackTrace();
            }
            HttpUtility.disconnect();

            JSONArray array = new JSONArray(responseList[0]);
            ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
            for(int i = 0; i<array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                mensajes.add(new Mensaje(object.getString("descripcion"), object.getString("date")));
            }
            return new Gson().toJson(mensajes);
        });
    }

    private static String getServer(){
        String requestURL = null;
        switch (server){
            case 1:
                requestURL = "http://ec2-34-224-94-29.compute-1.amazonaws.com:35001/logservice";
                server = 2;
                break;
            case 2:
                requestURL = "http://ec2-34-224-94-29.compute-1.amazonaws.com:35002/logservice";
                server = 3;
                break;
            case 3:
                requestURL = "http://ec2-34-224-94-29.compute-1.amazonaws.com:35003/logservice";
                server = 1;
                break;
        }
        return requestURL;
    }

    /** Permite retornar el puerto que por defecto asigna el entorno.
     * @return el puerto asignado por el entorno.
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    
}

