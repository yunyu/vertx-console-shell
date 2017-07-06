import SockJS from 'sockjs-client';
import Terminal from 'term.js';

export default function (url, options, element) {
    options = options || {};
    var cols = options.cols || 80;
    var rows = options.rows || 24;
    url += (url.indexOf('?') > -1 ? '&' : '?') + "cols=" + cols + "&rows=" + rows;
    var termOptions = options.termOptions || { screenKeys: true };
    termOptions.cols = cols;
    termOptions.rows = rows;
    var socket;
    if (url.substring(0, 2) == 'ws') {
        socket = new WebSocket(url);
        socket.binaryType = 'blob';
    } else {
        socket = new SockJS(url);
    }
    socket.onopen = function () {
        var term = new Terminal(termOptions);
        socket.onmessage = function (event) {
            if (event.type === 'message') {
                // console.log(typeof event.data);
                if (typeof event.data !== 'string') {
                    var reader = new FileReader();
                    reader.onloadend = function () {
                        term.write(reader.result);
                    };
                    reader.readAsText(event.data);
                } else {
                    term.write(event.data);
                }
            }
        };
        socket.onclose = function () {
            socket.onmessage = null;
            socket.onclose = null;
            // console.log('Destroying term')
            term.destroy();
        };
        term.on('data', function (data) {
            socket.send(JSON.stringify({ action: 'read', data: data }));
        });
        term.open(element);
    };

    return {
        close: function () {
            socket.close();
        }
    }
};
