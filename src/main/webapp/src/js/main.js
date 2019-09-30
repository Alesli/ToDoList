const getFormFields = () => ({
    name: document.querySelector('.task-name').value,
    description: document.querySelector('.task-description').value,
    eventDate: new Date(document.querySelector('.task-date').value).toISOString(),
});

taskForm.onsubmit = async(e) => {
    e.preventDefault();
    const file = document.querySelector('.form-file').files[0];
    // const file2 = document.getElementById('file').files[0];
    const responseId = await fetch('/api/task/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        },
        body: JSON.stringify(await getFormFields()),
    });

    const resp = await responseId.json();
    if (file) {
        const fileForm = new FormData();
        const fileArr = [file];
        fileForm.append('file', fileArr[0]);

        await fetch(`/api/file/saveFile/${resp.id}`, {
            method: 'POST',
            body: fileForm,
        });
    }
    window.location.reload();
};

