$('document').ready(function(){
    // Subscription Types
    $('table #editSubTypes').on('click', function (event){
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function(SubscriptionType, status){
            $('#idEdit').val(SubscriptionType.id);
            $('#nameEdit').val(SubscriptionType.name);
            $('#numberOfSessionsEdit').val(SubscriptionType.numberOfSessions);
            $('#daysValidEdit').val(SubscriptionType.daysValid);
            $('#priceEdit').val(SubscriptionType.price);
        });

        $('#editModalSubTypes').modal();
    })

    $('table #deleteSubTypes').on('click', function (event){
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function(SubscriptionType, status){
            $('#idDelete').val(SubscriptionType.id);
            $('#nameDelete').val(SubscriptionType.name);
            $('#numberOfSessionsDelete').val(SubscriptionType.numberOfSessions);
            $('#daysValidDelete').val(SubscriptionType.daysValid);
            $('#priceDelete').val(SubscriptionType.price);
        });

        $('#deleteModalSubTypes').modal();
    })


    //Sports
    $('table #editSport').on('click', function (event){
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function(Sport, status){
            $('#idEditSport').val(Sport.id);
            $('#nameEditSport').val(Sport.name);
            $('#colorEditSport').val(Sport.color);
        });

        $('#editModalSports').modal();
    })

    $('table #deleteSport').on('click', function (event){
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function(Sport, status){
            $('#idDeleteSport').val(Sport.id);
            $('#nameDeleteSport').val(Sport.name);
            $('#colorDeleteSport').val(Sport.color);
        });

        $('#deleteModalSports').modal();
    })


    //Roles
    $('table #editRole').on('click', function (event){
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function(Role, status){
            $('#idEditRole').val(Role.id);
            $('#nameEditRole').val(Role.name);
        });

        $('#editModalRoles').modal();
    })

    $('table #deleteRole').on('click', function (event){
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function(Role, status){
            $('#idDeleteRole').val(Role.id);
            $('#nameDeleteRole').val(Role.name);
        });

        $('#deleteModalRoles').modal();
    })
});