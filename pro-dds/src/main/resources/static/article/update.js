const titleEl = document.getElementById('title');
const introEl = document.getElementById('intro');
const contentEl = document.getElementById('content');
const id = location.href.split('/').slice(-1)[0];

fetch(`/api/article/record/${id}`).then(res => res.json()).then(res => {
  if (res.code === 0) {
    titleEl.value = res.data.title;
    introEl.value = res.data.intro;
    contentEl.value = res.data.content;
  }
});

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

  fetch(`/api/article/update/${id}`, {
    method: 'post',
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({title, intro, content}),
  }).then(res => res.json()).then(res => {
    if (res.message) {
      alert(res.message);

      if (res.code === 0) location.href = '/user';
    }
  });
}, !1);
