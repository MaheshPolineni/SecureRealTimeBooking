const chatIcon = document.getElementById("chatIcon");
const chatBox = document.getElementById("chatBox");
const chatMessages = document.getElementById("chatMessages");

chatIcon.onclick = () => {
    chatBox.style.display =
        chatBox.style.display === "none" ? "block" : "none";
};

function sendMessage() {
    const input = document.getElementById("chatInput");
    const msg = input.value;
    if (!msg) return;

    append("You", msg);

    fetch("/chat/send", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ message: msg })
    })
    .then(r => r.text())
    .then(res => append("System", res));

    input.value = "";
}

function append(sender, text) {
    const div = document.createElement("div");
    div.innerText = `${sender}: ${text}`;
    chatMessages.appendChild(div);
}
