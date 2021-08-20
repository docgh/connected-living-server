
function sendRequest(command) {
    const data = {
        command: command,
        auth: 'testAuth',
        robot: $('#robot').val()
    };
    $.ajax({
        url: 'http://localhost:8080/command',
        type: 'PUT',
        dataType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            console.log('sent');
        },
        fail: function (result) {
            console.log('fail');
        }
    });
}

function changeSelection() {
 	sendRequest('ping');
 }

function up() {
    sendRequest('camera_up');
}

function down() {
    sendRequest('camera_down');
}

function left() {
    sendRequest('camera_left');
}

function right() {
    sendRequest('camera_right');
}