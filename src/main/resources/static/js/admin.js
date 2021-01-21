$('document').ready(function () {
    // Subscription Types
    $('table #editSubTypes').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (SubscriptionType, status) {
            $('#idEdit').val(SubscriptionType.id);
            $('#nameEdit').val(SubscriptionType.name);
            $('#numberOfSessionsEdit').val(SubscriptionType.numberOfSessions);
            $('#daysValidEdit').val(SubscriptionType.daysValid);
            $('#priceEdit').val(SubscriptionType.price);
        });

        $('#editModalSubTypes').modal();
    })

    $('table #deleteSubTypes').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (SubscriptionType, status) {
            $('#idDelete').val(SubscriptionType.id);
            $('#nameDelete').val(SubscriptionType.name);
            $('#numberOfSessionsDelete').val(SubscriptionType.numberOfSessions);
            $('#daysValidDelete').val(SubscriptionType.daysValid);
            $('#priceDelete').val(SubscriptionType.price);
        });

        $('#deleteModalSubTypes').modal();
    })


    //Sports
    $('table #editSport').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (Sport, status) {
            $('#idEditSport').val(Sport.id);
            $('#nameEditSport').val(Sport.name);
        });

        $('#editModalSports').modal();
    })

    $('table #deleteSport').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (Sport, status) {
            $('#idDeleteSport').val(Sport.id);
            $('#nameDeleteSport').val(Sport.name);
        });

        $('#deleteModalSports').modal();
    })


    //Roles
    $('table #editRole').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (Role, status) {
            $('#idEditRole').val(Role.id);
            $('#nameEditRole').val(Role.name);
        });

        $('#editModalRoles').modal();
    })

    $('table #deleteRole').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (Role, status) {
            $('#idDeleteRole').val(Role.id);
            $('#nameDeleteRole').val(Role.name);
        });

        $('#deleteModalRoles').modal();
    })


    //Users
    $('#addUserBtn').on('click', function (event) {
        event.preventDefault();

            $('#addUsername').val("");
            $('#addPassword').val("");
            $('#addEmail').val("");

    })


    $('table #editUser').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (User, status) {
            $('#idEditUser').val(User.id);
            $('#usernameEditUser').val(User.username);
            $('#firstnameEditUser').val(User.firstname);
            $('#lastnameEditUser').val(User.lastname);
            $('#emailEditUser').val(User.email);
            $('#remainingSessionsEditUser').val(User.remainingSessions);
            $('#reminderMailEdit').prop('checked', User.reminderMail);
            $('#promotionsActiveEdit').prop('checked', User.promotionsActive);
            $('#profileIsActiveEdit').prop('checked', User.profileIsActive);
            $('#profileIsSuspendedEdit').prop('checked', User.profileIsSuspended);
            $('#isFemaleEditUser').prop('checked', User.female);
            $('#startDateEdit').val(User.startDate);
            $('#endDateEdit').val(User.endDate);
            $('#passwordEditUser').val(User.password);
            $('#roleEditUser').val(User.roleid);
            $('#reservedSessions').val(User.reservedSessions);
        });

        $('#editModalUser').modal();

    })

    $('table #deleteUser').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (User, status) {
            $('#idDeleteUser').val(User.id);
            $('#usernameDeleteUser').val(User.username);
            $('#emailDeleteUser').val(User.email);
        });

        $('#deleteModalUser').modal();
    })
});