$(document).ready(function() {
    // 显示错误信息
    function showError(message) {
        const errorHtml = `
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `;
        $('#registerForm').prepend(errorHtml);
    }

    // 清除错误信息
    function clearErrors() {
        $('.alert').remove();
    }

    // 检查用户名是否可用
    $('#username').on('blur', function() {
        const username = $(this).val();
        if (username) {
            checkUsername(username);
        }
    });

    // 检查邮箱是否可用
    $('#email').on('blur', function() {
        const email = $(this).val();
        if (email) {
            checkEmail(email);
        }
    });

    // 表单提交
    $('#registerForm').on('submit', function(e) {
        e.preventDefault();
        clearErrors();
        
        if (!validateForm()) {
            return;
        }

        const formData = {
            username: $('#username').val(),
            email: $('#email').val(),
            realName: $('#realName').val(),
            phone: $('#phone').val(),
            password: $('#password').val(),
            confirmPassword: $('#confirmPassword').val()
        };

        // 禁用提交按钮
        const submitBtn = $(this).find('button[type="submit"]');
        submitBtn.prop('disabled', true).html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> 注册中...');

        $.ajax({
            url: '/career/api/users/register',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                if (response.code === 200) {
                    // 显示成功消息
                    const successHtml = `
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            注册成功！请点击下方链接前往登录页面。\
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    `;
                    $('#registerForm').prepend(successHtml);

                    // 添加手动跳转链接
                    const loginLinkHtml = `<p class="text-center mt-3"><a th:href="@{/login}" class="text-decoration-none">前往登录</a></p>`;
                     $('#registerForm').append(loginLinkHtml);

                } else {
                    showError(response.message || '注册失败');
                }
            },
            error: function(xhr) {
                let errorMessage = '注册失败，请稍后重试';
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    errorMessage = xhr.responseJSON.message;
                }
                showError(errorMessage);
            },
            complete: function() {
                // 恢复提交按钮
                submitBtn.prop('disabled', false).text('注册');
            }
        });
    });

    // 检查用户名是否可用
    function checkUsername(username) {
        $.get('/career/api/users/check-username', { username: username })
            .done(function(response) {
                if (response.code === 200) {
                    if (response.data.available) {
                        $('#usernameFeedback').html('<span class="text-success">用户名可用</span>');
                    } else {
                        $('#usernameFeedback').html('<span class="text-danger">用户名已被使用</span>');
                    }
                }
            })
            .fail(function() {
                $('#usernameFeedback').html('<span class="text-danger">检查用户名时出错</span>');
            });
    }

    // 检查邮箱是否可用
    function checkEmail(email) {
        $.get('/career/api/users/check-email', { email: email })
            .done(function(response) {
                if (response.code === 200) {
                    if (response.data.available) {
                        $('#emailFeedback').html('<span class="text-success">邮箱可用</span>');
                    } else {
                        $('#emailFeedback').html('<span class="text-danger">邮箱已被使用</span>');
                    }
                }
            })
            .fail(function() {
                $('#emailFeedback').html('<span class="text-danger">检查邮箱时出错</span>');
            });
    }

    // 表单验证
    function validateForm() {
        const password = $('#password').val();
        const confirmPassword = $('#confirmPassword').val();

        if (password !== confirmPassword) {
            showError('两次输入的密码不一致');
            return false;
        }

        return true;
    }
}); 