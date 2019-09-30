const tableBodyTemplate = `<table class="table table-hover mt-1">
                          <thead>
                            <tr>
                              <th scope="col" class="col-1 align-middle">#</th>
                              <th scope="col" class="col-1 align-middle">Task name</th>
                              <th scope="col" class="col-5 align-middle">Description</th>
                              <th scope="col" class="col-1 align-middle">Date</th>
                              <th scope="col" class="col-2 align-middle" colspan=2>File</th>
                              <th scope="col" class="col-1 align-middle">Action</th>
                            </tr>
                          </thead>
                          <tbody class="table-body"></tbody>
                          </table>`;

const basketTableBodyTemplate = `<table class="table table-hover mt-1">
                                <thead>
                                  <tr>
                                    <th scope="col" class="col-1 align-middle">#</th>
                                    <th scope="col" class="col-1 align-middle">Task name</th>
                                    <th scope="col" class="col-5 align-middle">Description</th>
                                    <th scope="col" class="col-1 align-middle">Date</th>
                                    <th scope="col" class="col-2 align-middle" colspan=2>File</th>
                                    <th scope="col" class="col-1 align-middle">Action</th>
                                  </tr>
                                </thead>
                                <tbody class="table-body"></tbody>
                                <tfoot>
                                <tr>
                                  <td colspan=7>
                                    <button type="button" onclick="clearBasket()" class="btn btn-danger">Clear basket</button>
                                  </td>
                                </tr>
                                </tfoot>
                                </table>`;

const tableRowTemplate = `<th scope="row">_numberTask_</th>
                          <td>_taskName_</td>
                          <td>_taskDescription_</td>
                          <td class="align-middle">_taskDate_</td>
                          <td class="align-middle">_linkPlace_</td>
                          <td class="align-middle">
                            <div class="btn-group" role="group">
                              _btnAddFile_
                              _btnDelFile_
                            </div>
                          </td>
                          <td class="align-middle">
                            <div class="btn-group" role="group">
                              _btnCompleteTask_
                              _btnDeleteTask_
                            </div>
                          </td>`;

const completedTableBodyTemplate = `<table class="table table-hover mt-1">
                                <thead>
                                  <tr>
                                    <th scope="col" class="col-1 align-middle">#</th>
                                    <th scope="col" class="col-1 align-middle">Task name</th>
                                    <th scope="col" class="col-5 align-middle">Description</th>
                                    <th scope="col" class="col-1 align-middle">Date</th>
                                    <th scope="col" class="col-2 align-middle">File</th>
                                  </tr>
                                </thead>
                                <tbody class="table-body"></tbody>
                                </table>`;

const completedTableRowTemplate = `<th scope="row">_numberTask_</th>
                          <td>_taskName_</td>
                          <td>_taskDescription_</td>
                          <td class="align-middle">_taskDate_</td>
                          <td class="align-middle">_linkPlace_</td>`;

const imgTemplate = (id) => `<a href="/api/file/downloadFile/${id}" target="blank">
                      <img src="/api/file/getFile/${id}" width="50px" height="50px" />
                    </a>`;

const fileTemplate = '<a href="/api/file/downloadFile/_taskId_">_fileName_</a>';
