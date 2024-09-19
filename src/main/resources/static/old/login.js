document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();  // Prevent the default form submission

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('http://localhost:8080/login', {  // Adjust the URL to your backend login endpoint
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'  // Sending form data
        },
        body: new URLSearchParams({
            'username': username,
            'password': password
        }),
        credentials: 'include'  // Include cookies in the request
    })
    .then(response => {
        if (response.ok) {
            // Login successful, redirect to the main page or dashboard
            window.location.href = '/Sumit-Gym-Management-System/static/dashboard.html';  // Adjust as needed
        } else {
            // Handle errors (e.g., invalid credentials)
            alert('Login failed. Please check your credentials and try again.');
        }
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
});
