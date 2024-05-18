function store() {
    const storeData = {
        name: document.getElementById('name').value,
        address: document.getElementById('address').value,
        category: document.getElementById('category').value
    };

    fetch('/api/stores', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(storeData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('response 오류');
            }
            return response.text();
        })
        .then(data => {
            document.getElementById('messageDivContent').textContent = '상점 생성 완료.';
            window.location.href = '/stores';
        })
        .catch((error) => {
            console.error('가져오기 작업에 문제 발생:', error);
            alert('공백 또는 이미 가입된 계정인지 확인하세요');
        });
}


function updateData() {
    var name = document.getElementById('name').value;
    var address = document.getElementById('address').value;
    var category = document.getElementById('category').value;

    fetch(window.location.href, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: name,
            address: address,
            category: category
        }),
    })
        .then(response => {
            if (response.ok) {
                // 성공 시 처리
                window.location.reload();
            } else {
                // 실패 시 처리
                console.log("실패")
                return response.text();
            }
        })
        .then(result => {
            // 서버에서 "ok" 또는 "fail"을 응답으로 보내면 이에 따라 처리
            if (result.trim() === "fail") {
                // 클라이언트에서 수정 실패 시 팝업창 띄우기
                alert('상점 수정에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('에러:', error);
            // 서버에서 오류 응답이 오면 여기에서 처리
            // alert('상점 수정 중에 오류가 발생했습니다.');
        });

    // 데이터 수정 후 모달 닫기
    $('#textModal').modal('hide');
}


    function deleteStore() {
    if (confirm("정말로 가게를 삭제하시겠습니까?")) {
    fetch(window.location.href, {
    method: 'DELETE',
    headers: {
    'Content-Type': 'application/json',
},
})
    .then(response => {
    if (response.ok) {
    window.location.href = '/api/stores';
    // 또는
    // window.location.href = '/redirect-url'; // 리다이렉션
} else {
    // console.error('가게 삭제 중 오류 발생');
}
}).then(result => {
    // 서버에서 "ok" 또는 "fail"을 응답으로 보내면 이에 따라 처리
    if (result.trim() === "fail") {
    // 클라이언트에서 수정 실패 시 팝업창 띄우기
    alert('상점 삭제에 실패했습니다.');
}
})
    .catch(error => {
    console.error('에러:', error);
    alert('상점 삭제 중에 오류가 발생했습니다.');
})
}
}