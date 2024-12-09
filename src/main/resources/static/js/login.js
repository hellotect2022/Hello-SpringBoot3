function login() {
    const id = document.getElementById("id").value;
    const password = document.getElementById("password").value;
    if (id && password) {
        alert(`ID: ${id}, Password: ${password}`);
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