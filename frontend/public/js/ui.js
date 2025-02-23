function showMessage(message, type) {
    let messageBox = document.getElementById('message-box');

    if (!messageBox) {
        messageBox = document.createElement('div');
        messageBox.id = 'message-box';
        document.body.prepend(messageBox);
    }

    messageBox.textContent = message;
    messageBox.className = `message ${type}`;

    setTimeout(() => {
        messageBox.remove();
    }, 3000);
}
