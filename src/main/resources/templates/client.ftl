<!DOCTYPE html>
 <html>
 <head>
     <title>Java后端WebSocket的Tomcat实现</title>
     <script type="text/javascript" src="jquery.js"></script>
     <script type="text/javascript" src="base64.js"></script>
     <script type="text/javascript">
         var websocket = null;
         var fromAccount;
         var fromNickname;
         //判断当前浏览器是否支持WebSocket
         if ('WebSocket' in window) {
         }
         else {
             alert('当前浏览器 Not support websocket')
         }

         function connect() {
             var domain = document.domain;
             console.log(domain);
             var token = localStorage.getItem("token");
             websocket = new WebSocket("wss://"+domain+"/connect/" + token);
             //连接发生错误的回调方法
             websocket.onerror = function () {
                 setMessageInnerHTML("WebSocket连接发生错误");
             };

             //连接成功建立的回调方法
             websocket.onopen = function () {
                 setMessageInnerHTML("WebSocket连接成功");
             };

             //接收到消息的回调方法
             websocket.onmessage = function (event) {
                 var msg = JSON.parse(event.data);
                 var str = "--" + new Date(msg.time).toLocaleString() + "-- " + msg.fromNickname + " : " + msg.data;
                 setMessageInnerHTML(str);
             };

             //连接关闭的回调方法
             websocket.onclose = function () {
                 setMessageInnerHTML("WebSocket连接断开");
                 connect();
             };

             //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
             window.onbeforeunload = function () {
                 closeWebSocket();
             }


         }

         //将消息显示在网页上
         function setMessageInnerHTML(innerHTML) {
             document.getElementById('message').innerHTML += innerHTML + '<br/>';
         }

         //关闭WebSocket连接
         function closeWebSocket() {
             websocket.close();
         }

         //发送消息
         function send() {
             group = $("#group").val();
             var data = document.getElementById('text').value;
             var msg = {
                 "fromAccount": fromAccount,
                 "fromNickname": fromNickname,
                 "toGroupId": group,
                 "data": data,
                 'time': new Date().getTime()
             };
             websocket.send(JSON.stringify(msg));
             $('#text').val('');
         }

         function login() {
             $.post('/auth/login', $("#form").serialize(), function (res) {
                 if (res.code === 200) {
                     localStorage.setItem("token", res.data);
                     base64 = new Base64();
                     info = JSON.parse(base64.decode(res.data.split('.')[1]).replace('\0', ''));
                     fromAccount = info.account;
                     fromNickname = info.nickname;
                     connect();
                     alert("登录成功");
                 } else {
                     alert("登录失败：" + res.msg);
                 }
             })
         }
     </script>
 </head>

 <body>
 <h2>Welcome!</h2>
 <hr>
 <form id="form">
     account<input name="account"/><br/>
     password<input name="pwd" type="password"/><br/>
     imei<input name="imei" value="123321"/><br/>
 </form>
 <button onclick="login()">login</button>
 <br/>
 <hr/>
 toGroupId<input id="group"/><br/>
 <textarea id="text" rows=10 cols=50 onkeyup="new function enter(e) {
        var event = window.event||e;
        var code = event.keyCode||event.witch;
        if(code===13){
            send();
        }
         }"></textarea><br/>
 <button onclick="send()">发送消息</button>
 <hr/>
 <button onclick="closeWebSocket()">关闭WebSocket连接</button>
 <hr/>
 <div id="message"></div>
 </body>


 </html>
