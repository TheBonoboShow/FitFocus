$('table #editSession').on('click', function (event){
    event.preventDefault();

    var href = $(this).attr('href');

    $.get(href, function(Session, status){
        $('#idEdit').val(Session.id);
        $('#coachEdit').val(Session.userid);
        $('#informationEdit').val(Session.information);
        $('#sportEdit').val(Session.sportid);
        $('#dateEdit').val(Session.date);
        $('#startingHourEdit').val(Session.startingHour);
        $('#endHourEdit').val(Session.endHour);
        $('#maxParticipantsEdit').val(Session.maxParticipants);
        $('#onlyFemalesEdit').prop('checked', Session.onlyFemales);
    });

    $('#editModalSession').modal();
})

$('table #deleteSession').on('click', function (event){
    event.preventDefault();

    var href = $(this).attr('href');

    $.get(href, function(Session, status){
        $('#idDelete').val(Session.id);
        $('#coachDelete').val(Session.coach);
        $('#informationDelete').val(Session.information);
        $('#sportDelete').val(Session.sportid);
        $('#dateDelete').val(Session.date);
        $('#startingHourDelete').val(Session.startingHour);
        $('#endHourDelete').val(Session.endHour);
        $('#maxParticipantsDelete').val(Session.maxParticipants);
        $('#onlyFemalesDelete').prop('checked', Session.onlyFemales);
    });

    $('#deleteModalSession').modal();
})