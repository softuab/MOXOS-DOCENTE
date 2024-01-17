<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">
<head>
    <title>Sistema - Moxos</title>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link href="<c:url value="/public/css/main.css" />" rel="stylesheet">
    <link href="<c:url value="/public/css/font-awesome.min.css" />" rel="stylesheet">
    <link href="<c:url value="/static/css/cropper.min.css" />" rel="stylesheet">
    <link href="<c:url value="/static/css/circle-upload.css?v=4" />" rel="stylesheet">
</head>
<body>
<div id="loader"></div>
<div class="container">
    <div class="px-4 py-5 my-5 text-center">
        <div class="circulo-container">
            <div id="circulo"></div>
        </div>
        <h1 class="display-5 fw-bold">BIENVENIDO</h1>
        <div class="col-lg-6 mx-auto">
            <p class="lead mb-4">${snombre}</p>
        </div>
    </div>
</div>
<script src="<c:url value="/public/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/public/js/popper.min.js" />"></script>
<script src="<c:url value="/static/js/cropper.min.js" />"></script>
<script src="<c:url value="/static/js/circle-upload.js?v=4" />"></script>
<script src="<c:url value="static/js/loader.js?v=3" />"></script>
<script>
    let loadercontent = new Loader(document.getElementById('loader'), { textAction: 'Cargando...', UrlImage: '<c:url value="/static/image/logominiatura.png" />' });
    var fileupload = new Upload(document.getElementById('circulo'), {
        src: '${simagen}',
        maxSize: 52428800,
        upload(event) {
            let base64Image = event.src;
            event.progressBar.classList.remove('hidden');

            // Convert base64 string to blob
            const byteCharacters = atob(base64Image.split(',')[1]);
            const byteArrays = [];
            for (let i = 0; i < byteCharacters.length; i++) {
                byteArrays.push(byteCharacters.charCodeAt(i));
            }
            const blob = new Blob([new Uint8Array(byteArrays)], {type: 'image/jpeg'});

            // Create form data and append blob to it
            const formData = new FormData();
            formData.append('filedocente', blob, 'image.jpeg');

            // Send form data to server using XMLHttpRequest
            const xhr = new XMLHttpRequest();
            xhr.open('POST', '/upload-image', true);

            const metaEtiqueta = document.querySelector("meta[name='_csrf']");
            const tokenCSRF = metaEtiqueta.getAttribute("content"); // Obtener el token CSRF de tu servidor
            xhr.setRequestHeader('X-CSRF-Token', tokenCSRF);

            const progressBar = event.progressBar;
            // Update progress bar as upload progresses
            xhr.upload.addEventListener('progress', (event) => {
                const percent = (event.loaded / event.total) * 100;
                progressBar.querySelector('[data-span="true"]').innerHTML = percent + "%";
                if (percent >= 100) {
                    progressBar.classList.add('hidden');
                }
            });
            xhr.onload = () => {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.responseText);
                    if (response.status === "OK") {
                        window.parent.document.getElementById('idimagenpersona').src = response.message;
                    }
                } else {
                    console.error('Image upload failed');
                }
            };
            xhr.send(formData);
        }
    });
    document.addEventListener("DOMContentLoaded", function () {
        loadercontent.show();
        setTimeout(function () {
            loadercontent.hide();
        }, 2700);
    });
    document.oncontextmenu = function () {
        return false;
    }
</script>
</body>
</html>
