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


//-----------------------------------------------------------------

function sendit(perIdx){
    let movThumbnail;
    let name = document.querySelector("#name");
    let bio = document.querySelector("#bio");


    try{
        movThumbnail=document.querySelector("#base").value;
    }catch (exception){
        movThumbnail=null;
    }

    console.log('포스터'+movThumbnail);
    console.log('이름'+name.value);
    console.log('바이오그래피'+bio.value);


    if(name.value == ''){
        alert('이름을 입력하세요');
        return false;
    }


    fetch('http://localhost:9090/api/character/', {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            "transaction_time":`${new Date()}`,
            "resultCode":"ok",
            "description":"정상",
            "data":{
                "perIdx":`${perIdx}`,
                "perName":`${name.value}`,
                "perPhoto":movThumbnail,
                "perBiography":`${bio.value}`
            }
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.resultCode == 'OK') {
                alert('수정성공');
                location.href='/character_manage';
            } else {
                console.log('수정에 실패하였습니다. 다시한번 확인해주세요')
            }
        })
        .catch((err) => {
            alert('수정성공');
            location.href='/character_manage';
        });

}


