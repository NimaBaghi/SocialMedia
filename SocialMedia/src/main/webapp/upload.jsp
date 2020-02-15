<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Post Details</title>
</head>
<body>

<center>
    <form action="upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" onchange="previewFile()"><br>
        <br>
        <br>
        <img src="" width="800" height="800" alt="Image preview...">
        <br>
        <br>
        <script type="text/javascript">
            function previewFile() {
                const preview = document.querySelector('img');
                const file = document.querySelector('input[type=file]').files[0];
                const reader = new FileReader();
                reader.addEventListener("load", function () {
                    // convert image file to base64 string
                    preview.src = reader.result;
                });
                if (file) {
                    reader.readAsDataURL(file);
                }
            }
        </script>
        <br>

        Caption:
        <br>
        <textarea name="caption" rows="3" cols="50"></textarea>

        <br>
        <br>

        Hashtags:
        <br>
        <input type="text" name="hashtag" value="#" size="50">

        <br>
        <br>
        <input type="submit"/>
    </form>
</center>

</body>
</html>
