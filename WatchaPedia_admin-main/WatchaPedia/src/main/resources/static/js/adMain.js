const inputFile = document.getElementById("file2");
const sentence = document.getElementById("sentence");
const removeText = document.getElementById("advertise_empty");
const removeContainer = document.getElementById("advertise_container");

const ad_remove_btn = document.getElementById("ad_remove_btn");
advertise_empty.addEventListener("click", ad_removing);

function ad_removing() {
  removeText.classList.add("hide");
}

const video_extension = [
  "ogm",
  "wmv",
  "mpg",
  "webm",
  "ogv",
  "mov",
  "asx",
  "mpeg",
  "mp4",
  "m4v",
  "avi",
];

const image_extension = [
  "tiff",
  "jfif",
  "bmp",
  "pjp",
  "apng",
  "gif",
  "svg",
  "png",
  "xbm",
  "dib",
  "jxl",
  "jpeg",
  "svgz",
  "jpg",
  "webp",
  "ico",
  "tif",
  "pjpeg",
  "avif",
];

inputFile.addEventListener("change", function () {
  //전에 값이 있는지 없는지 확인하는 방법
  //1. 내부에 객체를 다 선택하는 querrySelectorAll("CSS선택방식")   => 리턴값이 배열[]
  //배열명.length을 활용
  const containerArr = document.querySelectorAll("#advertise_container > *");

  for (let i = 0; i < containerArr.length; i++) {
    removeContainer.removeChild(containerArr[i]);
  }

  //올려진 파일 확장자 따는 곳
  let extension = inputFile.files[0].name.split(".");

  //사진 확장자 검증 구간
  const final_extension = extension.pop().toLowerCase();
  console.log(final_extension);

  let image_flag = false;
  for (let item of image_extension) {
    if (item == final_extension) {
      image_flag = true;
      break;
    }
  }

  let video_flag = false;
  for (let item of video_extension) {
    if (item == final_extension) {
      video_flag = true;
      break;
    }
  }

  if (image_flag) {
    const file = inputFile.files[0];
    const img_box = document.createElement("img");
    const newAttribute = document.createAttribute("src");
    console.log(URL.createObjectURL(file));
    newAttribute.value = URL.createObjectURL(file);
    const width = document.createAttribute("style");
    width.value = "width:500px; height:500px; margin: 15px auto";
    img_box.setAttributeNode(newAttribute);
    img_box.setAttributeNode(width);
    removeContainer.appendChild(img_box);
  }

  //동영상 확장자 검증 구간

  if (video_flag) {
    const file = inputFile.files[0];
    if (inputFile.files[0] != null) {
      removeContainer.innerHTML = `<video id="video" width="1280px"
      height="450px" controls loop>s</video>`;
      const video = document.getElementById("video");
      const videourl = URL.createObjectURL(file);
      video.setAttribute("src", videourl);
      video.pause(); // 영상 멈춤상태
      // video.play();                           // 영상 실행상태
    }
  }
});
