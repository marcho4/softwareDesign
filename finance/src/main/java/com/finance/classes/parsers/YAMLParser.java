package com.finance.classes.parsers;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import com.finance.classes.FileParser;
import com.finance.values.Currency;
import com.finance.values.Money;
import com.finance.values.Operation;
import com.finance.values.TransactionType;


public class YAMLParser extends FileParser {
    private Yaml yaml;

    public YAMLParser() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        
        Representer representer = new Representer(options) {
            {
                this.representers.put(LocalDateTime.class, new Represent() {
                    @Override
                    public Node representData(Object data) {
                        LocalDateTime dateTime = (LocalDateTime) data;
                        String formattedDate = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        return representScalar(new Tag("!LocalDateTime"), formattedDate);
                    }
                });
                this.representers.put(TransactionType.class, new Represent() {
                    @Override
                    public Node representData(Object data) {
                        TransactionType type = (TransactionType) data;
                        return representScalar(new Tag("!TransactionType"), type.name());
                    }
                });
            }
        };
        
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setTagInspector(tag -> {
                if (tag.getClassName() != null && tag.getClassName().equals(Operation.class.getName())) {
                    return true;
                }
                return false;
        });

        CustomConstructor constructor = new CustomConstructor(loaderOptions);
        this.yaml = new Yaml(constructor, representer, options, loaderOptions);
    }


    @Override
    public void exportToFile(List<Operation> operations, String filename) {
        try {
            yaml.dump(operations, new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<Operation> parseFromString(String content) {
        Object data = yaml.load(content);
        System.out.println(data);
        if (data instanceof List) {
            List<Operation> operations = new ArrayList<>();
            for (Object obj : (List<?>) data) {
                if (obj instanceof Operation) {
                    operations.add((Operation) obj);
                } else if (obj instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) obj;
                    Operation operation = new Operation();
                    operation.setId((String) map.get("id"));
                    operation.setTransactionType(TransactionType.valueOf((String) map.get("transactionType")));
                    operation.setDescription((String) map.get("description"));
                    operation.setDate((LocalDateTime) map.get("date"));
                    Map<String, Object> amountMap = (Map<String, Object>) map.get("amount");
                    operation.setAmount(new Money(
                        Currency.valueOf((String) amountMap.get("currency")),
                        Double.parseDouble(amountMap.get("amount").toString())
                    ));

                    operations.add(operation);
                }
            }
            return operations;
        }
        return null;
    }
}