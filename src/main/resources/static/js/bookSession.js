$('table #deleteSession').on('click', function (event){
    event.preventDefault();

    var href = $(this).attr('href');

    $.get(href, function(Session, status){
        $('#idDelete').val(Session.id);
        $('#sportDelete').val(Session.sportid);
        $('#dateDelete').val(Session.date);
        $('#startingHourDelete').val(Session.startingHour);
    });

    $('#deleteModalSesh').modal();
})