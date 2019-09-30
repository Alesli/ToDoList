const makeTableData = data => data.map(({
                                            id, name, description, eventDate, originalName, mimeType,
                                        }, elNum) => {
    const tr = document.createElement('tr');
    let currentRowTemplate = tableRowTemplate;
    currentRowTemplate = currentRowTemplate.replace('_numberTask_', elNum + 1);
    currentRowTemplate = currentRowTemplate.replace('_taskName_', name);
    currentRowTemplate = currentRowTemplate.replace('_taskDescription_', description);
    currentRowTemplate = currentRowTemplate.replace('_taskDate_', formatDate(eventDate));

    if (mimeType) {
        if (IMAGES.includes(mimeType)) {
            let currentImgTemplate = imgTemplate(id);
            currentRowTemplate = currentRowTemplate.replace('_linkPlace_', currentImgTemplate);
        } else {
            let currentFileTemplate = fileTemplate;
            currentFileTemplate = currentFileTemplate.replace('_taskId_', id);
            currentFileTemplate = currentFileTemplate.replace('_fileName_', originalName);
            currentRowTemplate = currentRowTemplate.replace('_linkPlace_', currentFileTemplate);
        }
        currentRowTemplate = currentRowTemplate.replace('_btnAddFile_', btnDisable('Add'));
        currentRowTemplate = currentRowTemplate.replace('_btnDelFile_', btnDeleteFile(id));
    } else {
        currentRowTemplate = currentRowTemplate.replace(
            '_linkPlace_',
            `<input type="file" class="form-file-${id}" name="file" id="customFile" />`,
        );
        currentRowTemplate = currentRowTemplate.replace('_btnAddFile_', btnAddFile(id));
        currentRowTemplate = currentRowTemplate.replace('_btnDelFile_', btnDisable('Del'));
    }
    currentRowTemplate = currentRowTemplate.replace('_btnCompleteTask_', btnCompleteTask(id));
    currentRowTemplate = currentRowTemplate.replace('_btnDeleteTask_', btnDeleteTask(id));

    currentRowTemplate = currentRowTemplate.replace('_id_', id);
    tr.innerHTML = currentRowTemplate;
    return tr;
});

const downloadTasks = (data) => {
    const root = document.querySelector('.root');
    const div = document.createElement('div');
    if (data.length) {
        div.innerHTML = tableBodyTemplate;
        const tableBody = div.querySelector('.table-body');
        const newTableData = makeTableData(data);
        tableBody.append(...newTableData);
    } else {
        div.innerHTML = '<h2>There no any tasks</h2>';
    }
    root.appendChild(div);
};

const findAllByDay = (day) => {
    fetch(`/api/task/findAllByDay/${day}`)
        .then(resp => resp.json())
        .then(res => downloadTasks(res))
        .catch(err => console.log(err));
}

