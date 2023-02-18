//---------------------------------------------------------
//포스터이미지
const pobtn = document.querySelector("#pobtn");

pobtn.addEventListener(
    "change",
    function (e) {
      return readURL2(this.files);
    },
    false
);

const readURL2 = (input) => {
  // html 에 그리려고 만든 화살표함수터
  if (input.length == 0) {
    document.getElementById("poBox2").innerHTML = `파일 끌어다 추가하기`;
  } else {
    document.getElementById("poBox2").innerHTML = `<dd>${input[0].name} ${
        Math.round(input[0].size / 1024) + "kb"
    } <span onclick="deleteBtn2()" style="color: red;cursor: pointer;">[X]
        <input type="hidden" id="base">
</span></dd>`;
  }
};

poBox2.ondrop = (e) => {
  e.preventDefault();

  var data = e.dataTransfer.files;
  readLink(e.dataTransfer); //@@@@@@@@@@@@@@@@@@@@@@@@당겨오기nice

  if (pobtn.files.length != 0) {
    pobtn.value = ""; // input  태그에서 받은 값
  }
  pobtn.files = data; // 드래그엔 드롭으로 받아온 값을  input 태그에서 받은 값과 같게 하기 위해서 넘김
  readURL2(pobtn.files);
};

poBox2.ondragover = (e) => {
  e.preventDefault(); // 이 부분이 없으면 ondrop 이벤트가 발생하지 않습니다.
};


function deleteBtn2() {
  // 파일 리스트에서 인덱스에 부합한 배열 제거
  pobtn.value = "";

  // 리스트 다시 그려주기
  readURL2(pobtn.files);
}

function readLink(input) {
  if (input.files && input.files[0]) {
    let reader = new FileReader();
    reader.onload = function (e) {
      $('#falseinput').attr('src', e.target.result);
      $('#base').val(e.target.result);
    };
    reader.readAsDataURL(input.files[0]);
  }
}


//----------------------------------------------------------



const spoid = document.getElementById('spoid')
spoid.addEventListener('change', pastel)



function pastel(){
  ntcBtnColor = document.getElementById("ntcBtnColor");
  ntcBtnColor.value = spoid.value;
  //console.log(`ntcBtnColor에 담은 값 : ${ntcBtnColor.value}`);
}

function submitCheck() {

  let ntcBtnColor;
  try{
    ntcBtnColor=document.getElementById('ntcBtnColor').value;
    if(ntcBtnColor==''){
      ntcBtnColor=null;
    }
  }catch (exception){
    ntcBtnColor=null;
  }


  let ntcTitle = document.getElementById('ntcTitle');
  let ntcText = document.getElementById('ntcText');

  let ntcBtnLink;
  try{
    ntcBtnLink= document.getElementById('ntcBtnLink');
    if(ntcBtnLink==''){
      ntcBtnLink=null;
    }
  }catch (exception){
    ntcBtnLink=null;
  }



  let ntcBtnText;
  try{
    ntcBtnText=document.getElementById('ntcBtnText').value;
    if(ntcBtnText==''){
      ntcBtnText=null;
    }
  }catch (exception){
    ntcBtnText=null;
  }


  let ntcImagepath;
  try{
    ntcImagepath=document.querySelector("#base").value;
    if(ntcImagepath==''){
      ntcImagepath=null;
    }
  }catch (exception){
    ntcImagepath=null;
  }


  console.log("제목"+ntcTitle.value);
  console.log("공지내용"+ntcText.value);
  console.log("사진"+ntcImagepath);
  console.log("버튼텍스트"+ntcBtnText);
  console.log("버튼색상"+ntcBtnColor);
  console.log("연결주소" +ntcBtnLink.value);




  if (ntcTitle.value == '') {
    alert('제목을 입력하세요');
    ntcTitle.focus()
    return false;
  }

  if (ntcText.value == '') {
    alert('공지내용을 입력하세요');
    ntcText.focus()
    return false;
  }

  if(ntcBtnText!=null){
    if(ntcBtnColor==null){
      alert('버튼 색상을 선택해주세요');
      return false;
    }
  }

  if(ntcBtnText!=null){
    if(ntcBtnColor!=null){
      if(ntcBtnLink.value==''){
        alert('이동할 페이지 주소를 입력해주세요');
        return false;
      }
    }
  }

  if(ntcBtnLink.value!=''){
    if(ntcBtnColor==null|ntcBtnText==null){
      alert('색상 및 텍스트를 입력해주세요');
      return false;
    }
  }

  fetch('http://localhost:9090/api/notice', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      "transaction_time":`${new Date()}`,
      "resultCode":"ok",
      "description":"정상",
      "data":{
        "ntcTitle":`${ntcTitle.value}`,
        "ntcText":`${ntcText.value}`,
        "ntcImagepath":ntcImagepath,
        "ntcBtnText":ntcBtnText,
        "ntcBtnLink":`${ntcBtnLink.value}`,
        "ntcBtnColor":ntcBtnColor
      }
    }),
  })

      .then((response) => response.json())
      .then((data) => {
        if (data.resultCode == 'OK') {
          alert('등록성공');
          location.href='/notice';
        } else {
          console.log('등록에 실패하였습니다. 다시한번 확인해주세요')
        }
      })
      .catch((err) => {
        alert('등록성공');
        location.href='/notice';
      });


}