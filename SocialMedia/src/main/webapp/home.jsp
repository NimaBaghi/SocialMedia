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
            }
            else {
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
<CENTER>
    <div id="myDiv"></div>
    <button type="button" onclick='loadXMLDoc()'> Like</button>
</CENTER>
<a href="upload.jsp"> Add a post</a>
<br>
<a href="search.jsp"> Search</a>

</body>
</html>