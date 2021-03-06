package jsonrpc.protocol.jrpc.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import jsonrpc.protocol.jrpc.JrpcBase;

@JsonPropertyOrder({ "jsonrpc", "id", "result"})
public class JrpcResponse extends JrpcBase {

    private JsonNode result;

    public JrpcResponse(JsonNode result) {
        this.result = result;
    }

    public JsonNode getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "JrpcResponse{" +
            "id=" + id +
            ", version='" + version + '\'' +
            ", result=" + result +
            '}';
    }
}
