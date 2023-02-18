function blind_result() {
  const reportIdx = document.getElementById('hiddenIdx').innerText;
  const processAdmin = document.getElementById('hiddenSessionAdmin').innerText;
  const updateStatus = "블라인드";
  fetch('http://localhost:9090/api/report/update', {
    method: 'PUT',
    headers: {'content-Type': 'application/json'},
    body: JSON.stringify({
      "transaction_time":`${new Date()}`,
      "resultCode":"ok",
      "description":"정상",
      "data":{
        "reportIdx":`${reportIdx}`,
        "updateStatus" : `${updateStatus}`,
        "processAdmin" : `${processAdmin}`
      }
    })
  })
      .then(response => response.json())
      .then(data => {
        location.href = "/comment/report_page"
      }).catch(() =>{
        history.go()
      }

  )


  const spoiler_btn = document.getElementById("spoiler_btn");
  const noproblem_btn = document.getElementById("noproblem_btn");
  const blinder_btn = document.getElementById("blinder_btn");
  blinder_btn.classList.add("selected");
  spoiler_btn.classList.remove("selected");
  noproblem_btn.classList.remove("selected");
}

function spoiler_result() {
  const reportIdx = document.getElementById('hiddenIdx').innerText;
  const processAdmin = document.getElementById('hiddenSessionAdmin').innerText;
  const updateStatus = "스포일러";
  fetch('http://localhost:9090/api/report/update', {
    method: 'PUT',
    headers: {'content-Type': 'application/json'},
    body: JSON.stringify({
      "transaction_time":`${new Date()}`,
      "resultCode":"ok",
      "description":"정상",
      "data":{
        "reportIdx":`${reportIdx}`,
        "updateStatus" : `${updateStatus}`,
        "processAdmin" : `${processAdmin}`
      }
    })
  })
      .then(response => response.json())
      .then(data => {
        location.href = "/comment/report_page"
      }).catch(() =>{
        history.go()
      }

  )
}

function noproblem_result() {
  const reportIdx = document.getElementById('hiddenIdx').innerText;
  const processAdmin = document.getElementById('hiddenSessionAdmin').innerText;
  const updateStatus = "문제없음";
  fetch('http://localhost:9090/api/report/update', {
    method: 'PUT',
    headers: {'content-Type': 'application/json'},
    body: JSON.stringify({
      "transaction_time":`${new Date()}`,
      "resultCode":"ok",
      "description":"정상",
      "data":{
        "reportIdx":`${reportIdx}`,
        "updateStatus" : `${updateStatus}`,
        "processAdmin" : `${processAdmin}`
      }
    })
  })
      .then(response => response.json())
      .then(data => {
        location.href = "/comment/report_page"
      }).catch(() =>{
    location.href = "/comment/report_page"
      }

  )
}
