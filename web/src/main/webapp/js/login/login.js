function md5_password() {
    var password = $('#password').val();

    if(password.length > 6) {
        var md5Pass = md5(password);
        //$('#password').val(md5Pass);
    }

    if($('#rePassword').exists()) {
        var rePassword = $('#rePassword').val();
        if(rePassword.length > 6) {
            var md5RePass = md5(rePassword);
            //$('#rePassword').val(md5RePass);
        }
    }
}