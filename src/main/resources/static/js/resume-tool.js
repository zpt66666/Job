$(document).ready(function() {
    // 监听简历模板选择按钮的点击事件
    $('.select-template-btn').on('click', function(e) {
        e.preventDefault(); // 阻止默认的锚点跳转行为
        
        // TODO: 在这里可以添加逻辑来记住用户选择了哪个模板
        // 目前先直接跳转到填写简历页面
        
        window.location.href = '/career/resume/fill';
    });

    // 绑定退出按钮事件 (如果尚未绑定)
    $('#logoutBtn').off('click').on('click', function(e) {
        e.preventDefault();
        $.post('/career/user/logout', function() {
            window.location.href = '/career/';
        }).fail(function() {
            alert('退出失败');
        });
    });
}); 