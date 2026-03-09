let userInfo = {};
let webSocket;

fetch('/api/account/currentUser').then(res => res.json()).then(res => {
  if (!res.data) {
    console.error('未登录');
    return;
  }

  userInfo = res.data;
  initWebSocket();
});

function appendMsg(username, message, me) {
  document.getElementById('tbody').innerHTML = document.getElementById('tbody').innerHTML + `
    <tr>
        <td>${username || ''}</td>
        <td>${message || ''}</td>
        <td>${me || ''}</td>
      </tr>
    `
}

function initWebSocket() {
  // websocket初始化
  const webSocketUrl = `${window.location.origin.replace(
    'http',
    'ws'
  )}/websocket/chat/${userInfo.id}`;
  webSocket = new ReconnectingWebSocket(webSocketUrl, null, {
    debug: true,
    reconnectInterval: 3000,
  });
  webSocket.onmessage = e => {
    const resText = e.data;
    console.log('WebSocket.onmessage: ', resText);
    if (
      resText &&
      resText[0] === '{' &&
      resText[resText.length - 1] === '}'
    ) {
      const res = JSON.parse(resText);
      if (res.action === 'msg') {
        appendMsg(res.data.username, res.data.message, '');
      }
    }
  };
  setInterval(() => {
    webSocket.send(JSON.stringify({ action: 'heartBeat' }));
  }, 30 * 1000);
}

const submitEl = document.getElementById('submit');
const inputEl = document.getElementById('input');
submitEl.addEventListener('click', function () {
  console.log('提交信息');
  const message = inputEl.value.trim();
  if (!message) return;

  webSocket.send(JSON.stringify({action: 'msg', data: {userId: userInfo.id, username: userInfo.username, message}}));
  appendMsg('', message, 'me');
  inputEl.value = '';
}, false);
