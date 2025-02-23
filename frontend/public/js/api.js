const BASE_URL = "http://localhost:8080/auth";

async function postData(endpoint, data) {
    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });

        const responseData = await response.json();

        if (!response.ok) {
            throw new Error(responseData.error || 'Request failed');
        }

        return responseData; // ✅ Ensure response is returned correctly
    } catch (error) {
        console.error('Error:', error.message);
        return { error: 'Server error. Try again later.' };
    }
}


