
const { createApp } = Vue;

let searchVue = createApp({
  data() {
    return {
      search_msg: "",
      itemlist: {},
      itemlist2: {},
      modalInnerBtn: true
    };
  },
  methods:{
    chosen(idx){
      let flag = true

      for( let i of searchVue.itemlist2){
        if(idx == i.perIdx){
          personsArr.push(i)
        }
      }

      return flag
    }
  }
}).mount(".sb-nav-fixed");

function search_db() {
  console.log(searchVue.search_msg);
  //이전 검색 데이터 내용을 지우기
  const person_box = document.getElementById("modal_search_result");
  const child_all = document.querySelectorAll("#modal_search_result > *");

  // ajax로 정보 받아오기
  $.ajax({
    url: "/api/movie/searchKey",
    type: "GET",
    dataType: "json",
    processData: true,
    contentType: "application/json; charset=UTF-8",
    data: {searchKey : searchVue.search_msg},
    success: function(result){
      console.log('ajax 정보교환 성공!')
      for(let i of result.data){
        if(searchVue.itemlist2.length > 0){
          for(let j of searchVue.itemlist2){
            if(i.perIdx == j.perIdx){
              i.chooseCheck = "chosen";
            }
          }
        }
      }
      console.log(result.data);
      searchVue.itemlist = result.data;
    },
    error: function(){
      console.log("에러발생")
    }
  })
}

let personsArr = [];

function choseCheck(e){
  if(e.innerText == "선택"){
    //검색창에 배역입력할 동안 readonly주기
    const readOnlyAttr = document.createAttribute("readonly")
    const modal_searchbar = document.getElementById("modal_search_bar")
    modal_searchbar.setAttributeNode(readOnlyAttr);
    //배역을 입력하게 해주는 창으로 변경
    personRegist(e);
  }else{

    e.innerText = "선택"

    const perIdx = e.parentNode.parentNode.firstChild.innerText;

    //대입 배열 초기화
    personsArr = []

    //선택 인물 idx가 아닌 것들로 배열 재구성
    for( let i of searchVue.itemlist2){
      if(perIdx != i.perIdx){
        personsArr.push(i)
      }
    }
    //배열을 itemlist2에 삽입
    searchVue.itemlist2 = personsArr
  }
}


//
function personRegist(e){

  //모달 하단 버튼 변경
  searchVue.modalInnerBtn = false;
  console.log(e)

  //선택한 인물 큰 박스 복사한 Node
  let clone1 = e.parentNode.parentNode.cloneNode();
  const uniqueId = document.createAttribute("id");
  uniqueId.value = "uniqueId";
  clone1.setAttributeNode(uniqueId)

  const Nodes = e.parentNode.parentNode.childNodes;


  //"선택" 버튼을 누른 인물의 박스를 복사해옴
  for(let i in Nodes){
    if(i >= 3) break;
    const node = Nodes[i].cloneNode();
    node.innerHTML = Nodes[i].innerHTML;
    clone1.appendChild(node);
  }

  //역할 태그 생성
  const job = document.createElement("div");
  const castingAttr = document.createAttribute("style");
  castingAttr.value = "font-size:18px; font-weight: bold; margin-left:8px; margin-top:15px;"
  job.innerText = "역할";
  job.setAttributeNode(castingAttr);

  //input1 태그 생성
  const input1 = document.createElement("input");
  const castingInput1 = document.createAttribute("placeholder");
  castingInput1.value = "예) 감독, 주연, 특별출연, 단역"
  input1.setAttributeNode(castingInput1);
  const castingInput2 = document.createAttribute("style");
  castingInput2.value = "width: 300px; margin-left: 8px;"
  input1.setAttributeNode(castingInput2);
  const getId1 = document.createAttribute("id");
  getId1.value = "perJob";
  input1.setAttributeNode(getId1);

  //배역이름 태그 생성
  const casting = document.createElement("div");
  const castingAttr1 = document.createAttribute("style");
  castingAttr1.value = "font-size:18px; font-weight: bold; margin-left:8px; margin-top:15px;"
  casting.innerText = "배역이름";
  casting.setAttributeNode(castingAttr1);

  //input2 태그 생성
  const input2 = document.createElement("input");
  const input2Attr = document.createAttribute("style");
  input2Attr.value = "width: 300px; margin-left: 8px;"
  input2.setAttributeNode(input2Attr);
  const getId = document.createAttribute("id");
  getId.value = "perCasting"
  input2.setAttributeNode(getId);


  //나머지 Node 숨기기
  const currentAllNode = document.querySelectorAll(".onePersonBigBox");
  for(let i of currentAllNode){
    i.classList.add("none")
  }

  // //modal_search_result에 클론노드를 집어넣기
  const modal_search_result = document.getElementById("modal_search_result");
  modal_search_result.appendChild(clone1);
  modal_search_result.appendChild(job);
  modal_search_result.appendChild(input1);
  modal_search_result.appendChild(casting);
  modal_search_result.appendChild(input2);
  //여기까지가 한 인물 정보를 입력하는 모달화면으로 구성 변경한 것
}

