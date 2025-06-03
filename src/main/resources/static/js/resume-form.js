$(document).ready(function() {
    // 页面加载时获取简历数据
    fetchResumeData();

    // 绑定退出按钮事件 (如果尚未绑定)
    $('#logoutBtn').off('click').on('click', function(e) {
        e.preventDefault();
        $.post('/career/user/logout', function() {
            window.location.href = '/career/';
        }).fail(function() {
            alert('退出失败');
        });
    });

    // 监听简历表单提交事件
    $('#resumeForm').on('submit', function(e) {
        e.preventDefault(); // 阻止表单默认提交行为
        saveAll(); // 调用保存函数
    });
});

// 获取简历数据
function fetchResumeData() {
    $.ajax({
        url: '/career/api/resume/current',
        method: 'GET',
        success: function(response) {
            console.log('获取简历数据成功:', response);
            if (response.code === 200) {
                const data = response.data;
                if (data) {
                    // 填充基本信息
                    $('#fullName').val(data.fullName || '');
                    $('#contactEmail').val(data.contactEmail || '');
                    $('#phoneNumber').val(data.phoneNumber || '');

                    // 填充教育经历
                    $('#educationList').empty(); // 清空现有列表
                    if (data.educations && data.educations.length > 0) {
                        data.educations.forEach(edu => {
                            const $item = $(addEducation());
                            $item.find('[name="schoolName"]').val(edu.schoolName || '');
                            $item.find('[name="degree"]').val(edu.degree || '');
                            $item.find('[name="major"]').val(edu.major || '');
                            $item.find('[name="startDate"]').val(edu.startDate || '');
                            $item.find('[name="endDate"]').val(edu.endDate || '');
                            $item.find('[name="description"]').val(edu.description || '');
                        });
                    }

                    // 填充工作经历
                    $('#experienceList').empty(); // 清空现有列表
                    if (data.experiences && data.experiences.length > 0) {
                        data.experiences.forEach(exp => {
                            const $item = $(addExperience());
                            $item.find('[name="companyName"]').val(exp.companyName || '');
                            $item.find('[name="jobTitle"]').val(exp.jobTitle || '');
                            $item.find('[name="startDate"]').val(exp.startDate || '');
                            $item.find('[name="endDate"]').val(exp.endDate || '');
                            $item.find('[name="description"]').val(exp.description || '');
                        });
                    }

                    // 填充技能
                    $('#skillList').empty(); // 清空现有列表
                    if (data.skills && data.skills.length > 0) {
                        data.skills.forEach(skill => {
                            const $item = $(addSkill());
                            $item.find('[name="skillName"]').val(skill.skillName || '');
                            $item.find('[name="level"]').val(skill.level || '');
                        });
                    }

                    // 填充项目经历
                    $('#projectList').empty(); // 清空现有列表
                    if (data.projects && data.projects.length > 0) {
                        data.projects.forEach(project => {
                            const $item = $(addProject());
                            $item.find('[name="projectName"]').val(project.projectName || '');
                            $item.find('[name="startDate"]').val(project.startDate || '');
                            $item.find('[name="endDate"]').val(project.endDate || '');
                            $item.find('[name="description"]').val(project.description || '');
                            $item.find('[name="technologies"]').val(project.technologies || '');
                        });
                    }
                }
            } else {
                showToast(response.message || '获取简历数据失败', 'error');
            }
        },
        error: function(xhr) {
            console.error('获取简历数据失败:', xhr);
            showToast('获取简历数据失败', 'error');
        }
    });
}

// 添加教育经历
function addEducation() {
    const template = document.getElementById('educationTemplate');
    const clone = template.content.cloneNode(true);
    document.getElementById('educationList').appendChild(clone);
    // 返回新添加的元素以便填充数据
    return document.getElementById('educationList').lastElementChild;
}

// 添加工作经历
function addExperience() {
    const template = document.getElementById('experienceTemplate');
    const clone = template.content.cloneNode(true);
    document.getElementById('experienceList').appendChild(clone);
    // 返回新添加的元素以便填充数据
    return document.getElementById('experienceList').lastElementChild;
}

// 添加技能
function addSkill() {
    const template = document.getElementById('skillTemplate');
    const clone = template.content.cloneNode(true);
    document.getElementById('skillList').appendChild(clone);
    // 返回新添加的元素以便填充数据
    return document.getElementById('skillList').lastElementChild;
}

// 添加项目经历
function addProject() {
    const template = document.getElementById('projectTemplate');
    const clone = template.content.cloneNode(true);
    document.getElementById('projectList').appendChild(clone);
    // 返回新添加的元素以便填充数据
    return document.getElementById('projectList').lastElementChild;
}

