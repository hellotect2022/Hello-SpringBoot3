// components/chat.js
const RoomComponent = {
  template: `
    <div class="chat-list">

    </div>
  `,
  onMount(params) {
    console.log(params)
    // 컴포넌트가 활성화될 때 API 요청
    //alert(params.userId, params.tabName);
  },
};

export default RoomComponent;
