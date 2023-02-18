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

//------------------------------------------------------------
//아이콘사진 등록

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

//----------------------------------------------------------

const spoid = document.getElementById('spoid')
spoid.addEventListener('change', pastel)

function pastel(){
    adBtnColor = document.getElementById("adBtnColor");
    adBtnColor.value = spoid.value;
    //console.log(`ntcBtnColor에 담은 값 : ${ntcBtnColor.value}`);
}

function sendit(adIdx){
    let adTitle = document.querySelector("#adTitle");
    let adDescription = document.querySelector("#adDescription");
    let adVideoSource = document.querySelector("#adVideoSource");
    let adClient = document.querySelector("#adClient");
    let endDate = document.querySelector("#endDate");
    let adBtnColor;
    let adBtnLink;
    let adBtnText;
    let adImagesource;
    let adClientLogoimage;


    try{
        adImagesource=document.querySelector("#base").value;
    }catch (exception){
        adImagesource=null;
    }
    const spoid = document.getElementById('spoid')
    spoid.addEventListener('change', pastel)

    try{
        adBtnColor=document.getElementById('adBtnColor').value;
        if(adBtnColor==''){
            adBtnColor=null;
        }
    }catch (exception){
        adBtnColor=null;
    }

    try{
        adBtnLink= document.getElementById('adBtnLink').value;
        if(adBtnLink==''){
            adBtnLink=null;
        }
    }catch (exception){
        adBtnLink=null;
    }

    try{
        adBtnText=document.getElementById('adBtnText').value;
        if(adBtnText==''){
            adBtnText=null;
        }
    }catch (exception){
        adBtnText=null;
    }

    try{
        adImagesource=document.querySelector("#base").value;
        if(adImagesource==''){
            adImagesource=null;
        }
    }catch (exception){
        adImagesource=null;
    }

    try{
        adClientLogoimage=document.querySelector("#base3").value;
        if(adClientLogoimage==''){
            adClientLogoimage=null;
        }
    }catch (exception){
        adClientLogoimage=null;
    }


    console.log('타이틀'+adTitle.value);
    console.log('내용'+adDescription.value);
    console.log('비디오링크'+adVideoSource.value);
    console.log('클라이언트'+adClient.value);
    console.log('버튼색상'+adBtnColor);
    console.log('버튼링크'+adBtnLink);
    console.log('버튼텍스트'+adBtnText);
    console.log('광고이미지'+adImagesource);
    console.log('클라인트로고이미지'+adClientLogoimage);
    console.log('광고종료날짜'+endDate.value);


    if(adTitle.value == ''){
        alert('타이틀을 입력하세요');
        return false;
    }
    if(adVideoSource.value!=''){
        if(adImagesource!=null){
            alert('비디오와 사진 중 하나만 등록 가능합니다')
            return false;
        }
    }else if(adImagesource!=null){
        if(adVideoSource.value!=''){
            alert('비디오와 사진 중 하나만 등록 가능합니다')
            return false;
        }
    }
    if(adDescription.value == ''){
        alert('내용을 입력하세요');
        return false;
    }
    // if(adClient.value==null){
    //     alert('클라이언트를 입력하세요');
    //     return false;
    // }
    // if(adClientLogoimage==null){
    //     alert('클라이언트 로고이미지를 등록하세요');
    //     return false;
    // }
    // if(adBtnColor==null){
    //     alert('버튼색상을 선택하세요');
    //     return false;
    // }
    // if(adBtnLink==null){
    //     alert('버튼링크를 입력하세요');
    //     return false;
    // }
    // if(adBtnText==null){
    //     alert('버튼텍스트를 입력하세요');
    //     return false;
    // }
    // if(endDate.value==''){
    //     alert('종료날짜를 입력하세요');
    //     return false;
    // }


    fetch('http://localhost:9090/api/advertise', {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            "transaction_time":`${new Date()}`,
            "resultCode":"ok",
            "description":"정상",
            "data":{
                "adIdx":`${adIdx}`,
                "adTitle":`${adTitle.value}`,
                "adDescription":`${adDescription.value}`,
                "adVideoSource":`${adVideoSource.value}`,
                "adClient":`${adClient.value}`,
                "adBtnColor":`${adBtnColor}`,
                "adBtnLink":`${adBtnLink}`,
                "adBtnText":`${adBtnText}`,
                "adImagesource":adImagesource,
                "adClientLogoimage":adClientLogoimage,
                "endDate":`${endDate.value}`,
                "adStatus":'미등록'

            }
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.resultCode == 'OK') {
                alert('등록성공');
                location.href='/advertisement';
            } else {
                console.log('등록에 실패하였습니다. 다시한번 확인해주세요')
            }
        })
        .catch((err) => {
            alert('수정성공');
            location.href='/advertisement';
        });

}