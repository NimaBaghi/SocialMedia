<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form action="register" method="post" enctype="multipart/form-data">
    <center>

        Username: <input type="text" name="username">
        <br>
        <br>
        Full Name: <input type="text" name="fullname">
        <br>
        <br>
        Password: <input type="password" name="password">
        <br>
        <br>
        Profile Privacy:<br>
        <input type="radio" name="privacy" value="public"> Public <br>
        <input type="radio" name="privacy" value="private"> Private <br>
        <br>
        <br>

        <input type="file" name="image" onchange="previewFile()"><br>
        <br>
        <br>
        <img src="" height="200" width="200" alt="Image preview...">
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

        <input type="submit" value="Sign up"><br>
        <br>

        Have an account? <a href="login.jsp">Log in</a>

    </center>
</form>
</body>
</html>
