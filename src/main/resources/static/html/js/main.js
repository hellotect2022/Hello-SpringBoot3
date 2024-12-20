import HomeComponent from "./components/home.js";
import ChatComponent from "./components/chat.js";
import SettingsComponent from "./components/setting.js";
import RoomComponent from "./components/room.js";

// 현재 활성화된 콘텐츠를 업데이트하는 함수
function updateContent(component,params) {
  const contentElement = document.getElementById("component");
  contentElement.innerHTML = component.template;
  if (component.onMount) {
    component.onMount(params); // onMount 실행
  }
}

function updateContentChat(component,params) {
  const contentElement = document.getElementById("chat");
  contentElement.innerHTML = component.template;
  if (component.onMount) {
    component.onMount(params); // onMount 실행
  }
}

document.addEventListener("DOMContentLoaded", () => {
  const buttons = document.querySelectorAll(".nav-btn");
  const content = document.getElementById("component");

  // 탭별 컴포넌트 매핑
  const components = {
    home: HomeComponent,
    room: RoomComponent,
    settings: SettingsComponent,
    chat: ChatComponent
  };

  // 기본 화면: 홈 컴포넌트 로드
  content.innerHTML = components.home.template;

  // 버튼 클릭 이벤트 설정
  buttons.forEach((button) => {
    button.addEventListener("click", (params) => {
      // 모든 버튼의 활성화 상태 초기화
      buttons.forEach((btn) => btn.classList.remove("active"));
      // 현재 클릭된 버튼 활성화
      button.classList.add("active");

      // data-tab 속성 값에 따라 컴포넌트 변경
      const tab = button.getAttribute("data-tab");
      if (params.detail.tabName) {
          // 채팅 화면 활성화
          chat.style.display = "block"; // 보이기
          const component = components["chat"];
          updateContentChat(component,params.detail);
      } else {
          // 채팅 화면 숨기기
          chat.style.display = "none"; // 숨기기
          const tab = button.getAttribute("data-tab");
          const component = components[tab];
          if (component) {
            updateContent(component); // 컴포넌트 업데이트
          }
      }
    });
  });
});

