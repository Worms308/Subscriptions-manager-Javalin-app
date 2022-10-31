const ws = new WebSocket("ws://localhost:8080/changed-subscription/1");

const chat = document.getElementById("chat");

ws.onopen = () => createNewLog("Connected");
ws.onmessage = msg => createNewLog(msg.data)
ws.onclose = () => createNewLog("Disconnected");

function createNewLog(logText) {
    const el = document.createElement('p');
    el.innerText = logText;
    chat.appendChild(el);
}