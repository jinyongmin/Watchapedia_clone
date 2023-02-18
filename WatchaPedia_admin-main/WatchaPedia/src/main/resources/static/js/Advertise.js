function change_status(adIdx,adStatus) {
    if(adStatus=='등록'){
        let a = confirm("등록하시겠습니까?");
        if(a){
            fetch('http://localhost:9090/api/advertise/statusupdate', {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    "transaction_time":`${new Date()}`,
                    "resultCode":"ok",
                    "description":"정상",
                    "data":{
                        "adIdx":adIdx,
                        "adStatus":adStatus
                    }
                }),
            })
                .then((res) => {
                    alert('수정성공')
                    location.reload();
                    return;
                })
                .catch((err) => {
                    console.log('에러!!');
                    //location.reload();
                    return;
                });
        }else{
            return false;
        }
    }else{
        let a = confirm("등록해제하시겠습니까?")
        if(a){
            console.log(adIdx+adStatus);
            fetch('http://localhost:9090/api/advertise/statusupdate', {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    "transaction_time":`${new Date()}`,
                    "resultCode":"ok",
                    "description":"정상",
                    "data":{
                        "adIdx":`${adIdx}`,
                        "adStatus":`${adStatus}`
                    }
                }),
            })
                .then((res) => {
                    alert('수정성공')
                    location.reload();
                    return;
                })
                .catch((err) => {
                    console.log('에러!!');
                    //location.reload();
                    return;
                });
        }else{
            return false;
        }
    }
}
