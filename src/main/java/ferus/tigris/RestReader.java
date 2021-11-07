package ferus.tigris;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestReader {

    protected JSONObject read(URL url) throws IOException, ParseException {
        try (InputStream is = url.openStream()) {
            JSONParser parser = new JSONParser();
            String data = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println(data);
            return (JSONObject)parser.parse(data);
        }
    }
}
