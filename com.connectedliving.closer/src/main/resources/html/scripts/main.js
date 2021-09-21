
var facility = "greg's home";

var sessionId;

function sendRequest(command, ...args) {
    const data = {
        command: command,
        arguments: args ? args : [],
        auth: 'testAuth',
        robot: $('#robot').val(),
        facility: facility
    };
    return $.ajax({
        url: 'http://localhost:8080/command?sessionId=' + encodeURI(sessionId),
        type: 'PUT',
        data: JSON.stringify(data),
        success: function (result) {
            console.log('sent');
        },
        fail: function (result) {
            console.log('fail');
        }
    });
}

function updateRobot() {
	sendRequest('status').then(function (data) {
		if (data.Locations) {
			data.Locations.forEach(function (loc) {
			    $('#locations').append($('<option>').attr('value', loc).append(loc));
			});
			$('.locationSelector').show();
		}
		if (data.Battery) {
			$('#battery').val(data.Battery.level);
			$('.BatteryDiv').show();
		}
	});
}

function changeSelection() {
 	updateRobot();
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

function forward(arg) {
    sendRequest('move_forward', arg);
}

function stop(arg) {
    sendRequest('move_stop', arg);
}

function back(arg) {
    sendRequest('move_back', arg);
}

function rotateLeft(arg) {
    sendRequest('rotate_left', arg);
}

function rotateRight(arg) {
    sendRequest('rotate_right', arg);
}

function goto() {
	sendRequest('goto_location', $('#locations').val());
}

function picture() {
	sendRequest('camera_picture');
	$('#pictureDiv').empty();
	var url = 'http://localhost:8080/picture?facility=' + facility + '&robot=' + $('#robot').val() + '&sessionId=' + encodeURI(sessionId);
	$('#pictureDiv').append($('<img>').attr('src', encodeURI(url)));
}

function login() {
	var auth = {
		username: $('#username').val(),
		password: $('#password').val()
	}
	$.ajax({
        url: 'http://localhost:8080/auth',
        type: 'POST',
        data: JSON.stringify(auth),
        success: function (result) {
            console.log('login');
            if (result.sessionId) {
            	sessionId = result.sessionId;
            	$('#authDiv').hide();
            	$('#mainDiv').show();
            	return;
            }
            alert('Login failed');
        },
        fail: function (result) {
            console.log('fail');
        }
    });
 }
 
	