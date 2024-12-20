// components/home.js

async function fetchAndUpdateMessage() {
    try {
    // JWT token 가져오기
        const token = localStorage.getItem('token')
        const response = await fetch('http://10.10.27.18:8111/user/getAll', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
                'Device-Type': 'pc'
            },
            body: JSON.stringify({}) // 필요한 데이터
        });

        //console.log("response: " + response.status)

        if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
        }


        const data = await response.json();
        //console.log(data.data)
        renderUsers(data.data)
    } catch (error) {
        console.error('error->', error.name);
        console.error('error->', error.message);
    }
}

document.addEventListener('DOMContentLoaded', fetchAndUpdateMessage);

// 사용자 데이터를 DOM에 렌더링하는 함수
  function renderUsers(users) {
    const chatListElement = document.querySelector(".chat-list");
    chatListElement.innerHTML = ""; // 기존 내용 초기화
    users.forEach((user) => {
      //console.log('user=> ', user)
      const chatItem = document.createElement("div");
      chatItem.className = "chat-item";
      chatItem.addEventListener("click", (event) => {
        const name = event.currentTarget.querySelector(".chat-name").textContent;
        const userId = event.currentTarget.querySelector(".user-id").textContent;
        const isConfirmed = confirm(name,userId);

        if (isConfirmed) {
          const chatButton = document.querySelector('.nav-btn[data-tab="room"]');
            if (chatButton) {
              const params = { userId: userId, userName:name, tabName: 'chat' }
              const event = new CustomEvent("click", {
                  detail: params, // 전달할 데이터 추가
                });

                chatButton.dispatchEvent(event); // 이벤트 디스패치
            }
        } else {
          console.log("Cancelled!");
        }

      });

      // 아바타 생성
      const avatar = document.createElement("div");
      avatar.className = "avatar";
      avatar.style.backgroundImage = `url(${user.avatarUrl || "https://via.placeholder.com/50"})`;

      // 사용자 정보 생성
      const chatInfo = document.createElement("div");
      chatInfo.className = "chat-info";

      const userIdInfo = document.createElement("div");
      userIdInfo.className = "user-id";
      userIdInfo.textContent = user.userId;
      userIdInfo.hidden=true;

      const chatName = document.createElement("p");
      chatName.className = "chat-name";
      chatName.textContent = user.name;

      const lastMessage = document.createElement("p");
      lastMessage.className = "last-message";
      lastMessage.textContent = user.lastMessage;

      // 요소를 조합하여 chat-item 생성
      chatInfo.appendChild(chatName);
      chatInfo.appendChild(lastMessage);
      chatItem.appendChild(avatar);
      chatItem.appendChild(chatInfo);
      chatItem.appendChild(userIdInfo);

      // chat-item을 chat-list에 추가
      chatListElement.appendChild(chatItem);
    });
  }

const HomeComponent = {
  template: `
    <div class="chat-list">

    </div>
  `,
  onMount() {
    // 컴포넌트가 활성화될 때 API 요청
    fetchAndUpdateMessage();
  },
};
export default HomeComponent;
