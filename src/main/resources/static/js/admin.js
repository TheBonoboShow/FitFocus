//todo setup secret page + reset password logic
//todo after registration link (email is send msg)
//todo make register_succes and error pages
//todo setup change email address
//todo make contact page
//todo make email verification page (register_*  + add mail in title html page)
//todo make logout logic
//todo change view depending on role
//todo setup emails promo
//todo setup emails reminders
//todo setup sms reminders
//todo fix index and link sessions with schedule
//todo user.reserved sessions and session.participants
//todo implement buying subscriptions and payment method?
//todo add search filter admin page (users - username & email)
//todo add info on hover for profile edit (readonly cases)
//todo validation on new user (send mail to Pearl and Patrick)
//todo launch app on staes.me and change mail redirection

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
            $('#colorEditSport').val(Sport.color);
        });

        $('#editModalSports').modal();
    })

    $('table #deleteSport').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (Sport, status) {
            $('#idDeleteSport').val(Sport.id);
            $('#nameDeleteSport').val(Sport.name);
            $('#colorDeleteSport').val(Sport.color);
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
            $('#gsmEditUser').val(User.gsmNumber);
            $('#remainingSessionsEditUser').val(User.remainingSessions);
            $('#reminderSmsEdit').prop('checked', User.reminderSms);
            $('#reminderMailEdit').prop('checked', User.reminderMail);
            $('#promotionsActiveEdit').prop('checked', User.promotionsActive);
            $('#profileIsActiveEdit').prop('checked', User.profileIsActive);
            $('#profileIsSuspendedEdit').prop('checked', User.profileIsSuspended);
            $('#isFemaleEditUser').prop('checked', User.female);
            $('#startDateEdit').val(User.startDate);
            $('#endDateEdit').val(User.endDate);
            $('#passwordEditUser').val(User.password);
            $('#roleEditUser').val(User.roleid);
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