// 배역입력을 취소하고 리스트로 돌아감
function goBackList(){

  //모달 하단 버튼 변경
  searchVue.modalInnerBtn = true;

  //새로 생성했던 5개의 노드를 제거
  const modal_search_result = document.getElementById("modal_search_result");
  for(let i=0; i < 5; i++){
    modal_search_result.removeChild(modal_search_result.lastChild);
  }

  const currentAllNode = document.querySelectorAll(".onePersonBigBox");
  for(let i of currentAllNode){
    i.classList.remove("none")
  }



}


//원래는 블록 클릭이였기 때문에 살짝 바꿔줘야 함
function choosePerson(e){
  //검색창 readonly 해제
  const modal_searchbar = document.getElementById("modal_search_bar")
  modal_searchbar.removeAttribute("readonly")

  //모달 하단 버튼 변경
  searchVue.modalInnerBtn = true;

  //cloneNode 선택
  const cloneNode = document.getElementById("uniqueId");

  //clonNode의 자식노드 배열 추출
  const cloneNodeArr = cloneNode.childNodes;

  //인물의 idx값 추출
  const perIdx = cloneNodeArr[0].innerText;

  //사진 src값을 추출
  const perPhoto = cloneNodeArr[1].firstChild.getAttribute('src');

  //인물의 이름을 추출
  const perName = cloneNodeArr[2].firstChild.innerText;

  //인물의 지위?
  const perRole = cloneNodeArr[2].childNodes[1].innerText;

  //해당 인물의 버튼 "선택" "✔️선택됨" 구분
  const ghostNode = document.querySelectorAll(".onePersonBigBox.none");
  let theNum;

  for(let i in ghostNode){
    if(ghostNode[i].firstChild.innerText == perIdx){
      theNum = i;
      break;
    }
  }

  ghostNode[theNum].childNodes[3].firstChild.innerText = "✔️선택됨"

  const perJob = document.getElementById("perJob").value;
  const perCasting = document.getElementById("perCasting").value;

  const person = {
    perIdx: perIdx,
    perPhoto: perPhoto,
    perName: perName,
    perRole: perRole,
    perJob: perJob,
    perCasting: perCasting,
  }

  personsArr.push(person)

  searchVue.itemlist2 = personsArr

  //새로 생성했던 5개의 노드를 제거
  const modal_search_result = document.getElementById("modal_search_result");
  for(let i=0; i < 5; i++){
    modal_search_result.removeChild(modal_search_result.lastChild);
  }

  //none했던 기존 list none 해제
  const currentAllNode = document.querySelectorAll(".onePersonBigBox");
  for(let i of currentAllNode){
    i.classList.remove("none")
  }

}

function minusItemlist2(e){
  console.log("x버튼 눌렀을때 메소드 실행!")

  //클릭한 인물의 idx 추출
  const str = e.parentNode.querySelector(".hiddenBox").innerText;
  let indexArr = str.split("(")

  //대입 배열 초기화
  personsArr = []

  for( let i of searchVue.itemlist2){
    if(indexArr[0] != i.perIdx){
      personsArr.push(i)
    }
  }
  searchVue.itemlist2 = personsArr
}

// ------------------------------------------------------------------------------
//------------------------------------------------------------------------
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
  // html 에 그리려고 만든 화살표함수

  if (input.length == 0) {
    document.getElementById("poBox2").innerHTML = `파일 끌어다 추가하기`;
  } else {
    document.getElementById("poBox2").innerHTML = `<dd>${input[0].name} ${
        Math.round(input[0].size / 1024) + "kb"
    } <span onclick="deleteBtn2()" style="color: red;cursor: pointer;">[X]
<input type="hidden" id="base">
</span></dd>`;
  }

  console.log(input);
};

