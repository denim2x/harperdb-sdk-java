package io.harperdb.config.parser;

import io.harperdb.config.ConfigModel;
import io.harperdb.config.source.ConfigSource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class YamlParser implements ConfigParser {

    @Override
    public ConfigModel parse(ConfigSource source) {
        Yaml yaml = new Yaml();
        Optional<Reader> optionalReader = source.load();
        ConfigModel configModel = ConfigModel.create();
        if (optionalReader.isPresent()) {
            try (Reader reader = optionalReader.get()) {
                Map<String, Object> yamlMap = yaml.load(reader);
                Map<String, Object> configMap = new HashMap<>();
                Stack<String> iterationGuide = new Stack<>();
                constructConfigMap(iterationGuide, yamlMap, configMap);

                configMap.forEach(configModel::insert);
            } catch (IOException e) {
                //TODO log and handle exception
            }
        }
        return configModel;
    }

    private void constructConfigMap(Stack<String> iterationGuide, Map<String, Object> yamlMap, Map<String, Object> configMap) {
        for (Map.Entry<String, Object> entry: yamlMap.entrySet()) {
            if (!iterationGuide.isEmpty()) {
                if (isLeaf(entry)) {
                    configMap.put(iterationGuide.peek()  + "." + entry.getKey(), entry.getValue());
                } else {
                    Stack<String> intermediate = new Stack<>();
                    intermediate.push(iterationGuide.peek() + "." + entry.getKey());
                    constructConfigMap(intermediate, (Map<String, Object>) entry.getValue(), configMap);
                }
            } else {
                iterationGuide.push(entry.getKey());
                constructConfigMap(iterationGuide, (Map<String, Object>) entry.getValue(), configMap);
            }
        }
    }

    private boolean isLeaf(Map.Entry<String, Object> entry) {
        return !(entry.getValue() instanceof Map);
    }
}
