$(document).ready(function() {
    console.log('personal-info.js loaded and executed.');

    // 显示错误信息
    function showError(message) {
        const errorHtml = `
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `;
        $('#personalInfoForm').prepend(errorHtml);
    }

    // 清除错误信息
    function clearErrors() {
        $('.alert').remove();
    }

    // 获取个人信息并填充表单
    function fetchPersonalInfo() {
        $.get('/career/api/users/current')
            .done(function(response) {
                if (response.code === 200 && response.data) {
                    const user = response.data;
                    $('#username').val(user.username);
                    $('#email').val(user.email);
                    $('#realName').val(user.realName || '');
                    $('#phone').val(user.phone || '');
                } else {
                    showError(response.message || '获取用户信息失败');
                    console.warn('获取用户信息接口返回非200状态码或无数据:', response);
                }
            })
            .fail(function(xhr) {
                 let errorMessage = '获取用户信息失败，请稍后重试';
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    errorMessage = xhr.responseJSON.message;
                }
                showError(errorMessage);
                console.error('获取用户信息接口请求失败:', xhr);
            });
    }

    // 页面加载时获取个人信息
    fetchPersonalInfo();

    // 检查邮箱是否可用
    $('#email').on('blur', function() {
        const email = $(this).val();
        if (email) {
            checkEmail(email);
        } else {
             $('#emailFeedback').empty(); // 清空反馈信息
        }
    });

     // 检查邮箱是否可用 (与注册页面的逻辑相似)
    function checkEmail(email) {
        if (!email) {
            $('#emailFeedback').empty();
            return;
        }

        // 邮箱格式验证
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            $('#emailFeedback').html('<span class="text-danger">邮箱格式不正确</span>');
            return;
        }

        // 检查邮箱是否已被使用
        $.get('/career/api/users/check-email', { email: email })
            .done(function(response) {
                if (response.code === 200) {
                    if (response.data) {
                        $('#emailFeedback').html('<span class="text-danger">该邮箱已被使用</span>');
                    } else {
                        $('#emailFeedback').html('<span class="text-success">邮箱可用</span>');
                    }
                } else {
                    $('#emailFeedback').html('<span class="text-danger">邮箱检查失败</span>');
                }
            })
            .fail(function() {
                $('#emailFeedback').html('<span class="text-danger">邮箱检查失败</span>');
            });
    }

    // 验证手机号格式
    function validatePhone(phone) {
        if (!phone) return true;
        const phoneRegex = /^1[3-9]\d{9}$/;
        return phoneRegex.test(phone);
    }

    // 表单验证
    function validateForm() {
        const email = $('#email').val();
        const phone = $('#phone').val();
        let isValid = true;

        // 验证邮箱
        if (!email) {
            showError('邮箱不能为空');
            isValid = false;
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            showError('邮箱格式不正确');
            isValid = false;
        }

        // 验证手机号
        if (phone && !validatePhone(phone)) {
            showError('手机号格式不正确');
            isValid = false;
        }

        return isValid;
    }

    // 提交表单更新个人信息
    $('#personalInfoForm').on('submit', function(e) {
        e.preventDefault();
        clearErrors();
        
        if (!validateForm()) {
            return;
        }

        const formData = {
            email: $('#email').val(),
            realName: $('#realName').val(),
            phone: $('#phone').val()
        };

        // 禁用提交按钮
        const submitBtn = $(this).find('button[type="submit"]');
        submitBtn.prop('disabled', true).html('<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> 保存中...');

        $.ajax({
            url: '/career/api/users/update',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                if (response.code === 200) {
                    // 使用 Bootstrap Toast 显示成功消息
                    const toast = `
                        <div class="toast-container position-fixed bottom-0 end-0 p-3">
                            <div class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
                                <div class="d-flex">
                                    <div class="toast-body">
                                        <i class="fas fa-check-circle me-2"></i>个人信息更新成功！
                                    </div>
                                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                                </div>
                            </div>
                        </div>
                    `;
                    $('body').append(toast);
                    const toastElement = $('.toast');
                    const bsToast = new bootstrap.Toast(toastElement);
                    bsToast.show();
                    
                    // 3秒后自动移除 Toast
                    setTimeout(() => {
                        toastElement.remove();
                    }, 3000);
                } else {
                    showError(response.message || '个人信息更新失败');
                }
            },
            error: function(xhr) {
                let errorMessage = '个人信息更新失败，请稍后重试';
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    errorMessage = xhr.responseJSON.message;
                }
                showError(errorMessage);
            },
            complete: function() {
                // 恢复提交按钮
                submitBtn.prop('disabled', false).text('保存修改');
            }
        });
    });

    // 退出登录按钮点击事件
    $('#logoutBtn').on('click', function(e) {
        e.preventDefault();

        $.post('/career/api/users/logout')
            .done(function(response) {
                if (response.code === 200) {
                    // 退出成功，重定向到主页
                    window.location.href = '/career/'; // 或者直接 '/ ',取决于您的baseURL配置
                } else {
                    // 退出失败
                    alert('退出失败: ' + (response.message || ''));
                }
            })
            .fail(function(xhr) {
                alert('退出请求失败');
                console.error('退出请求失败:', xhr);
            });
    });

}); 