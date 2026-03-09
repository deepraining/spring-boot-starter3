document.getElementById('submit').addEventListener('click', () => {
  const oldPassword = document.getElementById('oldPassword').value;
  const newPassword = document.getElementById('newPassword').value;

  let error = '';
  if (!oldPassword) error = 'Old password cant be empty.';
  else if (!newPassword) error = 'New password cant be empty.';

  if (error) {
    alert(error);
    return;
  }

  fetch('/api/account/updatePassword', {
    method: 'post',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({oldPassword, newPassword}),
  }).then(res => res.json()).then(res => {
    if (res.message) {
      alert(res.message);

      if (res.code === 0) location.href = '/';
    }
  });
}, !1);
