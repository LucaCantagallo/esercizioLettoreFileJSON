package lettoreJSON_prova.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lettoreJSON_prova.bean.*;

public class TestJSON {
	
	public static void main(String[] args) {
		List<StateBean> lista = getListJsonMap();
		lista = lista.stream().sorted(Comparator.comparing(StateBean::getAbbreviation)).collect(Collectors.toList());
		lista.stream().forEach(s -> System.out.println(s.toString()));	
		
	}
	
	public static List<StateBean> getListJsonAttr(){
		List<StateBean> lsb = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = TestJSON.class.getResourceAsStream("/stateExample.json")) {
            List<StateBean> states = objectMapper.readValue(inputStream, new TypeReference<List<StateBean>>() {});

            // Esempio di utilizzo dei dati
            for (StateBean state : states) {
                lsb.add(state);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return lsb;
	}
	
	public static List<StateBean> getListJsonMap(){
		ObjectMapper objectMapper = new ObjectMapper();
		List<StateBean> lsb = new ArrayList<>();

        try (InputStream inputStream = TestJSON.class.getResourceAsStream("/stateExampleNoName.json")) {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            Map<String, String> stateMap = new HashMap<>();

            // Itera su ogni coppia chiave-valore nel JSON
            jsonNode.fields().forEachRemaining(entry -> {
                String abbreviation = entry.getKey();
                String name = entry.getValue().asText();
                stateMap.put(abbreviation, name);
            });

            // Esempio di utilizzo dei dati
            stateMap.forEach((abbreviation, name) -> {
            	StateBean sb = new StateBean();
            	sb.setAbbreviation(abbreviation);
            	sb.setName(name);
            	lsb.add(sb);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lsb;
    }
	

}
