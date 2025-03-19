package com.finance.classes.parsers;

import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConstructLocalDateTime implements Construct {
    @Override
    public Object construct(Node node) {
        String value = ((ScalarNode) node).getValue();
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public void construct2ndStep(Node node, Object object) {}
}