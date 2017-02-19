import 'whatwg-fetch';


class Request {

    Send(method, url, body, success, failed) {
        switch (method) {
            case 'GET':
            case 'POST':
            case 'PUT':
            case 'DELETE':
                fetch(url, {
                    method,
                    headers: {'Content-Type': 'application/json'},
                    body
                }).then(function (response) {
                    return response.json()
                }).then(function (json) {
                    success
                }).catch(function (ex) {
                    // failed
                });
                break;
            default:
        }
    }
}