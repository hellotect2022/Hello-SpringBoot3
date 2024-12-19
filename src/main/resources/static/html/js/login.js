function login() {
    const id = document.getElementById("id").value;
    const password = document.getElementById("password").value;
    if (id && password) {
        fetch("http://10.10.27.18:8111/na/nf/login", {
          method: 'POST', // HTTP 메서드
          headers: {
            'Device-Type' : 'pc',
            'Content-Type': 'application/json' // JSON 형식으로 보낼 경우
          },
          body: JSON.stringify({"userId":id, "passwd":password}) // 데이터를 JSON으로 변환
        })
          .then(response => {
            if (!response.ok) {
              throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
          })
          .then(data => {
            console.log('Response:', data);
            if (data.status == "SUCCESS") {
                alert(data.data);
                localStorage.setItem('token', data.data);
                window.location.href = "/html/main.html";
            }else {
                alert(`[${data.errorCode}] `+ data.errorMessage)
            }
          })
          .catch(error => {
            console.error('There was an error!', error);
          });

    } else {
        alert("ID와 비밀번호를 입력하세요.");
    }
}

function googleSSO() {
    alert("구글 SSO 인증 창이 열립니다.");
}

function goToSignup() {
    alert("회원가입 페이지로 이동합니다.");
    window.location.href = "/view/signUp.html"; // 실제 회원가입 페이지로 이동할 때 사용
}