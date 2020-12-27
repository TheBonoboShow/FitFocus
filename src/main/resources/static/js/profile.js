$('document').ready(function () {

    $('#editUser').on('click', function (event) {
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
            $('#passwordEditUserConfirm').val(User.password);
            $('#roleEditUser').val(User.roleid);
        });

        $('#editModalUser').modal();

    })
})