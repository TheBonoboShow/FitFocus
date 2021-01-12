document.addEventListener('DOMContentLoaded', function () {
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