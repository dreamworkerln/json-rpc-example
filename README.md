# json-rpc-example

###JSON-RPC 2.0 Specification  
JSON-RPC is a stateless, light-weight remote procedure call (RPC) protocol that uses JSON (RFC 4627) as data format.  
https://www.jsonrpc.org/specification


### Testing
use postman  
https://documenter.getpostman.com/view/9553788/SzYYzdrH?version=latest

or
```
curl --location --request POST 'http://localhost:8080/api/1.0/' \
--data-raw '{
    "id": 10,
    "method": "calculator.actions.add",
    "params": [
        5,7
    ]
}'
```
to test it
