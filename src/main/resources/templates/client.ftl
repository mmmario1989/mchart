<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WebSocket</title>
</head>
<body>
    <input id="text" type="text"/>
    <button onclick="send()">发送消息</button>
    <hr/>
<button onclick="closeWebSocket()">关闭WebSockeet连接</button>
<hr/>
<div id="message"></div>
</body>
</html>
<script type="text/javascript">
    var  webSocket=null;

    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window){
        webSocket=new WebSocket('ws://localhost:8080/webSocket/1');
    } else{
        alert("当前浏览器不支持WebSocket");
    }

    //连接发生错误的回调方法
    webSocket.onerror=function () {
        setMessageInnerHTML("WebSocket连接发生错误！");
    }

    webSocket.onopen=function () {
        setMessageInnerHTML("WebSocket连接成功！")
    }

    webSocket.onmessage=function (event) {
        setMessageInnerHTML(event.data);
    }

    webSocket.onclose=function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    window.onbeforeunload=function () {
        closeWebSocket();
    }

    function closeWebSocket() {
        webSocket.close();
    }

    function send() {
        var message=document.getElementById('text').value;
        webSocket.send(message);
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML+=innerHTML+'<br/>';
    }
</script>