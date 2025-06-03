$(document).ready(function() {

    console.log('skill-assessment.js loaded and DOM ready.');

    // TODO: 添加显示错误和成功信息的函数

    // 技能水平选项
    const skillLevels = [
        { value: 0, text: '初级' },
        { value: 1, text: '中级' },
        { value: 2, text: '高级' }
    ];

    // 获取用户技能评估并填充表单
    function fetchUserSkills() {
        console.log('Fetching user skills...');
        $.get('/career/api/user-skills/current')
            .done(function(response) {
                console.log('Fetch user skills done. Response:', response);
                if (response.code === 200 && response.data) {
                    renderSkills(response.data);
                } else {
                    // TODO: 显示获取技能失败的错误信息
                    console.error('获取用户技能失败:', response.message);
                }
            })
            .fail(function(xhr) {
                console.error('Fetch user skills failed. XHR:', xhr);
                // TODO: 显示获取技能接口请求失败的错误信息
                alert('获取用户技能失败，请稍后重试');
            });
    }

    // 渲染技能列表
    function renderSkills(skills) {
        console.log('Rendering skills:', skills);
        const skillsListDiv = $('#skills-list');
        skillsListDiv.empty(); // 清空现有列表

        if (skills && skills.length > 0) {
            skills.forEach(skill => {
                addSkillRow(skill.skillName, skill.skillLevel);
            });
        } else {
            // 如果没有技能，添加一个空的技能输入行
            console.log('No skills found, adding an empty row.');
            addSkillRow('', 0);
        }
    }

    // 添加单个技能输入行
    function addSkillRow(skillName = '', skillLevel = 0) {
        console.log('Adding skill row for:', skillName, skillLevel);
        const skillRowHtml = `
            <div class="mb-3 row skill-row">
                <div class="col-sm-5">
                    <input type="text" class="form-control skill-name" placeholder="技能名称" value="${skillName}">
                </div>
                <div class="col-sm-4">
                    <select class="form-select skill-level">
                        ${skillLevels.map(level => `<option value="${level.value}" ${skillLevel === level.value ? 'selected' : ''}>${level.text}</option>`).join('')}
                    </select>
                </div>
                <div class="col-sm-3 d-flex align-items-center">
                    <button type="button" class="btn btn-danger btn-sm remove-skill-btn"><i class="fas fa-trash-alt"></i></button>
                </div>
            </div>
        `;
        $('#skills-list').append(skillRowHtml);
    }

    // 添加技能按钮点击事件
    $('#add-skill-btn').on('click', function() {
        addSkillRow();
    });

    // 移除技能按钮点击事件 (事件委托)
    $('#skills-list').on('click', '.remove-skill-btn', function() {
        $(this).closest('.skill-row').remove();
    });

    // 表单提交事件
    $('#skillAssessmentForm').on('submit', function(e) {
        e.preventDefault();

        const userSkillList = [];
        $('.skill-row').each(function() {
            const skillName = $(this).find('.skill-name').val().trim();
            const skillLevel = $(this).find('.skill-level').val();
            if (skillName) { // 只保存非空的技能名称
                userSkillList.push({
                    skillName: skillName,
                    skillLevel: parseInt(skillLevel)
                });
            }
        });

        // TODO: 添加前端验证 (例如：技能名称不能为空)

        // 发送数据到后端
        $.ajax({
            url: '/career/api/user-skills/save',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(userSkillList),
            success: function(response) {
                if (response.code === 200) {
                    // TODO: 显示保存成功的提示
                    alert('技能评估保存成功！');
                    // 可以选择重新加载列表
                    fetchUserSkills();
                } else {
                    // TODO: 显示保存失败的错误信息
                    alert('技能评估保存失败:' + (response.message || ''));
                }
            },
            error: function(xhr) {
                // TODO: 显示请求失败的错误信息
                alert('技能评估保存请求失败');
                console.error('保存技能评估请求失败:', xhr);
            }
        });
    });

    // 页面加载时获取用户技能评估
    console.log('Calling fetchUserSkills on page load.');
    fetchUserSkills();

}); 