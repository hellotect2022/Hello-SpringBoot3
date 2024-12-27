// components/chat.js
async function getRooms(params) {

    const response = await fetch('http://10.10.27.18:8111/room/getbyuser', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json',
            'Device-Type': 'pc'
        },
        body: JSON.stringify({}) // 필요한 데이터
    });

    if (!response.ok) {
        throw new Error(`Error: ${response.status}`);
    }

    const result = await response.json();
    console.log('/room/getbyuser ->',result);
    renderRoom(result.data);
}


  function renderRoom(data) {
    const roomListElement = document.querySelector(".room-list");
    roomListElement.innerHTML = "";
    data.forEach((room) => {
      console.log("??",room)
      // 채팅방 요소 생성
      const chatItem = document.createElement("div");
      chatItem.className = "chat-item";
      chatItem.addEventListener("click", (event) => {
          const name = event.currentTarget.querySelector(".chat-name").textContent;
          const roomId = event.currentTarget.querySelector(".user-id").textContent;

          const chatButton = document.querySelector('.nav-btn[data-tab="room"]');
          if (chatButton) {
             const params = { userId:room.members.filter(member => member !== localStorage.getItem('accessId'))[0],
                            roomId: roomId,
                            userName:name,
                            tabName: 'chat' }
             const event = new CustomEvent("click", {
                 detail: params, // 전달할 데이터 추가
             });
             chatButton.dispatchEvent(event); // 이벤트 디스패치
          }


      });

      // 아바타 생성
      const avatar = document.createElement("div");
      avatar.className = "avatar";
      const defaultAvatar = "https://via.placeholder.com/50"; // 기본 이미지
      avatar.style.backgroundImage = `url(${room.profileImage || defaultAvatar})`;

      // 채팅 정보 생성
      const chatInfo = document.createElement("div");
      chatInfo.className = "chat-info";

      // 채팅방 이름 설정
      const chatName = document.createElement("p");
      chatName.className = "chat-name";
      chatName.textContent = room.title; // 채팅방 이름 (JSON 데이터의 title)

      // 마지막 메시지 표시
      const lastMessage = document.createElement("p");
      lastMessage.className = "last-message";
      lastMessage.textContent = room.lastMessage?.message || "No message yet"; // JSON의 lastMessage 내용 표시

      // 사용자 ID 정보 (숨김)
      const userIdInfo = document.createElement("div");
      userIdInfo.className = "user-id";
      userIdInfo.textContent = room.roomId; // JSON의 roomId 사용
      userIdInfo.hidden = true;

      // 요소 조합
      chatInfo.appendChild(chatName);
      chatInfo.appendChild(lastMessage);
      chatItem.appendChild(avatar);
      chatItem.appendChild(chatInfo);
      chatItem.appendChild(userIdInfo);

      // chat-item을 chat-list에 추가
      roomListElement.appendChild(chatItem);
    });
  }



const RoomComponent = {
  template: `
    <div class="room-list">

    </div>
  `,
  onMount(params) {
    getRooms(params)
  },
};

export default RoomComponent;