poBox2.ondrop = (e) => {
  e.preventDefault();

  var data = e.dataTransfer.files;
  console.log(data);
  readLink(e.dataTransfer);
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
//------------------------------------------------------------------------
//배경사진
const babtn = document.querySelector("#babtn");

babtn.addEventListener(
    "change",
    function (e) {
      return readURL3(this.files);
    },
    false
);
const readURL3 = (input) => {
  // html 에 그리려고 만든 화살표함수

  if (input.length == 0) {
    document.getElementById("baBox2").innerHTML = `파일 끌어다 추가하기`;
  } else {
    document.getElementById("baBox2").innerHTML = `<dd>${input[0].name} ${
        Math.round(input[0].size / 1024) + "kb"
    } <span onclick="deleteBtn3()" style="color: red;cursor: pointer;">[X]
<input type="hidden" id="base3">
</span></dd>`;
  }

  console.log(input);
};

baBox2.ondrop = (e) => {
  e.preventDefault();

  var data = e.dataTransfer.files;
  console.log(data);
  readLink1(e.dataTransfer)
  if (babtn.files.length != 0) {
    babtn.value = ""; // input  태그에서 받은 값
  }
  babtn.files = data; // 드래그엔 드롭으로 받아온 값을  input 태그에서 받은 값과 같게 하기 위해서 넘김
  readURL3(babtn.files);
};

baBox2.ondragover = (e) => {
  e.preventDefault(); // 이 부분이 없으면 ondrop 이벤트가 발생하지 않습니다.
};

function deleteBtn3() {
  // 파일 리스트에서 인덱스에 부합한 배열 제거
  babtn.value = "";

  // 리스트 다시 그려주기
  readURL3(babtn.files);
}
// ----------------------------------------------------------------------
let idnum=0;
function add() {
  const vBox = document.getElementById("vBox");

  const plusAndminus = document.getElementById("only_flex_box");

  const newNode = document.createElement("p");
  idnum+=1;
  newNode.innerHTML += `<div class="vBox1">
      <div>
        <input type="text" placeholder="제목(ex.메인예고편, 현장예고편)" class="vBox2" id="vt${idnum}">
      </div>
      <div>
        <input type="text" placeholder="URL주소"  class="vBox2" id="vu${idnum}">
      </div>
    </div>`; // 파일명 출력

  vBox.insertBefore(newNode, plusAndminus);
}


function minus() {
  const vBox = document.getElementById("vBox");
  const removeNode = document.querySelector("#vBox > p");
  if(idnum=!1){
    idnum-=1
  }
  vBox.removeChild(removeNode);
}
// ----------------------------------------------------------------------
function search() {
  window.open("https://www.naver.com/", "", "_blank");
}
// ----------------------------------------------------------------------

var ottSave;

function createOtt(ott) {
  console.log(ott);
  ottSave = ott;
  console.log(ottSave);
}

function ottVisible() {
  if (ottSave == "카카오웹툰") {
    const kakaoWeb_box = document.getElementById("kakaoWeb_box");
    kakaoWeb_box.classList.add("visible");
    const kakaoWeb_url = document.getElementById("kakaoWeb_url");
    let videourl = document.getElementById("vurl").value;
    kakaoWeb_url.value=videourl;
  }

  if (ottSave == "네이버웹툰") {
    const naverWebtoon_box = document.getElementById("naverWebtoon_box");
    naverWebtoon_box.classList.add("visible");
    const naverWebtoon_url = document.getElementById("naverWebtoon_url");
    let videourl = document.getElementById("vurl").value;
    naverWebtoon_url.value=videourl;
  }

  if (ottSave == "레진코믹스") {
    const reginComics_box = document.getElementById("reginComics_box");
    reginComics_box.classList.add("visible");
    const reginComics_url = document.getElementById("reginComics_url");
    let videourl = document.getElementById("vurl").value;
    reginComics_url.value=videourl;
  }
  if (ottSave == "왓챠") {
    const watcha_box = document.getElementById("watcha_box");
    watcha_box.classList.add("visible");
    const watcha_url = document.getElementById("watcha_url");
    let videourl = document.getElementById("vurl").value;
    watcha_url.value=videourl;
  }
  if (ottSave == "카카오페이지") {
    const kakaoPage_box = document.getElementById("kakaoPage_box");
    kakaoPage_box.classList.add("visible");
    const kakaoPage_url = document.getElementById("kakaoPage_url");
    let videourl = document.getElementById("vurl").value;
    kakaoPage_url.value=videourl;
  }
  if (ottSave == "봄툰") {
    const bomtoon_box = document.getElementById("bomtoon_box");
    bomtoon_box.classList.add("visible");
    const bomtoon_url = document.getElementById("bomtoon_url");
    let videourl = document.getElementById("vurl").value;
    bomtoon_url.value=videourl;
  }
  if (ottSave == "리디") {
    const ready_box = document.getElementById("ready_box");
    ready_box.classList.add("visible");
    const ready_url = document.getElementById("ready_url");
    let videourl = document.getElementById("vurl").value;
    ready_url.value=videourl;
  }
}
// ----------------------------------------------------------------------
const kakaoWeb_box_X = document.getElementById("kakaoWeb_box_X");
kakaoWeb_box_X.addEventListener("click", pop_out);

const reginComics_box_X = document.getElementById("reginComics_box_X");
reginComics_box_X.addEventListener("click", pop_out);

const kakaoPage_box_X = document.getElementById("kakaoPage_box_X");
kakaoPage_box_X.addEventListener("click", pop_out);

const watcha_box_X = document.getElementById("watcha_box_X");
watcha_box_X.addEventListener("click", pop_out);

const naverWebtoon_box_X = document.getElementById("naverWebtoon_box_X");
naverWebtoon_box_X.addEventListener("click", pop_out);

const bomtoon_box_X = document.getElementById("bomtoon_box_X");
bomtoon_box_X.addEventListener("click", pop_out);

function pop_out(e) {
  e.target.parentNode.parentNode.classList.remove("visible");
}

// ----------------------------------------------------------------------
function person_search_visible() {
  const search_input = document.getElementById("modal_search_bar")
  search_input.value = ""

  const person_search_modal = document.getElementById("person_search_modal");
  person_search_modal.classList.add("visible");
}

function search_cancel() {
  const person_search_modal = document.getElementById("person_search_modal");
  person_search_modal.classList.remove("visible");
  searchVue.itemlist = ""
}

// ----------------------------------------------------------------------

// unfilled : 반복문에 의해 불려질 인물 한사람의 정보를 담은 div 생성
function search_person(person) {
  console.log("검색어가 바뀌어서 테이블 탐색!");
  const person_box = document.getElementById("modal_search_result");

  const newNode = document.createElement("div");
  newNode.innerHTML = `${person}님의 인물정보를 시각화한div`;

  person_box.appendChild(newNode);
}
//-------------------------------------------------------------------
//포스터
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

//배경사진
function readLink1(input) {
  if (input.files && input.files[0]) {
    let reader = new FileReader();
    reader.onload = function (e) {
      $('#falseinput').attr('src', e.target.result);
      $('#base3').val(e.target.result);
    };
    reader.readAsDataURL(input.files[0]);
  }
}



//-------------------------------------------------------------------

function sendit(){


  //포스터
  let webThumbnail;
  try{
    webThumbnail=document.querySelector("#base").value;
    console.log('포스터'+webThumbnail.value);
  }catch (exception){
    webThumbnail=null;
  }

  //배경사진
  let webBackImg;
  try{
    webBackImg=document.querySelector("#base3").value;
    console.log('배경사진'+webBackImg.value);
  }catch (exception){
    webBackImg=null;
  }




  let webTitle = document.querySelector("#webTitle");
  let webTitleOrg = document.querySelector("#webTitleOrg");
  let webWriter = document.querySelector("#webWriter");

  let startday = document.querySelector("#webSerPeriodStart");
  let sdate= new Date(startday.value);
  let endday = document.querySelector("#webSerPeriodEnd");
  let edate = new Date(endday.value);



  //장르
  let mygenre = document.querySelectorAll("#webGenre + span li");
  let mygenre1;
  mygenre.forEach((element) => {
    mygenre1 += ','+element.title;
  });
  let webGenre = mygenre1.substring(10,mygenre1.length-1)

  let people=document.querySelectorAll(".hiddenBox");
  let webPeople;
  people.forEach(function(person) {
    webPeople += ',' + person.childNodes[0].data;
  });
  try{
    webPeople = webPeople.substring(10,webPeople.length)
  }catch{
    webPeople = null;
  }

  let webSerDetail = $("#webSerDetail option:selected");
  let webSerDay= $("#webSerDay option:selected");

  let a=sdate.getFullYear()
      +"."+
      (sdate.getMonth()<9 ? '0'+(sdate.getMonth()+1) : sdate.getMonth()+1 )
      +"."+
      (sdate.getDate()<10 ? `0${sdate.getDate()}` : sdate.getDate())
  let b=(endday.value==''?'':
      edate.getFullYear()
      +"."+
      (edate.getMonth()<9 ? '0'+(edate.getMonth()+1) : edate.getMonth()+1 )
      +"."+
      (edate.getDate()<10 ? `0${edate.getDate()}`  : edate.getDate()))
  let webSerPeriod =a+"~"+b

  let webAge= $("#webAge option:selected");
  let webSummary = document.querySelector("#webSummary");



  let webWatch=null;
  let watchlist=["kakaoWeb_url","reginComics_url","watcha_url","kakaoPage_url","naverWebtoon_url","bomtoon_url"];
  let tempcntnum=0;
  for(let watch of watchlist){
    let watch_value = document.getElementById(watch).value
    if(watch_value!=""&&watch_value!=null){
      tempcntnum+=1;
      webWatch+=watch_value+",";
    }
  }
  if(tempcntnum>0){
    webWatch=webWatch.substring(4,webWatch.length-1);
  }




  console.log('제목'+webTitle.value);
  console.log('원제'+webTitleOrg.value);
  console.log('작가'+webWriter.value);
  console.log('장르'+webGenre);
  console.log('연재정보'+webSerDetail.val());
  console.log('연재요일'+webSerDay.val());
  console.log('연재기간'+webSerPeriod);
  console.log('연재등급'+webAge.val());
  console.log('내용'+webSummary.value);
  console.log('감상가능한곳' + webWatch);
  console.log('포스터'+webThumbnail);
  console.log('배경사진' + webBackImg);
  console.log('출연/제작' + webPeople);



  if(webTitle.value == ''){
    alert('표기할 웹툰명을 입력하세요');
    return false;
  }
  if(webTitleOrg.value == ''){
    alert('원제를 입력하세요');
    return false;
  }
  if(webWriter.value == ''){
    alert('작가를 입력하세요');
    return false;
  }
  // if(webGenre == ','){
  //   alert('장르를 선택하세요');
  //   return false;
  // }
  // if(webSerDetail.val() == ''){
  //   alert('연재정보를 입력하세요');
  //   return false;
  // }
  // if(webSerDay.val() == ''){
  //   alert('연재요일을 입력하세요');
  //   return false;
  // }
  // if(startday.value == ''){
  //   alert('연재 시작일을 입력하세요');
  //   return false;
  // }
  // if(webAge.val() == ''){
  //   alert('연령 등급을 입력하세요');
  //   return false;
  // }
  // if(webPeople==null){
  //   alert('인물을 등록해주세요');
  //   return false;
  // }

  fetch('http://localhost:8888/api/webtoon', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      "transaction_time":`${new Date()}`,
      "resultCode":"ok",
      "description":"정상",
      "data":{
        "webIdx":webIdx,
        "webTitle":`${webTitle.value}`,
        "webTitleOrg":`${webTitleOrg.value}`,
        "webWriter":`${webWriter.value}`,
        "webGenre":`${webGenre}`,
        "webSerDetail":`${webSerDetail.val()}`,
        "webSerDay":`${webSerDay.val()}`,
        "webSerPeriod":`${webSerPeriod}`,
        "webAge":`${webAge.val()}`,
        "webSummary":webSummary.value,
        "webWatch":webWatch,
        "webThumbnail":webThumbnail,
        "webBackImg":webBackImg,
        "webPeople":webPeople
      }
    }),
  })
      .then((response) => response.json())
      .then((data) => {
        if (data.resultCode == 'OK') {
          alert('등록성공');
          location.href=`/contents/webtoon'`;
        } else {
          alert('등록에 실패하였습니다. 다시한번 확인해주세요')
        }
      })
      .catch((err) => {
        alert('등록성공');
        location.href=`/contents/webtoon'`;
      });

}