// components/chat.js
function exitChatView() {
    console.log("wow!!!");
    document.querySelector("#chat").style.display = "none"; // 숨기기
}

async function sendMessage(params) {
    var input = document.querySelector("#chat-input");
    var roomId = document.querySelector("#chat-room-id");
    console.log("wow you send message!!!",input.value);

    const obj = {
        roomId:roomId.value,
        senderId: localStorage.getItem('accessId'),
        targetId: params.userId,
        message: input.value,
        sendTime: new Date()
    }

    const response = await fetch('http://10.10.27.18:8111/message/send', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json',
            'Device-Type': 'pc'
        },
        body: JSON.stringify(obj) // 필요한 데이터
    });

    if (!response.ok) {
        throw new Error(`Error: ${response.status}`);
    }

    const result = await response.json();
    console.log('/message/send return ->',result);
    document.querySelector("#chat-room-id").value=result.data.roomId;
    input.value = "";
}

const ChatComponent = {
  template: `
    <input id="chat-room-id" hidden>
    <div class="chat-container">
      <div class="chat-header">
        <button id="back-btn">←</button>
        <p id="chat-view-header"></p>
      </div>
      <div class="chat-list" id="chat-list">
          <!-- 메시지가 동적으로 추가됩니다 -->
      </div>
      <div class="chat-input-container">
        <input type="text" id="chat-input" placeholder="Type a message">
        <button id="send-btn">Send</button>
      </div>
    </div>
    <link rel="stylesheet" href="css/chat.css">
  `,
  onMount(params) {
    const ptag = document.querySelector("#chat-view-header") // 채팅방 이름
    ptag.innerText = params.userName + "님과 대화"
    console.log("와우 이건 뭐 ...",params.roomId,params)
    document.querySelector("#chat-room-id").value = params.roomId ? params.roomId:'';

    const backButton = document.querySelector("#back-btn");    // 뒤로가기 버튼
    const sendButton = document.querySelector("#send-btn");    // 메세지 보내기 버튼
    //console.log('backButton->',backButton);
    backButton.addEventListener("click", exitChatView);
    sendButton.addEventListener("click", () => sendMessage(params));
    // 여기에 새로운 채팅 창을 만드는 거지
  },
};



export default ChatComponent;
