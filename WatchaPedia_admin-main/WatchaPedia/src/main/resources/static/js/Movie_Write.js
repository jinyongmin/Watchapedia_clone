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
  searchVue.itemlist = ""

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
    for(let i of searchVue.itemlist2){
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

  //검색창 readonly해제
  const search_input = document.getElementById("modal_search_bar")
  search_input.removeAttribute("readonly")

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
//갤러리
let fileLists = []; // 전체 파일 리스트 객체
const exte = ["jpg", "jpeg", "png", "gif"]; // 확장명
const inputFile = document.querySelector("#inputFile");
const resultFile = document.querySelector("#resultFile");
const file_count = document.getElementById("file_count");
const total_sizes = document.getElementById("total_size");

inputFile.addEventListener(
    "change",
    function (e) {
      console.log('this.files 출력')
      console.log(this.files)
      return readURL(this.files);
    },
    false
);

const readURL = (input) => {

  if (fileLists.length == 0) {
    // 최초 파일 업로드
    for (let i = 0; i < input.length; i++) {
      fileLists.push(input[i]);0
    }
  } else {
    // 리스트에 추가적으로 파일 업로드
    for (let i = 0; i < input.length; i++) {
      // 중복된 파일 검사(이름 기준)
      var isExist = false;
      for (let j = 0; j < fileLists.length; j++) {
        if (input[i].name == fileLists[j].name) {
          isExist = true;
        }
      }
      if (isExist == false) {
        fileLists.push(input[i]);
      }
    }
  }

  // 결과창 초기화
  resultFile.innerHTML = "";
  // 전체 파일 사이즈 초기화
  let total_size = 0;

  // 선택된 파일만큼 반복
  for (let i = 0; i < fileLists.length; i++) {
    total_size += Math.round(fileLists[i].size / 1024);

    //File정보를 읽을 수 있는 FileReader 호출
    const reader = new FileReader(fileLists[i]);
    let base64data
    //File 불러오기가 끝나면 실행될 함수
    reader.onload = function (e) {
      const img = new Image();

      // image파일의 blob 이 생성됨. blob -> 바이너리 오브젝트
      img.src = e.target.result;


      // 생성된 데이터를 템플릿 문자열에 넣어준다.
      return (resultFile.innerHTML += `
                <dl>
                    <dt class='total_size_kb'>${total_size}</dt>
                    <dd>${fileLists[i].name} ${
          Math.round(fileLists[i].size / 1024) + "kb"
      } <span onclick="deleteBtn(${i})" style="color: red;cursor: pointer;">[X]
        <input type="hidden" id="base(${i})" value="${img.src}">
                    </span></dd>
                </dl>
                `);
    };
    // readAsDataURL은 Blob, File 을 읽을 수 있다.
    reader.readAsDataURL(fileLists[i]);

  }

  file_count.innerHTML = `파일 <span style="color: red;"> ${fileLists.length} </span> 개`;
  total_sizes.innerHTML = total_size + " ";

  // 파일 리스트가 비어있으면 input[:file]의 value 값 초기화 ==> input[:file] event 자체 이슈임.
  if (fileLists.length == 0) {
    inputFile.value = "";
  }
};

/* 박스 안에서 Drag를 Drop했을 때 */
resultFile.ondrop = (e) => {
  e.preventDefault();

  // 드롭된 파일 데이터를 파일 리스트에 넣음. (중복 제거, 다중파일 업로드 가능)
  var data = e.dataTransfer.files;

  for (let i = 0; i < data.length; i++) {
    var isExist = false;
    var isExistExt = false;
    for (let j = 0; j < fileLists.length; j++) {
      if (data[i].name == fileLists[j].name) {
        isExist = true;
      }
    }

    var fileName = data[i].name;
    var fileNameArr = fileName.split(".");
    var ext = fileNameArr[fileNameArr.length - 1];

    if ($.inArray(ext, exte) == -1) {
      alert(`등록 불가 확장자 ${ext}가 포함되었습니다.`);
      isExistExt = true;
    }
    if (!isExist && !isExistExt) {
      fileLists.push(data[i]);
    }
  }
  readURL(fileLists);
};

resultFile.ondragover = (e) => {
  e.preventDefault(); // 이 부분이 없으면 ondrop 이벤트가 발생하지 않습니다.
};

// 파일 삭제
function deleteBtn(index) {
  // 파일 리스트에서 인덱스에 부합한 배열 제거
  fileLists.splice(index, 1);

  // 리스트 다시 그려주기
  readURL(fileLists);
}

//------------------------------------------------------------------------
const pobtn = document.querySelector("#pobtn");

pobtn.addEventListener(
    "change",
    function (e) {
      return readURL2(this.files);
    },
    false
);



let posterimg;
let backgroundimg;
let galleryimgs;

//포스터이미지
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

};

