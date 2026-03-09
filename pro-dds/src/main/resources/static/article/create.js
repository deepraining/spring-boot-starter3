document.getElementById('submit').addEventListener('click', () => {
  const title = document.getElementById('title').value;
  const intro = document.getElementById('intro').value;
  const content = document.getElementById('content').value;

  let error = '';
  if (!title) error = 'Title cant be empty.';
  else if (!intro) error = 'Intro cant be empty.';
  else if (!content) error = 'Content cant be empty.';

  if (error) {
    alert(error);
    return;
  }

  fetch('/api/article/create', {
    method: 'post',
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({title, intro, content}),
  }).then(res => res.json()).then(res => {
    if (res.message) {
      alert(res.message);

      if (res.code === 0) location.href = '/';
    }
  });
}, !1);
