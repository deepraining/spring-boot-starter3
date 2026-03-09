document.getElementById('submit').addEventListener('click', () => {
  const email = document.getElementById('email').value;
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const password2 = document.getElementById('password2').value;

  let error = '';
  if (!email) error = 'Email cant be empty.';
  else if (email.indexOf('@') === -1) error = 'Email is invalid.';
  else if (!username) error = 'Username cant be empty.';
  else if (!password) error = 'Password cant be empty.';
  else if (password !== password2) error = 'Confirm Password is not equal to password.';

  if (error) {
    alert(error);
    return;
  }

  fetch('/api/account/register', {
    method: 'post',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({username, email, password})
  }).then(res => res.json()).then(res => {
    if (res.message) {
      alert(res.message);

      if (res.code === 0) location.href = '/';
    }
  });
}, !1);
