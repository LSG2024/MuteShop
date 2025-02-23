async function register() {
    const email = document.getElementById('reg-email').value.trim();
    const password = document.getElementById('reg-password').value.trim();

    if (!email || !password) {
        showMessage('All fields are required!', 'error');
        return;
    }

    const result = await postData('/register', { email, password });

    if (result.message === "User registered successfully") { // ✅ Ensure correct message is checked
        showMessage('Registration successful! Please log in.', 'success');
    } else {
        showMessage(result.error || 'Registration failed.', 'error');
    }
}


async function login() {
    const email = document.getElementById('login-email').value.trim();
    const password = document.getElementById('login-password').value.trim();

    if (!email || !password) {
        showMessage('All fields are required!', 'error');
        return;
    }

    const result = await postData('/login', { email, password });

    // ✅ Fix: Check `result.message` instead of `result`
    if (result.message === "Login successful") {
        showMessage('Login successful!', 'success');
        setTimeout(() => window.location.href = 'dashboard.html', 1000);
    } else {
        showMessage(result.error || 'Invalid email or password.', 'error');
    }
}

