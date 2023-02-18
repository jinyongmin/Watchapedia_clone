

function sendit(adminIdx) {
  console.log('adminidx = ' + adminIdx);
  const oldPw = document.getElementById("oldPw");
  const reuserpw = document.getElementById("reuserpw");
  const re_userpw = document.getElementById("re_userpw");

  if (oldPw.value == "") {
    document.getElementById("atn").innerHTML =
        "<p style= 'color: red'>기존 비밀번호를 입력하세요</p>";
    userpw.focus();
    return false;
  }

  if (reuserpw.value == "") {
    document.getElementById("atn").innerHTML =
        "<p style= 'color: red'>새 비밀번호를 입력하세요</p>";
    reuserpw.focus();
    return false;
  }

  if (re_userpw.value == "") {
    document.getElementById("atn").innerHTML =
        "<p style= 'color: red'>새 비빌번호 확인칸을 입력해주세요</p>";
    re_userpw.focus();
    return false;
  }

  if (reuserpw.value != re_userpw.value) {
    document.getElementById("atn").innerHTML =
        "<p style= 'color: red'>비밀번호와 비밀번호 확인의 값이 다릅니다</p>";
    re_userpw.focus;
    return false;
  }

  fetch('http://localhost:9090/api/admin', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      "transaction_time":`${new Date()}`,
      "resultCode":"ok",
      "description":"정상",
      "data":{
        "adminIdx":`${adminIdx}`,
        "adminPw":`${reuserpw.value}`,
        "oldPw":`${oldPw.value}`
      }
    }),
  })

      .then((response) => response.json())
      .then((data) => {
        if (data.resultCode == 'OK') {
          alert('수정성공');
          location.href='/admin_myinfo';
        } else {
          alert('기존비밀번호가 일치하지 않습니다')
          oldPw.focus();
        }
      })
      .catch((err) => {
        console.log('에러!!');
        location.reload();
      });
}
