package ru.home.jsonrpc.example.controller.jrpc.calculator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsonrpc.protocol.dto.base.HandlerName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import ru.home.jsonrpc.example.controller.jrpc.base.JrpcController;
import ru.home.jsonrpc.example.controller.jrpc.base.JrpcMethod;
import ru.home.jsonrpc.example.service.CalculatorService;

import javax.validation.ValidationException;
import java.lang.invoke.MethodHandles;



/**
 * Интерфейс управления калькулятором
 */
@JrpcController(path = HandlerName.calculatorN.path)
public class CalculatorController {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private CalculatorService calculatorService;
    private final ObjectMapper objectMapper;


    @Autowired
    protected CalculatorController(CalculatorService calculatorService, ObjectMapper objectMapper) {
        this.calculatorService = calculatorService;
        this.objectMapper = objectMapper;
    }



    // Далее для простоты опущено использование класса Converter,
    // (имеющий свою реализацию под каждую отдельную entity)
    // который использует MapStruct и выполняет преобразование JSON <-> DTO <-> Entity
    // + проводит валидацию входных данных



    @JrpcMethod(method = HandlerName.calculatorN.add)
    public JsonNode add(JsonNode params) {

        Double[] pair = parseDoublePair(params);
        double res = calculatorService.add(pair[0], pair[1]);
        return toJson(res);
    }


    @JrpcMethod(method = HandlerName.calculatorN.sub)
    public JsonNode sub(JsonNode params) {

        Double[] pair = parseDoublePair(params);
        double res = calculatorService.sub(pair[0], pair[1]);
        return toJson(res);
    }


    @JrpcMethod(method = HandlerName.calculatorN.mul)
    public JsonNode mul(JsonNode params) {

        Double[] pair = parseDoublePair(params);
        double res = calculatorService.mul(pair[0], pair[1]);
        return toJson(res);
    }


    @JrpcMethod(method = HandlerName.calculatorN.div)
    public JsonNode div(JsonNode params) {

        Double[] pair = parseDoublePair(params);
        double res = calculatorService.div(pair[0], pair[1]);
        return toJson(res);
    }




    // ====================================================================================

    

    // Json => Entity
    private Double[] parseDoublePair(JsonNode params) {

        //List<Double> result;
        //JavaType jt = objectMapper.getTypeFactory().constructCollectionType(List.class, Double.class);
        //result = objectMapper.readValue(objectMapper.treeAsTokens(params), jt);

        Double[] result;

        try {

            result = objectMapper.treeToValue(params, Double[].class);

            // validate
            if (result == null || result.length != 2) {
                throw new ValidationException("Wrong arguments count");
            }

            for (int i = 0; i < 2; i++) {
                if (result[i] == null || result[i].isNaN() || result[i].isInfinite()) {
                    throw new ValidationException(i + "st argument validation failed");
                }
            }

        } catch (JsonProcessingException e) {
            throw new ParseException(0, "params parse error", e);
        }

        return result;
    }



    // Entity => Json
    private JsonNode toJson(Double d) {
        try {
            return objectMapper.valueToTree(d);
        }
        catch (Exception e) {
            throw new ParseException(0, "toJson convert error", e);
        }
    }

}
