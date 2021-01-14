document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        slotMinTime: '08:00:00',
        slotMaxTime: '23:30:00',
        slotDuration: '01:00:00',
        allDaySlot: false,

        eventClick: function(info) {
            // alert('Event: ' + info.event.extendedProps.information + "    " + info.event.title);
            var modal = document.getElementById("eventModal");
            modal.style.display = "block"

            fillModalSession(info);

            var span = document.getElementsByClassName("closeModal")[0];
            span.onclick = function() {
                modal.style.display = "none";
            }
            window.onclick = function(event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }
        },
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: '',
        },

        height: 'auto',

        events: '/calendar',

        eventTimeFormat: {
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        },

    });

    calendar.render();
});

function showYoga(){

        $('#all').removeClass('currentSession');
        $('#cycling').removeClass('currentSession');
        $('#boxing').removeClass('currentSession');
        $('#aerobic').removeClass('currentSession');
        $('#cardio').removeClass('currentSession');
        $("#yoga").addClass('currentSession');

        var calendarEl = document.getElementById('calendar');

        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'timeGridWeek',
            slotMinTime: '08:00:00',
            slotMaxTime: '23:30:00',
            slotDuration: '01:00:00',
            allDaySlot: false,

            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: '',
            },

            height: 'auto',

            events: '/calendarYoga',

            eventTimeFormat: {
                hour: '2-digit',
                minute: '2-digit',
                hour12: false
            }
        });

        calendar.render();
}

function showCardio(){

    $('#all').removeClass('currentSession');
    $('#cycling').removeClass('currentSession');
    $('#boxing').removeClass('currentSession');
    $('#aerobic').removeClass('currentSession');
    $('#yoga').removeClass('currentSession');
    $("#cardio").addClass('currentSession');

    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        slotMinTime: '08:00:00',
        slotMaxTime: '23:30:00',
        slotDuration: '01:00:00',
        allDaySlot: false,

        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: '',
        },

        height: 'auto',

        events: '/calendarCardio',

        eventTimeFormat: {
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        }
    });

    calendar.render();
}

function showBoxing(){

    $('#all').removeClass('currentSession');
    $('#cycling').removeClass('currentSession');
    $('#yoga').removeClass('currentSession');
    $('#aerobic').removeClass('currentSession');
    $('#cardio').removeClass('currentSession');
    $("#boxing").addClass('currentSession');

    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        slotMinTime: '08:00:00',
        slotMaxTime: '23:30:00',
        slotDuration: '01:00:00',
        allDaySlot: false,

        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: '',
        },

        height: 'auto',

        events: '/calendarBoxing',

        eventTimeFormat: {
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        }
    });

    calendar.render();
}

function showAerobic(){

    $('#all').removeClass('currentSession');
    $('#cycling').removeClass('currentSession');
    $('#boxing').removeClass('currentSession');
    $('#yoga').removeClass('currentSession');
    $('#cardio').removeClass('currentSession');
    $("#aerobic").addClass('currentSession');

    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        slotMinTime: '08:00:00',
        slotMaxTime: '23:30:00',
        slotDuration: '01:00:00',
        allDaySlot: false,

        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: '',
        },

        height: 'auto',

        events: '/calendarAerobic',

        eventTimeFormat: {
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        }
    });

    calendar.render();
}

function showCycling(){

    $('#all').removeClass('currentSession');
    $('#yoga').removeClass('currentSession');
    $('#boxing').removeClass('currentSession');
    $('#aerobic').removeClass('currentSession');
    $('#cardio').removeClass('currentSession');
    $("#cycling").addClass('currentSession');

    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        slotMinTime: '08:00:00',
        slotMaxTime: '23:30:00',
        slotDuration: '01:00:00',
        allDaySlot: false,

        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: '',
        },

        height: 'auto',

        events: '/calendarCycling',

        eventTimeFormat: {
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        }
    });

    calendar.render();
}

function showAll(){

    $('#yoga').removeClass('currentSession');
    $('#cycling').removeClass('currentSession');
    $('#boxing').removeClass('currentSession');
    $('#aerobic').removeClass('currentSession');
    $('#cardio').removeClass('currentSession');
    $("#all").addClass('currentSession');

    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        slotMinTime: '08:00:00',
        slotMaxTime: '23:30:00',
        slotDuration: '01:00:00',
        allDaySlot: false,

        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: '',
        },

        height: 'auto',

        events: '/calendar',

        eventTimeFormat: {
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        }
    });

    calendar.render();
}

function addZero(i) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}

function fillModalSession(info){

    document.getElementById("information").innerHTML = "Information: " + info.event.extendedProps.information;
    document.getElementById("sport").innerHTML = info.event.title;
    document.getElementById("timeslot").innerHTML = addZero(info.event.start.getHours()).toString() + ":" + addZero(info.event.start.getMinutes()).toString()
         + " - " + addZero(info.event.end.getHours()).toString() + ":" + addZero(info.event.end.getMinutes()).toString();
    document.getElementById("coach").innerHTML = "Coach: " + info.event.extendedProps.coach;
    document.getElementById("maxParticipants").innerHTML = "Maximum participants: " + info.event.extendedProps.maxParticipants;
    document.getElementById("freePlaces").innerHTML = "Free spots left: " + info.event.extendedProps.participants; //not ok yet
    document.getElementById("femaleOnly").innerHTML = "Woman only course!" //make hidden on false
    if (!info.event.extendedProps.onlyFemales){
        document.getElementById("femaleOnly").style.visibility = "hidden";
    } else {
        document.getElementById("femaleOnly").style.visibility = "visible";
    }


    var sport = info.event.title;
    if (sport == "Boxing"){
    document.getElementById("eventModalSidebar").style.backgroundImage="url(img/boxing.jpg)";
    }
    else if (sport == "Yoga"){
        document.getElementById("eventModalSidebar").style.backgroundImage="url(img/yoga.jpg)";
    }
    else if (sport == "Aerobic"){
        document.getElementById("eventModalSidebar").style.backgroundImage="url(img/aerobic.jpg)";
    }
    else if (sport == "Cycling"){
        document.getElementById("eventModalSidebar").style.backgroundImage="url(img/cycling.jpg)";
    }
    else {
        document.getElementById("eventModalSidebar").style.backgroundImage="url(img/cardio.jpg)";
    }

}