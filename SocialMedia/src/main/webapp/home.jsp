<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home</title>
    <script type="text/javascript">

        function loadXMLDoc() {
            var xmlhttp;
            var url = "like";
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest();
            } else {
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    document.getElementById("myDiv").innerHTML = xmlhttp.responseText;
                }
            }
            xmlhttp.open("Post", url, true);
            xmlhttp.send();
        }
    </script>
</head>
<body>
<center>
    <button id="addPost">Add a post</button>
    <button id="search">Search</button>
    <button id="rList">Requests List</button>
    <button id="fList">Friends List</button>

    <script type="text/javascript">
        document.getElementById("addPost").onclick = function () {
            location.href = "upload.jsp";
        };
        document.getElementById("search").onclick = function () {
            location.href = "search.jsp";
        };
        document.getElementById("rList").onclick = function () {
            location.href = "requestList";
        };
        document.getElementById("fList").onclick = function () {
            location.href = "friendsList";
        };
    </script>
</center>

<br>
<center>
    <div id="myDiv"></div>
    <button type="button" onclick='loadXMLDoc()'> Like</button>
</center>
</body>
</html>