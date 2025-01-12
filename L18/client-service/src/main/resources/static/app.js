const SOCKET_URL = '/gs-guide-websocket';

const CONNECT = "connect";
const DISCONNECT = "disconnect";
const CHAT_TABLE_ID = "chatLine";
const ROOM_ID_INPUT_ID = "roomId";
const MESSAGE_INPUT_ID = "message";

let stompClient = null;
const subscriptions = new Map();

const setConnected = (connected) => {
  const connectBtn = document.getElementById(CONNECT);
  const disconnectBtn = document.getElementById(DISCONNECT);
  const chatLine = document.getElementById(CHAT_TABLE_ID);

  connectBtn.disabled = connected;
  disconnectBtn.disabled = !connected;
  chatLine.hidden = !connected;
};

const clearChatTable = () => {
  const chatTable = document.getElementById(CHAT_TABLE_ID);
  while (chatTable.rows.length > 0) {
    chatTable.deleteRow(0);
  }
};

const createTopicName = (roomId) => `/topic/response.${roomId}`;

const createUserTopicName = (userName, topicName) => `/user/${userName}${topicName}`;

const createMessagePayload = (message) => JSON.stringify({'text': message});

const subscribeToTopic = (stompClient, topicName, callback) => {
  return stompClient.subscribe(topicName, (message) => callback(JSON.parse(message.body).text));
};

const subscribeToAllTopics = (stompClient, roomId, userName) => {
  const topicName = createTopicName(roomId);
  const topicNameUser = createUserTopicName(userName, topicName);

  const sub1 = subscribeToTopic(stompClient, topicName, showMessage);
  const sub2 = subscribeToTopic(stompClient, topicNameUser, showMessage);

  subscriptions.set(topicName, sub1);
  subscriptions.set(topicNameUser, sub2);
};

const connect = () => {
  disconnect();

  stompClient = Stomp.over(new SockJS(SOCKET_URL));

  stompClient.connect(
    {},
    (frame) => {
      setConnected(true);
      const userName = frame.headers["user-name"];
      const roomId = document.getElementById(ROOM_ID_INPUT_ID).value;
      console.log(`Connected to roomId: ${roomId} frame:${frame}`);
      subscribeToAllTopics(stompClient, roomId, userName);
      clearChatTable();
    },
    (error) => {
      console.error("Failed to connect:", error);
      disconnect();
    }
  );
};


const unsubscribeFromAll = () => {
  subscriptions.forEach(subscription => subscription.unsubscribe());
  subscriptions.clear();
};

const disconnect = () => {
  unsubscribeFromAll();
  if (stompClient) {
    stompClient.disconnect();
  }
  stompClient = null;
  setConnected(false);
  console.log("Disconnected");
};

const sendMessage = () => {
  const roomId = document.getElementById(ROOM_ID_INPUT_ID).value;
  const message = document.getElementById(MESSAGE_INPUT_ID).value;
  stompClient.send(`/app/message.${roomId}`, {}, createMessagePayload(message));
};

const showMessage = (message) => {
  const chatTable = document.getElementById(CHAT_TABLE_ID);
  const newRow = chatTable.insertRow(-1);
  const newCell = newRow.insertCell(0);
  const newText = document.createTextNode(message);
  newCell.appendChild(newText);
};
