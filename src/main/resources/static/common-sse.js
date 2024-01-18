const eventSource = new EventSource("/api/sse/connect");

eventSource.onmessage = function (event) {
	const notification = JSON.parse(event.data);
	alert("알림 메시지")
	console.log("Notification received:", notification);
};

console.log("EventSource created successfully.");