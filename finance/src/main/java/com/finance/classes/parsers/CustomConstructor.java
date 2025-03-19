package com.finance.classes.parsers;

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.nodes.Tag;

public class CustomConstructor extends Constructor {
    public CustomConstructor(LoaderOptions loaderOptions) {
        super(loaderOptions);
        this.yamlConstructors.put(new Tag("!LocalDateTime"), new ConstructLocalDateTime());
    }
}