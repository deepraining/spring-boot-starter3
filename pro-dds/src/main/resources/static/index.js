fetch('/api/article/list').then(res => res.json()).then(res => {
  document.getElementById('article-tbody').innerHTML = res.data.list.map(item => `
      <tr>
        <td>${item.id}</td>
        <td>${item.title}</td>
        <td>${item.readCount}</td>
        <td>${item.supportCount}</td>
        <td>${item.intro}</td>
        <td>${item.frontUserId}</td>
        <td>${item.createTime}</td>
        <td>${item.updateTime}</td>
        <td><a href="/article/record/${item.id}">Detail</a></td>
      </tr>
    `).join('');
  document.getElementById('article-info').innerText = `pageNum: ${
    res.data.pageNum
  }, pageSize: ${res.data.pageSize}, pages: ${res.data.pages}, total: ${
    res.data.total
  }`;
});
document.getElementById('to-logout').addEventListener('click', () => {
  fetch('/api/account/logout', {
    method: 'post',
  }).then(res => res.json()).then(res => {
    if (res.message) {
      alert(res.message);

      if (res.code === 0) location.href = '/';
    }
  });
}, !1);
fetch('/api/account/currentUser').then(res => res.json()).then(res => {
  const userInfo = document.getElementById('user-info');
  const userLoggedIn = document.getElementById('user-logged-in');
  const userLogin = document.getElementById('user-login');
  if (res.data) {
    userLoggedIn.style.display = 'block';
    userLogin.style.display = 'none';
    userInfo.innerHTML = `Id: ${res.data.id}, Username: ${res.data.username}, Email: ${res.data.email}`;
  }
});
