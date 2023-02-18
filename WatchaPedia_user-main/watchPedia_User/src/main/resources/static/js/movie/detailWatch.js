watch.addEventListener('click', () => {
    if(document.querySelector("#login-idx")) {
    $.ajax({
        url: '/watch/save',
        headers: {'Content-Type': 'application/json;charset=UTF-8'},
        data: JSON.stringify({
            contentType: "movie",
            contentIdx: contentIdx,
            userIdx: loginIdx.title
        }),
        type: "POST",
        dataType: "json",
        success: function (result) {
            if (result == true) {
                watch.classList.remove('css-1tc9iuk-StylelessButton-ContentActionButton');
                watch.classList.add('css-15hndx7-StylelessButton-ContentActionButton')
                wish.classList.remove('css-15hndx7-StylelessButton-ContentActionButton')
                wish.classList.add('css-1tc9iuk-StylelessButton-ContentActionButton');
                wishIcon.item(1).style.display = 'none';
                wishIcon.item(0).style.display = 'block';
            } else {
                watch.classList.add('css-1tc9iuk-StylelessButton-ContentActionButton');
                watch.classList.remove('css-15hndx7-StylelessButton-ContentActionButton')
            }
        }, error: function () {
            alert("에러발생!")
        }
    });
    }else{
        loginModalOn();
    }
})