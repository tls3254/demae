const eventSource = new EventSource("/api/orders/connect");

eventSource.onmessage = function (event) {
	const notification = JSON.parse(event.data);
	// 알림 처리 로직
	console.log("Notification received:", notification);
};

console.log("EventSource created successfully.");