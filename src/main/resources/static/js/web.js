$(document).ready(function () {
   /* $(".login-register #login").click(function () {
        $.post(
            "http://localhost:8080/model/login.do",
            function (data) {
                var data = eval("(" + data + ")");
                alert(data.name)
            });
    });
*/
    /*登录功能*/
    $("#login_modal_btn").click(function () {
        $("#loginModel").modal({
            backdrop:"static"
        });
    });

    $("#login_btn").click(function () {
        $.ajax({
            url:"login",
            type:"get",
            /*将登录表单中的数据序列化*/
            data:$("#login_form").serialize(),
            success:function(data){
                if (data.code==100){
                    $("#loginModel").modal("hide");
                }else if (data.code==200){
                    $("#login_faild_msg").text(data.extend.msg);
                }
            }
        });
        // return false;
    });


    /*注册功能*/
    $("#register_modal_btn").click(function () {
        $("#registerModel").modal({
            backdrop:"static"
        });
    });

    $("#register_btn").click(function () {
        $.ajax({
            url:"user",
            type:"POST",
            /*将登录表单中的数据序列化*/
            data:$("#register_form").serialize(),
            success:function(data){
                if (data.code==100){
                    $("#registerModel").modal("hide");

                }else if (data.code==200){
                    $("#registerModel").removeData();
                    $("#username_check_msg").text(data.extend.addUserMsg.username_check_msg);
                    $("#account_check_msg").text(data.extend.addUserMsg.account_check_msg);
                    $("#email_check_msg").text(data.extend.addUserMsg.email_check_msg);
                    $("#tel_check_msg").text(data.extend.addUserMsg.tel_check_msg);
                }
            }
        });
        // return false;
    });

});
