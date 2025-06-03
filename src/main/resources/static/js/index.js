$(document).ready(function() {
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

    // 检查用户登录状态
    function checkLoginStatus() {
        $.get('/career/api/users/current')
            .fail(function() {
                window.location.href = '/career/login';
            });
    }

    // 定期检查登录状态
    setInterval(checkLoginStatus, 5 * 60 * 1000); // 每5分钟检查一次
}); 