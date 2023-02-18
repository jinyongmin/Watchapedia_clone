function delntc(ntcIdx){
    let a= confirm('해당 공지글을 삭제하시겠습니까?')
    if(a){
        fetch('http://localhost:9090/api/notice/'+ntcIdx, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "transaction_time":`${new Date()}`,
                "resultCode":"ok",
                "description":"정상",
                "data":{
                    "ntcIdx":`${ntcIdx}`,
                }
            }),
        })
            .then((res) => {
                alert('삭제성공')
                location.href='/notice';
                return;
            })
            .catch((err) => {
                console.log('에러!!');
                location.reload();
                return;
            });
    }
    else{
        return false;
    }
}