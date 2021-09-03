
var facility = "greg's home";

function sendRequest(command, ...args) {
    const data = {
        command: command,
        arguments: args ? args : [],
        auth: 'testAuth',
        robot: $('#robot').val(),
        facility: facility
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
    sendRequest('camera_up', 10);
}

function down() {
    sendRequest('camera_down', 10);
}

function left(arg) {
    sendRequest('camera_left', arg);
}

function right(arg) {
    sendRequest('camera_right', arg);
}

function picture() {
	sendRequest('camera_picture');
	var url = 'http://localhost:8080/picture?facility=' + facility + '&robot=' + $('#robot').val();
	$('#picture').attr('src', encodeURI(url));
}