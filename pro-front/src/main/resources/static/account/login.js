document.getElementById('submit').addEventListener('click', () => {
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  let error = '';
  if (!username) error = 'Username cant be empty.';
  else if (!password) error = 'Password cant be empty.';

  if (error) {
    alert(error);
    return;
  }

  const formData = new FormData();
  formData.append('username', username);
  formData.append('password', password);
  fetch('/api/account/login', {
    method: 'post',
    body: formData
  }).then(res => res.json()).then(res => {
    if (res.message) {
      alert(res.message);

      if (res.code === 0) location.href = '/';
    }
  });
}, !1);
