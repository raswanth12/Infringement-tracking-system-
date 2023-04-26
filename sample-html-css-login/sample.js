const form = document.getElementById('login-form');
form.addEventListener('submit', async (event) => {
  event.preventDefault();

  // Get username and password input values
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  // Send login request to backend
  const response = await fetch('/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ username, password })
  });

  // Handle login response
  if (response.ok) {
    window.location.href = '/dashboard';
  } else {
    alert('Incorrect username or password');
  }
});
