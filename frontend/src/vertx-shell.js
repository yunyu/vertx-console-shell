import SockJS from 'sockjs-client';
import Terminal from 'xterm';

Terminal.loadAddon('fit');

export default function (url, options, element) {
    var term = new Terminal();
    term.open(element, true);

    var socket;
    var initialResize = true;
    var onResize = function (size) {
        var cols = size.cols, rows = size.rows;
        if (initialResize) {
            initialResize = false;
            url += (url.indexOf('?') > -1 ? '&' : '?') + "cols=" + cols + "&rows=" + rows;
            if (url.substring(0, 2) == 'ws') {
                socket = new WebSocket(url);
                socket.binaryType = 'blob';
            } else {
                socket = new SockJS(url);
            }
            socket.onopen = function () {
                socket.onmessage = function (event) {
                    if (event.type === 'message') {
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
            };
        } else {
            socket.send(JSON.stringify({ action: 'resize', cols: cols, rows: rows }));
        }
    }

    term.on('resize', onResize)
    term.fit();

    return {
        close: function () {
            socket.close();
        },
        refit: function () {
            term.fit();
        }
    }
};
