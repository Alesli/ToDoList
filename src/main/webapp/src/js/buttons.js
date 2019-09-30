const btnDisable = name => `<button type="button" class="btn btn-secondary disabled" disabled>${name}</button>`;

const btnDeleteFile = id => `<button type="button" onclick="delFile(${id})" class="btn btn-danger">Del</button>`;

const btnAddFile = id => `<button type="button" onclick="addFile(${id})" class="btn btn-success">Add</button>`;

const btnCompleteTask = id => `<button type="button" onclick="completeTask(${id})" class="btn btn-success">Complete</button>`;

const btnDeleteTask = id => `<button type="button" onclick="deleteTask(${id})" class="btn btn-danger">Delete</button>`;

const btnRevertTask = id => `<button type="button" onclick="revertTask(${id})" class="btn btn-success">Revert</button>`;

const btnBasketDelTask = id => `<button type="button" onclick="basketDelTask(${id})" class="btn btn-danger">Delete</button>`;
