function sendit() {
  const adminId = document.getElementById("adminId");
  const adminPw = document.getElementById("adminPw");
  flag = false;

  if (adminId.value == "") {
    // 아무것도 입력되지 않는 아이디 칸
    alert("아이디를 입력하세요");
    adminId.focus();
    return false;
    // 안넘어가게함
  }

  if (adminPw.value == "") {
    // 아무것도 입력되지 않는 비밀번호 칸
    alert("비밀번호를 입력하세요");
    adminPw.focus();
    return false;
  }

  document.getElementById("regform").submit();
}
