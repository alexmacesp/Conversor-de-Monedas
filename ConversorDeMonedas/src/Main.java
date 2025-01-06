import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner lectura = new Scanner(System.in);
        List<moneda> historial = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        while (true) {
            String menu = """
                    Conversor de divisas
                    Inserta 1 para hacer una conversion de divisas.
                    Inserta 2 para desplegar una lista con ejemplos de codigos monetarios.
                    Inserta 3 para salir del programa.
                    """;
            System.out.println(menu);

            var opcion = lectura.nextLine();

            switch (opcion){
                case "1":
                    System.out.println("Escribe el codigo de la moneda base a utilizar: ");
                    var monedaBase = lectura.nextLine();
                    if(!monedaBase.matches("[a-zA-z]+")){
                        throw new ErrorEnCodigoException("No es un codigo valido. Reinicia el programa.");
                    }
                    System.out.println("Escriba el codigo de la moneda a la cual quiere cambiar: ");
                    var codigo = lectura.nextLine();
                    if(!codigo.matches("[a-zA-z]+")){
                        throw new ErrorEnCodigoException("No es un codigo valido. Reinicia el programa.");
                    }
                    String direccion = "https://v6.exchangerate-api.com/v6/f46965344faf54cf8acc27a0/pair/"
                                    + monedaBase + "/" + codigo;
                        try {
                            HttpClient client = HttpClient.newHttpClient();
                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(direccion))
                                    .build();

                            HttpResponse<String> response = client
                                    .send(request, HttpResponse.BodyHandlers.ofString());

                            String json = response.body();


                            monedaAPI miMonedaAPI = gson.fromJson(json, monedaAPI.class);

                            moneda miMoneda = new moneda(miMonedaAPI);
                            if(miMoneda.getResultado().equalsIgnoreCase("error")){
                                throw new ErrorEnCodigoException("No es un codigo valido. Reinicia el programa.");
                            }

                            System.out.println("Cuantas monedas quieres cambiar: ");
                            var valor = lectura.nextLine();
                            miMoneda.setValorCambio(Float.parseFloat(valor));
                            miMoneda.setValorFinal(conversionMoneda(miMoneda.getTasaCambio(), miMoneda.getValorCambio()));
                            Date currentDate = new Date();
                            miMoneda.setFecha(String.valueOf(currentDate));

                            System.out.println(valor + miMoneda.getBase() +
                                    " equivalen a " + miMoneda.getValorFinal() + miMoneda.getObjetivo());

                            historial.add(miMoneda);

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    break;
                case "2":
                    String codigos = """
                            Ejemplos
                            USD = Dolar estadounidense
                            EUR = Euro
                            MXN = Peso Mexicano
                            ARS = Peso Argentino
                            BRL = Real Brasileño
                            CLP = Peso Chileno
                            COP = Peso Colombiano
                            NIO = Cordoba
                            PAB = Balboa
                            PEN = Nuevo Sol
                            UYU = Peso Uruguayo
                            CUP = Peso Cubano
                            
                            La lista completa de codigos disponibles se encuentra aqui: https://www.exchangerate-api.com/docs/supported-currencies
                            """;
                    System.out.println(codigos);
                    break;
                case "3":
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
            if (opcion.equalsIgnoreCase("3")){
                break;
            }
        }
        FileWriter escritura = new FileWriter("historial.json");
        escritura.write(gson.toJson(historial));
        escritura.close();
    }

    public static float conversionMoneda ( float tasa, float valor){
        return tasa * valor;
    }

}