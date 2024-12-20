document.getElementById('signupForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const form = document.getElementById('signupForm');
    fetch("http://10.10.27.18:8111/na/nf/signUp", {
      method: 'POST', // HTTP 메서드
      headers: {
        'Device-Type' : 'pc',
        'Content-Type': 'application/json' // JSON 형식으로 보낼 경우
      },
      body: formToJSON(form) // 데이터를 JSON으로 변환
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
        }else {
            alert(`[${data.errorCode}] `+ data.errorMessage)
        }
      })
      .catch(error => {
        console.error('There was an error!', error);
      });
});

// API URL
// 폼 데이터 JSON으로 변환하는 함수
function formToJSON(formElement) {
  const formData = new FormData(formElement); // FormData 객체 생성
  const formObject = {}; // 데이터를 저장할 빈 객체

  // FormData 객체를 순회하며 값을 formObject에 추가
  formData.forEach((value, key) => {
    formObject[key] = value;
  });

  // 객체를 JSON 문자열로 변환
  return JSON.stringify(formObject);
}