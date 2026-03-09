const titleEl = document.getElementById('title');
const introEl = document.getElementById('intro');
const contentEl = document.getElementById('content');
const id = location.href.split('/').slice(-1)[0];

fetch(`/api/article/record/${id}`).then(res => res.json()).then(res => {
  if (res.code === 0) {
    titleEl.innerText = res.data.title;
    introEl.innerText = res.data.intro;
    contentEl.innerText = res.data.content;
  }
});
