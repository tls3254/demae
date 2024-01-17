const eventSource = eventSource || new EventSource("api/connect");

eventSource.onmessage = function (event) {
	const notification = JSON.parse(event.data);
	// 알림 처리 로직
	console.log(notification);
};