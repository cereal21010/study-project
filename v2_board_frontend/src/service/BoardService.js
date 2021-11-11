import axios from "../utils/AxiosInterceptors";

export class BoardService {
    constructor(host) {
        this.host = host;
        this.testUrl = 'http://localhost'
    }

    moreGetBoardList(params) {
        return axios
            .get(this.testUrl + `/api/board/moreList`, {params: params})
            .then(response => {
                return response.data;
            })
            .catch((e) => {
                console.log(e)
            });
    }

    getBoardList(params) {
        return axios
            .get(this.testUrl + `/api/board/list`, {params: params})
            .then(response => {
                return response.data;
            })
            .catch((e) => {
                console.log(e)
            });
    }


    insertBoard(boardDetail, uploadFiles) {

        const formData = new FormData();
        // formData.append('title', boardDetail.title );
        // formData.append('contents', boardDetail.contents);
        // formData.append('category', boardDetail.category);
        const json = JSON.stringify(boardDetail);
        const blob = new Blob([json], {
            type: 'application/json'
        });
        formData.append('requestBody', blob);
        if (uploadFiles.length > 0) {
            for (let index = 0; index < uploadFiles.length; index++) {
                formData.append('files', uploadFiles[index]);
            }
        }


        return axios
            .post(this.testUrl + `/api/board/insert`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            })
            .then(response => {
                console.log('>>== response', response);
                return response;
            })
            .catch((e) => {
                console.log(e);
                throw new Error("게시글 작성에 실패 하셨습니다.")
            });
    }

    getBoardDetail(seq, loginId) {
        return axios
            .get(this.testUrl + `/api/board/detail/${seq}/${loginId}`)
            .then(response => {
                return response.data;
            })
            .catch((e) => {
                console.log(e);
                return {
                    boardDetail: {
                        error: true
                    }
                }
            });
    }

    deleteBoard(seq) {
        return axios
            .delete(this.testUrl + `/api/board/delete/` + seq)
            .then(response => {
                return response;
            })
            .catch((e) => {
                console.log(e);
            });
    }

    updateBoard(boardDetail, uploadFiles, deleteFileList) {
        const formData = new FormData();
        const json = JSON.stringify(boardDetail);
        const blob = new Blob([json], {
            type: 'application/json'
        });
        formData.append('requestBody', blob);
        if (uploadFiles.length > 0) {
            for (let index = 0; index < uploadFiles.length; index++) {
                formData.append('files', uploadFiles[index]);
            }
        }
        formData.append('deleteFileList', deleteFileList);

        return axios
            .put(this.testUrl + `/api/board/update`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            })
            .then(response => {
                console.log('>>== response', response);
                return response;
            })
            .catch((e) => {
                console.log(e);
                throw new Error("게시글 수정에 실패 했습니다.")
            });
    }

    downloadFile(fileSeq) {
        return axios
            .get(this.testUrl + `/api/board/downloadFile`, {
                params: {seq: fileSeq},
                responseType: 'arraybuffer'
            })
            .then(response => {
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                const contentDisposition = response.headers['content-disposition'];
                let fileName = 'unknown';
                if (contentDisposition) {
                    const [fileNameMatch] = contentDisposition.split(';').filter(str => str.includes('filename'));
                    if (fileNameMatch) [, fileName] = fileNameMatch.split('=');
                }
                link.href = url;
                fileName = fileName.replace(/"/gi, "");
                fileName = fileName.replace(/\+/g, " ");
                fileName = decodeURIComponent(fileName)
                link.setAttribute('download', `${fileName}`);
                link.style.cssText = 'display:none';
                document.body.appendChild(link);
                link.click();
                link.remove();
            })
            .catch((e) => {
                console.log(e);
            });

    }

    getChangedBoardList(seq) {
        return axios
            .get(this.testUrl + `/api/board/changes/${seq}`)
            .then(response => {
                return response.data;
            })
            .catch((e) => {
                console.log(e);
            });
    }

    passwordCheck(boardSeq, inputPassword) {
        return axios
            .get(this.testUrl + `/api/board/passwordCheck/${boardSeq}`, {
                params: {
                    inputPassword: inputPassword,
                }
            })
            .then(response => {
                console.log(`>>== response`, response)
                return response.data.passwordCheck
            })
            .catch((e) => {
                console.log(e);
            });

    }


}