// 删除项目
function removeItem(button) {
    $(button).closest('.card').remove();
}

// 收集表单数据
function collectFormData() {
    const data = {
        fullName: $('#fullName').val(),
        contactEmail: $('#contactEmail').val(),
        phoneNumber: $('#phoneNumber').val(),
        educations: [],
        experiences: [],
        skills: [],
        projects: []
    };

    // 收集教育经历
    $('.education-item').each(function() {
        const $item = $(this);
        data.educations.push({
            schoolName: $item.find('[name="schoolName"]').val(),
            degree: $item.find('[name="degree"]').val(),
            major: $item.find('[name="major"]').val(),
            startDate: $item.find('[name="startDate"]').val(),
            endDate: $item.find('[name="endDate"]').val(),
            description: $item.find('[name="description"]').val()
        });
    });

    // 收集工作经历
    $('.experience-item').each(function() {
        const $item = $(this);
        data.experiences.push({
            companyName: $item.find('[name="companyName"]').val(),
            jobTitle: $item.find('[name="jobTitle"]').val(),
            startDate: $item.find('[name="startDate"]').val(),
            endDate: $item.find('[name="endDate"]').val(),
            description: $item.find('[name="description"]').val()
        });
    });

    // 收集技能
    $('.skill-item').each(function() {
        const $item = $(this);
        data.skills.push({
            skillName: $item.find('[name="skillName"]').val(),
            level: $item.find('[name="level"]').val()
        });
    });

    // 收集项目经历
    $('.project-item').each(function() {
        const $item = $(this);
        data.projects.push({
            projectName: $item.find('[name="projectName"]').val(),
            startDate: $item.find('[name="startDate"]').val(),
            endDate: $item.find('[name="endDate"]').val(),
            description: $item.find('[name="description"]').val(),
            technologies: $item.find('[name="technologies"]').val()
        });
    });

    return data;
}

// 简单的表单验证
function validateForm() {
    let isValid = true;
    $('#fullName').removeClass('is-invalid');
    $('#contactEmail').removeClass('is-invalid');
    $('#phoneNumber').removeClass('is-invalid');

    if (!$('#fullName').val()) {
        $('#fullName').addClass('is-invalid');
        isValid = false;
    }
    if (!$('#contactEmail').val()) {
        $('#contactEmail').addClass('is-invalid');
        isValid = false;
    } else if (!isValidEmail($('#contactEmail').val())) {
         $('#contactEmail').addClass('is-invalid');
         showToast('请输入有效的邮箱地址', 'error');
         isValid = false;
    }
    if (!$('#phoneNumber').val()) {
        $('#phoneNumber').addClass('is-invalid');
        isValid = false;
    } else if (!isValidPhoneNumber($('#phoneNumber').val())) {
        $('#phoneNumber').addClass('is-invalid');
        showToast('请输入有效的手机号码', 'error');
        isValid = false;
    }

    // 可以根据需要添加更多对动态添加项的验证

    return isValid;
}

// 邮箱格式验证
function isValidEmail(email) {
    const emailRegex = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
    return emailRegex.test(email);
}

// 手机号格式验证 (简单示例，根据实际需求调整)
function isValidPhoneNumber(phone) {
    const phoneRegex = /^1[3-9]\d{9}$/;
    return phoneRegex.test(phone);
}

// 保存所有数据
function saveAll() {
    if (!validateForm()) {
        showToast('请检查表单中的错误', 'error');
        return;
    }

    const data = collectFormData();
    console.log('提交的数据:', data);

    $.ajax({
        url: '/career/api/resume/save-simple',
        method: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        success: function(response) {
            console.log('保存简历成功:', response);
            if (response.code === 200) {
                showToast('保存成功', 'success');
                // 保存成功后重新加载数据以获取可能的后端生成的ID等
                fetchResumeData();
            } else {
                showToast(response.message || '保存失败', 'error');
            }
        },
        error: function(xhr) {
            console.error('保存简历失败:', xhr);
            showToast('保存失败', 'error');
        }
    });
}

// 显示提示信息 (Bootstrap Toast)
function showToast(message, type = 'info') {
    // 确保有一个容器来放置Toast
    if ($('.toast-container').length === 0) {
        $('body').append('<div class="toast-container position-fixed top-0 end-0 p-3" style="z-index: 11;"></div>');
    }

    const toast = `
        <div class="toast align-items-center text-white bg-${type === 'error' ? 'danger' : 'success'} border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    `;

    const $toast = $(toast);
    $('.toast-container').append($toast);
    const bsToast = new bootstrap.Toast($toast[0]);
    bsToast.show();

    $toast.on('hidden.bs.toast', function() {
        $(this).remove();
    });
} 