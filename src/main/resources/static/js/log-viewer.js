let currentPage = 1;
let pageSize = 20;
let totalLogs = 0;

$(document).ready(function() {
    // 初始化加载日志
    fetchLogs();

    // 绑定过滤按钮点击事件
    $('#applyFilterBtn').on('click', function() {
        currentPage = 1; // 应用过滤时重置页码
        fetchLogs();
    });

    // 绑定上一页按钮点击事件
    $('#prevPageBtn').on('click', function() {
        if (currentPage > 1) {
            currentPage--;
            fetchLogs();
        }
    });

    // 绑定下一页按钮点击事件
    $('#nextPageBtn').on('click', function() {
        const totalPages = Math.ceil(totalLogs / pageSize);
        if (currentPage < totalPages) {
            currentPage++;
            fetchLogs();
        }
    });

    // 绑定每页显示数量变化事件
    $('#pageSize').on('change', function() {
        pageSize = parseInt($(this).val());
        currentPage = 1; // 改变每页数量时重置页码
        fetchLogs();
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

function fetchLogs() {
    const logLevel = $('#logLevel').val();
    const logKeyword = $('#logKeyword').val();

    $('#logContent').text('加载中...'); // 显示加载状态

    $.ajax({
        url: '/career/app/api/logs',
        method: 'GET',
        dataType: 'json', // 现在后端返回的是JSON
        data: {
            level: logLevel,
            keyword: logKeyword,
            page: currentPage,
            size: pageSize
        },
        success: function(response) {
            if (response.logs) {
                totalLogs = response.total;
                const totalPages = Math.ceil(totalLogs / pageSize);

                // 更新日志内容
                let logsText = '';
                if (response.logs.length > 0) {
                    logsText = response.logs.map(log => {
                        // 根据LogEntry的字段格式化显示
                        return `${log.timestamp} [${log.thread}] ${log.level} ${log.logger} - ${log.message}`;
                    }).join('\n');
                } else {
                    logsText = '没有找到匹配的日志。';
                }
                $('#logContent').text(logsText);

                // 更新分页信息
                $('#currentPage').text(currentPage);
                $('#totalPages').text(totalPages);
                $('#totalLogs').text(totalLogs);

                // 控制分页按钮的可用状态
                $('#prevPageBtn').prop('disabled', currentPage <= 1);
                $('#nextPageBtn').prop('disabled', currentPage >= totalPages);

            } else {
                 // 如果返回结构不对，打印错误信息
                console.error('后端返回数据格式错误:', response);
                $('#logContent').text('获取日志失败: 数据格式错误。');
            }
        },
        error: function(xhr) {
            console.error('获取日志失败:', xhr);
            $('#logContent').text('获取日志失败。');

             // 重置分页信息
            $('#currentPage').text(1);
            $('#totalPages').text(1);
            $('#totalLogs').text(0);
            $('#prevPageBtn').prop('disabled', true);
            $('#nextPageBtn').prop('disabled', true);
        }
    });
} 