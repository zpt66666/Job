console.log("login.js loaded and executing");

$(document).ready(function() {
    // 显示错误信息
    function showError(message) {
        const errorHtml = `
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `;
        $('#loginForm').prepend(errorHtml);
    }

    // 清除错误信息
    function clearErrors() {
        $('.alert').remove();
    }

    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        clearErrors();
        
        const formData = {
            usernameOrEmail: $('#username').val(),
            password: $('#password').val()
        };

        // 禁用提交按钮
        const submitBtn = $(this).find('button[type="submit"]');
        submitBtn.prop('disabled', true).html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> 登录中...');

        $.ajax({
            url: '/career/api/users/login',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                if (response.code === 200) {
                    window.location.href = '/career/';
                } else {
                    showError(response.message || '登录失败');
                }
            },
            error: function(xhr) {
                let errorMessage = '登录失败，请稍后重试';
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    errorMessage = xhr.responseJSON.message;
                }
                showError(errorMessage);
            },
            complete: function() {
                // 恢复提交按钮
                submitBtn.prop('disabled', false).text('登录');
            }
        });
    });
}); 