baBox2.ondrop = (e) => {
  e.preventDefault();

  var data = e.dataTransfer.files;
  console.log(e.dataTransfer)
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
        <input type="text" placeholder="제목(ex.메인예고편, 이재원 촬영감독 현장예고편)" class="vBox2" id="vt${idnum}">
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
//전역변수 배열에 select해서 넘어온 ott명 저장
let ottSave = "";


function createOtt(ott) {
  console.log(ott);
  ottSave = ott;
}

function ottVisible() {
  if (ottSave == "티빙") {
    const tving_box = document.getElementById("tving_box");
    tving_box.classList.add("visible");

    const tving_url = document.getElementById("tving_url");
    let videourl = document.getElementById("vurl").value;
    tving_url.value=videourl;
  }
  if (ottSave == "웨이브") {
    const wave_box = document.getElementById("wave_box");
    wave_box.classList.add("visible");
    const wave_url = document.getElementById("wave_url");
    let videourl = document.getElementById("vurl").value;
    wave_url.value=videourl;
  }
  if (ottSave == "디즈니플러스") {
    const disney_box = document.getElementById("disney_box");
    disney_box.classList.add("visible");
    const disney_url = document.getElementById("disney_url");
    let videourl = document.getElementById("vurl").value;
    disney_url.value=videourl;
  }
  if (ottSave == "왓챠") {
    const watcha_box = document.getElementById("watcha_box");
    watcha_box.classList.add("visible");
    const watcha_url = document.getElementById("watcha_url");
    let videourl = document.getElementById("vurl").value;
    watcha_url.value=videourl;
  }
  if (ottSave == "넷플릭스") {
    const netflix_box = document.getElementById("netflix_box");
    netflix_box.classList.add("visible");
    const netflix_url = document.getElementById("netflix_url");
    let videourl = document.getElementById("vurl").value;
    netflix_url.value=videourl;
  }
  if (ottSave == "쿠팡플레이") {
    const coupang_box = document.getElementById("coupang_box");
    coupang_box.classList.add("visible");
    const coupang_url = document.getElementById("coupang_url");
    let videourl = document.getElementById("vurl").value;
    coupang_url.value=videourl;
  }
}

// ----------------------------------------------------------------------
// 티빙 박스 none으로 만들기
const tving_box_X = document.getElementById("tving_box_X");
tving_box_X.addEventListener("click", pop_out);

const wave_box_X = document.getElementById("wave_box_X");
wave_box_X.addEventListener("click", pop_out);

const disney_box_X = document.getElementById("disney_box_X");
disney_box_X.addEventListener("click", pop_out);

const watcha_box_X = document.getElementById("watcha_box_X");
watcha_box_X.addEventListener("click", pop_out);

const netflix_box_X = document.getElementById("netflix_box_X");
netflix_box_X.addEventListener("click", pop_out);

const coupang_box_X = document.getElementById("coupang_box_X");
coupang_box_X.addEventListener("click", pop_out);

function pop_out(e) {
  e.target.parentNode.parentNode.classList.remove("visible");
}
function delval(str){
  document.getElementById(str).value=null;
}
//-------------------------------------------
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

//-----------------------------------------------------------------
var world = [
  "아프가니스탄",
  "올란드 제도",
  "알바니아",
  "알제리",
  "아메리칸사모아",
  "안도라",
  "앙골라",
  "앵귈라",
  "남극",
  "앤티가 바부다",
  "아르헨티나",
  "아르메니아",
  "아루바",
  "오스트레일리아",
  "오스트리아",
  "아제르바이잔",
  "바하마",
  "바레인",
  "방글라데시",
  "바베이도스",
  "벨라루스",
  "벨기에",
  "벨리즈",
  "베냉",
  "버뮤다",
  "부탄",
  "볼리비아",
  "보스니아 헤르체고비나",
  "보츠와나",
  "부베 섬",
  "브라질",
  "영국령 인도양 지역",
  "브루나이",
  "불가리아",
  "부르키나 파소",
  "부룬디",
  "캄보디아",
  "카메룬",
  "캐나다",
  "카보베르데",
  "케이맨 제도",
  "중앙아프리카 공화국",
  "차드",
  "칠레",
  "중국",
  "크리스마스섬",
  "코코스 제도",
  "콜롬비아",
  "코모로",
  "콩고 민주 공화국",
  "쿡 제도",
  "코스타리카",
  "코트디부아르",
  "크로아티아",
  "쿠바",
  "키프로스",
  "체코",
  "덴마크",
  "지부티",
  "도미니카 연방",
  "도미니카 공화국",
  "에콰도르",
  "이집트",
  "엘살바도르",
  "적도 기니",
  "에리트레아",
  "에스토니아",
  "에티오피아",
  "포클랜드 제도",
  "페로 제도",
  "피지",
  "핀란드",
  "프랑스",
  "프랑스령 기아나",
  "프랑스령 폴리네시아",
  "프랑스령 남방 및 남극",
  "가봉",
  "감비아",
  "조지아",
  "독일",
  "가나",
  "지브롤터",
  "그리스",
  "그린란드",
  "그레나다",
  "과들루프",
  "괌",
  "과테말라",
  "건지",
  "기니",
  "기니비사우",
  "가이아나",
  "아이티",
  "허드 맥도널드 제도",
  "바티칸 시국",
  "온두라스",
  "홍콩",
  "헝가리",
  "아이슬란드",
  "인도",
  "인도네시아",
  "이슬람 공화국",
  "이라크",
  "아일랜드섬",
  "맨섬",
  "이스라엘",
  "이탈리아",
  "자메이카",
  "일본",
  "저지섬",
  "요르단",
  "카자흐스탄",
  "케냐",
  "키리바시",
  "조선민주주의인민공화국",
  "대한민국",
  "쿠웨이트",
  "키르기스스탄",
  "라오스",
  "라트비아",
  "레바논",
  "레소토",
  "라이베리아",
  "리비아",
  "리히텐슈타인",
  "리투아니아",
  "룩셈부르크",
  "마카오",
  "북마케도니아",
  "마다가스카르",
  "말라위",
  "말레이시아",
  "몰디브",
  "말리",
  "몰타",
  "마셜 제도",
  "마르티니크",
  "모리타니",
  "모리셔스",
  "마요트",
  "멕시코",
  "야프",
  "몰도바",
  "모나코",
  "몽골",
  "몬테네그로",
  "몬트세랫",
  "모로코",
  "모잠비크",
  "미얀마",
  "나미비아",
  "나우루",
  "네팔",
  "네덜란드",
  "네덜란드령 안틸레스",
  "누벨칼레도니",
  "뉴질랜드",
  "니카라과",
  "니제르",
  "나이지리아",
  "니우에",
  "노퍽섬",
  "북마리아나 제도",
  "노르웨이",
  "오만",
  "파키스탄",
  "팔라우",
  "팔레스타인 영토",
  "파나마",
  "파푸아뉴기니",
  "파라과이",
  "페루",
  "필리핀",
  "핏케언 제도",
  "폴란드",
  "포르투갈",
  "푸에르토리코",
  "카타르",
  "레위니옹",
  "루마니아",
  "러시아",
  "르완다",
  "르완다",
  "세인트키츠 네비스",
  "세인트루시아",
  "생피에르 미클롱",
  "세인트빈센트 그레나딘",
  "사모아",
  "산마리노",
  "상투메 프린시페",
  "사우디아라비아",
  "세네갈",
  "세르비아",
  "세이셸",
  "시에라리온",
  "싱가포르",
  "슬로바키아",
  "슬로베니아",
  "솔로몬 제도",
  "소말리아",
  "남아프리카 공화국",
  "사우스조지아 사우스샌드위치 제도",
  "스페인",
  "스리랑카",
  "수단",
  "수리남",
  "스발바르 얀마옌 제도",
  "에스와티니",
  "스웨덴",
  "스위스",
  "시리아",
  "대만",
  "타지키스탄",
  "탄자니아",
  "태국",
  "동티모르",
  "토고",
  "토켈라우",
  "통가",
  "트리니다드 토바고",
  "튀니지",
  "터키",
  "투르크메니스탄",
  "터크스 케이커스 제도",
  "투발루",
  "우간다",
  "우크라이나",
  "아랍에미리트",
  "영국",
  "미국",
  "미국령 군소 제도",
  "우루과이",
  "우즈베키스탄",
  "바누아투",
  "베네수엘라",
  "베트남",
  "영국령 버진아일랜드",
  "미국령 버진아일랜드",
  "왈리스 푸투나",
  "서사하라",
  "예멘",
  "잠비아",
  "짐바브웨",
];
let selectedworld;
$(document).ready(function () {
  var checkList = [1, 3];
  $("#myList")
      .select2({
        width: "100%",
        templateSelection: function (data, container) {
          var selection = $("#myList").select2("data");
          var idx = selection.indexOf(data);
          // console.log(">>Selection", data.text, data.idx, idx);
          data.idx = idx;
          $(container).css("background-color", world[data.idx]);
          return data.text;
        },
      })
      .val(checkList)
      .trigger("change");
  $("#myList").on("select2:select", function (evt) {
    var element = evt.params.data.element;
    var $element = $(element);
    $element.detach();
    $(this).append($element);
    $(this).trigger("change");
    selectedworld=element;
  });
});
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

//갤러리
function readLinks(input) {
    if (input.files && input.files[0]) {
      let reader = new FileReader();
      reader.onload = function (e) {
        $('#falseinput').attr('src', e.target.result);
        //$('#base('+i+')').val(e.target.result);
        $(`#base(${i})`).val(20);
      };
      reader.readAsDataURL(input.files[0]);
    }

}

//-------------------------------------------------------------------

    function sendit() {
      let movThumbnail;
      let movBackImg;
      let gallerynum = 0;

      try {
        movThumbnail = document.querySelector("#base").value;
        console.log('포스터' + movThumbnail.value);
      } catch (exception) {
        movThumbnail = null;
      }

      try {
        movBackImg = document.querySelector("#base3").value;
        console.log('배경사진' + movBackImg.value);
      } catch (exception) {
        movBackImg = null;
      }

      try {
        gallerynum = parseInt(document.querySelector("#file_count>span").innerHTML)
      } catch (exception) {
      }


      let movTitle = document.querySelector("#movTitle");
      let movTitleOrg = document.querySelector("#movTitleOrg");
      let movMakingDate = document.querySelector("#movMakingDate");


      let myList = document.querySelectorAll("#myList + span li");
      let myList1;
      myList.forEach((element) => {
        myList1 += '/' + element.title;
      });
      let movCountry = myList1.substring(10, myList1.length - 1)


      let mygenre = document.querySelectorAll("#movGenre + span li");
      let mygenre1;
      mygenre.forEach((element) => {
        mygenre1 += ',' + element.title;
      });
      let movGenre = mygenre1.substring(10, mygenre1.length - 1)


      let movAge = $("#movAge option:selected");

      let people = document.querySelectorAll(".hiddenBox");
      let movPeople;
      people.forEach(function (person) {
        movPeople += ',' + person.childNodes[0].data;

      });
      try {
        movPeople = movPeople.substring(10, movPeople.length)
      } catch {
        movPeople = null;
      }

      let movTime = document.querySelector("#movTime");
      let movSummary = document.querySelector("#movSummary");


      let movGallery = null;
      if (gallerynum == 1) {
        movGallery = document.getElementById("base(0)").value
      } else if (gallerynum > 1) {
        for (let i = 0; i < gallerynum; i++) {
          baseid = "base(" + i + ")"
          movGallery += document.getElementById(baseid).value + "|"
        }
        movGallery = movGallery.substring(4, movGallery.length - 1);
      }


      let movVideo = null;
      if (idnum != 0) {
        for (let i = 0; i <= idnum; i++) {
          vt = "vt" + i
          vu = "vu" + i
          if (document.getElementById(vt).value == "") {
            window.alert('빈칸이 있으면 안됩니다');
            movVideo = null;
            return false;
          }
          if (document.getElementById(vu).value == "") {
            window.alert('빈칸이 있으면 안됩니다');
            movVideo = null;
            return false;
          }
          movVideo += (document.getElementById(vu).value) + ',' + (document.getElementById(vt).value) + '|';
        }
        movVideo = movVideo.substring(4, movVideo.length - 1);
      } else {
        if (document.getElementById("vt0").value == "" && document.getElementById("vu0").value != "") {
          window.alert('빈칸이 있으면 안됩니다');
          movVideo = null;
          return false;
        } else if (document.getElementById("vt0").value != "" && document.getElementById("vu0").value == "") {
          window.alert('빈칸이 있으면 안됩니다');
          movVideo = null;
          return false;
        } else if (document.getElementById("vt0").value != "" && document.getElementById("vu0").value != "") {
          movVideo = (document.getElementById("vu0").value + "," + document.getElementById("vt0").value);
        }
      }


      let movWatch = null;
      let watchlist = ["tving_url", "wave_url", "disney_url", "watcha_url", "netflix_url", "coupang_url"];
      let tempcntnum = 0;
      for (let watch of watchlist) {
        let watch_value = document.getElementById(watch).value
        if (watch_value != "" && watch_value != null) {
          tempcntnum += 1;
          movWatch += watch_value + ",";
        }
      }
      if (tempcntnum > 0) {
        movWatch = movWatch.substring(4, movWatch.length - 1);
      }

      console.log('제목' + movTitle.value);
      console.log('원제목' + movTitleOrg.value);
      console.log('개봉일' + movMakingDate.value);
      console.log('국가' + movCountry);
      console.log('장르' + movGenre);
      console.log('상영시간' + movTime.value);
      console.log('나이' + movAge.val());
      console.log('내용' + movSummary.value);
      console.log('갤러리 사진갯수' + gallerynum);
      console.log('동영상제목' + movVideo);
      console.log('감상가능한곳' + movWatch);
      console.log('등장인물' + movPeople);


      if (movTitle.value == '') {
        alert('표기할 영화제목을 입력하세요');
        return false;
      }
      if (movTitleOrg.value == '') {
        alert('원제를 입력하세요');
        return false;
      }
      if (movMakingDate.value == '') {
        alert('개봉날짜를 입력하세요');
        return false;
      }
      if (movCountry == '/') {
        alert('국가를 입력하세요');
        return false;
      }
      if (movGenre == ',') {
        alert('장르를 입력하세요');
        return false;
      }
      if (movTime.value == '') {
        alert('상영시간을 입력하세요');
        return false;
      }
      if (movAge.val() == '') {
        alert('연령 등급을 입력하세요');
        return false;
      }
      if (movPeople == null) {
        alert('인물을 등록해주세요');
        return false;
      }

      fetch('http://localhost:9090/api/movie', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
          "transaction_time": `${new Date()}`,
          "resultCode": "ok",
          "description": "정상",
          "data": {
            "movAge": `${movAge.val()}`,
            "movBackImg": movBackImg,
            "movCountry": `${movCountry}`,
            "movGenre": `${movGenre}`,
            "movGallery": movGallery,
            "movMakingDate": `${movMakingDate.value}`,
            "movPeople": movPeople,
            "movSummary": `${movSummary.value}`,
            "movThumbnail": movThumbnail,
            "movTime": `${movTime.value}`,
            "movTitle": `${movTitle.value}`,
            "movTitleOrg": `${movTitleOrg.value}`,
            "movVideo": movVideo,
            "movWatch": movWatch
          }
        }),
      }) .then((response) => response.json())
          .then((data) => {
            let movIdx=data.data[0].movIdx
            let people = document.querySelectorAll(".hiddenBox");
            people.forEach(function (person) {
              let idxnum = person.childNodes[0].data.indexOf('(');
              let num = person.childNodes[0].data.substring(0, idxnum)
              fetch('http://localhost:9090/api/character', {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                  "transaction_time": `${new Date()}`,
                  "resultCode": "ok",
                  "description": "정상",
                  "data": {
                    "perIdx": num,
                    "perMovie": movIdx
                  }
                })
              })
            })
          })
          .then((data) => {
            if (data.resultCode == 'OK') {
              alert('등록성공');
              location.href='/contents/movie';}})
          .catch(() => {
            alert('등록성공');
            location.href='/contents/movie';
          });
    }










