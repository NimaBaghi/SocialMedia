<%@ page import="org.socialMedia.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings</title>
    <% User user = (User) request.getSession().getAttribute("userDetails");%>
</head>
<body>
<center>
    <button id="goHome">Home</button>
    <button id="mypro">My Profile</button>

    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
        document.getElementById("mypro").onclick = function () {
            location.href = "myProfile.jsp";
        };
    </script>
    <br>
    <br>


    <form action="setting" method="post" enctype="multipart/form-data">
        Profile Privacy:
        <br>

        <% if (user.getProfilePrivacy() == 0) {%>
        <input type="radio" name="privacy" value="1"> Public <br>
        <input type="radio" name="privacy" value="0" checked> Private <br>
        <% } else {%>
        <input type="radio" name="privacy" value="1" checked> Public <br>
        <input type="radio" name="privacy" value="0"> Private <br>
        <% }%>

        <br>

        Profile Picture:
        <br>
        <br>
        <input type="file" name="file" onchange="previewFile()"><br>
        <br>
        <br>
        <img src="" width="400" height="400" alt="Image preview...">
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
        <br>
        <input type="submit" value="Save">
    </form>
</center>
</body>
</html>
