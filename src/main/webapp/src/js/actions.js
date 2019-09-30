const addFile = (id) => {
    const file = document.querySelector(`.form-file-${id}`).files[0];

    if (file) {
        const fileForm = new FormData();
        const fileArr = [file];
        fileForm.append('file', fileArr[0]);
        fetch(`/api/file/saveFile/${id}`, {
            method: 'POST',
            body: fileForm,
        });
    }
    window.location.reload();
};

const delFile = (id) => {
    fetch(`/api/file/removeAttachment/${id}`, {
        method: 'PUT'
    });
    window.location.reload();
};

const completeTask = id => {
    fetch(`/api/task/markAsCompleted/${id}`);
    window.location.reload();
};

const deleteTask = id => {
    fetch(`/api/task/markAsDeleted/${id}/true`);
    window.location.reload();
};

const revertTask = id => {
    fetch(`/api/task/markAsDeleted/${id}/false`);
    window.location.reload();
};

const basketDelTask = id => {
    fetch(`/api/task/remove/${id}`, {
        method: 'DELETE'
    });
    window.location.reload();
};

const clearBasket = () => {
    fetch('/api/task/removeAll', {
        method: 'DELETE'
    });
    window.location.reload();
};
