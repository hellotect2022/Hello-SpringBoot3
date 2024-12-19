async function fetchAndUpdateMessage() {
    try {
    // JWT token 가져오기
        const token = localStorage.getItem('token')
        const response = await fetch('http://10.10.27.18:8111/room/users', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
                'Device-Type': 'pc'
            },
            body: JSON.stringify({}) // 필요한 데이터
        });

        console.log("response: " + response.status)

        if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
        }

        if

        const data = await response.json();
        const messageElement = document.getElementById('message');

        // API 결과값을 h3 요소에 삽입
        messageElement.textContent = data.data || 'No message available';
    } catch (error) {
        console.error('error->', error.name);
        console.error('error->', error.message);
        console.error("Error Details:", error.details);
        document.getElementById('message').textContent = 'Failed to load message';
    }
}

document.addEventListener('DOMContentLoaded', fetchAndUpdateMessage);